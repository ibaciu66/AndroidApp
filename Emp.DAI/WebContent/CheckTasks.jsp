<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<style>
div {
  border-left: 6px solid blue;
  background-color: lightgrey;
}

tr{
	font-size: 25px;
	
}
th{
	font-size: 25px;
	padding-right:20px
}

td{
	padding-right:25px;
	
}

tr:nth-child(even){
		background: #dae5f4;
	}

</style>

<body>
<%@ page import="com.emp.dao.DAO" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>

<form method="post" action="LogoutServlet">
<input type="submit" style="float:right" value="Logout">
</form>

<div><p style="font-size:30px; color:blue">[Manager] <%out.print(session.getAttribute("NUME"));%></p></div>
<br>

<br>
<table>
	<tr>
		<th>Title</th>
		<th>Deadline</th>
		<th>Comment</th>
		<th>Submitted by</th>
		<th></th>
		<th></th>
	</tr>
	<% 
		int id_task;
		String title;
		String deadline;
		String comment;
		int id_prog;
		String nume_prog = null;
		
		Connection con = DAO.createConnection();
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM SUBMIT_TASK");
		while(rs.next()){
			id_task = rs.getInt("ID");
			title = rs.getString("TITLE");
			deadline = rs.getString("DEADLINE");
			comment = rs.getString("COMMENT");
			id_prog = rs.getInt("ID_PROG");
			Statement s = con.createStatement();
			ResultSet res = s.executeQuery("SELECT NUME FROM PROGRAMMERS WHERE ID="+id_prog);
			while(res.next()){
				nume_prog = res.getString("NUME");
			}
			out.println("<tr>");
			out.println("<td align=\"center\">"+title+"</td>");
			out.println("<td align=\"center\">"+deadline+"</td>");
			out.println("<td align=\"center\">"+comment+"</td>");
			out.println("<td align=\"center\">"+nume_prog+"</td>");
			out.println(String.format("<td><button id=%d onclick='approve(this.id)'>Approve Task</button></td>",id_task));
			out.println(String.format("<td><button id=%d onclick='decline(this.id)'>Decline Task</button></td>",id_task));
			out.println("</tr>");
		}
	%>
</table>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script>
function approve(id){
	$.ajax({
		type: "POST",
		url: "ApproveTaskServlet",
		data: { taskID: id },
		success: function (result) {
					location.reload();
					alert("Task was approved!"); 
				}
	});
}

function decline(id){
	$.ajax({
		type: "POST",
		url: "DeclineTaskServlet",
		data: { taskID: id },
		success: function (result) {
					location.reload();
					alert("Task was declined!"); 
				}
	});
}
</script>

</body>
</html>