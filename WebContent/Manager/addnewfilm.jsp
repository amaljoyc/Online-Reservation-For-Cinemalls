<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add New Film</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel="stylesheet" type="text/css" href="fileuploader.css" />
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
<script type="text/javascript" src="validate.js"></script>
<script src="../jquery.js" type="text/javascript"></script>
<script src="fileuploader.js" type="text/javascript"></script>

<br>
<div id="divid"></div>
<fieldset>
<form action="../AddFilm.do" method="post">Film Name:<input
	type="text" name="fname" id="fname" onfocus="if(this.value=='--name--') this.value='';" onblur="if(this.value=='') this.value='--name--';" value="--name--"> <input type="button" name="submit"
	onmouseup="confirmNoExist()"  value="OK"></form>
</fieldset>
<br>
<%
	String msg = (String) session.getAttribute("message");
	if (msg != null) {
%><fieldset><br>
<%
	if (msg.equals("faild")) {
%> <font color="red">An error occured: <%=msg%> </font> <%
 	} else {
 %><font color="green">The addion of new film was <%=msg%> </font> <%
 	}
 %>
</fieldset>

<%
	}
	session.removeAttribute("message");
%>
</html>