package com.timesinternet.alive.search.services;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.timesinternet.alive.search.database.DataBaseServices;
import com.timesinternet.alive.utils.CacheBuilder;
import com.timesinternet.alive.utils.WordBreak;

public class SearchHelper {

	String query = "http://119.9.79.166:8983/solr/shop/select?q=";
	String queryPart = "&df=title&rows=100&start=0&wt=json&fl=cp_id,title,link,source,image_link,mrp,offer_price,discount,master_category1,master_category2";
	List<String> master_category1;
	List<String> master_category2;
	String fq_master_category2 = "";
	String fq_master_category1 = "";
	String fq = "";
	DataBaseServices databaseService;
	WordBreak wordBreak;
	Iterator<String> iterator;
	Iterator<Entry<String, String>> iterator1;
	CacheBuilder cacheBuilder;
	QueryTracker queryTracker;
	Filter filter;
	String gender = "";
	String[] categoryArray;
	static String[] keywordsTemp;
	Map<String, String> hashMap;
	Boost boost;
	static int boostVal = 1;
	static boolean flag;// Flag is for determining whether queryString could be
						// boosted or not

	public String findSearchCategoryString(String searchText) {

		fq = findCategoryManually(searchText);

		if (fq.equals("")) {
			fq = findfilterQueryBasedOnProductAccessories(searchText);
		}
		if (fq.equals("")) {

			fq = findSearchCategory(searchText);
		}

		if (fq.equals("")) {
			fq = fq
					+ "-((master_category:(\"books%20%26%20entertainment\"))%20AND%20(category_name:(\"book\"%20OR%20\"books\"%20OR%20\"DVD\"%20OR%20\"music\")))";
		} else {
			fq = fq
					+ "&fq=-((master_category:(\"books%20%26%20entertainment\"))%20AND%20(category_name:(\"book\"%20OR%20\"books\"%20OR%20\"DVD\"%20OR%20\"music\")))";
		}
		fq.replaceAll(" ", "%20");
		return fq;
	}

