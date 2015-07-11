<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="partials/_shared.html"%>
<%@page import="project2.Movie"%>
<%@ page import="java.util.*" %>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);
	//prevents caching at the proxy server
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search List</title>
</head>
<body>
	<div class = "container">
		<div class = "row" >
			<div class="col-sm-12">
				<table class="table table-striped table-condensed">
					<tr>
						<th width="20%"><a class="text-info"
							href="#">Title</a></th>
						<th width="20%"><a class="text-info"
							href="#">Year</a></th>
						<th width="20%">Director</th>
						<th width="20%">Star</th>
						<th width="20%"></th>
					</tr>
					<%
						List<Movie> movies = (List<Movie>) request.getAttribute("movies");
						if (movies != null) {
							if (movies.size() > 0) {
								for (Movie movie : movies) {
					%>
					<tr class="text-left">
						<td><%=movie.getTitle()%></td>
						<td><%=movie.getYear()%></td>
						<td><%=movie.getDirector()%></td>
						<td><%=movie.getStarFirstName()%></td>
						<td><%=movie.getStarLastName()%></td>
					
					</tr>
					<%
						}
						}
						}
					%>
				</table>
			</div>
		</div>
	</div>
</body>
</html>