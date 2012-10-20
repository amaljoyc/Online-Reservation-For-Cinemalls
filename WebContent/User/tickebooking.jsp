<%@page import="com.cinemall.data.BookingData"%>
<%@page import="com.cinemall.data.MovieData"%>
<%@page import="com.cinemall.data.ShowData"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Tickets</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<script type="text/javascript" src="ajaxprofedit.js"></script>
<link rel='shortcut icon' href='../images/favicon.ico'>
<link rel='icon' type='image/gif' href='../images/animated_favicon1.gif'>
<style type="text/css">
h2 {
	font-family: verdana;
	fontsize: 20pt;
	color: blue;
	font-style: italic;
}
</style>

</head>
<body id="page2" class="comonbody">
<%
	String un = (String) session.getAttribute("userName");
	if (un == null || (un.equals("anonimous"))) {
		response.sendRedirect("../hom.jsp");
	}
%>
<div><jsp:include page="headder.jsp" /></div>
<div class="comonbody">
<div class="reserv">
<center><!--<h2>Reservation</h2>--></center>

<!--<body background="Images/Homebk.jpg">--> <%
 	String msg = (String) session.getAttribute("msg");
 	if (msg != null) {
 %> <script type="text/javascrip">
	alert("Success");
</script> <%
 	session.removeAttribute("msg");
 	}
 %>
<center>
<br> 
<form action="../ReserveTicket" method="post" id='book'>
<table>
	<tr height="40">
		<td>Date</td>
		<td><SCRIPT LANGUAGE="JavaScript" SRC="../CalendarPopup.js"></SCRIPT>

		<SCRIPT LANGUAGE="JavaScript" >
	var cal9 = new CalendarPopup();
	var da = new Date();
	da.setDate(da.getDate() + 1);
	cal9.addDisabledDates(null, formatDate(da, "yyyy-MM-dd"));
	da.setDate(da.getDate() + 20);
	cal9.addDisabledDates(formatDate(da, "yyyy-MM-dd"), null);
	cal9.setReturnFunction("setMultipleValues");

	function setMultipleValues(y, m, d) {
		var thi_page = document.URL;
		var selected_date = 'y=' + y + '&m=' + m + '&d=' + d;
		try {
			if (thi_page.indexOf('?') != -1) {
				if (thi_page.indexOf('&m=') == -1) {
					window.location += '&' + selected_date;
				} else {
					var t = thi_page.split("y=");
					window.location = t[0] + selected_date;
					//findAllMoviesFor(selected_date);
				}
			} else {
				window.location += '?' + selected_date;
				//findAllMoviesFor(selected_date);
			}
		} catch (e) {
			alert('An error occured' + e + "  d");
		}
	}

	function setParameter(parm, pn) {
		try {
			if (parm != "") {
				var thi_page = document.URL;
				//alert(fname);
				if (thi_page.indexOf(pn + '=') == -1) {
					window.location += '&' + pn + '=' + parm;
				} else {
					var t = thi_page.split(pn + '=');
					window.location = t[0] + pn + '=' + parm;
					//findAllMoviesFor(selected_date);
				}

			}
		} catch (e) {
			alert('An error occured' + e + "  d");
		}
	}

	