	private String findCategoryManually(String searchText) {
		if (searchText.indexOf("office phone") != -1 || searchText.indexOf("telephone") != -1
				|| searchText.indexOf("desk phone") != -1 || searchText.indexOf("home phone") != -1) {

			fq = "sub_category_name:(\"landline%20phones\"%20OR%20\"Electronics/Categories/Telephones%20%26%20Accessories/Landline%20Phones\"%20OR%20\"Corded%20Phones\"%20OR%20\"Cordless%20Phones\")";
			
		} else if (searchText.indexOf("computer monitor") != -1) {

			fq = "sub_category_name:(\"Computers%20%26%20Accessories/Categories/Monitors\")";

			
		} else if (searchText.indexOf("laptop computer") != -1 || searchText.indexOf("computer laptop") != -1) {

			fq = "category_name:("
					+ URLEncoder.encode("computers OR computer & peripherals OR personal computers OR meta category")
					+ ")&fq=sub_category_name:laptops";
			
		} else if (searchText.indexOf("flat screen tv") != -1 || searchText.indexOf("television") != -1
				|| searchText.indexOf("screen tv") != -1 || searchText.indexOf("tv screen") != -1
				|| searchText.indexOf("flat tv") != -1 || searchText.indexOf("tv led") != -1
				|| searchText.indexOf("led tv") != -1 || searchText.indexOf("tv lcd") != -1) {
			fq = "category_name:(" + URLEncoder.encode("meta category OR tvs") + ")&fq=sub_category_name:("
					+ URLEncoder.encode("Televisions OR led OR lcd OR plasma") + ")";
			
		} else if (searchText.indexOf("macbook") != -1) {
			fq = "master_category1:\"laptops\"";
			
		} else if (searchText.indexOf("refrigerator") != -1 || searchText.indexOf("fridge") != -1) {
			fq = "sub_category_name:(\"large%20appliances\"%20OR%20refrigerator%20OR%20refrigerators%20OR%20frost)";
			
		} else if (searchText.indexOf("study table") != -1) {
			fq = "sub_category_name:(tables%20OR%20furniture)";
			
		} else if (searchText.indexOf("study table") != -1 || searchText.indexOf("chair") != -1) {
			fq = "sub_category_name:(chairs%20OR%20furniture)";
			
		} else if (searchText.indexOf("beanbag") != -1 || searchText.indexOf("bean bag") != -1) {
			fq = "master_category2:(\"bean%20bags\"%20OR%20\"bean%20bag%20covers\")";
			
		} else if (searchText.indexOf("hairband") != -1 || searchText.indexOf("hair band") != -1) {
			fq = "master_category1:(\"men%20accessories\"%20OR%20\"women%20accessories\"%20OR%20\"general%20accessories\")";
			
		} else if (searchText.indexOf("bath towel") != -1 || searchText.indexOf("hair towel") != -1
				|| searchText.indexOf("towel cap") != -1) {
			fq = "master_category2:(\"towels\")";
			
		} else if (searchText.indexOf("shower cap") != -1 || searchText.indexOf("bath cap") != -1
				|| searchText.indexOf("bathing cap") != -1) {
			fq = "sub_category_name:(bath%20OR%20bathing)";
			
		} else if (searchText.indexOf("smart watch") != -1 || searchText.indexOf("smartwatch") != -1) {
			fq = "sub_category_name:(\"Bluetooth%20Devices\"%20OR%20\"Smart%20Watches\"%20OR%20\"Smartwatches\")";
			
		} else if (searchText.indexOf("washing machine") != -1 && searchText.indexOf("cover") == -1) {
			fq = "master_category2:(\"washing%20machines%20%26%20dryers\")";
			
		} else if (searchText.indexOf("wall paint") != -1) {
			fq = "master_category2:(\"paints%20and%20paint%20tools\")";
			
		} else if (searchText.indexOf("lamp ") != -1 || searchText.indexOf("lamps") != -1) {
			fq = "master_category1:(\"lights%20%26%20lamps\")";
			
		} else if (searchText.indexOf("white cup") != -1 || searchText.indexOf(" mug ") != -1
				|| searchText.indexOf("ceramic mug") != -1 || searchText.indexOf("ceramic cup") != -1) {
			fq = "master_category1:(\"kitchenware\")";
			
		} else if (searchText.indexOf("microwave") != -1) {
			fq = "((master_category2:(\"microwave%20ovens%20%26%20otgs\"))%20OR%20(source:amazon%20AND%20category_name:\"Kitchen\"%20AND%20(sub_category_name:\"Home%20%26%20Kitchen/Categories/Kitchen%20%26%20Home%20Appliances/Small%20Kitchen%20Appliances/Microwave%20Ovens\")))";
			
		}
        
		return fq;
	}

	private String findfilterQueryBasedOnProductAccessories(String searchText) {
		String[] words = (String[]) searchText.split(" ");
		cacheBuilder = new CacheBuilder();
		Set<String> product = (Set<String>) cacheBuilder.getCache("productBoostKeywords");
		Set<String> accessories = (Set<String>) cacheBuilder.getCache("accessoriesBoostKeywords");
		String productName = "";
		String accessoryName = "";
		for (int i = 0; i < words.length; i++) {
			if (product.contains(words[i])) {
				productName += words[i];
				break;
			}
		}
		for (int i = 0; i < words.length; i++) {
			if (accessories.contains(words[i])) {
				accessoryName += words[i];
			}
		}
		String tempQuery = "";
		if (!productName.equals("") && !accessoryName.equals("")) {
			tempQuery = productName + "-" + accessoryName;
		}

		if (!tempQuery.equals("")) {
			fq = findSearchCategory(tempQuery);
		}
		return fq;
	}

