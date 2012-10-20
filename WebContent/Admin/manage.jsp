<%@page import="java.util.ArrayList"%>
<%@page import="com.cinemall.data.AdminData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Ticket Rates,Number of sets Avilable for
Reservation</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body id="page1">
<%@ page import="com.cinemall.data.UserData"%>
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
<center>
<h1>Space Manager</h1>
</center>
<table align="center" border="1px" cellpadding="2" cellspacing=0 bordercolor="silver">
<tr>
<th>Table Name</th>
<th>Table Rows</th>
<th>Data Length</th>
<th>Index Length</th>
<th>Size in MB</th></tr>
<%
AdminData ad=new AdminData();
ArrayList<String> info=ad.getSizeInfo();
for (int i = 0; i<info.size(); i++) {
	String []pid = info.get(i).split(";");
%><tr>
<td><%=pid[0] %></td>
<td><%=pid[1] %></td>
<td><%=pid[2] %></td>
<td><%=pid[3] %></td>
<td><%=pid[4] %></td>
</tr>
<%
}ad.close();
%>
</table>
</body>
</html>