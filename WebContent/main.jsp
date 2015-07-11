<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="partials/_shared.html" %>
<%
String customerFirstName = (String)session.getAttribute("customerFirstName");
String customerLastName = (String)session.getAttribute("customerLastName");
String greeting = "Welcome to FabFlix, " + customerFirstName + " " + customerLastName + "!";
%>
<body>
	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-sm-offset-3 text-center">
					<%if (request.getParameter("error") != null){%>
					<%@ include file="partials/_addtocartError.html"%>
					<%}%>	
					<%if (request.getParameter("success") != null){%>
					<%@ include file="partials/_addtocartSuccess.html"%>
					<%}%>	
				<H1>
					<%=greeting %>
				</H1>
				
			</div>
		</div>
		<div class="row">
		<div class="col-sm-6 col-sm-offset-3 text-center">
				
				<p class="text-info">
					To search for a movie in our catalog, press the Search for Movies button. If you would like to browse our movie catalog, press the Movie Browser button.
				</p>
				<p class="text-info">
					You can view the contents of your shopping cart at any time by pressing the Shopping Cart button.
				</p>
				
				<button class="btn btn-default" type="button" onclick="location.href='/project4/servlet/SearchMovies'" >Search for Movies</button>
				<button class="btn btn-default" type="button" onclick="location.href='/project4/servlet/BrowseMovies'">Movie Browser</button>
				<button class="btn btn-default" type="button" onclick="location.href='/project4/servlet/ShoppingCart'">Shopping Cart</button>
			</div>
		</div>
	</div>
</body>
</html>