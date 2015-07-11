<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="project2.Movie" %>
<%@page import="project2.Star" %>
<%@page import="project2.SingleStarItem"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);
	//prevents caching at the proxy server
%>
<%@ include file="partials/_shared.html"%>
<%
	SingleStarItem singleStarItem = (SingleStarItem)request.getAttribute("singleStar");
	Star star = singleStarItem.getStar();
	List<Movie> movies = singleStarItem.getMovies();
	String fullName = star.getFirstName() + " " + star.getLastName();
	String headerString = "Information about " + fullName;
%>
<body>
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<H1 class="text-center"><%=headerString%></H1>

			</div>
		</div>
		<div class="row">
			<div class="col-sm-offset-7 col-sm-5">
			<div class="btn-group btn-group-justified pull-right">
				<a class="btn btn-default" href="/project4/servlet/SearchMovies">Movie Search</a>
					
					<a class="btn btn-default" 
						href="/project4/servlet/BrowseMovies">Movie Browser</a>
						
					<a class="btn btn-default" 
						href="/project4/servlet/ShoppingCart">Shopping Cart</a>
				</div>
			
				
			</div>
		</div>
		<div class="row" style="padding-top:30px">
		
			<div class="col-sm-2">
				<img src="<%=star.getPhotoUrl()%>" alt='Photo loading.'/>
			</div>
			<div class="col-sm-6">
				<h3><%=fullName%></h3>
			 	<p>First Name : <%=star.getFirstName() %></p>
			 	<p>Last Name : <%=star.getLastName() %></p>
			 	<p>Birth Date : <%= star.getDateOfBirth() %></p>
			</div>
			<div class="col-sm-4">
			<h3><%=fullName %> has appeared in the following films...</h3>
			<%
				if(movies != null) {
					for(Movie movie : movies) {
						String singleMovieString = "/project4/servlet/SingleMovie?movieId="+ movie.getId(); 
						%>
						
						<a href='<%=singleMovieString%>'> <%=movie.getTitle() %></a><br/>
						<% } } %> 
		
		</div>

		</div>
		</div>
	

</body>
</html>