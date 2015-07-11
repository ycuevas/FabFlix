

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
public class LoginForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginForm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("http://localhost:8080/project2/index.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     response.setContentType("text/html");
	     try {
	    	 
		     Context initCtx = new InitialContext();	   
		     Context envCtx = (Context) initCtx.lookup("java:comp/env");	
	         DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
	         Connection dbcon = ds.getConnection();
             Statement statement = dbcon.createStatement();
             String email = request.getParameter("email");
             String password = request.getParameter("pass");
             String query = "SELECT * from customers where email = '" + email + "' and password = '" + password + "'";

             // Perform the query
             ResultSet rs = statement.executeQuery(query);
             if(rs.next()){
            	 String firstName = rs.getString("first_name");
            	 String lastName = rs.getString("last_name");
            	 String customerID = rs.getString("id");
            	 HttpSession session = request.getSession(true);
            	 session.setAttribute("customerFirstName", firstName);
            	 session.setAttribute("customerLastName", lastName);
            	 session.setAttribute("customerID", customerID);
            	 rs.close();
            	 statement.close();
            	 dbcon.close();
            	 response.setStatus(HttpServletResponse.SC_OK);
            	 response.sendRedirect("http://localhost:8080/project2/main.jsp");
             }
             else {
            	 rs.close();
                 statement.close();
                 dbcon.close();
                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                 response.sendRedirect("http://localhost:8080/project2/index.jsp?error=yes");
             }
	     }
	     catch (SQLException ex) {
             while (ex != null) {
                   System.out.println ("SQL Exception:  " + ex.getMessage ());
                   ex = ex.getNextException ();
               }  // end while
           }  // end catch SQLException

       catch(java.lang.Exception ex)
           {
               System.out.println("<HTML>" +
                           "<HEAD><TITLE>" +
                           "MovieDB: Error" +
                           "</TITLE></HEAD>\n<BODY>" +
                           "<P>SQL error in doGet: " +
                           ex.getMessage() + "</P></BODY></HTML>");
               return;
           }

	}

}
