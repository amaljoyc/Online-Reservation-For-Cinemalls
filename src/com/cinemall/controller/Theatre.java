package com.cinemall.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cinemall.data.ShowData;

/**
 * Servlet implementation class Theatre
 */
public class Theatre extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Theatre() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShowData d = new ShowData();
		String errmsg = "ERROR:Some fields are missing!!! Enter again....";
		String smsg = "UPDATION SUCCESSFULL....!!!";
		HttpSession session = request.getSession(true);
		try {
			String s = (String) request.getParameter("screen");
			String clas = (String) request.getParameter("class");
			String o = (String) request.getParameter("option");
			String v = (String) request.getParameter("value");
			if ((v.equals("") || v == null) || (s.equals("") || s == null)
					|| (clas.equals("") || clas == null)
					|| (o.equals("") || o == null)) {
				session.setAttribute("err", errmsg);
				response.sendRedirect("Manager/manage.jsp");
			} else {
				int screen = Integer.parseInt(s);
				int option = Integer.parseInt(o);
				d.theatreInfo(screen, clas, option, v);
				session.setAttribute("success", smsg);
				response.sendRedirect("Manager/manage.jsp");
			}
		} finally {
			d.close();
		}

	}

}
