<%@page import="com.cinemall.data.InstallDataBase"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Install</title>
<link rel="stylesheet" type="text/css" href="style.css" />

<link rel="shortcut icon" href="images/favicon.ico">
<link rel="icon" type="image/gif" href="images/animated_favicon1.gif">
</head>
<body>
<%@ page import="com.cinemall.data.InstallDataBase"%>
<h2>Install</h2>
<ul>
<li>You are going to install this application</li>
<li>Create a new database using 'CREATE DATBASE 'name';'in mysql.</li>
<li>Decide on a new user name and password to access this data base.</li> 
</ul>
<form action="install.jsp" method="post">
<%
String err="No Errors";
if((err!=null)&&!(err.equals("success"))) {%>

<fieldset>
<label for="password">Root Password :</label> <input type="password"
	id="password" name="password" /><br>
	Data Base Name:<input type="text" name="dbname"><br>
	New User Name:<input type="text" name="dbuser"><br>
	New User Password:<input type="text" name="dbpass">
	<input type="submit" value="submit"></fieldset>
</form>
<%String pass=request.getParameter("password");
String dbname=request.getParameter("dbname");
String dbuser=request.getParameter("dbuser");
String dbpass=request.getParameter("dbpass");

if((pass!=null)&&(dbname!=null)&&(dbuser!=null)&&(dbpass!=null)){
	InstallDataBase d=new InstallDataBase(pass);
	err=d.install(dbuser,dbpass,dbname);
	d.close();
}
%><br>
<font color="red">
<%if((err!=null)&&!(err.equals("No Errors"))) {%>
Sorry There were some errors:
<%=err %><%} %>
<%}else {%><font color='green'>Database and other settings successfuly configured.</font>
</font><%}

%>
</body>
</html>