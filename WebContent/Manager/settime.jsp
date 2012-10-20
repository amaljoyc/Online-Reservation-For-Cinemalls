<%@page import="java.sql.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.cinemall.data.UserData"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Set Time Table</title>
<link rel="stylesheet" type="text/css" href="style.css" />
	<SCRIPT LANGUAGE="JavaScript" SRC="../CalendarPopup.js"></SCRIPT>

</head>
<body id="page2">
<%@ page import="com.cinemall.data.ShowData"%>
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


<%
	ShowData d = new ShowData();
	LinkedList<String> flist = d.getAllFilms();
	String item;
	Iterator<String> i = flist.iterator();
	if (!flist.isEmpty()) {
%><form action="" method="get"><select name="fname">
	<%
		while (i.hasNext()) {
				item = i.next();
	%>
	<option value="<%=item%>"><%=item%></option>

	<%
		}
	%>
</select> <input type="submit" name="submit" value="Set"></form>
<br>
<fieldset>
<form name="time" action="../AddFilm.do" method="post" >
<%
	}
	String fn = request.getParameter("fname");
	if (fn != null) {
		int filmid = d.getFilmId(fn);
%>Your Selection is "<%=fn%>". <input type="text" name="filmid"
	value="<%=filmid%>"><br>
<%
	}
%> <strong>Time:</strong><input type="radio" name="time" value="time1">First
Show <input type="radio" name="time" value="time2">Mateny <input
	type="radio" name="time" value="time3">Evenying Show <input
	type="radio" name="time" value="time4">Second Show<br>
<hr />
<strong>Screen:</strong><input type="radio" name="screen" value="1">Screen
1 <input type="radio" name="screen" value="2">Screen 2 <input
	type="radio" name="screen" value="3">Screen 3 <input
	type="radio" name="screen" value="4">Screen 4<input
	type="radio" name="screen" value="5">Screen 5<br>
<hr />
Date<br>
<SCRIPT LANGUAGE="JavaScript" ID="js9">

var cal9 = new CalendarPopup();
var da=new Date();
da.setDate(da.getDate()+1);
cal9.addDisabledDates(null,formatDate(da,"yyyy-MM-dd"));
da.setDate(da.getDate()+20);
cal9.addDisabledDates(formatDate(da,"yyyy-MM-dd"),null);
cal9.setReturnFunction("setMultipleValues");

function setMultipleValues(y,m,d) {
	document.forms['time'].year.value=y;
	document.forms['time'].month.value=m;
	document.forms['time'].date.value=d;
	document.getElementById('time1').className="edit_avail";
	}

</SCRIPT>
<INPUT TYPE="text" NAME="date" VALUE="" SIZE=3> /
<INPUT TYPE="text" NAME="month" VALUE="" SIZE=3> /
<INPUT TYPE="text" NAME="year" VALUE="" SIZE=5> (d/m/y)
<A HREF="#" onClick="cal9.showCalendar('anchor9'); return false;" TITLE="cal9.showCalendar('anchor9'); return false;" NAME="anchor9" ID="anchor9">select</A>
<div id="time1" class="edit_not_avail">
<SCRIPT LANGUAGE="JavaScript" ID="js1">

var cal1 = new CalendarPopup();
var da=new Date();
da.setDate(da.getDate()+2);
cal1.addDisabledDates(null,formatDate(da,"yyyy-MM-dd"));
da.setDate(da.getDate()+21);
cal1.addDisabledDates(formatDate(da,"yyyy-MM-dd"),null);
cal1.setReturnFunction("set1MultipleValues");

function set1MultipleValues(y,m,d) {
	document.forms['time'].yea.value=y;
	document.forms['time'].mon.value=m;
	document.forms['time'].dat.value=d;
	}

</SCRIPT>
<INPUT TYPE="text" NAME="dat" VALUE="" SIZE=3>/
<INPUT TYPE="text" NAME="mon" VALUE="" SIZE=3> /
<INPUT TYPE="text" NAME="yea" VALUE="" SIZE=5> (d/m/y)
<A HREF="#" onClick="cal1.showCalendar('anchor1'); return false;" TITLE="cal1.showCalendar('anchor1'); return false;" NAME="anchor1" ID="anchor1">select</A><hr />
</div>
<input type="submit" name="submit" value="Accept"></form>
</fieldset>



<%
	String err = (String) request.getAttribute("err");
	if (err != null) {
%><%=err%>

<%
request.removeAttribute("err");
	}
	
%>
<%if(fn!=null){ %>
<center>
<table border="1px" cellpadding="2" cellspacing="0"  >
<tr><td colspan="8" align="center"><%=fn %></td></tr>
<tr><th>
Screen 
</th>
<%Calendar date=Calendar.getInstance();
Calendar datten=Calendar.getInstance();
datten.add(Calendar.DATE,7);
Date day=new Date(date.getTimeInMillis());
for(;date.before(datten);date.add(Calendar.DATE,1)){
	day.setTime(date.getTimeInMillis());
%>
<th><%=day %></th>
<%} %>

</tr>
</table>
</center>
<%} %>
<%
	d.close();
%>
</body>
</html>