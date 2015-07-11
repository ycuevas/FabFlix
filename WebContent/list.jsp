<head>
	<link href="${pageContext.request.contextPath}/css/stylesheet.css" rel="stylesheet" >
	
	<%-- <script src="<%=request.getContextPath()%>/project2.js" type="text/javascript"></script> --%>
	
</head>



<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="project2.Movie" %>
<%@page import="project2.Star" %>
<%@page import="project2.SingleMovieItem"%>
<%@page import="java.util.ArrayList"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);
	//prevents caching at the proxy server
%>
<%@ include file="partials/_shared.html"%>


<script var res = <%=request.getAttribute("resultList") %>>

</script>
<body>
	<%
		
		
		String sortOrder = request.getParameter("sortOrder");
		String titleSort = null;
		String yearSort = null;
		String numberOfPages =(String)request.getAttribute("numberOfPages");
		String pageSize = (String)request.getAttribute("pageSize");
		String currentPage = (String)request.getAttribute("currentPage");
		int currentPageInt = 1;
		
		
		
		if(currentPage != null){
			currentPageInt = Integer.parseInt(currentPage);	
		}
		int numberPagesInt = 1;
		if(numberOfPages != null){
			numberPagesInt = Integer.parseInt(numberOfPages);
		}
		int nextPage = currentPageInt + 1;
		int previousPage = currentPageInt - 1;
		if(previousPage == 0 ) previousPage = 1;
		if(nextPage > numberPagesInt) nextPage = numberPagesInt;
		String qs = request.getQueryString();
		String requestString = (String)request.getAttribute("templateString");
		String templateString = null;
		
		
		if (requestString == null || requestString.contains("MovieList"))
		{
			
			
			templateString = "/project4/servlet/MovieList?" + qs;
			
		}
		else
			templateString = requestString + qs;
		
		
	
		
		
		String nextPageString = templateString;
		String previousPageString = templateString; 
		
	
		
		if(qs != null && qs.contains("currentPage")){
			int len = templateString.length();
			int start= templateString.indexOf("currentPage=");
			int end = templateString.substring(start).indexOf("&");
		
		 
			if(end == -1){
				nextPageString = nextPageString.substring(0, len-1 ) + Integer.toString(nextPage);
				previousPageString = nextPageString.substring(0, len-1 ) + Integer.toString(previousPage);
			}else{
				String tempNextPageString = nextPageString.substring(start, end - 1) + Integer.toString(nextPage);
				String tempPreviousPageString = nextPageString.substring(start,end - 1) + Integer.toString(previousPage);
				nextPageString = templateString.substring(0, start) + tempNextPageString + templateString.substring(end, len-1);
				previousPageString = templateString.substring(0, start) + tempPreviousPageString + templateString.substring(end, len-1);
				
			}
		} else {
			nextPageString = nextPageString + "&currentPage="+Integer.toString(nextPage);
			previousPageString = previousPageString + "&currentPage="+Integer.toString(previousPage);
		}  
		
		
		
 		if (sortOrder == null) {
			titleSort = "TITLEASC";
			yearSort = "YEARASC";
		} else {
			if (sortOrder.equals("TITLEASC")) {
				titleSort = "TITLEDESC";
				yearSort = "YEARASC";
			} else if (sortOrder.equals("TITLEDESC")) {
				titleSort = "TITLEASC";
				yearSort = "YEARASC";
			} else if (sortOrder.equals("YEARASC")) {
				titleSort = "TITLEASC";
				yearSort = "YEARDESC";
			} else {
				titleSort = "TITLEASC";
				yearSort = "YEARASC";
			}
		}
 		
 		
		String genre = request.getParameter("genre");
		String titleStart = request.getParameter("title");
		String headerString = null;
		String sortString = null;
		
		if(templateString.contains("MovieList") || templateString == null)
		{
			
			if (genre != null) 
			{
					sortString = "/project4/servlet/MovieList?genre="+genre+"&sortOrder=";
					headerString = "Movies in the " + genre + " genre.";
					
	
			} 
			else if (titleStart != null)
			{
					sortString = "/project4/servlet/MovieList?title="+titleStart+"&sortOrder=";
					headerString = "Movies beginning with " + titleStart + ".";
			}
			
		}
		else
		{
			String movieTitle = request.getParameter("title");
			String movieYear = request.getParameter("year");
			String directorName = request.getParameter("DirectorName");
			String starFirstName = request.getParameter("firstName");
			String starLastName = request.getParameter("lastName");
			
		
				sortString = "/project4/servlet/SearchList?title="+movieTitle+"&year="+movieYear+
						"&DirectorName="+directorName+"&firstName="+starFirstName+
						"&lastName="+starLastName+"&sortOrder=";
				
	
			
		}
		

			
	

		
	%>

	
	
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<H1 class="text-center"><%=headerString%></H1>

			</div>
		</div>
		<div class="row">
			<div class="col-md-5">
				<div class="btn-group btn-group-justified">
					<a class="btn btn-default" href="/project4/servlet/SearchMovies">Movie Search</a>
						
						<a class="btn btn-default" 
							href="/project4/servlet/BrowseMovies">Movie Browser</a>
							
						<a class="btn btn-default" 
							href="/project4/servlet/ShoppingCart">Shopping Cart</a>
				</div>
			</div>
			
			<div class = "col-md-7 col-md-push-4" >
				<form class="navbar-form" role="search">
			        <div class="input-group">
			            <input type="text" class="form-control" data-toggle="dropdown" autocomplete="off" placeholder="Search" name="srch-term" id="srch-term">
			            	<ul class="dropdown-menu" role="menu" id = "suggestions">
			            		
 			            		
							 </ul>
						  
			     
			       </div>
			    </form>
			
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<table class="table table-striped table-condensed" id = "#movieList">
					<thead>
					<tr>
					
						<th width="20%"><a class="text-info"
							href="<%=sortString+ titleSort%>">Title</a></th>
						<th width="20%"><a class="text-info"
							href="<%=sortString+ yearSort%>">Year</a></th>
						<th width="10%">Director</th>
						<th width="10%">Genres</th>
						<th width="10%">Stars</th>
						<th>Trailer</th>
						<th>Banner</th>
						<th width="10%"> </th>
					</tr>
					</thead>
					<tbody>
					
					<%
						List<SingleMovieItem> movies = (List<SingleMovieItem>) request.getAttribute("movies");
						int i = 0;
						if (movies != null) {
							if (movies.size() > 0) {
								
								for (SingleMovieItem movie : movies) {

									String singleMovieString = "/project4/servlet/SingleMovie?movieId=" + movie.getMovie().getId();
									String genres = "";
									i++;
									for(String movieGenre : movie.getGenres()){
										genres += movieGenre + " ";
									}

					%>
					
					
					<tr class="text-left" id = <%=movie.getMovie().getId()%>>
					    
						<td><a href=<%=singleMovieString%>><%=movie.getMovie().getTitle()%></a></td>
						<td><%=movie.getMovie().getYear()%></td>
						<td><%=movie.getMovie().getDirector()%></td>
						<td><%=genres%></td>
						<td><%
				if(movie.getStars() != null) {
					for(Star star : movie.getStars()) {
						String singleStarString = "/project4/servlet/SingleStar?starId="+ star.getId(); 
						String fullname = star.getFirstName() + " " + star.getLastName();
						%>
						
						<a href='<%=singleStarString%>'> <%=fullname %></a><br/>
						<% } } %> </td>
						
						<%
							if (movie.getMovie().getTrailerUrl() == null) {
						%>
						<td>Trailer unavailable.</td>
						<%
							} else {
						%>
						<td><a href="<%=movie.getMovie().getTrailerUrl()%>">Trailer</a></td>
						<%
							}
						%>
						<%
							if (movie.getMovie().getBannerUrl() == null) {
						%>
						<td>Banner unavailable.</td>
						<%
							} else {
						%>
						<td><a href="<%=movie.getMovie().getBannerUrl()%>"><img style="width: 48px; height: 48px;"
							class="img-thumbnail" src="<%=movie.getMovie().getBannerUrl()%>"
							alt="<%=movie.getMovie().getTitle()%> banner" /></a></td>
						<%
							}
						%>
						<td>
							<form action="AddToCart" method="POST">
								<input type="submit" class="btn btn-info btn-sm" style="padding:4px" value="Add to Cart">
								<input type="hidden" name=movie_id value=<%=movie.getMovie().getId()%>>
							</form>
						</td>
					</tr>
					<%
						}
							} else {
					%>
					<tr>
						<td colspan="6"><span class="text-warning">No movies
								found matching search criteria.</span></td>
					</tr>
					<%
						}
						}
					%>
					</tbody>
					
				</table>


			</div>
		</div>

		<div class="row">
			<div class="col-sm-4">
				<p class="text-info">
				<%
				String pageSize10 = "";
				String pageSize25 = "";
				String pageSize50 = "";
				String newQueryString = "";
				
					pageSize10 = templateString;
					pageSize25 = templateString;
					pageSize50 = templateString;
					newQueryString = qs;
					  if(templateString.contains("pageSize")) {
						  newQueryString = qs.replaceAll("pageSize=[0-9][0-9]&", "");
					  } 
						  pageSize10 = templateString+"pageSize=10&"+newQueryString;
						  pageSize25 = templateString+"pageSize=25&"+newQueryString;
						  pageSize50 = templateString+"pageSize=50&"+newQueryString;
					
				
				 
				  
				 %>
					Number of entries per page: <a href='<%=pageSize10%>'>10</a> | <a href='<%=pageSize25%>'>25</a> | <a href='<%=pageSize50%>'>50</a>
				</p>
			</div>
			<div class="col-sm-3 col-sm-offset-5" style="padding-bottom: 10px;">
				<p class="text-right">
				<%
						if (currentPageInt != 1) {
					%>
					<a href="<%=previousPageString %>"> Previous </a> 
					
					<%
						}
					%>
					<%
						if (currentPageInt < numberPagesInt) {
					%>
					<a href="<%=nextPageString %>">Next</a>
					<%
						}
					%> 
				</p>
			</div>
		</div> 
	

</body>
</html>