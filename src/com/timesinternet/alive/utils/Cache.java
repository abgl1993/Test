package com.timesinternet.alive.utils;

import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class Cache {

private ConcurrentMap<String, String > cacheMap;

private ConcurrentLinkedQueue<String> queue;

public Cache() {

	cacheMap = new ConcurrentHashMap<String, String >();
	queue = new ConcurrentLinkedQueue<String>();
}

public void put(String key, String  value) {
		queue.add(key);
		cacheMap.put(key, value);
}

public String get(String key) {

	String value = null;
	if(queue.contains(key)){
		value=cacheMap.get(key);
	}
	return value;
}

public void displayCache(){
	System.out.println("size: "+cacheMap.size());
	Set<Entry<String,String>> entrySet= cacheMap.entrySet();
	Iterator iterator=entrySet.iterator();
	while(iterator.hasNext()){
		Entry<String,String>entry=(Entry<String, String>) iterator.next();
		String key=entry.getKey();
		System.out.println("key: "+key);
	}
    System.out.println(cacheMap);
}

public void clearCache() {
	cacheMap.clear();
	queue.clear();
}

public int size() {
   return queue.size();
	
}
}