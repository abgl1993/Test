<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.timesinternet.alive.search.beans.Result" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%  
ArrayList<Result>resultList=(ArrayList<Result>)request.getAttribute("resultList");
%>
size: <%out.println(resultList.size()); %>

<% for(int i=0;i<resultList.size();i++){
	Result result=(Result)resultList.get(i);
%>
<br>
<table>
<tr>
<%out.println(i+1); %>
<td>
Title:<% out.println(result.getTitle()); %><br>

<img alt="" src="<%out.print(result.getImageLink());%>"><br>

Source: <%out.println(result.getSource());%><br>
Link: <a href="<%out.println(result.getLink()); %>" >Source Link</a><br>
master_category1: <%out.println(result.getMaster_category1());%><br>
master_category2: <%out.println(result.getMaster_category2()); %><br>
</td>
</tr>
	
</table>
<%}%>

</body>
</html>