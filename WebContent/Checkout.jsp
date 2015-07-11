<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="partials/_shared.html"%>
<BODY>

	<div class="container">
		<%if (request.getParameter("error") != null){%>
		<%@ include file="partials/_loginError.html"%>
		<%}%>	
		
		<%if (session.getAttribute("customerID") == null){%>
		<%response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);%>
        <%response.sendRedirect("http://localhost:8080/project4/index.jsp?error=yes"); %>
		<%}%>
		
		<div class="row">
			<div class="col-sm-6 col-sm-offset-3">

				<H1>Checkout Processing</H1>

				<FORM ACTION="/project4/servlet/Checkout" METHOD="POST">
					Credit Card Number: <INPUT TYPE="TEXT" NAME="creditcard_id"><BR>
					
					Expiration Date: <INPUT TYPE="TEXT" NAME="exp_date"><BR>
					
					First Name: <INPUT TYPE="TEXT" NAME="first_name"><BR>

					Last Name: <INPUT TYPE="TEXT" NAME="last_name"><BR>
					
					<button class="btn btn-default" type="button" 
					onclick="location.href='/project4/servlet/ShoppingCart'">Cancel</button>
					
					<INPUT TYPE="SUBMIT" CLASS="btn btn-primary" VALUE="Submit">
				</FORM>

			</div>

		</div>
	</div>
</BODY>
</HTML>