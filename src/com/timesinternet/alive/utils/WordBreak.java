package com.timesinternet.alive.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Atul.Baghel
 *
 */
public class WordBreak {

	String[] dictionary = { "lunch", "bag", "bean", "box", "phone", "mobile", "cover","case", "office", "shoe", "rack", "desk", "2",
			"4", "8", "16", "32", "64", "128", "256", "512", "1","gb", "mb", "tb", "pen", "drive","mi","4i","6s","washing","machine",
			"10000","10400","13000","10400","10050","15600","2600","mah"};

	public static List<String> wordsList;

	public List<String> breakWords(String string) {
		wordsList = new ArrayList<String>();
        String originalString=string;
		if (breakWordsUtils(string, wordsList,originalString)) {
			return wordsList;
		}

		return null;
	}

	private boolean dictionaryContains(String word, List<String> wordsList,String originalString) {
		for (int i = 0; i < dictionary.length; i++) {
			if (dictionary[i].equals(word) == true&&dictionary[i].equals(originalString)==false) {
                 if(wordsList.contains(dictionary[i])==false){
                	 wordsList.add(dictionary[i]);	 
                 }				
				return true;
			}
		}
		return false;
	}

	private boolean breakWordsUtils(String string, List<String> wordsList,String originalString) {

		int size = string.length();
		if (size == 0) {
			return true;
		}
		for (int i = 0; i <= size; i++) {

			if (dictionaryContains(string.substring(0, i), wordsList,originalString)
					&& breakWordsUtils(string.substring(i, size), wordsList,originalString)) {

				return true;
			}
		}
		return false;
	}
     public List<String>getBrokenwords(){
    	 return wordsList;
     }
}
