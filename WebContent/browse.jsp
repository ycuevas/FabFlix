<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList"%>
<%@ include file="partials/_shared.html"%>
<BODY>

	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<H1 class="text-center">Movie Browser</H1>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-offset-9 col-sm-3 pull-right">
		
				<a class="btn btn-default" href="/project4/servlet/SearchMovies">Movie Search</a>
					
						
					<a class="btn btn-default" 
						href="/project4/servlet/ShoppingCart">Shopping Cart</a>
				
			
				
			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-6">
				<H3 class="text-center">Browse by Genre</H3>
				<%
					ArrayList<String> genres = (ArrayList<String>) request
							.getAttribute("genres");
					if (genres != null) {
				
					char last = '0';
						for (String genre : genres) {
							if (genre.charAt(0) != last && last != '0') {
				%>
				<br />
				<%
					}
				%>
				<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?genre=<%=genre%>"><%=genre%></a> 



				<%
					last = genre.charAt(0);
					}
				%>
				<%
					}
				%>
			</div>
			<div class="col-sm-6">
				<H3 class="text-center">Browse by Title</H3>
				<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=0">0</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=1">1</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=2">2</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=3">3</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=4">4</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=5">5</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=6">6</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=7">7</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=8">8</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=9">9</a> |
				<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=A">A</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=B">B</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=C">C</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=D">D</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=E">E</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=F">F</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=G">G</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=H">H</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=I">I</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=J">J</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=K">K</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=L">L</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=M">M</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=N">N</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=O">O</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=P">P</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=Q">Q</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=R">R</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=S">S</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=T">T</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=U">U</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=V">V</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=W">W</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=X">X</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=Y">Y</a> |
					<a class="text-info"
					href="http://localhost:8080/project4/servlet/MovieList?title=Z">Z</a> |
			</div>
		</div>
		
	</div>
</body>
</html>