<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
ul, #myUL {
  list-style-type: none;
}

div {
  border-left: 6px solid blue;
  background-color: lightgrey;
}
.myUL {
  margin: 0;
  padding: 0;
}

.caret {
  cursor: pointer;
  -webkit-user-select: none; /* Safari 3.1+ */
  -moz-user-select: none; /* Firefox 2+ */
  -ms-user-select: none; /* IE 10+ */
  user-select: none;
}

.caret::before {
  content: "\25B6";
  color: black;
  display: inline-block;
  margin-right: 6px;
}

.caret-down::before {
  -ms-transform: rotate(90deg); /* IE 9 */
  -webkit-transform: rotate(90deg); /* Safari */'
  transform: rotate(90deg);  
}

.nested {
  display: none;
}

.active {
  display: block;
}

button {
	display:none;
}

li {
  margin: 6px;

}

</style>
</head>
<body>
<a href="index.jsp"><button class="btn" style="float:right">Logout</button></a>
<div><p style="font-size:30px; color:blue">[Admin] Remove User</p></div>

<%@ page import="com.emp.dao.DAO" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>


<ul class="myUL">
	<%
		Connection con = DAO.createConnection();
		Statement stm = con.createStatement();
		ResultSet rs = stm.executeQuery("SELECT ID, NUME FROM MANAGERS");
		while(rs.next()){
			String html = String.format("<li><span class=\"caret\">%s</span><ul class=\"nested\">",rs.getString("NUME"));
			ResultSet rs2 = con.createStatement().executeQuery("SELECT ID, NUME FROM PROGRAMMERS WHERE ID_MANAGER="
																+rs.getInt("ID"));
			while(rs2.next()){
				html += String.format("<li onclick=\"showButton(%d)\">%s&nbsp<button id=%d value=%d onclick='deleteUser(this.id)'>Delete</button></li>",
												rs2.getInt("ID"),
												rs2.getString("NUME"),
												rs2.getInt("ID"),
												rs2.getInt("ID"));
			}
			html+="</ul></li>";
			out.println(html);
		}
	%>  
</ul>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>

<script>
var toggler = document.getElementsByClassName("caret");
var i;

for (i = 0; i < toggler.length; i++) {
  toggler[i].addEventListener("click", function() {
    this.parentElement.querySelector(".nested").classList.toggle("active");
    this.classList.toggle("caret-down");
  });
}

function showButton(id){
	document.getElementById(id).style.display="inline";
    var list = document.getElementsByTagName("button");
    var i;
    for(i=0;i<list.length;i++){
    	if(list[i].id!=id){
        	list[i].style.display="none";
        }
    }   
}

function deleteUser(id){
	$.ajax({
		type: "POST",
		url: "RemoveUserServlet",
		data: { userID: id },
		success: function (result) {
					location.reload();
					alert("User was successfully removed!"); 
				}
	});
}
</script>

</body>
</html>