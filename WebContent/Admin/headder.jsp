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
<a href='../index.jsp'><h1>Cine Mall</h1></a>
<%@ page import="com.cinemall.controller.LogOut"%>
<%
	String un = (String) session.getAttribute("userName");
%>
<div align="right"><font color="blue"><%=un%>/<a href="../LogOut.do">LogOut</a></font></div>
<ul id="tabs">
	<li id="tab1"><a href="manage.jsp">Space Management</a></li>
	<li id="tab2"><a href="allregisteredusers.jsp">Users</a></li>
</ul>

</body>
</html>