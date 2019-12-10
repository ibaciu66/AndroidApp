<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2 align="center">Log In</h2>
<br>
<div align="center">
	<form action="LoginServlet" method="post">
	<table>
		<tr>
			<td>Username:</td>
			<td><input type="text" name="user" required></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input type="password" name="pass" required></td>
		</tr>
	</table>
	<br>
	<input type="submit" value="Log In">	
	</form>
	<%
		String failed = null;
		failed = (String)request.getAttribute("failed");
		if(failed!=null)
			out.println("<p style=\"color:red;\">Incorrect username or password</p>");
	%>
</div>
</body>
</html>