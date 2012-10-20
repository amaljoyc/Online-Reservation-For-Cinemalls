<%@page import="com.cinemall.data.UserData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel='shortcut icon' href='../images/favicon.ico'>
<link rel='icon' type='image/gif' href='../images/animated_favicon1.gif'>
</head>
<body>
<a href='index.jsp'><h1>Cine Mall</h1></a>
<%@ page import="com.cinemall.controller.LogOut"%>
<%
	String un = (String) session.getAttribute("userName");
	if (un == null)
		response.sendRedirect("hom.jsp");
	%>
	<div align="right"><font color="blue"><%=un%>/<a href="LogOut.do">LogOut</a></font></div>
	<%
	UserData d=new UserData();
	if(d.adminAccess(un)){
		%>
		<ul id="tabs">
			<li id="tab1"><a href="Admin/allregisteredusers.jsp">All Users</a></li>
			<li id="tab2"><a href="Admin/manage.jsp">Memory management</a></li>
		</ul>
		<%		
	}
	else if(d.getAccess(un)){
%>
<ul id="tabs">
	<li id="tab1"><a href="Manager/addnewfilm.jsp">Add New Film</a></li>
	<li id="tab2"><a href="Manager/settime.jsp">Set Time Slots</a></li>
	<li id="tab3"><a href="Manager/manage.jsp">Rate Seats</a></li>
</ul>
<%
session.setAttribute("redurl","User/tickebooking.jsp");
	}
	else{
%>
<ul id="tabs">
	<li id="tab1"><a href="User/userprofile.jsp">Profile</a></li>
	<li id="tab2"><a href="User/tickebooking.jsp">Book Tickets</a></li>
	<li id="tab3"><a href="User/reservations.jsp">Reservations</a></li>
</ul>

<%
	}
	d.close();
%>
</body>
</html>