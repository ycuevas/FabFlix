

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

/**
 * Servlet implementation class LoginForm
 */
public class BrowseMovies extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseMovies() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try{
					Context initCtx = new InitialContext();	   
				     Context envCtx = (Context) initCtx.lookup("java:comp/env");	
			         DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
			         Connection dbcon = ds.getConnection();
		             Statement statement = dbcon.createStatement();
		             String query = "SELECT name from genres order by name";
		             ResultSet rs = statement.executeQuery(query);
	            	 ArrayList<String> genres = new ArrayList<String>();
		             if(rs.next()){
		            	 do {
		            		 genres.add(rs.getString("name"));
		            	 } while (rs.next());
		             }
		        rs.close();
	            statement.close();
	            dbcon.close();
	            request.setAttribute("genres", genres);
				request.getRequestDispatcher("/browse.jsp").forward(request,response);
			}
			 catch (SQLException ex) {
	             while (ex != null) {
	                   System.out.println ("SQL Exception:  " + ex.getMessage ());
	                   ex = ex.getNextException ();
	               }  // end while
	           }  // end catch SQLException
			catch(java.lang.Exception ex)
	           {

	               return;
	           }
		}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("http://localhost:8080/project2/browse.jsp");
	}

}
