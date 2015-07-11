<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ include file="partials/_shared.html"%>
<BODY>

	<div class="container">
		<div class="row">
			<div class="col-sm-6 col-sm-offset-3">

				<H1>Adding a New User</H1>

				<FORM ACTION="<%=request.getContextPath()%>/servlet/AddUser" METHOD="POST">
					Employee Email: <INPUT TYPE="TEXT" NAME="email"><BR>
					
					Full Name: <INPUT TYPE="TEXT" NAME="fullname"><BR>
					
					Password: <INPUT TYPE="PASSWORD" NAME="password"><BR>
					
					Host: <INPUT TYPE="TEXT" NAME="host"><BR>
					
					<button class="btn btn-default" type="button" 
					onclick="location.href='<%=request.getContextPath()%>/servlet/AllPrivileges'">Cancel</button>
					
					<INPUT TYPE="SUBMIT" CLASS="btn btn-primary" VALUE="Submit">
				</FORM>

			</div>

		</div>
	</div>
</BODY>
</HTML> 
