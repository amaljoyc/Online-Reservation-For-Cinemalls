<%@page import="com.cinemall.data.BookingData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

  <head>
     <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
     <title>Payment!</title>
  </head>

  <body class="comonbody">
  <%
	String un = (String) session.getAttribute("userName");
	if (un == null||(un.equals("anonimous"))){
		response.sendRedirect("../hom.jsp");
		}
	
%>
   <div><jsp:include page="headder.jsp" /></div>  
   <a href="tickebooking.jsp">Booking</a>-><a href="">Choose Seats</a>-><a href="">Pay</a>
   
     <center>
        <h1> Cash Payment!</h1>
     </center>
     <br>
     <h2>WELCOME...</h2>
     <p>Dear Customer, please enter your prepaid card details...
     <br><br><br>
     <center> 
        <form>
           Enter your prepaid card number :
           <br>
           <input type="text" name="cardno" size=20>
           <br><br>
           Enter your 4 digit pin number :
           <br>
           <input type="password" name="cpass" size=10>  
           <br><br>
           <input type="submit" value="Submit">
           <input type="reset">  
        </form>
     </center>
     
     <%BookingData d = new BookingData();
	   String cno= (String)request.getParameter("cardno");
	   String pass= (String)request.getParameter("cpass");
	   if(cno!=null&&pass!=null){
       boolean b=d.passCheck(cno,pass);
       if(b){%>
         <script language = JavaScript>
             document.writeln("Valid Details...")
         </script>
       <%}
       else{%>
         <script language = JavaScript>
            window.alert("ERROR : Invalid card number or pin number")
         </script><%}} %>
  </body>
</html> 