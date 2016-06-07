package com.timesinternet.alive.search.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author Atul.Baghel
 * 
 *         This class creates an email and attachment file of all those queries
 *         for which category was not found and sends it to an intended user.
 *
 */
public class EmailService {

	FileWriter fw = null;
	BufferedWriter bw = null;
	PrintWriter out = null;
	String queries="";

	public void sendEmail(List<String> queriesList) {
		System.out.println("inside sendemail");
		String to = "atul.baghel@timesinternet.in";
		String from = "atul.baghel@timesinternet.in";
		String host = "smtp.timesinternet.in";
		Properties props = new Properties();

		props.put("mail.smtp.host", "outlook.office365.com");
		props.put("mail.smtp.port", "587");
		// props.put("mail.debug", "true");
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.EnableSSL.enable", "true");

		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);
		Transport transport = null;
		if (session != null) {
			try {
				transport = session.getTransport();
			} catch (NoSuchProviderException e) {

				e.printStackTrace();
			}
		}

		try {
			MimeMessage msg = new MimeMessage(session);

			// Set message attributes
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject("Queries for which category was not found");
			msg.setSentDate(new Date());
			BodyPart messageBodyPart = new MimeBodyPart();
			Iterator<String> iterator = queriesList.iterator();
			int i=1;
			while(iterator.hasNext()){	
				queries=queries+i+". "+iterator.next()+"\n";
				i++;
			}
			messageBodyPart.setText(queries);
			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			msg.setContent(multipart);

			// Send the message
			if (transport != null) {
				transport.send(msg);
			}

		} catch (MessagingException mex) {
			// Prints all nested (chained) exceptions as well
			mex.printStackTrace();
		}
	}

}

final class SMTPAuthenticator extends javax.mail.Authenticator {
	public PasswordAuthentication getPasswordAuthentication() {
		String username = "atul.baghel@timesinternet.in";
		String password = "india@123";
		return new PasswordAuthentication(username, password);
	}
}
