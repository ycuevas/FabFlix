

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.sql.DataSource;

import project2.MovieCart;

/**
 * Servlet implementation class LoginForm
 */
public class UpdateQuantity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateQuantity() {
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
		            
		            String query;
		            
		            //Delete from shopping cart if quantity is set to 0
		            if (Integer.parseInt(request.getParameter("quantity")) == 0) {
		            	query = "DELETE FROM shoppingcart WHERE movie_id = " 
		            			   + request.getParameter("movie_id") + " and customer_id = " 
		            			   + session.getAttribute("customerID").toString();
		            }
		            else {
		            	query = "UPDATE shoppingcart SET quantity = " + request.getParameter("quantity") +
		            			   " WHERE movie_id = " + request.getParameter("movie_id") + " and customer_id = " 
		            			   + session.getAttribute("customerID").toString();
		            }
		            
		            //System.out.println(query); //DEBUG
		            
		            //Grab movie information
					statement.executeUpdate(query);
	            	statement.close();
	            	dbcon.close();
	            	response.sendRedirect("http://localhost:8080/project2/servlet/ShoppingCart");
	            	//Output movie information
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
		response.sendRedirect("http://localhost:8080/project2/servlet/ShoppingCart");
	}

}
