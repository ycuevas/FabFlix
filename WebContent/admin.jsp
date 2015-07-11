<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Administrator</title>
</head>
<body>
<%@ include file="partials/_shared.html"%>
<div class = "container">
	<%if (request.getParameter("error") != null){%>
		<%@ include file="partials/_adminError.html"%>
		<%}%>
	<div class = "row" >
		<div class="col-sm-6 col-sm-offset-3">
			<h1>FabFlix Admin Panel</h1>
		</div>
	</div>
  	<div class = "row" >
		<div class="col-sm-6 col-sm-offset-3">	
			<button class="btn btn-default" type="button" onclick="location.href='<%=request.getContextPath()%>/servlet/AllPrivileges'" >Show All Users</button>
			<button class="btn btn-default" type="button" onclick="location.href='<%=request.getContextPath()%>/servlet/Administration'" >Modify Users</button>
	
	
		</div>
	</div>
</div>

</body>
</html>