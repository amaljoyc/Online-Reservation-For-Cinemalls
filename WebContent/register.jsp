<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
<script type="text/javascript" src="test.js">

</script>
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel="shortcut icon" href="images/favicon.ico">
<link rel="icon" type="image/gif" href="images/animated_favicon1.gif">
</head>
<body leftmargin="10px" style="background-image: url('images/backgrd.jpg')">

<form action="Register" method="post">
<div class="regiserback">
<fieldset><legend>Register</legend>
<h1>Register Now</h1>
<hr/>

<%String err=(String)session.getAttribute("err");
if(err!=null){
%><fieldset><font color="red">
Error:<br>
<%=err %>
</font>
</fieldset>

<%session.removeAttribute("err");
} %>
<table border="0">
	<tr>
		<td><label for="userName">User Name:</label></td>
		<td><input type="text" id="userid" name="id" autocomplete="off" onkeyup="validateUserId()"/><div id="userIdMessage"></div></td>
	</tr>
	<tr>
		<td><label for="password">Password :</label></td>
		<td><input type="password" id="password" name="password" /></td>
	</tr>
	<tr>
		<td><label for="conPassword">Confirm Password :</label></td>
		<td><input type="password" id="conPassword" name="conPassword" /></td>
	</tr>
	<tr>
		<td><label for="name">Name:</label></td>
		<td><input type="text" id="name" name="name" /></td>
	</tr>
	<tr>
		<td><label for="eMail">E-Mail :</label></td>
		<td><input type="text" id="eMail" name="eMail" /></td>
	</tr>
	<tr>
		<td>Adderess:</td>
		<td><textarea name="add" rows="3" cols="40"></textarea></td>
	</tr>
	<tr>
		<td><label for="phn">Phone Number:</label></td>
		<td><input type="text" id="phn" name="phn" /></td>
	</tr>
<tr ><td colspan="2" align="center">
<input type="submit" value="submit" id="submit_btn"></td></tr></table></fieldset></div>

</form>

</body>
</html>