

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
public class ShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
					Context initCtx = new InitialContext();	   
				    Context envCtx = (Context) initCtx.lookup("java:comp/env");	
			        DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
			        Connection dbcon = ds.getConnection();
		            Statement statement = dbcon.createStatement();
		            
		            HttpSession session = request.getSession(true);
		            
		            String query = "SELECT movies.id as id, title, year, director, banner_url, trailer_url, quantity " +
		            			   "FROM movies, shoppingcart WHERE movies.id = movie_id and customer_id = " 
		            			   + session.getAttribute("customerID").toString();
		            
		            //System.out.println(query); //DEBUG
		            
		            //Grab movie information
					ResultSet rs = statement.executeQuery(query);
					ArrayList<MovieCart> movies = new ArrayList<MovieCart>();
					if (rs.next()) {
						do {
							int id = rs.getInt("id");
							String title = rs.getString("title");
							String director = rs.getString("director");
							int year = rs.getInt("year");
							String bannerUrl = rs.getString("banner_url");
							String trailerUrl = rs.getString("trailer_url");
							int quantity = rs.getInt("quantity");
							MovieCart movie = new MovieCart();
							movie.setId(id);
							movie.setTitle(title);
							movie.setDirector(director);
							movie.setYear(year);
							movie.setBannerUrl(bannerUrl);
							movie.setTrailerUrl(trailerUrl);
							movie.setQuantity(quantity);
							movies.add(movie);
						} while (rs.next());
					} 
	            	statement.close();
	            	dbcon.close();
	            	
	            	//Output movie information
	            	request.setAttribute("movies", movies);
	            	response.setStatus(HttpServletResponse.SC_OK);
	            	request.getRequestDispatcher("/shoppingcartlist.jsp").forward(request,response);
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("http://localhost:8080/project2/main.jsp");
	}

}
