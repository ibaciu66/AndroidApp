package com.emp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emp.dao.DAO;

/**
 * Servlet implementation class ApproveTaskServlet
 */
@WebServlet("/ApproveTaskServlet")
public class ApproveTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ApproveTaskServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int task_id = Integer.parseInt(request.getParameter("taskID"));
		Connection con = DAO.createConnection();
		try {
			PreparedStatement stm = con.prepareStatement("DELETE FROM SUBMIT_TASK WHERE ID=?");
			stm.setInt(1, task_id);
			stm.executeUpdate();
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DAO.closeConection(con);
	}

}
