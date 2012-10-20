<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel="shortcut icon" href="images/favicon.ico">
<link rel="icon" type="image/gif" href="images/animated_favicon1.gif">
</head>
<body>


<form action="Hi" method="post">
<fieldset><legend>Log In</legend> <label for="name">Name
:</label> <input type="text" id="name" name="name"   onfocus="if(this.value=='User Id') this.value='';" onblur="if(this.value=='') this.value='User Id';"  value="User Id"/> <label for="password">Password:</label>
<input type="password" id="password" name="password" onfocus="if(this.value=='password') this.value='';" onblur="if(this.value=='') this.value='password';"  value="password""> <input
	type="submit" value="submit"><span style="padding-left:50px"><a href="register.jsp">Reister</a></span>
<%
	String n = (String) request.getAttribute("err");
	if (n != null) {
%><font color="red" size="2.5"> An error occured,<%=n%> you can
try again.<%
	}
%> </font> <%
 	request.removeAttribute("err");
 	n = null;
 %>
</fieldset>
</form>
</body>
</html>