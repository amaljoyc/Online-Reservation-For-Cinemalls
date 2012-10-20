package com.cinemall.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cinemall.Accessories.MailClient;
import com.cinemall.data.BookingData;
import com.cinemall.data.MovieData;
import com.cinemall.data.ShowData;
import com.cinemall.data.UserData;

/**
 * Servlet implementation class ReserveTicket
 */
public class ReserveTicket extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String error;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReserveTicket() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("deprecation")
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			ShowData db = new ShowData();
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("userName");
			String fname = request.getParameter("fname");
			String d = request.getParameter("d");
			String m = request.getParameter("m");
			String y = request.getParameter("y");
			String screen = request.getParameter("screen");
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");

			String show;
			if (d != null && m != null && y != null) {
				Date shdate = new Date(0);
				shdate.setDate(Integer.parseInt(d));
				shdate.setMonth(Integer.parseInt(m) - 1);
				shdate.setYear(Integer.parseInt(y) - 1900);
				if (id != null && fname != null && screen != null) {
					MovieData md = new MovieData();
					show = db.getShowFor(shdate, md.getFilmId(fname), screen);
					md.close();
					System.out.print(show);
					response.getWriter().write("<valid>" + show + "</valid>");
				} else if (id != null && fname != null && screen == null) {
					MovieData md = new MovieData();
					screen = db.getScreenFor(shdate, md.getFilmId(fname));
					md.close();
					System.out.print(screen);
					response.getWriter().write("<valid>" + screen + "</valid>");
				} else if (id != null && fname == null && screen == null) {
					screen = db.getFilmsFor(shdate);
					System.out.print(screen + shdate);
					response.getWriter().write("<valid>titanic</valid>");
				} else {
					response.getWriter().write("<valid>false</valid>");
				}
				db.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("userName");
		int showid = 0;
		int bid = 0;
		String show = request.getParameter("show");
		String screen = request.getParameter("screen");
		String fname = request.getParameter("fname");
		String clas = request.getParameter("class");
		String bamt = request.getParameter("bamt");
		String notic = request.getParameter("notic");
		String seatno = request.getParameter("seatno");
		BookingData db = new BookingData();
		try {
			if (fname != null && screen != null && show != null && clas != null) {
				Date bdate = new Date(0);

				ShowData sd = new ShowData();
				bdate.setDate(Integer.parseInt(request.getParameter("d")));
				bdate.setMonth(Integer.parseInt(request.getParameter("m")) - 1);
				bdate.setYear(Integer.parseInt(request.getParameter("y")) - 1900);
				showid = sd.getShowId(bdate, Integer.parseInt(screen), fname,
						show);
				sd.close();
				Date bkdate = new Date(Calendar.getInstance().getTimeInMillis());
				if (bdate != null && showid != 0) {
					bid = db.makResrv(id, showid, clas, bkdate,
							Integer.parseInt(notic), Double.parseDouble(bamt),
							"ok");
					session.setAttribute("msg", "successfull");
					response.sendRedirect("User/seatchooser.jsp?bid=" + bid
							+ "&class=" + clas + "&screen=" + screen + "&show="
							+ show + "&date=" + bdate);
				} else {
					System.out.print(2);
				}

			} else if (seatno != null) {
				bid = Integer.parseInt(request.getParameter("bid"));
				

				Date dat = Date.valueOf(request.getParameter("date"));
				
					if (!db.isSeatAloted(Integer.parseInt(seatno), dat, clas,
							Integer.parseInt(screen), show)) {
						db.putSeat(seatno, bid);
					}
					int notick = db.getNumberOfSeats(bid);
					int curCount = db.getCountOfSeatsFor(bid);
				if (curCount < notick) {
					response.sendRedirect("User/seatchooser.jsp?bid=" + bid
							+ "&class=" + clas + "&screen=" + screen + "&show="
							+ show + "&date=" + dat);
				} else {
//					String msg="<html><body><h1>Omega Theaters</h1><table><tr><td>"+dat+"</td><td>"+bid+"</td><td>"+clas+"</td><td>"+screen+"</td><td>"+show+"</tr></table></body></html>";
//					UserData ud=new UserData();
//					MailClient.send(ud.getEmail(id), "Tickets Booked", msg);
//					ud.close();
					response.sendRedirect("User/reservations.jsp");
				}
			}
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

}
