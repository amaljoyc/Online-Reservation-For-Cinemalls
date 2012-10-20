<%@page import="com.cinemall.data.MovieData"%>
<%@page import="com.cinemall.data.UserData"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.cinemall.data.TodaysCollection"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="style.css" />
<title>Today's Collection</title>
<style type="text/css">
.f {
	font-family: Times New Roman, Times, serif;
}

.g {
	font-family: Courier New, Courier, monospace;
}
</style>
</head>
<body id="page4">
<%
	String id = (String) session.getAttribute("userName");
	UserData ud = new UserData();
	if (id == null) {

		response.sendRedirect("../hom.jsp");
	} else if (!ud.getAccess(id))
		response.sendRedirect("../User/userprofile.jsp");
	ud.close();
%>
<div><jsp:include page="headder.jsp" /></div>
<hr>
<h2 class="g">Today's total collection is</h2>
<strong>
 <%
 	TodaysCollection c = new TodaysCollection();
 	String sdate = c.convert();
 %>
<table border="10px" cellpadding="5" cellspacing=0 bordercolor="silver"
	align="center">
	<tr>
		<th>
		<h1><font color=red> <%=c.totalAmt(sdate)%> </font> <font color=green> .Rs </font>
</h1>
</th>
</tr>
</table>
</strong>
<hr>
<h3 class="f">Total Collection of each film is listed below....</h3>
<table border="1px" cellpadding="2" cellspacing=0 bordercolor="blue"
	align="center">
	<tr>
		<th>Film ID</th>
		<th>Amount</th>
	</tr>
	<%
	
	MovieData md=new MovieData();
		List<Integer> filmList = c.getFilmid(sdate);
		Iterator<Integer> fids = filmList.iterator();
		while (fids.hasNext()) {
			int fid = fids.next();
	%><tr>
		<td><%=md.getFilmName(fid)%></td>
		<td><%=c.getAmt(fid, sdate)%>.Rs</td>
	</tr>
	<%
		}
		c.close();
	%>
</table>
<font face="Times New Roman, Times, serif">
</font>
</body>
</html>