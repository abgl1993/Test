package com.timesinternet.alive.search.services;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Atul.Baghel
 *
 *This class saves all those queries for which any category was not found and creates a list of all those queries
 *and sends an email with the queryList attached when queryList size exceeds 100 or timer expires
 */
public class QueryTracker {
	EmailService emailService;
    private static List <String>searchQueriesList=new ArrayList<String>();;

    public static List<String> getSearchQueriesList() {
		return searchQueriesList;
	}  
	public void saveQuery(String queryString){
		emailService=new EmailService();
		System.out.println("Category was not found for this search query");	
		searchQueriesList.add(queryString);
		if(searchQueriesList.size()>100){
			emailService.sendEmail(searchQueriesList);
			searchQueriesList.clear();
		
		}
	}
}

