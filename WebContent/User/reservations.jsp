<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.cinemall.data.BookingData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Resrvations</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel='shortcut icon' href='../images/favicon.ico'>
<link rel='icon' type='image/gif' href='../images/animated_favicon1.gif'>
</head>
<body id="page3" class="comonbody">
<%
	String un = (String) session.getAttribute("userName");
	if (un == null||(un.equals("anonimous"))){
		response.sendRedirect("../hom.jsp");
		}
	
%>
<div><jsp:include page="headder.jsp" /></div>
<table border="1px" cellpadding="2" cellspacing=0 bordercolor="blue"
	align="center">
	<tr>
		<th>Booking ID</th>
		<th>Show ID</th>
		<th>Class</th>
		<th>Date</th>
		<th>Amount</th>
		<th>Status</th>
	</tr>
	<%
		BookingData r = new BookingData();
		List<Integer> bookingList = r.getAllBooking(un);
		Iterator<Integer> bids = bookingList.iterator();
		while (bids.hasNext()) {
			int bid = bids.next();
	%><tr>
		<td><%=bid%></td>
		<td><%=r.getShowid(bid)%></td>
		<td><%=r.getClass(bid)%></td>
		<td><%=r.getDat(bid)%></td>
		<td><%=r.getAmt(bid)%></td>
		<td><%=r.getStatus(bid)%></td>
	</tr>
	<%
		}
		r.close();
	%>
</table>

</body>
</html>