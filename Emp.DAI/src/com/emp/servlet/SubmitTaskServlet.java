package com.emp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emp.dao.DAO;
import com.emp.models.Task;

@WebServlet("/SubmitTaskServlet")
public class SubmitTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SubmitTaskServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int task_id = Integer.parseInt(request.getParameter("TASK_ID"));
		String comment = (String)request.getParameter("Comment");
		String title = null;
		String deadline = null;
		int prog_id = 0;
		Connection con = DAO.createConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM TASKS WHERE ID="+task_id);
			while(rs.next()) {
				title = rs.getString("TITLE");
				deadline = rs.getString("DEADLINE");
				prog_id = rs.getInt("ID_PROG");
			}
			rs.close();
			st.close();
			PreparedStatement pst = con.prepareStatement("DELETE FROM TASKS WHERE ID=?");
			pst.setInt(1, task_id);
			pst.executeUpdate();
			pst.close();
			
			pst = con.prepareStatement("INSERT INTO SUBMIT_TASK VALUES(?,?,?,?,?)");
			pst.setInt(1, task_id);
			pst.setString(2, title);
			pst.setString(3, deadline);
			pst.setString(4, comment);
			pst.setInt(5, prog_id);
			pst.executeUpdate();
			pst.close();
			
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM TASKS WHERE ID_PROG="+prog_id);
			ArrayList<Task> tasks = new ArrayList<Task>();
			while(rs.next()) {
				Task task = new Task();
				task.setID(rs.getInt("ID"));
				task.setTitle(rs.getString("TITLE"));
				task.setDeadline(rs.getString("DEADLINE"));
				task.setID_prog(rs.getInt("ID_PROG"));
				tasks.add(task);
			}
			request.setAttribute("tasks", tasks);
			RequestDispatcher view = request.getRequestDispatcher("Programmer.jsp");
			view.forward(request, response);
			
		} catch (SQLException e) {
			DAO.closeConection(con);
			e.printStackTrace();
		}
		DAO.closeConection(con);
		
	}

}
