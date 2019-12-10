package com.emp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp.dao.DAO;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = DAO.createConnection();
		try {
			String user = (String)request.getParameter("user");
			String pass = (String)request.getParameter("pass");
			String sql = "SELECT U_TYPE FROM USERS WHERE USERNAME = ? AND PASS = ?";
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, user);
			st.setString(2, pass);
			ResultSet rs = st.executeQuery();
			
			if(!rs.next()) {
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
				request.setAttribute("failed", "failed");
				rd.forward(request, response);
			}else {
				String uType;
				do {
					uType = rs.getString("U_TYPE");					
				}
				while(rs.next());
				
				switch(uType) {
					case "ADMIN":
						response.sendRedirect("admin.html");
						break;
					case "PROGRAMMER":
						HttpSession session = request.getSession();
						Statement statement = con.createStatement();
						ResultSet r = statement.executeQuery("SELECT NUME,ID FROM PROGRAMMERS WHERE USERNAME='"+user+"'");
						while(r.next()) {
							session.setAttribute("NUME", r.getString("NUME"));
							session.setAttribute("ID", r.getInt("ID"));
						}
						r.close();
						statement.close();
						RequestDispatcher view = request.getRequestDispatcher("ProgrammerServlet");
						view.forward(request, response);
						break;
					case "MANAGER":
						session = request.getSession();
						statement = con.createStatement();
						r = statement.executeQuery("SELECT NUME,ID FROM MANAGERS WHERE USERNAME='"+user+"'");
						while(r.next()) {
							session.setAttribute("NUME", r.getString("NUME"));
							session.setAttribute("ID", r.getInt("ID"));
						}
						r.close();
						statement.close();
						view = request.getRequestDispatcher("ManagerServlet");
						view.forward(request, response);						
						break; 
				}
			}
			st.close();
			con.close();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
