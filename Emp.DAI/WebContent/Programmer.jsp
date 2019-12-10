<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
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
</style>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form method="post" action="LogoutServlet">
	<input type="submit" style="float:right" value="Logout">
</form>

<div><p style="font-size:30px; color:blue">[Programmer] <%out.print(session.getAttribute("NUME"));%></p></div>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.emp.models.Task" %>


	<%
		ArrayList<Task> tasks;
		tasks = (ArrayList<Task>)request.getAttribute("tasks");
		for(Task t : tasks){
			out.println("<form method=\"post\" action=\"SubmitTaskServlet\">");
			out.println("<table><tr><th width=\"40%\">Description</th><th width=\"20%\">Deadline</th><th width=\"5%\"></th><th width=\"25%\">Comments</th><th width=\"10%\"></th></tr>");
			out.println("<tr style=\"outline: thin solid red\">");
			out.println(String.format("<td align=\"center\">%s</td>",t.getTitle()));
			out.println(String.format("<td align=\"center\">%s</td>",t.getDeadline()));			
			out.println(String.format("<td align=\"center\"><input type =\"hidden\" value=%d name=\"TASK_ID\"></td>",t.getID()));
			out.println("<td align=\"center\"><input type =\"text\" name=\"Comment\"></td>");
			out.println("<td align=\"center\"><input type =\"submit\" value=\"Submit Task\"></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("</form>");

		}
	%>

</body>
</html>