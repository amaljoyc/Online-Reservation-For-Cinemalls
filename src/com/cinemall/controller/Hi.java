package com.cinemall.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cinemall.data.UserData;

/**
 * Servlet implementation class Hi
 */
public class Hi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Hi() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, 
    		HttpServletResponse response)throws ServletException, IOException {

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
    	String name = request.getParameter("name").trim();
    	String welcomeMessage;
    	UserData d=new UserData();
    	try{
    		if(d.ifExists(name)){
    			
    			String pass=request.getParameter("password");
    			if(d.checkPassword(pass, name)){
    				welcomeMessage = "Welcome "+name;
    				HttpSession session = request.getSession(true);
    				session.setAttribute( "userName", name );
    				if(d.adminAccess(name))
    					response.sendRedirect("Admin/allregisteredusers.jsp");
    				else if(d.getAccess(name))
    						response.sendRedirect("Manager/addnewfilm.jsp");
    					else
    						response.sendRedirect("User/userprofile.jsp");
    			}
    			else{
    				welcomeMessage="Wrong Password!!";
    				request.setAttribute("err", welcomeMessage);
    				request.getRequestDispatcher("hom.jsp").forward(request, response);
    			}
    		}
    		else{
    			welcomeMessage=name+" is  either not a registerd user!!\n or user name was wrong.";
    			request.setAttribute("err", welcomeMessage);
				request.getRequestDispatcher("hom.jsp").forward(request, response);
    		}		
    	}finally{
    		d.close();
    	}
	}
}
