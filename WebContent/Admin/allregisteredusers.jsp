<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body id="page2">
<%@ page import="com.cinemall.data.UserData"%>
<%
	String id = (String) session.getAttribute("userName");
	UserData ud = new UserData();
	if (id == null) {

		response.sendRedirect("../hom.jsp");
	} else if (!ud.getAccess(id))
		response.sendRedirect("../User/userprofile.jsp");
	ud.close();
	int pno = 0;
	String pgno = request.getParameter("page");
	if (pgno != null) {
		pno = Integer.parseInt(pgno);
	}
	UserData d = new UserData();
	ArrayList<String> userList = d.getAllUsers();
	int max = userList.size();
%>
<div><jsp:include page="headder.jsp" /></div>
<script type="text/javascript" src="validate.js">

</script>
<br>
<div align="center">
<br>
<form action="" name='hid'><input type="hidden" name='page'
	value="<%=pno%>"> <input type="hidden" name='maxpage'
	value="<%=((max % 10) - 1)%>"> <input type="submit"
	onclick="var val=document.forms['hid'].page;val.value-=1;" name="back"
	value='back'> <input type="submit"
	onclick="var val=document.forms['hid'].page;val.value=parseInt(val.value)+1;"
	name="next" value='next'></form>
</div>

<script type="text/javascript">
	if (document.forms['hid'].page.value == '0') {
		document.forms['hid'].back.disabled = true;
	}
	if (document.forms['hid'].page.value == document.forms['hid'].maxpage.value) {
		document.forms['hid'].next.disabled = true;
	}
	document.getElementById('edit').visibility = "hidden";

</script>
<br>
<table border="1px" cellpadding="2" cellspacing=0 bordercolor="blue"
	align="center">
	<tr>
		<th width=30>No.</th>
		<th width=100>User Id</th>
		<th width=100>User Name</th>
		<th width=200>Email</th>
		<th width=110>Pnone No</th>
		<th width=100>PassWord</th>
		<th>Access</th>
	</tr>
	<%
		for (int i = (pno * 10); (i < max && i < ((pno + 1) * 10)); i++) {
			String pid = userList.get(i);
	%><tr>
		<td><%=(i + 1)%></td>
		<td align="center"><input type="button"
			onmousedown="userEditer(this)" value="<%=pid%>" name='<%=pid%>'></input></td>
		<td><%=d.getUserName(pid)%></td>
		<td><%=d.getEmail(pid)%></td>
		<td><%=d.getPhoneNumber(pid)%></td>
		<td>********</td>
		<td><%=d.getAccess(pid)%></td>
	</tr>

	<%
		}
		d.close();
	%>
</table>


<div id='edit' class="edit_not_avail">

<fieldset>
<form action="../ChangeUserAccess" method="post" name='ed'>
<input type='text' name='usrid'>
<select name="level">
<option value=9>manager</option>
<option value=8>administrator</option>
</select>
<input type="submit">
</form>
</fieldset>
</div>
</body>
</html>