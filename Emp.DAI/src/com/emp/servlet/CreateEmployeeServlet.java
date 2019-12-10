package com.emp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.emp.dao.DAO;


@WebServlet("/CreateEmployeeServlet")
public class CreateEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String[] arguments = {"name","role","manager","username", "password"};
	
    public CreateEmployeeServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection con = DAO.createConnection();
		HashMap<String,String> params = new HashMap<String,String>();
		
		for(int i = 0 ; i < arguments.length; i++) {
			params.put(arguments[i], request.getParameter(arguments[i]));
		}
		
		try {
			PreparedStatement st = con.prepareStatement("INSERT INTO USERS VALUES(?,?,?)");
			st.setString(1, params.get("username"));
			st.setString(2, params.get("password"));
			st.setString(3, params.get("role"));
			st.executeUpdate();
			st.close();
				String mng = params.get("role");
			if(mng.equals("MANAGER")) {
				st = con.prepareStatement("INSERT INTO MANAGERS VALUES(?,?)");
				st.setString(1, params.get("name"));
				st.setString(2, params.get("username"));
				st.executeUpdate();
				st.close();
			}else {
				st = con.prepareStatement("INSERT INTO PROGRAMMERS VALUES(?,?,?)");
				st.setString(1, params.get("name"));
				st.setString(2, params.get("username"));
				st.setInt(3, Integer.parseInt(params.get("manager")));
				st.executeUpdate();
				st.close();
			}
			response.sendRedirect("UserCreated.html");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}