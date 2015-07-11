<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath}/css/stylesheet.css" rel="stylesheet" >
	<title>Administration</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	
	<script src="<%=request.getContextPath()%>/project2.js"></script>

</head>
<body>

<%@page import="java.util.ArrayList"%>
<%@include file="partials/_shared.html"%>

<div class = "container">
	<div class = "row">
		<div class = "text-center">
			<h1>Administration</h1>
		</div>
	</div>
</div>	
<div class = "container box">	
	<div class = "row" style="margin-top: 60px;">
		<div class = "col-lg-9 col-md-9 col-sm-9 text-center">
			<label for="selection" >Select User : </label>
			<select class = "form-fix" name = "options" form = "privilegeselect">
			
			<%	
			
				ArrayList<String> users = (ArrayList)request.getAttribute("userList");
				int i = 0;
				for(String user : users)
				{
				%>
				
				<option value = "<%=user%>"><%=user%></option>
				<% 
					i++;
				}
				
				%>
			</select>
			
		</div>
			
	</div>
	
	<div class = "row" style  = "margin-top: 30px;">
		<div class = "col-md-12 col-lg-12 col-sm-12" >
			<span class="label label-default col-md-12 col-lg-12 col-sm-12" style="font-size:107%;" >Schema Privileges</span>
			
			
		</div>
	</div>
	<form action = "Administration" id = "privilegeselect" method="get">
	
	</form>	
</div>


</body>
</html>