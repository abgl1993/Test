package com.timesinternet.alive.search.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.timesinternet.alive.utils.CacheBuilder;
import com.timesinternet.alive.utils.WordBreak;

/**
 * @author Atul.Baghel
 * 
 *         This class removes unnecessary keywords from the input keywords and
 *         tries to make keywords more searchable
 */
public class Filter {

	String[] unnecessaryKeywords = { "and", "or", "to", "our", "a", "an", "the", "all", "about", "above", "across",
			"after", "against", "along", "among", "around", "as", "at", "before", "behind", "below", "beside",
			"besides", "between", "but", "by", "down", "for", "from", "in", "inside", "into", "like", "minus", "near",
			"of", "off", "on", "onto", "opposite", "over", "past", "per", "plus", "save", "than", "through", "to",
			"toward", "towards", "under", "up", "upon", "via", "with", "within", "wearing" };

	String[] wordsEndingWithS = { "shoes", "shorts", "glass", "lens", "dress" };

	private Set<String> unnecessaryKeywordsSet;

	private Set<String> wordsEndingWithSSet;

	Map<String, String> gender = new HashMap<String, String>();

	CacheBuilder cache = new CacheBuilder();

	String genderValue = "";

	Set<String> colorHashSet;

	Set<String> brandHashSet;

	List<String> keywordsList = new ArrayList<String>();

	static String[] keywordsTemp;

	WordBreak wordBreak;

	public Filter() {
		unnecessaryKeywordsSet = new HashSet<String>();
		wordsEndingWithSSet = new HashSet<String>();
		for (int i = 0; i < unnecessaryKeywords.length; i++) {
			unnecessaryKeywordsSet.add(unnecessaryKeywords[i]);
		}

		for (int i = 0; i < wordsEndingWithS.length; i++) {
			wordsEndingWithSSet.add(wordsEndingWithS[i]);
		}

		gender.put("mens", "men");
		gender.put("man", "men");
		gender.put("men's", "men");
		gender.put("men", "men");
		gender.put("boy", "men");
		gender.put("boy's", "men");
		gender.put("boys", "men");
		gender.put("women", "women");
		gender.put("woman", "woman");
		gender.put("women's", "women");
		gender.put("womens", "women");
		gender.put("girl", "women");
		gender.put("girls", "women");
		gender.put("girl's", "women");
		gender.put("kids", "kid");
		gender.put("baby", "kid");
	}

	// removes unnecessary words
	public String[] removeUnnecessaryKeywords(String[] keywords) {

		for (int i = 0; i < keywords.length; i++) {

			if (unnecessaryKeywordsSet.contains(keywords[i])) {
				keywords[i] = "";
			}

		}
		return keywords;

	}

	private String removeS(String string) {
		String tempString = "";

		if (!wordsEndingWithSSet.contains(string)) {

			if (string != null && string.length() >= 2 && string.substring(string.length() - 1).equals("s")) {
				tempString = string.substring(0, string.length() - 1);
			}
		}

		return tempString;

	}

	private String removeES(String string) {
		String tempString = "";

		if (!wordsEndingWithSSet.contains(string)) {
			if (string != null && string.length() >= 2 && string.substring(string.length() - 2).equals("es")) {
				tempString = string.substring(0, string.length() - 2);
			}
		}
		return tempString;

	}

	public List<String> getGenderList(String queryString) {
		queryString = queryString.trim();
		String[] keywords = queryString.split(" ");

		List<String> genderList = new ArrayList<String>();
		for (int i = 0; i < keywords.length; i++) {
			if (gender.containsKey(keywords[i])) {
				String genderValue = gender.get(keywords[i]);
				genderList.add(genderValue);

			}
		}
		return genderList;
	}

	// Removes gender keywords form the keywords array
	public void removeGenderKeywords(String keywords[]) {
		for (int i = 0; i < keywords.length; i++) {
			if (gender.containsKey(keywords[i])) {
				// genderValue = gender.get(keywords[i]);
				keywords[i] = "";
			}
		}

	}

	// removes colors and brands from the keywords array
	@SuppressWarnings("unchecked")
	public void removeBrandsAndColorsKeywords(String[] keywords) {

		colorHashSet = (Set<String>) cache.getCache("colors");
		brandHashSet = (Set<String>) cache.getCache("brands");
		for (int i = 0; i < keywords.length; i++) {
			if (colorHashSet.contains(keywords[i]) || brandHashSet.contains(keywords[i])) {
				keywords[i] = "";
			}
		}

	}

	public void removeColor(String[] keywords) {
		colorHashSet = (Set<String>) cache.getCache("colors");
		for (int i = 0; i < keywords.length; i++) {
			if (colorHashSet.contains(keywords[i])) {
				keywords[i] = "";
			}
		}
	}

	// Gives the final search String that will be searched against title,
	// description and other fields in solr
	public String getSearchString(String queryString) {
		if (queryString.indexOf("t shirt") > 0) {
			queryString.replaceAll("t shirt", "t-shirt");
		}
		return queryString;
	}

	// Gives Keywords that will be help to get categories in which search
	// is to be done
	public String[] getKeywordsToFindProductAndCategory(String[] keywords) {
		keywords = removeUnnecessaryKeywords(keywords);

		removeGenderKeywords(keywords);

		removeBrandsAndColorsKeywords(keywords);

		wordBreak = new WordBreak();
		ArrayList<String> keywordsTemp = new ArrayList<String>();
		for (int i = 0; i < keywords.length; i++) {
			if (keywords[i] != "") {
				List<String> wordsList = wordBreak.breakWords(keywords[i]);
				if (wordsList != null && wordsList.size() > 0) {
					Iterator<String> iterator = wordsList.iterator();
					while (iterator.hasNext()) {
						keywordsTemp.add((String) iterator.next());
					}
				}
				keywordsTemp.add(keywords[i]);
			}
		}
		keywords = keywordsTemp.toArray(new String[keywordsTemp.size()]);
		return keywords;
	}
   //Add singular and pluaral words to the keywords array
	public String[] addSingularOrPluarWords(String[] keywords) {

		for (int i = 0; i < keywords.length; i++) {
			String result = keywords[i].replaceAll("[\\+\\.\\^:,'/(]", "");
			keywords[i] = result;
			keywordsList.add(keywords[i]);

			String word1 = removeS(keywords[i]);
			String word2 = removeES(keywords[i]);
			if (!word1.equals("")) {
				if (keywordsList.contains(word1) != true) {
					keywordsList.add(word1);
				}

			}

			if (!word2.equals("")) {
				if (keywordsList.contains(word2) != true) {
					keywordsList.add(word2);
				}
			}
		}
		String[] kewordsTemp = keywordsList.toArray(new String[keywordsList.size()]);
		return kewordsTemp;

	}

}
