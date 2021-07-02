/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Aswin
 */
public class NewUser extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			/* TODO output your page here. You may use following sample code. */
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet AddPatientServlet</title>");
			out.println("<link rel=\"stylesheet\" href=\"resultstyle.css\">");			
			out.println("</head>");
			out.println("<body>");
			out.println("<div class=\"panel\">");

//			out.println("<h1>Servlet AddPatientServlet at " + request.getContextPath() + "</h1>");

			String uid, name, dob, gender, email, password, uni;
			uid = request.getParameter("uid");
			name = request.getParameter("name");
//			gender = request.getParameter("gender");
			dob = request.getParameter("dob");
			email = request.getParameter("email");
			password = request.getParameter("password");
			uni = request.getParameter("uni");

			try{  
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/BLOG_SYSTEM","root","root");
				PreparedStatement stmt=con.prepareStatement("INSERT INTO USER VALUES(?, ?, ?, ?, ?, ?);"); 
				stmt.setString(1, uid);
				stmt.setString(2, password);
				stmt.setString(3, email);
				stmt.setString(4, name);
				stmt.setString(5, uni);
				stmt.setString(6, dob);
//				stmt.setString(7, gender);

				try{
					int rs = stmt.executeUpdate();
					if(rs!=0){
//					out.println("inserted successfully");
					HttpSession session = request.getSession();
						session.setAttribute("user", uid);
					RequestDispatcher rd = request.getRequestDispatcher("/homepage.html");
						rd.forward(request, response);
					}
				}
				catch(SQLException e){
					if(e.getErrorCode() == 1062 ){
						out.println("<a href>this user id already present");
					}
					else{
						out.println(e.getErrorCode());
						out.print(e);

					} 

				}
			}
			catch(Exception  e){
				out.println(e);
			}
			
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
		}
		
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
