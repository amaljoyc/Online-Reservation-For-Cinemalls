package com.cinemall.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import com.cinemall.data.SendSms;
import com.cinemall.data.UserData;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String error;   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
    }
boolean validateUserData(String name, String pass, String conpass,
			String email) {
    	UserData d = new UserData();
		boolean val=true;
		//Set the email pattern string
	      Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

	      //Match the given string with the pattern
	      Matcher m = p.matcher(email);

	      //check whether match is found 
	      boolean matchFound = m.matches();

	      if (!matchFound){
	       error="Invalid Email Id.";
	       val=false;
	      }else if(!pass.equals(conpass)){
	    	  error="Password and confirm password are different!!.";
	    	  val=false;
	      }
	      else if(d.ifExists(name)){
	    	  error="The username is alredy in use try another one!!.";
	    	  val=false;
	      }
	      d.close();
		return val;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{String targetId = request.getParameter("id");
		UserData db = new UserData();
	    if ((targetId != null) && !db.ifExists(targetId.trim())&&(targetId.length()>6)) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write("<valid>true</valid>");
        } else {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write("<valid>false</valid>");
        }
	    }catch(Exception e){
	    	e.printStackTrace();
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		UserData db = new UserData();
		HttpSession session=request.getSession();
		String uname = request.getParameter("id");
		String name = request.getParameter("name");
		String pass = request.getParameter("password");
		String conpass = request.getParameter("conPassword");
		String email = request.getParameter("eMail");
		String add=request.getParameter("add");
		try {
			long ph=Long.parseLong(request.getParameter("phn"));
			if (validateUserData(uname, pass, conpass, email)) {
				db.addNewUser(uname,name, pass, email,add,ph);
//				String text=new String("<html><body color='#DE407A'><h1>Welcome to our site.</h1>Your User Details are:<br>User name:"+
//						name+"<br>Password :"+pass+"<br></body></html>");
				
				out.println("<html><head><title>Helo" + name + " </title><link rel='shortcut icon' href='images/favicon.ico'><link rel='icon' type='image/gif' href='images/animated_favicon1.gif'>");
				out.println("</head><body><h1>");
				out.println("Congratulation You Are Registered</h2><BR>");
				out.println("<a href=\"index.jsp\">Log In</a></body></html>");
				//SendSms s=new SendSms();
				//s.send(request.getParameter("phn"),"Welcome to cinimall");
				//s.close();
//				MailClient m=new MailClient();
//				m.send(email, "Welcome !!!", text);
			}else{
				session.setAttribute("err", error);
				response.sendRedirect("register.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("err", "Phone Number"+e);
			response.sendRedirect("register.jsp");
			
		} finally {
			out.close();
			db.close();
		}
	}
	}

