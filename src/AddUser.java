import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

/**
 * Servlet implementation class LoginForm
 */
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
					Context initCtx = new InitialContext();	   
				    Context envCtx = (Context) initCtx.lookup("java:comp/env");	
			        DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
			        Connection dbcon = ds.getConnection();
		            Statement statement = dbcon.createStatement();
		            String context = request.getContextPath();
		            //HttpSession session = request.getSession(true);
		            
		            String query = "SELECT * FROM employees WHERE email = '" + request.getParameter("email") + "'";
		            
		            //System.out.println(query); //DEBUG
		            
		            ResultSet rs = statement.executeQuery(query);
		            if(!rs.next()){
		            	//CREATE ACCOUNT IN EMPLOYEES TABLE
		            	String insert = "INSERT INTO employees VALUES ('" + request.getParameter("email") + "','" 
		            	+ request.getParameter("password") + "','" + request.getParameter("fullname") + "')";
		            	
		            	//System.out.println(insert); //DEBUG
		            	
		            	statement.executeUpdate(insert);
		            	
		            	//CREATE USER WITHIN DATADASE WITH NO PRIVILEGES
		            	insert = "CREATE USER '" + request.getParameter("email") + "'@'" + request.getParameter("host")
		            	+ "' IDENTIFIED BY '" + request.getParameter("password") + "'";
		            	
		            	//System.out.println(insert); //DEBUG
		            	
		            	try {	
		            		statement.execute(insert);
		            	}
		            	catch (SQLException exception)
		            	{
		            		System.out.println("User already exists in mysql.user TABLE");
		            		
		            		insert = "DELETE FROM employees WHERE email = '" + request.getParameter("email") + "' AND password = '" 
		    		            	+ request.getParameter("password") + "' AND fullname = '" + request.getParameter("fullname");
		            		statement.executeUpdate(insert);
		            		
		            		rs.close();
			                statement.close();
			                dbcon.close();
			                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			                response.sendRedirect("http://localhost:8080" + context + "/servlet/AllPrivileges?error=yes1");
		            	}
		            	rs.close();
		                statement.close();
		                dbcon.close();
		                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		                response.sendRedirect("http://localhost:8080" + context + "/servlet/AllPrivileges?success=yes");
		            }
		            else {
		                rs.close();
		                statement.close();
		                dbcon.close();
		                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		                response.sendRedirect("http://localhost:8080" + context + "/servlet/AllPrivileges?error=yes2");
		            }
			}
			 catch (SQLException ex) {
	             while (ex != null) {
	                   System.out.println ("SQL Exception:  " + ex.getMessage ());
	                   ex = ex.getNextException ();
	               }
	           }  
			catch(java.lang.Exception ex) {
	               return;
	           }
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String context = request.getContextPath();
		response.sendRedirect("http://localhost:8080" + context + "/servlet/AllPrivileges");
	}

}
