package com.cinemall.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cinemall.data.MovieData;
import com.cinemall.data.ShowData;

/**
 * Servlet implementation class AddFilm
 */
public class AddFilm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddFilm() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		String fname = request.getParameter("fname");
		MovieData d = new MovieData();
		if (d.filmNameExist(fname)) {
			response.getWriter().write("<valid>false</valid>");
		} else {
			d.addNewFilm(fname);
			response.getWriter().write(
					"<valid>New film " + fname
							+ " was added to the database</valid>");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String submit = request.getParameter("submit");
		ShowData d = new ShowData();
		MovieData md = new MovieData();
		if (submit.equals("OK")) {
			String fname = request.getParameter("fname");
			HttpSession session = request.getSession();
			if (md.addNewFilm(fname))
				session.setAttribute("message", "Successfull");
			else
				session.setAttribute("message", "faild");
			response.sendRedirect("Manager/addnewfilm.jsp");
			d.close();
		} else if (submit.equals("Accept")) {
			try {
				String time = request.getParameter("time");
				int screen = Integer.parseInt(request.getParameter("screen"));
				int filmid = Integer.parseInt(request.getParameter("filmid"));
				Date date = new Date(0);
				Date date2 = new Date(0);
				Calendar cal = Calendar.getInstance();
				Calendar cal2 = Calendar.getInstance();

				date.setDate(Integer.parseInt(request.getParameter("date")));
				date.setMonth(Integer.parseInt(request.getParameter("month")) - 1);
				date.setYear(Integer.parseInt(request.getParameter("year")) - 1900);

				cal.setTime(date);

				date2.setDate(Integer.parseInt(request.getParameter("dat")));
				date2.setMonth(Integer.parseInt(request.getParameter("mon")) - 1);
				date2.setYear(Integer.parseInt(request.getParameter("yea")) - 1900);
				cal2.setTime(date2);
				//System.out.print("works"+date+"\n"+date2+"\n"+cal);
				for (; cal.before(cal2); cal.add(Calendar.DATE, 1)) {
					//System.out.print("works");
					date.setTime(cal.getTimeInMillis());
					if (d.showExists(date, screen, time)) {
						d.updateShow(date, screen, time, filmid);
					} else {
						d.addShow(date, screen, time, filmid);
					}
				}
				request.setAttribute("err", "Time Successfully Set");
			} catch (Exception e) {
				request.setAttribute("err", e);
			}
			d.close();
			response.sendRedirect("Manager/settime.jsp");
		}

	}

}
