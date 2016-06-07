package com.timesinternet.alive.search.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Atul.Baghel
 *
 */
public class DataBase {

	private String url="jdbc:mysql://119.9.119.48:3306/alive";
	
    private Connection con=null;
    
    public Connection getConnection(String userName,String password){
    	try{
    	Class.forName("com.mysql.jdbc.Driver");
    	con=DriverManager.getConnection(url,userName,password);
    	System.out.println("connected to database");
    	}catch(Exception e){
    		System.out.println("Could not connect to sql database "+ e);
    	}
    	return con;
    }
    
    
	
}
