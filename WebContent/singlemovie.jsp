<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="project2.Movie" %>
<%@page import="project2.Star" %>
<%@page import="project2.SingleMovieItem"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);
	//prevents caching at the proxy server
%>

<%
	SingleMovieItem singleMovieItem = (SingleMovieItem)request.getAttribute("singleMovie");
	Movie movie = singleMovieItem.getMovie();
	List<Star> stars = singleMovieItem.getStars();
	List<String> genres = singleMovieItem.getGenres();
	String headerString = "Information about " + movie.getTitle();
%>
<html>
<head>
	<link rel="stylesheet" type="text/css"
		href="http://localhost:8080/project4/css/bootstrap-superhero.min.css">
	<link href="${pageContext.request.contextPath}/css/stylesheet.css" rel="stylesheet" >
	
</head>
<body>
	<div class = "container">
		<div class="row">
			<div class="col-sm-6">
				<H1 class="text-center"><%=headerString%></H1>
			</div>
		</div>
		<div class="row" id ="buttons">
			<div class="col-sm-offset-7 col-sm-5">
				<div class="btn-group btn-group-justified pull-right">
						<a class="btn btn-default" 
							href="/project4/servlet/SearchMovies">Movie Search</a>
						<a class="btn btn-default" 
							href="/project4/servlet/BrowseMovies">Movie Browser</a>
						<a class="btn btn-default" 
							href="/project4/servlet/ShoppingCart">Shopping Cart</a>
				</div>
			</div>
		</div>
		<div class="row" style="padding-top:30px">
		
			<div class="col-lg-2 col-md-2 col-sm-1">
				<img src="<%=movie.getBannerUrl()%>" alt='Movie banner loading.' height="150" width="150"/>
			</div>
			<div class="col-lg-7 col-md-7 col-sm-1">
				<h3><%=movie.getTitle()%></h3>
					<form action="AddToCart" id = 'cartForm' method="POST">
						<input type="submit" id ="addtocart" class="btn btn-info btn-sm" style="padding:4px;" value="Add to Cart">
						<input type="hidden" name=movie_id value=<%=movie.getId()%>>
					</form>
			 	<p><%=movie.getTitle() %> was directed by <%=movie.getDirector() %> and was released in <%=movie.getYear() %>.</p>
			 	<% if(movie.getTrailerUrl() == null) { %>
			 	<p>Unfortunately, There is no movie trailer available for this film.</p>
			 	<%} else { %>
			 	<p>You can view the trailer for <%=movie.getTitle()%> <a href='<%=movie.getTrailerUrl()%>'>here</a>.</p>
			 	<%} %>
			 	<p><%=movie.getTitle() %> belongs to the following genres: <%for(String genre: genres){ %>
			 		<a href="/project4/servlet/MovieList?genre=<%=genre%>"><%=genre %></a>
			 		<%} %>
			 	</p>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-1">
			<h3>Stars in this film....</h3>
			<%
				if(stars != null) {
					for(Star star : stars) {
						String singleStarString = "/project4/servlet/SingleStar?starId="+ star.getId(); 
						String fullname = star.getFirstName() + " " + star.getLastName();
						%>
						
						<a href='<%=singleStarString%>'> <%=fullname %></a><br/>
						<% } } %> 
		
		</div>

		</div>
		</div>
	

</body>
</html>