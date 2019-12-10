package com.emp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emp.dao.DAO;

/**
 * Servlet implementation class DeclineTaskServlet
 */
@WebServlet("/DeclineTaskServlet")
public class DeclineTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeclineTaskServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int task_id = Integer.parseInt(request.getParameter("taskID"));
		Connection con = DAO.createConnection();
		String titlu=null;
		try {
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("SELECT TITLE,DEADLINE,ID_PROG FROM SUBMIT_TASK WHERE ID="+task_id);
			while(rs.next()) {
				PreparedStatement ps = con.prepareStatement("INSERT INTO TASKS VALUES(?,?,?)");
				titlu = rs.getString("TITLE");
				ps.setString(1, titlu);
				ps.setString(2, rs.getString("DEADLINE"));
				ps.setInt(3, rs.getInt("ID_PROG"));
				ps.executeUpdate();
			}
			rs.close();
			s.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement stm = con.prepareStatement("DELETE FROM SUBMIT_TASK WHERE TITLE=?");
			stm.setString(1, titlu);
			stm.executeUpdate();
			stm.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
