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

@WebServlet("/RemoveUserServlet")
public class RemoveUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RemoveUserServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("userID"));
		Connection con = DAO.createConnection();
		try {
			PreparedStatement st;
			String[] querys = {"DELETE FROM USERS WHERE USERNAME IN (SELECT USERNAME FROM PROGRAMMERS WHERE ID=?)",
								"DELETE FROM PROGRAMMERS WHERE ID=?"};
			for(String query : querys) {
				st = con.prepareStatement(query);
				st.setInt(1, id);
				st.executeUpdate();
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
