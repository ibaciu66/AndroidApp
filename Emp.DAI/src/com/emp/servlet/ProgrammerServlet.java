package com.emp.servlet;

import java.io.IOException;
import java.sql.Connection;
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
import javax.servlet.http.HttpSession;

import com.emp.dao.DAO;
import com.emp.models.Task;

/**
 * Servlet implementation class ProgrammerServlet
 */
@WebServlet("/ProgrammerServlet")
public class ProgrammerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProgrammerServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con = DAO.createConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM TASKS WHERE ID_PROG='"+request.getSession().getAttribute("ID")+"'");
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
			e.printStackTrace();
		}
		
	}

}
