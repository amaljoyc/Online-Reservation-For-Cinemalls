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
<body id="page3">
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
<h1>Theatre Management!</h1>
</center>
<br>
<h2>WELCOME...</h2>
<p>Dear Manager, please fill in the details below... <br>
<br>
<%
	String e = (String) session.getAttribute("err");
	String s = (String) session.getAttribute("success");
%> <%
 	if (e != null) {
 %><fieldset><font size=5 color=red> <%=e%></font></fieldset>
<%
	}
	session.removeAttribute("err");
%> <%
 	if (s != null) {
 %>
<fieldset><font size=5 color=blue> <%=s%></font></fieldset>
<%
	}
	session.removeAttribute("success");
%> <br>
<fieldset><legend><font size=5 color=lime>Theatre
Information</font></legend>
<form action="../Theatre" method=post><font size=4> Select
the Screen:</font> <br>
<table border bgcolor=silver width=500>
	<td><input type=radio name=screen value=1>Screen 1
	<td><input type=radio name=screen value=2>Screen 2
	<td><input type=radio name=screen value=3>Screen 3
	<td><input type=radio name=screen value=4>Screen 4
	<td><input type=radio name=screen value=5>Screen 5
</table>
<br>
<br>
<font size=4> Select the Class:</font> <br>
<table border bgcolor=silver width=300>
	<td><input type=radio name=class value=A>Class A</td>
	<td><input type=radio name=class value=B>Class B</td>
	<td><input type=radio name=class value=C>Class C</td>
</table>
<br>
<br>
<font size=4> Select the option for updation:</font> <br>
<table border bgcolor=silver width=500>
	<td><input type=radio name=option value=1>Maximum rate per
	ticket</td>
	<td><input type=radio name=option value=2>Maximum number
	of seats for reservation</td>
</table>
<br>
<br>
<font size=4>Enter the value for the option selected above:</font> <br>
<input type="text" name="value" id="value" size=30> <br>
<br>
<input type="submit" value="Submit"> <input type="reset">
</form>
</fieldset>
</body>
</html>