	private String findSearchCategory(String queryString) {
		filter = new Filter();
		queryTracker = new QueryTracker();
		List<String> genderList = filter.getGenderList(queryString);
		String temp = fixSpaceSepartedKeywords(queryString);
		String[] keywordsTemp = temp.split(" ");
		databaseService = new DataBaseServices();
		keywordsTemp = filter.addSingularOrPluarWords(keywordsTemp);

		List<List<String>> cateogryList = databaseService.getData(keywordsTemp);

		if (cateogryList != null && cateogryList.isEmpty() == false) {
			master_category1 = cateogryList.get(0);
			master_category2 = cateogryList.get(1);

			/*
			 * if (master_category1 == null && master_category2 == null ||
			 * master_category1.isEmpty() && master_category2.isEmpty()) {
			 * queryTracker.saveQuery(queryString); }
			 */

			if (master_category2 != null && master_category2.isEmpty() == false) {
				while (master_category2.isEmpty() == false) {
					String categoryString = master_category2.remove(0);
					// System.out.println("categoryString_master_category2 "+categoryString);
					if (categoryString != null) {
						if (categoryString.indexOf(",") != -1) {
							categoryArray = categoryString.split(",");
						} else {
							categoryArray = new String[1];
							categoryString = categoryString.replaceAll(" ", "%20");
							categoryString = categoryString.replaceAll("&", "%26");
							categoryArray[0] = categoryString;
						}
					}
					String[] cateogryArraytTemp = removeIrrelevantcategories(categoryArray, genderList);
					for (int i = 0; i < cateogryArraytTemp.length; i++) {
						cateogryArraytTemp[i] = cateogryArraytTemp[i].trim().replaceAll(" ", "%20");
						cateogryArraytTemp[i] = cateogryArraytTemp[i].trim().replaceAll("&", "%26");
						if (fq_master_category2.equals("") && !cateogryArraytTemp[i].equals("")) {

							fq_master_category2 += "\"" + cateogryArraytTemp[i].trim() + "\"";
						} else if (!cateogryArraytTemp[i].equals("")) {

							fq_master_category2 += "%20OR%20" + "\"" + cateogryArraytTemp[i].trim() + "\"";
						}
					}
				}
			}
			if (master_category1 != null && master_category1.isEmpty() == false) {
				while (master_category1.isEmpty() == false) {
					String categoryString = master_category1.remove(0);
					if (categoryString != null) {
						if (categoryString.indexOf(",") != -1) {
							categoryArray = categoryString.split(",");
						} else {
							categoryArray = new String[1];
							categoryString = categoryString.replaceAll(" ", "%20");
							categoryString = categoryString.replaceAll("&", "%26");
							categoryArray[0] = categoryString;
						}
					}

					String[] cateogryArraytTemp = removeIrrelevantcategories(categoryArray, genderList);

					for (int i = 0; i < cateogryArraytTemp.length; i++) {

						cateogryArraytTemp[i] = cateogryArraytTemp[i].trim().replaceAll(" ", "%20");
						cateogryArraytTemp[i] = cateogryArraytTemp[i].trim().replaceAll("&", "%26");
						if (fq_master_category1.equals("") && !cateogryArraytTemp[i].equals("")) {

							fq_master_category1 += "\"" + cateogryArraytTemp[i].trim() + "\"";
						} else if (!cateogryArraytTemp[i].equals("")) {

							fq_master_category1 += "%20OR%20" + "\"" + cateogryArraytTemp[i].trim() + "\"";
						}
					}
				}
			}
		}

		
		if (!fq_master_category2.equals("")) {
			fq += "(";
			fq = fq + "master_category2:" + "(" + fq_master_category2.trim() + ")";
		}
		if (!fq_master_category1.equals("")) {
			if (!fq_master_category2.equals("")) {
				fq = fq + "%20OR%20" + "master_category1:" + "(" + fq_master_category1.trim() + ")";
			} else {
				fq += "(";
				fq = fq + "master_category1:" + "(" + fq_master_category1.trim() + ")";
			}
			fq += ")";
		} else if (!fq_master_category2.equals("")) {
			fq += ")";
		}
		fq = fq.replaceAll("&", "%26");

		return fq;
	}

	private String[] removeIrrelevantcategories(String[] categoryArray, List<String> genderList) {

		Iterator<String> iterator = genderList.iterator();
		while (iterator.hasNext()) {
			String gender = iterator.next();
			// If there is men keyword than no need to search into women and
			// kids category
			if (gender.equals("men")) {
				for (int i = 0; i < categoryArray.length; i++) {
					if (categoryArray[i].indexOf("women") != -1 || categoryArray[i].indexOf("Women") != -1) {
						categoryArray[i] = "";

					} else if (categoryArray[i].indexOf("kids") != -1 || categoryArray[i].indexOf("Kids") != -1) {
						categoryArray[i] = "";
					}
				}
			}
			// If there is women keyword than no need to search into women and
			// men category
			else if (gender.equals("women")) {
				for (int i = 0; i < categoryArray.length; i++) {
					if (categoryArray[i].indexOf("women") == -1) {

						if (categoryArray[i].indexOf("men") != -1 || categoryArray[i].indexOf("Men") != -1) {
							categoryArray[i] = "";

						} else if (categoryArray[i].indexOf("kids") != -1 || categoryArray[i].indexOf("Kids") != -1) {
							categoryArray[i] = "";
						}
					}
				}
			}
			// If there is kids keyword than no need to search into women and
			// men category
			else if (gender.equals("kids")) {
				for (int i = 0; i < categoryArray.length; i++) {
					if (categoryArray[i].indexOf("women") != -1 || categoryArray[i].indexOf("Women") != -1) {
						categoryArray[i] = "";
					} else if (categoryArray[i].indexOf("men") != -1 || categoryArray[i].indexOf("men") != -1) {
						categoryArray[i] = "";
					}
				}
			}
		}
		return categoryArray;

	}

