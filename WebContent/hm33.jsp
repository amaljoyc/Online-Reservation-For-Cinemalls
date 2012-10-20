<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.cinemall.data.ShowData"%>
<%@page import="com.cinemall.data.UserData"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>CineHome</title>
<style type="text/css">
.heading {
	font-family: Georgia, "Times New Roman", Times, serif;
	font-size: xx-large;
}
.heading {
	color: red;
}
.txt {
	color: red;
}
.txt {
	color: blue;
}
.txt {
	color: blue;
}
.heading {
	color: blue;
}
.txt {
	color: red;
}
.txt {
	color: red;
}
.heading {
	color: #0F0;
	font-size: 16px;
}
.red {
	color: #0F0;
}
Link {
	font-size: 14px;
}
.heading a {
	font-size: 14px;
	font-family: Georgia, "Times New Roman", Times, serif;
}
.zaxs {
	font-size: 14px;
	font-family: "Times New Roman", Times, serif;
}
</style>
<link href="style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel="shortcut icon" href="images/favicon.ico">
<link rel="icon" type="image/gif" href="images/animated_favicon1.gif">
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
<form action="Hi" method="post">
<table width="76%" height="751" border="0" align="center" cellpadding="0" cellspacing="0" background="images/gradientbkPC3.jpg"  bgcolor="#FFFF00" frame="void">
<tr>
  <th width="13%" height="80" align="center" valign="middle" class="heading" scope="col"><img src="images/LOGO5.jpg" width="83" height="79" align="middle" class="SuperHeading" /></th>
  <th height="94" colspan="7" align="center" valign="middle" class="heading" scope="col"><p class="SuperHeading">OMEGA CINEMAS</p></th>
</tr>
<tr>
  <th width="62%" height="22" colspan="5" align="center" valign="middle" background="images/red console.jpg" class="heading" scope="col">&nbsp;</th>
  <th width="16%" height="22" align="center" valign="middle" background="images/red console.jpg" class="abc" scope="col"><span class="red">User ID</span></th>
  <th width="16%" height="22" align="center" valign="middle" background="images/red console.jpg" class="def" scope="col"><span class="red">Password</span></th>
  <th width="6%" align="center" valign="middle" background="images/red console.jpg" class="heading" scope="col">&nbsp;</th>
  </tr>
<tr>
  <th height="31" colspan="5" align="center" valign="middle" background="images/red console.jpg" class="heading" scope="col">&nbsp;</th>
  <th hight="16%"height="31" align="center" valign="middle" background="images/red console.jpg" class="abc" scope="col"><span class="heading"><span class="txt">
    <input name="name" type="text" id="name" size="20" onfocus="if(this.value=='User Id') this.value='';" onblur="if(this.value=='') this.value='User Id';"  value="User ID""/>
  </span></span></th>
  <th width="16%"height="31" align="center" valign="middle" background="images/red console.jpg" class="def" scope="col"><span class="txt">
    <input name="password" type="password" id="password" size="20" onfocus="if(this.value=='password') this.value='';" onblur="if(this.value=='') this.value='password';"  value="password""/>
  </span></th>
  <th width="6%" align="center" valign="middle" background="images/red console.jpg" class="heading" scope="col"><a href="#"><strong><input	type="submit" value="Login"></strong></a></th>
  </tr>

<tr>
  <th height="19" colspan="5" align="center" valign="middle" background="images/red console.jpg" class="heading" scope="col">&nbsp;</th>
  <th height="19" align="center" valign="middle" background="images/red console.jpg" class="heading" scope="col"><a href="register.jsp">Register</a>    <div align="center"></div></th>
  <th height="19" align="center" valign="middle" background="images/red console.jpg" class="heading" scope="col"><a href="#">Forgot Password?</a></th>
  <th height="19" align="center" valign="middle" background="images/red console.jpg" class="heading" scope="col">&nbsp;</th>
  </tr>
<tr>
  <th height="427" colspan="8" align="center" valign="middle" scope="col"><img src="images/cinema.jpg" name="coin" width="884" height="476" border="1" align="absmiddle" id="coin" /></th>
  
</tr>
<tr>
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
 %> <a href="<%=(redir + "?fname=" + item)%>"> <img
	src='images/<%=item%>.jpg' width="300" height="500"> <span>
<%=item%> </span> </a> <%
 	}
 	}
 %>
</tr>
<tr>
  <th height="51" colspan="2" align="center" valign="middle" bgcolor="#FFFFFF" scope="col">&nbsp;<a href="Help/FAQ.jsp">FAQ</a>&nbsp;</th>
  <th height="51" colspan="5" align="center" valign="middle" bgcolor="#FFFFFF" scope="col"><a href="Help/Contact.jsp">Contact</a></th>
  <th height="51" colspan="1" align="center" valign="middle" bgcolor="#FFFFFF" scope="col"><a href="Help/Hlp.jsp">Help</a>&nbsp;</th>
</tr>
<tr>
  <th height="7" colspan="8" align="center" valign="middle" scope="col">&nbsp;</th>
  </tr>
</table>
</form>
</body>
</html>
