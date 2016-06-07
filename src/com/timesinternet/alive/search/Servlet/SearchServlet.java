package com.timesinternet.alive.search.Servlet;

import java.io.IOException;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.timesinternet.alive.search.beans.Result;
import com.timesinternet.alive.search.services.FinalResult;
import com.timesinternet.alive.search.services.Scheduler;
import com.timesinternet.alive.search.services.SearchHelper;
import com.timesinternet.alive.search.services.Filter;
import com.timesinternet.alive.utils.CacheBuilder;

/**
 * @author Atul.Baghel
 * 
 *         Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String[] keywords;
	CacheBuilder cacheBuilder;
	Scheduler scheduler;
	FinalResult finalResult;
	Filter filter;
	String searchString = "";
	String searchUrl = "";
	String fq = "";

	/**
	 * Default constructor.
	 */
	public SearchServlet() {

	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		cacheBuilder = new CacheBuilder();
		cacheBuilder.buildCache();
		scheduler = new Scheduler();
		scheduler.start();
		super.init(config);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String queryString = request.getParameter("search").toLowerCase().trim();
		queryString = queryString.replaceAll("\\s+", " ");
		keywords = queryString.split(" ");
		filter = new Filter();
		finalResult = new FinalResult();
		SearchHelper searchHelper = new SearchHelper();
		String fq = searchHelper.findSearchCategoryString(queryString);
		searchUrl = searchHelper.getFinalSearchUrl(fq, queryString);
		String result = finalResult.getData(searchUrl);
		List<Result> resultList = finalResult.displayResults(result, response);
		request.setAttribute("resultList", resultList);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/render.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {

	}

	public void destroy() {
		cacheBuilder.clearCache();

	}

}
