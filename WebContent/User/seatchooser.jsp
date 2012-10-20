<%@page import="com.cinemall.Accessories.Utilities"%>
<%@page import="java.sql.Date"%>
<%@page import="com.cinemall.data.BookingData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Tickets</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<script type="text/javascript" src="ajaxprofedit.js"></script>
</head>
<body id="page2" class="comonbody">
<%
	String un = (String) session.getAttribute("userName");
	String strbid = request.getParameter("bid");
	if (un == null || (un.equals("anonimous"))) {
		response.sendRedirect("../hom.jsp");
	} else if (strbid == null) {
		response.sendRedirect("../tickebooking.jsp");
	}
%>
<div><jsp:include page="headder.jsp" /></div>
<a href="tickebooking.jsp">Booking</a>
/
<a href="">Choose Seats</a>
<center>
<form action="../ReserveTicket" method="post" id='book'>
<%
	String show = request.getParameter("show");
	String screen = request.getParameter("screen");
	String clas = request.getParameter("class");
	String dat = request.getParameter("date");
%> <input type="hidden" name="bid" value="<%=strbid%>"> <input
	type="hidden" name="show" value="<%=show%>"> <input
	type="hidden" name="screen" value="<%=screen%>"> <input
	type="hidden" name="class" value="<%=clas%>"> <input
	type="hidden" name="date" value="<%=dat%>">
<table>
	<%
		BookingData db = new BookingData();
		int[] rac = Utilities.splitRowAndColumn(db.numberOfSeats(clas,
				Integer.parseInt(screen)));
		for (int i = 0; i < rac[0]; i++) {
	%>
	<tr>
		<%
			for (int j = (rac[1] * i) + 1; j < ((i + 1) * rac[1]) + 1; j++) {
		%>
		<td>
		<button name="seatno" value="<%=j%>"
			<%if (db.isSeatAloted(j, Date.valueOf(dat), clas,
							Integer.parseInt(screen), show)) {%>
			disabled="disabled"
			<%if (db.bookedForSameBId(Integer.parseInt(strbid), j)) {%>
			style="background-color: yellow;" <%} else {%>
			style="background-color: red;" <%}
					}%>><%=j%></button>
		</td>
		<%
			}
		%>
	</tr>
	<%
		}
	%>
</table>
</form>
</center>
</body>
</html>