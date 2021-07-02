/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Aswin
 */
public class AddBlog extends HttpServlet {

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
		try (PrintWriter out = response.getWriter()) {
			/* TODO output your page here. You may use following sample code. */
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Add new Blog</title>");
			out.println("<link rel=\"stylesheet\" href=\"resultstyle.css\">");			
			out.println("</head>");
			out.println("<body>");
			out.println("<div class=\"panel\">");
			out.println("kjlf");

			String blogName, imgLink,blogDate, blogContent, blogId, imageId, uid, tag;
			int likes, dislikes;
			tag	 = request.getParameter("tag");
			HttpSession session = request.getSession(false);
			uid = (String)session.getAttribute("user");
//			uid	 = request.getParameter("uid");
//			Random random = new Random(System.nanoTime());
			out.println("kjlf");
//			int randomInt = random.nextInt(1000000000);
//			out.println(randomInt);
//			out.println("kjlf");
			long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
			blogId = String.valueOf(number);
//			out.print(number);
			blogName = request.getParameter("name");
			blogContent = request.getParameter("content");
//			imageId = request.getParameter("imgid");
			imgLink = request.getParameter("imglink");
			likes=0;
			dislikes=0;
			
			Date date = new Date();
			SimpleDateFormat sqlFormater = new SimpleDateFormat("YYYY-MM-dd");
			blogDate = sqlFormater.format(date);
			
			out.print(blogDate);
			
			try{  
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/BLOG_SYSTEM","root","root");
				PreparedStatement stmt=con.prepareStatement("INSERT INTO blog VALUES(?, ?, ?, ?, ?, ?, ?);"); 
				stmt.setString(1, blogId);
				stmt.setString(2, uid);
				stmt.setString(3, blogName);
				stmt.setString(4, blogDate);
				stmt.setInt(5, likes);
				stmt.setInt(6, dislikes);
				stmt.setString(7, blogContent);
//
				try{
					int rs = stmt.executeUpdate();
					if(rs!=0){
						stmt = con.prepareStatement("INSERT INTO image(blog_id, img) VALUES(?, ?);");
						stmt.setString(1, blogId);
//						stmt.setString(2, imageId);
						stmt.setString(2, imgLink);
						rs = stmt.executeUpdate();
						
						if(rs!=0){
							
							stmt = con.prepareStatement("INSERT INTO tags(blog_id, tag) VALUES(?, ?);");
							stmt.setString(1, blogId);
							stmt.setString(2, tag);
							rs = stmt.executeUpdate();

							if(rs!=0){
								String forw = "viewblog?bid="+blogId;
								RequestDispatcher rd = request.getRequestDispatcher(forw);
								rd.forward(request, response);
							}
							else{
								out.println("some problem on inserting tags");

							}

							
							
						}
						else{
							out.println("some problem on inserting image");

						}
						
					}
					else{
						out.println("some problem on inserting blog");

					}
					
				}
				catch(SQLException e){
					if(e.getErrorCode() == 1062 ){
						out.println("this blog id already present random number generator collided please retry!!!!");
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