	public String getFinalSearchUrl(String fq, String queryString) {
		filter = new Filter();
		String queryTemp = "";
		String[] keywords = queryString.split(" ");
		String[] words = null;
		keywords = filter.removeUnnecessaryKeywords(keywords);
		words = filter.addSingularOrPluarWords(keywords);
		wordBreak = new WordBreak();
		for (int i = 0; i < words.length; i++) {
			queryTemp += words[i] + " ";
		}
		
		wordBreak.breakWords(queryTemp.trim());
		List<String> tempWords = wordBreak.getBrokenwords();
		if (tempWords != null && tempWords.size() > 0) {
			
			queryTemp += " ";
			iterator = tempWords.iterator();
			while (iterator.hasNext()) {
				queryTemp += (String) iterator.next() + " ";
			}
		}

		flag = false;
		queryTemp = fixSpaceSepartedKeywords(queryTemp);
		boost = new Boost();
		keywordsTemp = queryTemp.split(" ");
		queryTemp = boost.boostQueryString(queryTemp, keywordsTemp);
		// queryTemp=filter.fixGenderKeywords()
		
		// queryTemp = boostString(queryTemp);
		queryTemp = queryTemp.trim();

		// If Category was not found for the search query and keywords can not
		// be boosted than remove color and gender so that probability
		// of matching the product increases
		if (fq != null && fq.isEmpty() && flag == false) {
			keywordsTemp = queryTemp.split(" ");
			filter.removeGenderKeywords(keywordsTemp);
			filter.removeColor(keywordsTemp);

			String temp = "";
			for (int i = 0; i < keywordsTemp.length; i++) {
				temp += keywordsTemp[i] + " ";
			}
			if (!temp.equals("")) {
				queryTemp = temp;
			}
		}
		queryTemp=queryTemp.replaceAll("\\s+"," ");
		System.out.println("QueryTemp " + queryTemp);
        queryTemp=URLEncoder.encode(queryTemp);
		query = query + queryTemp + queryPart;

		if (!fq.equals("")) {
			query += "&fq=" + fq;
		}

		System.out.println(query);
		return query;
	}

	private String fixSpaceSepartedKeywords(String queryTemp) {
		cacheBuilder = new CacheBuilder();
		hashMap = (Map<String, String>) cacheBuilder.getCache("spaceSeparatedKeywords");
		List<String> keywordsList = new ArrayList<String>();

		String[] keywordsTemp = queryTemp.split(" ");
		for (int i = 0; i < keywordsTemp.length; i++) {
			keywordsList.add(keywordsTemp[i]);
		}

		if (hashMap != null) {
			iterator1 = hashMap.entrySet().iterator();
			while (iterator1.hasNext()) {
				Entry<String, String> entry = iterator1.next();
				String key = entry.getKey();
				String value = entry.getValue();
				int index = queryTemp.indexOf(key);
				if (index != -1) {
					keywordsList.add(value);
				}
			}
		}
		String temp = "";
		iterator = keywordsList.iterator();
		while (iterator.hasNext()) {
			temp += (String) iterator.next() + " ";
		}
		return temp;

	}

	/*
	 * public String boostString(String queryString) { cacheBuilder = new
	 * CacheBuilder(); queryString = queryString.trim(); String queryWords[] =
	 * queryString.split(" "); Set<String> boostKeyWords = (Set<String>)
	 * cacheBuilder.getCache("boostkeywords"); int boostVal = queryWords.length;
	 * for (int i = 0; i < queryWords.length; i++) { String queryWord =
	 * queryWords[i].trim(); if (boostKeyWords.contains(queryWord)) {
	 * queryString = queryString.replaceAll(queryWords[i], queryWords[i] + "^" +
	 * boostVal); flag = true; } }
	 * 
	 * return queryString; }
	 */

}
