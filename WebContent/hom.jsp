<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.cinemall.data.ShowData"%>
<%@page import="com.cinemall.data.UserData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Cine Mall</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel="shortcut icon" href="images/favicon.ico"/>
<link rel="icon" type="image/gif" href="images/animated_favicon1.gif"/>
<script type="text/javascript" src="jquery.js"></script>
<script type="text/javascript" src="coin-slider/coin-slider.min.js"></script>
<link rel="stylesheet" href="coin-slider/coin-slider-styles.css"
	type="text/css" />
<script type="text/javascript">
$(document).ready(function() {
$('#coin-slider').coinslider({ width: 315, height: 155, navigation: true, opacity: 0.5, delay: 5000 });
});
</script>
<style type="text/css">
body {
	background-repeat: no-repeat;
}
.heading {	font-family: Georgia, "Times New Roman", Times, serif;
	font-size: xx-large;
}
.heading {	color: red;
}
.heading {	color: blue;
}
.heading {	color: #0F0;
	font-size: 16px;
}
.SuperHeading {
	font-size: 40px;
	font-family: Algerian;
	text-decoration: blink;
	color: #FF0006;
}
.red {
	color: #0F0;
	font-size: 12px;
}
.txt {	color: red;
}
.txt {	color: blue;
}
.txt {	color: blue;
}
.txt {	color: red;
}
.txt {	color: red;
}
.hyper {
	font-size: 12px;
}
.hyper {
	font-size: 12px;
}
</style>


</head>

<body>
<table width="69%" height="109" border="0" align="center" cellpadding="3" cellspacing="3" background="images/gradientbk1.jpg" frame="box">
  <tr>
    <th width="17%" height="93" scope="col" dir="ltr"><span class="heading"><img src="images/LOGO5.jpg" alt="logo" width="83" height="79" align="middle" class="SuperHeading" /></span></th>
    <th width="83%" background="images/gradientbk1.jpg" bgcolor="#D6D6D6" scope="col" dir="ltr">&nbsp;<span class="SuperHeading">OMEGA CINEMAS</span></th>
  </tr>
</table>
<form action="Hi" method="post">
<table width="69%" border="0" align="center" cellpadding="3" cellspacing="3" background="images/BOTTOM2.jpg" frame="box">
  <tr>
    <th height="12" colspan="2" rowspan="3" scope="col">&nbsp;</th>
    <th width="18%" scope="col"><span class="red">User ID</span></th>
    <th width="14%" scope="col"><span class="red">Password</span></th>
    <th width="12%" rowspan="3" scope="col"><a href="#">
    <input	type="submit" value="Login"></input></a></th>
  </tr>
  <tr>
    <th height="20" scope="col"><span class="txt">
      <input name="name" type="text" id="name" size="20" onfocus="if(this.value=='User Id') this.value='';" onblur="if(this.value=='') this.value='User Id';"  value="User Id"/ />
    </span></th>
    <th scope="col"><span class="txt">
      <input  type="password" id="password" size="20"id="password" name="password" onfocus="if(this.value=='password') this.value='';" onblur="if(this.value=='') this.value='password';"  value="password"" />
    </span></th>
  </tr>
  <tr>
    <th height="25" scope="col"><a href="register.jsp" class="hyper">Register</a></th>
    <th height="25" scope="col"><a href="Help/Contact.jsp" class="hyper">Forgot Password?</a></th>
  </tr>
</table>
</form>
<table width="69%" height="500" border="0" align="center" cellpadding="3" cellspacing="3" background="images/cinema.jpg" cols="400" frame="box">
  <tr height='7px'>
  </tr>
  <tr>
     <th scope="row" width="228px">&nbsp;</th> 
    <td>
    <div id='coin-slider'>
<%
    String redir = "";
	ShowData d = new ShowData();
	LinkedList<String> flist = d.getAllFilms();
	String item;
	Iterator<String> i = flist.iterator();
	if (!flist.isEmpty()) {
%> <%
 	while (i.hasNext()) {
 			item = i.next();
 %> <a href="<%=(redir + "?fname=" + item)%>">
  <img	src='images/<%=item%>.jpg' width="300" height="150"/> <span>
<%=item%> </span> </a> <%
 	}
 	}
 %>
 </div>
 </td>
  </tr>
</table>
<table width="69%" border="1" align="center" cellpadding="3" cellspacing="3" background="images/red console.jpg">
  <tr>
    <th height="46" scope="col"><a href="Help/FAQ.jsp">FAQ</a></th>
    <th scope="col"><a href="Help/Contact.jsp">Contact</a></th>
    <th scope="col"><p><a href="Help/Hlp.jsp">Help</a></p></th>
  </tr>
</table>
</body>
</html>
