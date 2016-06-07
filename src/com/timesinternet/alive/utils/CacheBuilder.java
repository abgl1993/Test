package com.timesinternet.alive.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.timesinternet.alive.search.database.DataBaseServices;

/**
 * @author Atul.Baghel
 *
 */
public class CacheBuilder {
	private static Set<String> colorHashSet;
	private static Set<String> brandHashSet;
	private static Map<String,String>spaceSeparatedKeywords;
	private static Set<String> productboostKeyWords;
	private static Set<String> accessoriesboostKeyWords;
	private static Set<String> genderboostKeyWords;
    private static Cache cache;
    private static LRUCache lrucache;
    DataBaseServices dataBaseService;
	File file=null;
	Scanner scanner = null;

	public void buildCache() {
		colorHashSet = new HashSet<String>();
		brandHashSet = new HashSet<String>();
		spaceSeparatedKeywords=new HashMap<String,String>();
		productboostKeyWords=new HashSet<String>();
		accessoriesboostKeyWords=new HashSet<String>();
		genderboostKeyWords=new HashSet<String>();
		cache=new Cache();
		lrucache=new LRUCache(50);
		ClassLoader classLoader = getClass().getClassLoader();
		
	    file = new File(classLoader.getResource("colors.properties").getFile());
	    buildCacheUtils(file, "colors");
	    file = new File(classLoader.getResource("brands.properties").getFile());
	    buildCacheUtils(file, "brands");
		
        
        file=new File(classLoader.getResource("accessoriesBoostKeywords.properties").getFile());
        buildCacheUtils(file,"accessoriesBoostKeywords");
        
        file=new File(classLoader.getResource("genderBoostKeywords.properties").getFile());
        buildCacheUtils(file,"genderBoostKeywords");
        
        file=new File(classLoader.getResource("productBoostKeywords.properties").getFile());
        buildCacheUtils(file,"productBoostKeywords");
        
        buildIntitialLRUCache(cache);
        
        spaceSeparatedKeywords.put("t shirt", "t-shirt");
        spaceSeparatedKeywords.put("bean bag","beanbag");
        spaceSeparatedKeywords.put("tshirt", "t-shirt");
        spaceSeparatedKeywords.put("washingmachine","washing machine");
	}

	private void buildIntitialLRUCache(Cache cache) {
		
		dataBaseService=new DataBaseServices();
		dataBaseService.buildInitialCache(cache);
		//cache.displayCache();
	}

	private void buildCacheUtils(File file, String cacheType) {
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {

		}

		if (cacheType.equals("colors")) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line!=null){
					line=line.trim();
				}
				if(!line.equals("")){
					colorHashSet.add(line);
				}
				
			}

		}
           else if (cacheType.equals("brands")) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line!=null){
					line=line.trim();
				}
				if(!line.equals("")){
					brandHashSet.add(line);
				}
				
			}
		}
		else if (cacheType.equals("accessoriesBoostKeywords")) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line!=null){
					line=line.trim();
				}
				if(!line.equals("")){
					accessoriesboostKeyWords.add(line);
				}
				
			}
		}
		else if (cacheType.equals("genderBoostKeywords")) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line!=null){
					line=line.trim();
				}
				if(!line.equals("")){
					genderboostKeyWords.add(line);
				}
				
			}
		}
		else if (cacheType.equals("productBoostKeywords")) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line!=null){
					line=line.trim();
				}
				if(!line.equals("")){
					productboostKeyWords.add(line);
				}
				
			}
		}
		
		

	}
	public Object getCache(String cacheType){

		if(cacheType.equals("colors")){
			return colorHashSet;		
		}
		else if(cacheType.equals("brands")){
			return  brandHashSet;
		}
		else if(cacheType.equals("productBoostKeywords")){
			return productboostKeyWords;
			
		}
		else if(cacheType.equals("genderBoostKeywords")){
			return genderboostKeyWords;
			
		}
		
		else if(cacheType.equals("accessoriesBoostKeywords")){
			
			return accessoriesboostKeyWords;
			
		}
		else if(cacheType.equals("spaceSeparatedKeywords")){
			return spaceSeparatedKeywords;
			
		}else if(cacheType.equals("cache")){
			return cache;
		}else if(cacheType.equals("lrucache")){
			return lrucache;
		}
		else{
			return null;
		}
	}
    
	public void clearCache() {
		colorHashSet.clear();
		brandHashSet.clear();
		accessoriesboostKeyWords.clear();
		productboostKeyWords.clear();
		genderboostKeyWords.clear();
		cache.clearCache();
		lrucache.clearCache();
	}
}
