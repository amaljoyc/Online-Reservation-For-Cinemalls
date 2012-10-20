<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.cinemall.data.ShowData"%>
<%@page import="com.cinemall.data.UserData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cine Mall</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel="shortcut icon" href="images/favicon.ico">
<link rel="icon" type="image/gif" href="images/animated_favicon1.gif">
<script type="text/javascript" src="jquery.js"></script>
<script type="text/javascript" src="coin-slider/coin-slider.min.js"></script>
<link rel="stylesheet" href="coin-slider/coin-slider-styles.css"
	type="text/css" />
<script type="text/javascript">
$(document).ready(function() {
$('#coin-slider').coinslider({ width: 570, height: 700, navigation: true, opacity: 0.5, delay: 5000 });
});
</script>
</head>
<body>
<%
	String id = (String) session.getAttribute("userName");
	String redir = "";
	UserData ud = new UserData();
	if (id == null) {

		session.setAttribute("userName", "anonimous");
	}
	id = (String) session.getAttribute("userName");
	if (id.equals("anonimous")) {
%>
<div><jsp:include page="login.jsp" /></div>

<%
	} else if(ud.getAccess(id)){
		redir = "Manager/settime.jsp";
%>
<div><jsp:include page="headder.jsp" /></div>
<%
	if (session.getAttribute("redurl") != null) {
			redir = (String) session.getAttribute("redurl");
		}
		ud.close();
	}else{
		redir = "User/tickebooking.jsp";
	}
%>
<br>
<center>
<div id='coin-slider'>
<%
	ShowData d = new ShowData();
	LinkedList<String> flist = d.getAllFilms();
	String item;
	Iterator<String> i = flist.iterator();
	if (!flist.isEmpty()) {
%> <%
 	while (i.hasNext()) {
 			item = i.next();
 %> <a href="<%=(redir + "?fname=" + item)%>"> <img
	src='images/<%=item%>.jpg' width="300" height="500"> <span>
<%=item%> </span> </a> <%
 	}
 	}
 %>
</div>

</center>

</body>
</html>