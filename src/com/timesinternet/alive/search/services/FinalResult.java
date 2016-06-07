package com.timesinternet.alive.search.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.timesinternet.alive.search.beans.Result;

public class FinalResult {

	public String getData(String searchUrl) {

		final String CONNECT_TIMEOUT = "5000";
		final String READ_TIMEOUT = "20000";
		System.setProperty("sun.net.client.defaultConnectTimeout", CONNECT_TIMEOUT);
		System.setProperty("sun.net.client.defaultReadTimeout", READ_TIMEOUT);
		String inputLine = "";
		StringBuffer il = new StringBuffer();
		try {
			URL url=new URL(searchUrl);
			 URLConnection urlc=url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream(),"UTF-8"));
			
			while ((inputLine = in.readLine()) != null)
				il.append(inputLine);
			in.close();
			System.out.println(new java.util.Date() + "##SolrSearch URL::" + searchUrl);
			System.out.println(new java.util.Date() + "##SolrSearch Result::" + il.length());

		} catch (Exception e) {
			System.out.println("SolrSearch in GetData " + e);
		}
		return il.toString().trim();
	}

	public List<Result> displayResults(String result, HttpServletResponse response) {
		
		PrintWriter out=null;
		
		JSONParser parser=new JSONParser();
		List<Result>resultList=new ArrayList<Result>();
		
		try {
			Object obj=parser.parse(result);
			
			JSONObject jsonObject=(JSONObject)obj;
			
			JSONObject responseObjedct=(JSONObject)jsonObject.get("response");
			JSONArray jsonArray =(JSONArray)responseObjedct.get("docs");
			Iterator iterator=jsonArray.iterator();
			
			
			while(iterator.hasNext()){
				JSONObject resultObj=(JSONObject) iterator.next();
			   // System.out.println("resultObj "+resultObj);
				
				Result resultObject=new Result();
				resultObject.setLink((String)resultObj.get("link"));
				resultObject.setImageLink((String)resultObj.get("image_link"));
				resultObject.setTitle((String)resultObj.get("title"));
				resultObject.setSource((String)resultObj.get("source"));
			    resultObject.setMaster_category1((String)resultObj.get("master_category1"));
			    
			    //System.out.println("master_category1 "+(String)resultObj.get("master_category1"));
			    
			    resultObject.setMaster_category2((String)resultObj.get("master_category2"));
				resultList.add(resultObject);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return resultList;
		
	}

}
