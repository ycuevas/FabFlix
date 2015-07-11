

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
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
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
		            
		            if (session.getAttribute("customerID") == null)
		            {
		            	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		                response.sendRedirect("http://localhost:8080/project2/index.jsp?error=yes");
		                return;
		            }
		            
		            String query = "SELECT * FROM creditcards WHERE id = '" + request.getParameter("creditcard_id") +
		            			   "' AND first_name = '" + request.getParameter("first_name") +
		            			   "' AND last_name = '" + request.getParameter("last_name") + 
		            			   "' AND expiration = '" + request.getParameter("exp_date") + "'";
		            
		            //System.out.println(query); //DEBUG
		            
		             ResultSet rs = statement.executeQuery(query);
		             if(rs.next()){
		            	 //Record shopping cart details into sales
		            	 rs.close();
		            	 
		            	 String customerID = session.getAttribute("customerID").toString();
		            	 
		            	 query = "SELECT * FROM shoppingcart WHERE customer_id = " + customerID;
		            	 rs = statement.executeQuery(query);
		            	 
		            	 Calendar calendar = Calendar.getInstance();
		            	 int currentYear = calendar.get(Calendar.YEAR),
		            	 	 currentDay = calendar.get(Calendar.DAY_OF_MONTH),
		            	     currentMonth = calendar.get(Calendar.MONTH) + 1;
		            	     
		            	 String currentDate = currentYear + "/" + currentMonth + "/" + currentDay;
		            	 ArrayList<String> newSales = new ArrayList<String>();
		            	 
		            	 while(rs.next())
		            	 {
		            		 int myQuantity = rs.getInt("quantity");
		            		 
		            		 for (int i = 0; i < myQuantity; i++)
		            		 {
		            			 newSales.add("INSERT INTO sales (customer_id,movie_id,sale_date) " +
		            		 		 "VALUES (" + customerID + "," + rs.getInt("movie_id") +
		            		 		 ",'" + currentDate +"')");
		            		 }
		            	 }
		            	 
		            	 for (int i = 0; i < newSales.size(); i++)
		            	 {
		            		 //System.out.println(newSales.get(i)); //DEBUG
		            		 statement.executeUpdate(newSales.get(i));
		            	 }
		            	 rs.close();
		            	 
		            	 //Clear shopping cart after recording sales
		            	 query = "DELETE FROM shoppingcart WHERE customer_id = " + customerID;
		            	 statement.executeUpdate(query);
		            	 rs.close();
		            	 
		            	 statement.close();
		            	 dbcon.close();
		            	 response.setStatus(HttpServletResponse.SC_OK);
		            	 response.sendRedirect("http://localhost:8080/project2/servlet/ShoppingCart?success=yes");
		             }
		             else {
		            	 //GO BACK TO SHOPPING CART
		            	 rs.close();
		                 statement.close();
		                 dbcon.close();
		                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		                 response.sendRedirect("http://localhost:8080/project2/servlet/ShoppingCart?error=yes");
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
		response.sendRedirect("http://localhost:8080/project2/servlet/ShoppingCart");
	}

}