</SCRIPT>
<%
 	String y = request.getParameter("y");
 	String m = request.getParameter("m");
 	String d = request.getParameter("d");
 	Date shdate = null;
 	if (d != null && m != null && y != null) {
 		shdate = new Date(0);
 		shdate.setDate(Integer.parseInt(d));
 		shdate.setMonth(Integer.parseInt(m) - 1);
 		shdate.setYear(Integer.parseInt(y) - 1900);
 	}
 %><INPUT TYPE="text" NAME="d" <%if (d != null) {%> VALUE="<%=d%>" <%}%>
			SIZE=3> / <INPUT TYPE="text" NAME="m" <%if (m != null) {%>
			VALUE="<%=m%>" <%}%> SIZE=3> / <INPUT TYPE="text" NAME="y"
			<%if (y != null) {%> VALUE="<%=y%>" <%}%> SIZE=5> (d/m/y) <A
			HREF="" onClick="cal9.showCalendar('anchor9'); return false;"
			TITLE="cal9.showCalendar('anchor9'); return false;" NAME="anchor9"
			ID="anchor9">select</A></td>
	</tr>

	<tr height="40">
		<td>Film:</td>
		<td>
		<div id='this_div'><select name="fname"
			onchange="setParameter(this.value,'fname');">
			<%
				ShowData sd = new ShowData();
				String fname = request.getParameter("fname");
				if (shdate == null) {
			%>
			<option <%if (fname != null) {%> value="<%=fname%>"
				<%} else {%> value=""
				<%fname = "select a date";
				}%>><%=fname%></option>
			<%
				} else if (shdate != null && fname == null) {
					String[] fnames = sd.getFilmsFor(shdate).split(";");
			%><option value="">choose a film</option>
			<%
				for (int i = 0; i < fnames.length; i++) {
			%>
			<option value="<%=fnames[i]%>"><%=fnames[i]%></option>
			<%
				}
				} else if (shdate != null && fname != null) {
					String[] fnames = sd.getFilmsFor(shdate).split(";");
			%>
			<option value="<%=fname%>"><%=fname%></option>
			<%
				for (int i = 0; i < fnames.length; i++) {
						if (!fnames[i].equals(fname)) {
			%><option value="<%=fnames[i]%>"><%=fnames[i]%></option>
			<%
				}
					}
			%>
		</select>
		<%
			if (sd.fimNotShown(fname, shdate)) {
		%> <span
			style="background-color: red">This film is not shown on that
		day</span>
		<%
			}
			}
		%>
		</div>
		</td>
	</tr>
	<tr height="40">
		<td>Screen</td>
		<td><select name="screen"
			onchange="setParameter(this.value,'screen');">
			<%
				String screen = request.getParameter("screen");
				MovieData md = new MovieData();
				if (shdate != null && fname != null && screen == null) {
					String[] screens = sd.getScreenFor(shdate, md.getFilmId(fname))
							.split(";");
			%>
			<option value="">choose a screen</option>
			<%
				for (int i = 0; i < screens.length; i++) {
			%>

			<option value='<%=screens[i]%>'><%=screens[i]%></option>
			<%
				}
				} else if (shdate != null && fname != null && screen != null) {
			%><option value='<%=screen%>'><%=screen%></option>
			<%
				}
			%>
		</select></td>
	</tr>
	<tr height="40">
		<td>Show</td>
		<td><select name="show"
			onchange="setParameter(this.value,'show');">
			<%
				String show = request.getParameter("show");
				if (shdate != null && fname != null && screen != null
						&& show == null) {
					String[] shows = sd.getShowFor(shdate, md.getFilmId(fname),
							screen).split(";");
			%>
			<option value="">choose a show</option>
			<%
				for (int i = 0; i < shows.length; i++) {
			%>
			<option value='<%=shows[i]%>'><%=shows[i]%></option>
			<%
				}
				} else if (shdate != null && fname != null && screen != null
						&& show != null) {
					String[] shows = sd.getShowFor(shdate, md.getFilmId(fname),
							screen).split(";");
			%><option value='<%=show%>'><%=show%></option>
			<%
				for (int i = 0; i < shows.length; i++) {
						if (shows[i].equals(show)) {
							continue;
						}
			%>
			<option value='<%=shows[i]%>'><%=shows[i]%></option>
			<%
				}
				}
			%>
		</select></td>
	</tr>

	<tr height="40">
		<td>Class</td>
		<td><select name="class"
			onchange="setParameter(this.value,'class');">
			<%
				String clas = request.getParameter("class");
				if (shdate != null && fname != null && screen != null
						&& show != null && clas == null) {
			%>
			<option value="">choose a class</option>
			<option value="A">A-Class</option>
			<option value="B">B-Class</option>
			<option value="C">C-Class</option>
			<%
				} else if (shdate != null && fname != null && screen != null
						&& show != null && clas != null) {
			%><option value='<%=clas%>'><%=clas + "-Class"%></option>
			<%
				for (int j = 'A'; j < 'D'; j++) {
						if (j != clas.toCharArray()[0]) {
			%><option value='<%=(char) j%>'><%=((char) j) + "-Class"%></option>
			<%
				}
					}
				}
			%>

		</select></td>
	</tr>

	<tr height="40">
		<%
			String notic = request.getParameter("notic");
			if (shdate != null && fname != null && screen != null
					&& show != null && clas != null) {
		%>
		<td>Number Of Tickets:</td>
		<td><select name="notic"	onchange="setParameter(this.value,'notic');" >
			<%
				if (notic == null) {
			%>
			<option value=0>0</option>
			<%
				for (int j = 1; j < 7; j++) {
			%>
			<option <%=j%>><%=j%></option>
			<%
				}
					} else {
			%><option value=<%=notic%>><%=notic%></option>
			<%
				for (int j = 1; j < 7; j++) {
							if (j != Integer.parseInt(notic)) {
			%>
			<option <%=j%>><%=j%></option>
			<%
				}
						}
					}
			%>

		</select></td>
	</tr>
	<tr height="40">
		<td>Amount Incurred</td>
		<td>
		<%
			if (notic != null) {
					BookingData bd = new BookingData();
		%> <%=bd.calcAmt(Integer.parseInt(screen), clas,
							Integer.parseInt(notic))%>
		<input type="hidden" name="bamt"
			value="<%=bd.calcAmt(Integer.parseInt(screen), clas,
							Integer.parseInt(notic))%>">
		<%
			bd.close();
		%> <%
 	}
 %>
		</td>
	</tr>

	<tr height="40">
		<td colspan="2"><input type="submit" value="Request Tickets" 
			<%if (notic == null) {%> disabled="disabled" <%}%> id='sub_butn' /></td>
	</tr>
	<%
		}
	%>
</table>
<%
	sd.close();
	md.close();
%> <%-- <form action="" method="get">  --%> <%-- </select> <input type="submit" name="submit" value="Set"></form> --%>
<br>
</form>

</center>
</div>
<a href="reservations.jsp">View My Existing Reservations</a> <br />
</div>
</body>
</html>