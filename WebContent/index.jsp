<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="partials/_shared.html"%>
<BODY>

	<div class="container">
		<%if (request.getParameter("error") != null){%>
		<%@ include file="partials/_loginError.html"%>
		<%}%>	
		<div class="row">
			<div class="col-sm-6 col-sm-offset-3">


				<H1>FabFlix Customer Login</H1>


				<FORM ACTION="/project4/servlet/LoginForm" METHOD="POST">
					Customer e-mail: <INPUT TYPE="TEXT" NAME="email"><BR>

					Customer password: <INPUT TYPE="PASSWORD" NAME="pass"><BR>

					<INPUT TYPE="SUBMIT" VALUE="Login">

				</FORM>

			</div>

		</div>
	</div>
</BODY>
</HTML>
