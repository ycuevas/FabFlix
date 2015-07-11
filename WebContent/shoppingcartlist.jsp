<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="project2.MovieCart"%>
<%@ include file="partials/_shared.html" %>
<html>
<body>
	<%
		

	%>
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
					<%if (request.getParameter("error") != null){%>
					<%@ include file="partials/_creditcardError.html"%>
					<%}%>	
					<%if (request.getParameter("success") != null){%>
					<%@ include file="partials/_checkoutSuccess.html"%>
					<%}%>	 
				<H1 class="text-center">Shopping Cart</H1>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-4">
				<p class="text-info">Press "Checkout" to process your order.</p>
			</div>
			<div class="col-sm-4 col-sm-offset-4" style="padding-bottom: 10px;">
				<button class="btn btn-default" type="button"
					onclick="location.href='/project4/servlet/SearchMovies'">Movie 
					Search</button>
				<button class="btn btn-default" type="button"
					onclick="location.href='/project4/servlet/BrowseMovies'">Movie
					Browser</button>
				<button class="btn btn-default" type="button" 
					onclick="location.href='/project4/Checkout.jsp'">Checkout</button>
			</div>
		</div>

		<div class="row">
			<div class="col-sm-12">
				<table class="table table-striped table-condensed">
					<tr>
						<th width="20%">Title</th>
						<th width="15%">Year</th>
						<th width="15%">Director</th>
						<th width="15%">Trailer</th>
						<th width="15%">Banner</th>
						<th>Quantity</th>
						<th></th>
					</tr>
					<%
						List<MovieCart> movies = (List<MovieCart>) request.getAttribute("movies");
						if (movies != null) {
							if (movies.size() > 0) {
								for (MovieCart movie : movies) {
					%>
					<tr class="text-left">
						<td><%=movie.getTitle()%></td>
						<td><%=movie.getYear()%></td>
						<td><%=movie.getDirector()%></td>
						<%
							if (movie.getTrailerUrl() == null) {
						%>
						<td>Trailer unavailable.</td>
						<%
							} else {
						%>
						<td><a href="<%=movie.getTrailerUrl()%>">Trailer</a></td>
						<%
							}
						%>
						<%
							if (movie.getBannerUrl() == null) {
						%>
						<td>Banner unavailable.</td>
						<%
							} else {
						%>
						<td><img style="width: 48px; height: 48px;"
							class="img-thumbnail" src="<%=movie.getBannerUrl()%>"
							alt="<%=movie.getTitle()%> banner" /></td>
						<%
							}
						%>
						<td>
							<form class="form-group" action="UpdateQuantity" method="POST">
								<div class="input-group">
									<input type="text" class="form-control" name="quantity" value=<%=movie.getQuantity()%> />
									<span class="input-group-btn">
								      <input type="submit" class="btn btn-primary" value="Update"> 
								    </span>
								</div>
								<input type="hidden" name="movie_id" value=<%=movie.getId()%>>
							</form>
						</td>
					</tr>
					<%
						}
							} else {
					%>
					<tr>
						<td colspan="5"><span class="text-warning">Your shopping cart is empty.</span></td>
					</tr>
					<%
						}
						}
					%>
				</table>


			</div>
		</div>
</body>
</html>