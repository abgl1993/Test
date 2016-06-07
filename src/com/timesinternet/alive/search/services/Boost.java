package com.timesinternet.alive.search.services;

import java.util.Set;

import com.timesinternet.alive.utils.CacheBuilder;

public class Boost {
	Set<String> productboostKeyWords;
	Set<String> brandsboostKeyWords;
	Set<String> accessoriesboostKeyWords;
	Set<String> genderboostKeyWords;
	Set<String> colorboostKeyWords;
	int genderBoostVal, productBoostVal, brandBoostVal, accessoriesBoostVal,colorBoostVal;
    CacheBuilder cacheBuilder;
    Boolean electronickeyword,genderKeyword;
	public String boostQueryString(String queryString, String[] queryWords) {

		cacheBuilder=new CacheBuilder();
		productboostKeyWords = (Set<String>) cacheBuilder.getCache("productBoostKeywords");

		genderboostKeyWords = (Set<String>) cacheBuilder.getCache("genderBoostKeywords");

		brandsboostKeyWords = (Set<String>) cacheBuilder.getCache("brands");

		accessoriesboostKeyWords = (Set<String>) cacheBuilder.getCache("accessoriesBoostKeywords");
		
		colorboostKeyWords = (Set<String>) cacheBuilder.getCache("colors");

		genderBoostVal = 4;
		brandBoostVal = 2;
		productBoostVal = 3;
		accessoriesBoostVal = 4;
		colorBoostVal=3;
		
		electronickeyword=false;
		
		genderKeyword=false;
		
        String electronics[]={"phone","mobile","laptop","tv","plasma","led","lcd","television","mixer","juicer","blender"
		         ,"stereo","microwave","toaster","grinder","oven","fan","ac","air conditioner","heater","blower"
		         ,"vaccum cleaner","ipad","ipod","tab","tablet","ear phones","speaker","dvd player","cd plater"
		         ,"candle bulb","led bulb","computer","desktop","av player","xbox","ps2","pen drive","microphones"
		         ,"tv remote","cordless phone","keyboard","plasma tv","led tv","lcd tv","radio","musical keyboard","earphones","mp3 player"
		         ,"led light","printer","laser printer","inkjet printer","thermal printer","data card","webcam","camera","headphone"
		         ,"playstation","air cooler","washing machine","refrigerator","water purifier","sandwich maker","coffee maker","tea maker"
		         ,"induction cooktop","scanner","laptop","batteries","adapters","pen drive","memory card","setpup box","computer","computer monitor"
		         ,"home appliances","digital camera"};
        
        
        for(int i=0;i<electronics.length;i++){
        	if(queryString.indexOf(electronics[i])!=-1){
        		electronickeyword=true;
        		break;
        	}
        	
        }
        
       
        
        
		for (int i = 0; i < queryWords.length; i++) {
			
			String queryWord = queryWords[i].trim();
				
			if (accessoriesboostKeyWords.contains(queryWord)) {

				if((queryWord.indexOf("^")==-1)){
					
					queryWords[i] = queryWords[i].replaceAll(queryWords[i], queryWords[i] + "^" + accessoriesBoostVal);
				}

			} else if (productboostKeyWords.contains(queryWord)) {
				if((queryWord.indexOf("^")==-1)){
					
					queryWords[i] = queryWords[i].replaceAll(queryWords[i], queryWords[i] + "^" + productBoostVal);
				}

			} else if (brandsboostKeyWords.contains(queryWord)) {
				if((queryWord.indexOf("^")==-1)){
					queryWords[i] = queryWords[i].replaceAll(queryWords[i], queryWords[i] + "^" + brandBoostVal);
			   }

			} else if (genderboostKeyWords.contains(queryWord)) {
				if((queryWord.indexOf("^")==-1)){
					queryWords[i] = queryWords[i].replaceAll(queryWords[i], queryWords[i] + "^" + genderBoostVal);
				}

			}else if(!electronickeyword&&colorboostKeyWords.contains(queryWord)){
				if((queryWord.indexOf("^")==-1)){
					queryWords[i] = queryWords[i].replaceAll(queryWords[i], queryWords[i] + "^" + colorBoostVal);
				}
			}
			
		}
		String temp="";
		for(int i=0;i<queryWords.length;i++){
			temp+=queryWords[i]+" "; 
		}
		return temp;

	}

}
