/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aswin
 */
public class updateBlog extends HttpServlet {

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
			out.println("<title>Servlet updateBlog</title>");			
			out.println("<link rel=\"stylesheet\" href=\"resultstyle.css\">");			
			out.println("</head>");
			out.println("<body>");
			out.println("<div class=\"panel\">");
//			out.print("addblog");
//			out.println("<h1>Servlet AddPatientServlet at " + request.getContextPath() + "</h1>");

			String blogName, blogDate, blogContent, blogId, imageId;
//			int likes, dislikes;
			blogId	 = request.getParameter("bid");
			blogName = request.getParameter("name");
			blogContent = request.getParameter("content");
			out.print(blogId+blogName+blogContent);
//			imageId = request.getParameter("imgid");

//			likes=0;
//			dislikes=0;
			
			Date date = new Date();
			SimpleDateFormat sqlFormater = new SimpleDateFormat("YYYY-MM-dd");
			blogDate = sqlFormater.format(date);
			
//			out.print(blogDate);
			
			try{  
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/BLOG_SYSTEM","root","root");
				PreparedStatement stmt=con.prepareStatement("UPDATE blog SET bname=?, bdate=?, content=? WHERE bid=?;"); 
				stmt.setString(1, blogName);
				stmt.setString(2, blogDate);
				stmt.setString(3, blogContent);
				stmt.setString(4, blogId);

//
//				try{
					int rs = stmt.executeUpdate();
					if(rs!=0){
//						stmt = con.prepareStatement("INSERT INTO image VALUES(?, ?, ?);");
//						stmt.setString(1, blogId);
////						stmt.setString(2, imageId);
//						InputStream in = new FileInputStream("F:\\im.jpg");
//						stmt.setBlob(3, in);
//						
//						rs = stmt.executeUpdate();
//						
//						if(rs!=0){
							out.println(rs+"updated successfully");
							String forw = "viewblog?bid="+blogId;
							RequestDispatcher rd = request.getRequestDispatcher(forw);
							rd.forward(request, response);
//						}
						
					}
					
//				}
//				catch(SQLException e){
//					if(e.getErrorCode() == 1062 ){
//						out.println("this blog id already present");
//					}
//					else{
//						out.println(e.getErrorCode());
//						out.print(e);
//
//					} 
//				}
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
