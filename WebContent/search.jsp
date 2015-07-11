<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="partials/_shared.html"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="<%=request.getContextPath()%>/project2.js"></script>
<title>search</title>
</head>

<body>

	<div class= "container">
		<h1>Search Movies</h1>
			<div class="col-sm-offset-4 col-sm-5">
            	<div class="btn-group btn-group-justified pull-right">
                    <a class="btn btn-default"
                        href="/project4/servlet/BrowseMovies">Movie Browser</a>
                        
                    <a class="btn btn-default"
                        href="/project4/servlet/ShoppingCart">Shopping Cart</a>
                </div>
            </div>
		<form action="/project4/servlet/SearchList" method="GET">
			<div class="form-group">
				<br>
				<div class = "row">
					<div class = "col-md-4">
						<label>Title: </label>
						<br> 
						<input name = "title" type="text" id="movieTitle" placeholder="Enter Movie Title">
					</div>
					<div class = "col-md-4">
	
						<label>Year: </label>
						<br>
						<input name = "year" type="text" id="movieYear" placeholder="Enter Release Year">
					</div>
				</div>
				
			</div>

			<div class="form-group">
				<br> 
				<div class = "row">
					<div class ="col-md-12">
						<label>Director Name: </label>
						<br>
						<input type="text"  name = "DirectorName" id="DirectorName" placeholder="Enter Director Name">
					</div> 
			
				</div>
				
			</div>
			<div class="form-group">
				<br>
				<div class = "row">
					<div class = "col-md-4"> 
						<label>Movie Star's First Name: </label> 
						<input type="text" name = "firstName" id="FirstName" placeholder="Enter Star's First Name">
					</div>
					<div class = "col-md-4"> 
						<label>Movie Star's Last Name: </label> 
						<input type="text" name = "lastName" id="LastName" placeholder="Enter Star's Last Name">
					</div>
				</div>		
			</div>
			<br>
			<button type="submit" class="btn btn-default" onclick="location.href='/project4/servlet/SearchList">Search</button>
		</form>

	</div>

</body>
</html>