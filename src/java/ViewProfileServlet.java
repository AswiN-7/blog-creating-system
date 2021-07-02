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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Aswin
 */
public class ViewProfileServlet extends HttpServlet {

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
			out.print("	<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.4.1/semantic.min.css\"/>");
			out.println("<link rel=\"stylesheet\" href=\"resultstyle.css\">");				

			out.println("</head>");
			
			out.println("<body>");
			out.println("<div class=\"panel\">");
			out.print("<div class=\"ui attached stackable menu\"><div class=\"ui container\"><a class=\"item\" href=\"homepage.html\"><i class=\"home icon\"></i> Home</a><div class=\"right menu\"><a class=\"ui item\" href=\"newBlog.html\" >Add Blog</a><a class=\"ui item\" href=\"viewprofile\">view profile</a><a class=\"ui item\" href=\"logout\" >Logout</a></div></div></div>");
			String uid, name, dob, gender, email, password, uni;

//			uid = request.getParameter("uid");
			HttpSession session = request.getSession(false);
			uid = (String)session.getAttribute("user");

			try{  
				Class.forName("com.mysql.cj.jdbc.Driver");  
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/BLOG_SYSTEM","root","root"); 
				PreparedStatement stmt=con.prepareStatement("SELECT * FROM USER WHERE (uid=?)");  
				stmt.setString(1, uid);
				ResultSet rs = stmt.executeQuery();
				if(rs.next()){
					password = rs.getString(2);
					email = rs.getString(3);
					name = rs.getString(4);
					uni = rs.getString(5);
					dob = rs.getString(6);
//					if(rs.getString(7)=="m")
//						gender = "male";
//					else
//						gender = "female";
					
					out.println("<input class=\"inputfield\"  type=\"text\" name=\"pid\" value="+uid+" readonly>"); 
					out.println("<input class=\"inputfield\" type=\"text\" name=\"name\" value="+ name+" readonly>");
//					out.println("<input class=\"inputfield\" type=\"text\" name=\"gender\" value="+ gender+" readonly>");
					out.println("<input class=\"inputfield\" type=\"date\" name=\"dob\" value="+ dob+" readonly>");
					out.println("<input class=\"inputfield\" type=\"text\" name=\"email\" value="+ email+" readonly>");
					out.println("<input class=\"inputfield\" type=\"text\" name=\"password\" value="+ password+" readonly>");
					out.println("<textarea class=\"inputfield\" name=\"address\" cols=\"30\" rows=\"10\" readonly >"+uni+"</textarea>");

				}
				else{
					out.println("<div class=\"panel\">");
					out.println(uid+"is not present in the database please enter avaliable persons id");
					stmt=con.prepareStatement("SELECT uid FROM USER;");
					rs = stmt.executeQuery();
					while(rs.next()){
						out.println("<input class=\"inputfield\" value="+rs.getString(1)+" readonly>"); 
					}
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
