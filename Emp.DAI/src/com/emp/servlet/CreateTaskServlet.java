package com.emp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emp.dao.DAO;

/**
 * Servlet implementation class CreateTaskServlet
 */
@WebServlet("/CreateTaskServlet")
public class CreateTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CreateTaskServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = (String)request.getParameter("title");
		String deadline = (String)request.getParameter("deadline");
		int id_prog = Integer.parseInt(request.getParameter("prog"));
		
		Connection con = DAO.createConnection();
		try {
			PreparedStatement st = con.prepareStatement("INSERT INTO TASKS VALUES(?,?,?)");
			st.setString(1, title);
			st.setString(2, deadline);
			st.setInt(3, id_prog);
			st.executeUpdate();
			RequestDispatcher view = request.getRequestDispatcher("ManagerServlet");
			view.forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
