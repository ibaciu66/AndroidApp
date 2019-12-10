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

import com.emp.dao.DAO;
import com.emp.models.Programmer;

@WebServlet("/ManagerServlet")
public class ManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ManagerServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nume = (String)request.getSession().getAttribute("NUME");
		
		Connection con = DAO.createConnection();
		try {
			ArrayList<Programmer> programmers = new ArrayList<Programmer>();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT ID,NUME FROM PROGRAMMERS WHERE ID_MANAGER='"+request.getSession().getAttribute("ID")+"'");
			while(rs.next()) {
				Programmer p = new Programmer();
				p.setID(rs.getInt("ID"));
				p.setNume(rs.getString("NUME"));
				programmers.add(p);
				Statement statement = con.createStatement();
				ResultSet r = statement.executeQuery("SELECT COUNT(ID) AS N_TASKS FROM TASKS WHERE ID_PROG="+p.getID());
				while(r.next()) {
					p.setTasks(r.getInt("N_TASKS"));
				}
				
			}
			request.setAttribute("programmers", programmers);
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher view = request.getRequestDispatcher("Manager.jsp");
		view.forward(request, response);
	}

}
