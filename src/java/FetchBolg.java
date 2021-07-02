/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aswin
 */
public class FetchBolg extends HttpServlet {

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
			out.println("<title>fetch</title>");
//			out.print("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css\"/>");
			out.println("<link rel=\"stylesheet\" href=\"resultstyle.css\">");	
			out.println("</head>");
			out.println("<body>");
			String blogId = request.getParameter("bid");
			
			try{  
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BLOG_SYSTEM","root","root");
				
				PreparedStatement stmt=con.prepareStatement("SELECT * FROM BLOG WHERE (bid=?)");  
				stmt.setString(1, blogId);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					out.println("<form class=\"panel\" action=\"updateblog\" method=\"post\">");
					out.println("<div class=\"info\">");
//					out.println("<span class=\"item\" name=\"bid\" > blogId: "+rs.getString(1)+"</span>"); 
					out.println("<span class=\"item\" name=\"uid\" > UserId: "+rs.getString(2)+"</span>"); 
					out.println("<span class=\"item\" name=\"lastModified\" > last modified: "+rs.getString(4)+"</span>"); 
					out.println("<span class=\"item\" name=\"likes: \" > likes: "+rs.getString(5)+"</span>");
					out.println("<span class=\"item\" name=\"dislikes: \" > Dislikes: "+rs.getString(6)+"</span>"); 


					
//					out.println("<input class=\"inputfield\" type=\"text\" name=\"uid\" value="+ rs.getString(2)+" >");
//					out.println("<input class=\"inputfield\" type=\"date\" name=\"date\" value="+ rs.getString(4)+" >");
//					out.println("<input class=\"inputfield\" type=\"number\" name=\"likes\" value="+ rs.getInt(5)+" >");
//					out.println("<input class=\"inputfield\" type=\"number\" name=\"dislikes\" value="+ rs.getInt(6)+" >");
					out.println("</div>");
					out.println("<input class=\"inputfield\" type=\"text\" name=\"bid\" value="+ rs.getString(1)+" readonly >");

					out.println("<input class=\"inputfield\" type=\"text\" name=\"name\" value="+ rs.getString(3)+" >");
//					out.print("<div class=\"ui fluid icon input\"><input type=\"text\" name=\"name\" value="+ rs.getString(3)+" placeholder=\"blog Name\"></div>");
					out.println("<textarea class=\"inputfield\" placeholder=\"Write..\" name=\"content\" cols=\"70\" rows=\"30\">"+rs.getString(7)+"</textarea>");
					out.println("<button class=\"addons-link\">Update</button>");
					out.println("</form>");

				}
				// else{
				// 	out.println("<div class=\"panel\">");
				// 	out.println(pid+"is not present in the database please enter avaliable persons id");
				// 	stmt=con.prepareStatement("SELECT pid FROM patient_detials;");
				// 	rs = stmt.executeQuery();
				// 	while(rs.next()){
				// 		out.println("<input class=\"inputfield\" value="+rs.getString(1)+" readonly>"); 
				// 	}
				// }				
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
