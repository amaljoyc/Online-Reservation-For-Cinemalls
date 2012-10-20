<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Profile</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel='shortcut icon' href='../images/favicon.ico'>
<link rel='icon' type='image/gif' href='../images/animated_favicon1.gif'>
</head>
<body id="page1" class="comonbody">
<%@ page import="com.cinemall.data.UserData"%>
<%
	String id = (String) session.getAttribute("userName");
	if (id == null||(id.equals("anonimous"))){
		response.sendRedirect("../hom.jsp");
	}
%>
<div><jsp:include page="headder.jsp" /></div>

<div class="comonbody">
<div id="status"><p>On this page a user who hasloged in will be able to see his
details and even edit them .</p></div>
<hr />
<%
	UserData d = new UserData();
%><div id="divid" style="position: absolute; right: 400; top: 300;"></div>
<script type="text/javascript">
document.getElementById("divid").style.left= "500px";
document.getElementById("divid").style.top= "240px";
</script>
<script type="text/javascript" src="ajaxprofedit.js"></script>
<!--<form><table><tr><td>OldPassword</td><td><input type='password'></td></tr><tr><td>New password:</td><td><input type='password'></td></tr></table></form>-->
<table border="0" align="left">
	<tr>
		<td><label for="userName">User Name:</label></td>
		<td><%=id%></td>
	</tr>
	<tr>
		<td><label for="password">Password :</label></td>
		<td>*****</td>
		<td><input type="submit"name='edit' value="Change"onmouseup="chngePassword()" onmouseover="document.getElementById('status').innerHTML='<br>Change Your Password'" onmouseout="document.getElementById('status').innerHTML='<br>Welcome to CiniMall'"/></td>
	</tr>
	<tr>
		<td><label for="name">Name:</label></td>
		<td><%=d.getUserName(id)%></td>
	</tr>
	<tr>
		<td><label for="eMail">E-Mail :</label></td>
		<td><%=d.getEmail(id)%></td>
		<td><input type="submit"name='edit' value="Change"onmouseup="chngeEMail()" onmouseover="document.getElementById('status').innerHTML='<br>Change Your E-Mail Address'" onmouseout="document.getElementById('status').innerHTML='<br>Welcome to CiniMall'"/></td>
	</tr>
	<tr>
		<td>Adderess:</td>
		<td><%=d.getAddress(id)%></td>
		<td><input type="submit" name='edit'value="Change"onmouseup="chngeAddr()" onmouseover="document.getElementById('status').innerHTML='<br>Change Your Address'" onmouseout="document.getElementById('status').innerHTML='<br>Welcome to CiniMall'"/></td>
	</tr>
	<tr>
		<td><label for="phn">Phone Number:</label></td>
		<td><%=d.getPhoneNumber(id)%></td>
		<td><input type="submit" name='edit'value="Change"onmouseup="chngePhnNo()" onmouseover="document.getElementById('status').innerHTML='<br>Change Your Phone Number'" onmouseout="document.getElementById('status').innerHTML='<br>Welcome to CiniMall'"/></td>
	</tr>
</table>

<%
	d.close();
%>
</div>
</body>
</html>