

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
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCart() {
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
		            
		            HttpSession session = request.getSession(true);
		            String customer_id = session.getAttribute("customerID").toString();
		            String movie_id = request.getParameter("movie_id");
		            
		            
		            //Test if movie is already in cart
		            String query = "SELECT * FROM shoppingcart WHERE movie_id = " + movie_id +
		            			   " AND customer_id = " + customer_id;
		            System.out.println(query); //DEBUG
		            
		             ResultSet rs = statement.executeQuery(query);
		             if(!rs.next()) {
		            	//Record movie into shopping cart with quantity = 1
		            	 rs.close();
		            	 query = "INSERT INTO shoppingcart (customer_id, movie_id, quantity) VALUES(" +
		            			 customer_id + "," + movie_id + "," + 1 + ")";
		            	 System.out.println(query); //DEBUG
		            	 statement.executeUpdate(query);
		            	 rs.close();
		            	 statement.close();
		            	 dbcon.close();
		            	 response.setStatus(HttpServletResponse.SC_OK);
		            	 response.sendRedirect("http://localhost:8080/project2/servlet/BrowseMovies?success=yes");
		             }
		             else {
		            	 //Go back to browsing, give error statement at top
		            	 rs.close();
		                 statement.close();
		                 dbcon.close();
		                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		                 response.sendRedirect("http://localhost:8080/project2/servlet/BrowseMovies?error=yes");
		             }
		            statement.close();
		            dbcon.close();
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
		response.sendRedirect("http://localhost:8080/project2/servlet/BrowseMovies");
	}

}
