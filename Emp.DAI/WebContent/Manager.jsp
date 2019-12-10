<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
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
</head>
<body>

<form method="post" action="LogoutServlet">
<input type="submit" style="float:right" value="Logout">
</form>

<div><p style="font-size:30px; color:blue">[Manager] <%out.print(session.getAttribute("NUME"));%></p></div>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.emp.models.Programmer" %>
<a href='CheckTasks.jsp'>Go to submitted tasks</a>
<br>
<br>
<br>
<table>
	<tr>
		<th>Name</th>
		<th>No. Tasks in progress</th>
	</tr>
<%
	ArrayList<Programmer> programmers = (ArrayList<Programmer>)request.getAttribute("programmers");
	for(Programmer p : programmers){
		out.println("<tr>");
		out.println("<td align=\"center\">"+p.getNume()+"</td>");
		out.println("<td align=\"center\">"+p.getTasks()+"</td>");
		out.println("</tr>");
	}
%>
</table>
<br>
<button onclick='(function(){ document.getElementById("createTask").style.display="inline"; })();'>Create Task</button>
<br>
<br>
<form style="display:none"  method="post" id="createTask" action="CreateTaskServlet">
	<table>
		<tr>
			<td>Title: </td>
			<td><input type="text" name="title" required></td>
		</tr>
		<tr>
			<td>Deadline: </td>
			<td><input type="date" name="deadline" required></td>
		</tr>
		<tr>
			<td>To: </td>
			<td>
				<select name="prog">
					<%
						programmers = (ArrayList<Programmer>)request.getAttribute("programmers"); 
						for(Programmer p : programmers){
							out.println(String.format("<option value=%d>%s</option>",p.getID(),p.getNume()));
						}
					%>
				</select>
			</td>
		</tr>
	</table>
	<input type="submit" value="Submit">
</form>
</body>
</html>