package com.cinemall.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cinemall.Accessories.Utilities;
import com.cinemall.data.UserData;

/**
 * Servlet implementation class EditUserData
 */
public class EditUserData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditUserData() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String oldpass = request.getParameter("oldpass");
			String newpass = request.getParameter("newpass");
			String conpass = request.getParameter("conpass");
			String email = request.getParameter("email");
			String addr = request.getParameter("add");
			String phn = request.getParameter("phn");
			HttpSession sess = request.getSession();
			String er=new String();
			String id = (String) sess.getAttribute("userName");
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			UserData db = new UserData();
			if ((oldpass != null) && (db.checkPassword(oldpass, id))
					&& (newpass != null) && (conpass != null)
					&& (newpass.equals(conpass))) {
				db.setPassword(id, newpass);
				response.getWriter().write("<valid>Password Re-Set</valid>");
			} else if ((email != null) ) {
				if(Utilities.validEmailId(email)){
				db.setEmailId(id, email);
				response.getWriter().write("<valid>Email Id Re-Set</valid>");
				}else{
					er="not a vlid Email address";
					response.getWriter().write("<valid>false</valid>\n");
					//response.getWriter().write("<valid>"+er+"</valid>");
					}
			} else if ((addr != null)/* &&(edit.equals("Change Email")) */) {
				db.setAddress(id, addr);
				response.getWriter().write("<valid>Address Re-Set</valid>");
			} else if ((phn != null) && (Utilities.validPhoneNo(phn))) {
				db.setPhoneNo(id, Long.parseLong(phn));
				response.getWriter().write(
						"<valid>Phone Number Id Re-Set</valid>");
			} else {
				response.getWriter().write("<valid>false</valid>\n");
			//	response.getWriter().write("<valid>"+er+"</valid>");
			}
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String oldpass = request.getParameter("oldpass");
			String newpass = request.getParameter("newpass");
			HttpSession sess = request.getSession();
			String id = (String) sess.getAttribute("userName");
			UserData db = new UserData();
			if ((oldpass != null) && !db.checkPassword(oldpass, id)
					&& (newpass != null)) {
				db.setPassword(id, newpass);
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("<valid>true</valid>");
			} else {
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				response.getWriter().write("<valid>false</valid>");
			}
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
