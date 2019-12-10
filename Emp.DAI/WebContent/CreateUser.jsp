<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.emp.dao.DAO" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
    
<!DOCTYPE html>
<html>
<head>
<style>
div {
  border-left: 6px solid blue;
  background-color: lightgrey;
}
</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<a href="index.jsp"><button class="btn" style="float:right">Logout</button></a>
<div><p style="font-size:30px; color:blue">[Admin] Create User</p></div>
	<form action="CreateEmployeeServlet" method="post">
	<table>
		<tr>
			<td>Name:</td>
			<td><input type="text" name="name" required></td>
		</tr>
		<tr>
			<td>Role:</td>
			<td>
				<select name="role" id="roleSelect" onchange="showManager()">
					<option value="PROGRAMMER">Programmer</option>
    				<option value="MANAGER">Manager</option>
 				 </select>  
			</td>
		</tr>
		<tr id="manager">
			<td>Manager: </td>
			<td>
				<select name="manager">
				<% 	
					Connection con = DAO.createConnection(); 
					Statement stm = con.createStatement();
					ResultSet rs = stm.executeQuery("SELECT ID, NUME FROM MANAGERS");
					while(rs.next()){
						out.println(String.format("<option value=\"%s\">%s</option>", rs.getString("ID"), rs.getString("NUME")));	
					}
					DAO.closeConection(con);
					stm.close();
				%>
				</select>			
			</td>
		</tr>
		<tr>
			<td>Username:</td>
			<td><input type="text" name="username" required></td>
		</tr>
		<tr>	
			<td>Password:</td>
			<td><input type="password" name="password" required></td>
		</tr>
	</table>
	<br>
	<input type="submit" value="Create">	
	</form>

<script>
function showManager() {
  var x = document.getElementById("roleSelect").value;
  if(x=="MANAGER"){
	  document.getElementById("manager").style.display="none";  
  }else {
	  document.getElementById("manager").style.display="table-row";
  }
}
</script>
</body>
</html>