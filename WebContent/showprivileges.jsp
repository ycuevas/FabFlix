<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Privileges</title>
</head>
	
<body>
<%@page import="java.util.List"%>
<%@ include file="partials/_shared.html"%>

<%if (request.getParameter("error") != null){%>
<%@ include file="partials/_addUserError.html"%>
<%}%>	
<%if (request.getParameter("success") != null){%>
<%@ include file="partials/_addUserSuccess.html"%>
<%}%>
	<div class = "container">
		<div class = "row">
			<h3>User Information</h3>
				<div class = "row">	
					<button class="btn btn-default" type="button" 
					onclick="location.href='<%=request.getContextPath()%>/servlet/Administration'" >
					Go to Administration</button>	
					<button class="btn btn-default" type="button" 
					onclick="location.href='<%=request.getContextPath()%>/AddUser.jsp'" >
					Add a New User</button>	
				</div>
			<%List<List<String>> userData = (List<List<String>>)request.getAttribute("userData");%>
			
			<table class="table">
				<tr>
					<th>Username</th>
					<th>Host</th>
					<th>Select Privilege</th>
					<th>Insert Privilege</th>
					<th>Update Privilege</th>
					<th>Delete Privilege</th>
					<th>Execute Privilege</th>
				</tr>
				
				<% for (int i = 0; i != userData.size(); i++){%>
				 	<tr>
				 
						<%for(int j = 0; j != userData.get(i).size(); j++){%>
					
							<td><%=userData.get(i).get(j)%></td>
						<%}%>
					</tr>
				<%}%>
			</table>
		</div>
	</div>
</body>
</html>