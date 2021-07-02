/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

/**
 *
 * @author Aswin
 */
public class viewBlog extends HttpServlet {

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
			out.println("<title>Servlet ViewProfileServlet</title>");
//			out.print("	<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css\"/>");

			out.println("<link rel=\"stylesheet\" href=\"viewblog.css\">");				

			out.println("</head>");

			out.println("<body>");
			out.print("<div class=\"nav\"><a class=\"item\" href=\"homepage.html\">Home</a><a class=\"item\" href=\"newBlog.html\" >Add Blog</a><a class=\"item\" href=\"viewprofile\">view profile</a><a class=\"item\" href=\"logout\" >Logout</a></div>");
			out.println("<div class=\"panel\">");
			String blogName, blogDate, blogContent, blogId, imageId, uid;
			int likes, dislikes;
			
			blogId = request.getParameter("bid");
//			HttpSession session = request.getSession(false);
//			blogId = (String)session.getAttribute("user");
//			out.print(blogId);
			try{  
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/BLOG_SYSTEM","root","root"); 
				PreparedStatement stmt=con.prepareStatement("SELECT * FROM BLOG WHERE (bid=?)");  
				stmt.setString(1, blogId);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					uid = rs.getString(2);
					blogName = rs.getString(3);
					blogDate = rs.getString(4);
					likes = rs.getInt(5);
					dislikes = rs.getInt(6);
					blogContent = rs.getString(7);
					
					PreparedStatement stmtTag=con.prepareStatement("SELECT tag FROM tags WHERE (blog_id=?)");
					stmtTag.setString(1, blogId);
					ResultSet rsTag = stmtTag.executeQuery();
					out.print("<div class=\"tag\" >");

					while(rsTag.next())
						out.print(rsTag.getString(1));
					out.print("</div>");

//					out.print("<div class=\"tag\" ><p>"+rsTag.getString(1)+"</p></div>");
					out.print("<div class=\"head\"><h2>"+blogName+"</h2></div>");
					PreparedStatement stmtUser =con.prepareStatement("SELECT * FROM user WHERE (uid=?);");
					stmtUser.setString(1, uid);
					ResultSet rsUser = stmtUser.executeQuery();
					if(rsUser.next()){
						out.print("<div class=\"user-info\"><span>"+rsUser.getString("name")+"</span><span>"+rsUser.getString("email")+"</span></div>");
					}
					String imgId, imgLink;
					PreparedStatement stmtImg =con.prepareStatement("SELECT * FROM IMAGE WHERE (blog_id=?);");  
					stmtImg.setString(1, blogId);
					ResultSet rsImg = stmtImg.executeQuery();
					
					if(rsImg.next()){
						imgId = rsImg.getString(2);
						imgLink = rsImg.getString(3);
						out.print("<div class=\"blog-img\"><img src=\""+imgLink+"\"></div>");
					}
					HttpSession session = request.getSession(false);
					String user=(String)session.getAttribute("user");
					out.print("<div class=\"content\">"+blogContent+"</div>");
//					out.print("<div class=\"addons\" ><span class=\"addons-link\">"+likes+" likes </span><span class=\"addons-link\">"+dislikes+" dislikes </span></div>");
					if(uid.equals(user))
					  out.print("<div class=\"addons-left\" ><a class=\"addons-link\" href=\"deleteblog?bid="+blogId+"\">Delete</a> <a class=\"addons-link\" href=\"fetchbolg?bid="+blogId+"\">Edit</a></div>");
					PreparedStatement stmtComment =con.prepareStatement("SELECT * FROM COMMENT WHERE (bid=?);");
					stmtComment.setString(1, blogId);
					ResultSet rsC = stmtComment.executeQuery();
					out.print("<div class=\"comment\">");
					out.print("<form class=\"com-input\" method=\"get\" action=\"addcomment\"><input type=\"text\" name=\"bid\" value=\""+blogId+"\" id=\"sen-bid\" readonly><input type=\"text\" name=\"content\" placeholder=\"comment... \"class=\"inputfield\" required /><button>post</button></form>");
					out.print("<hr>");
					while(rsC.next()){
						out.print("<div class=\"com\" >");
//						out.print("<div class=\"com-ud\"><a class=\"item\" href=\"#\">"+rsC.getString(2)+"</a><span class=\"item\">"+rsC.getString(7)+"</span></div>");
						out.print("<span class=\"com-ud\" >"+rsC.getString(2)+"   #"+rsC.getString(7)+"</span>");
						out.print("<p class=\"com-cont\" >"+rsC.getString(4)+"</p>");
//						out.print("<p><span>"+rsC.getString(5)+"likes</span> <span>"+rsC.getString(6)+" Dislikes</span></p>");
						if(rsC.getString(2).equals(user))
							out.print("<a class=\"com-delete\" href=\"deletecomment?bid="+blogId+"&cid="+rsC.getString(1)+"\">Delete</a>");

//							out.print("<div class=\"addons\" ><a class=\"addons-link\" href=\"deletecomment?bid="+blogId+"&cid="+rsC.getString(1)+"\">Delete</a> <a class=\"addons-link\" href=\"fetchbolg?bid="+blogId+"\">Edit</a></div>");
						out.print("</div>");
					}
					out.print("</div>");	
				}
				con.close();  
			}
				catch(Exception e){ 
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
