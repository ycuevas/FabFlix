

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import project2.Movie;

/**
 * Servlet implementation class SearchList
 */
public class SearchList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String movieTitle = null;
	String movieYear = null;
	String directorName = null;
	String starFirstName = null;
	String starLastName = null;
	String query = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchList() {
        super();
        // TODO Auto-generated constructor stub
    }


	private void updateNumberOfMoviesShown(HttpServletRequest request) {
		String pageSize = request.getParameter("pageSize");
		if (pageSize == null) {
			request.setAttribute("pageSize", 10);
		} else {
			request.setAttribute("pageSize", pageSize);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		request.getRequestDispatcher("/searchlist.jsp").forward(request,response);
	}
		
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		movieTitle = request.getParameter("movieTitle");
		movieYear = request.getParameter("year");
		directorName = request.getParameter("DirectorName");
		starFirstName = request.getParameter("firstName");
		starLastName = request.getParameter("lastName");

		try 
		{
			int page = 1;
			int recordsPerPage = 10;
			String pageSize = request.getParameter("pageSize");
			String currentPage = request.getParameter("currentPage");
			if (pageSize == null) {
				request.setAttribute("pageSize", Integer.toString(10));
			} else {
				request.setAttribute("pageSize", pageSize);
				recordsPerPage = Integer.parseInt(pageSize);
			}
			if(currentPage == null) {
				request.setAttribute("currentPage", new Integer(1));
			} else {
				request.setAttribute("currentPage", currentPage);
				page = Integer.parseInt(currentPage);
			}


			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");

			Connection dbcon = ds.getConnection();
			Statement statement = dbcon.createStatement();
			
			ResultSet rs = dbcon.getMetaData().getTables(null, null, "movie_set", null);

			if (!(rs.next()))
			{
				statement.executeUpdate("CREATE OR REPLACE VIEW movie_set as select movies.id, stars.first_name, "
						+ "stars.last_name, movies.banner_url, movies.director, movies.title,"
						+ "movies.trailer_url, movies.year"
						+ "from stars_in_movies, stars, movies "
						+ "where stars_in_movies.movie_id = movies.id "
						+ "and stars.id = stars_in_movies.star_id;");
			}
			query = "select * from movie_set where title like '"+movieTitle+"%' and first_name like '"+starFirstName+"%' and "
					+ "last_name like '"+starLastName+"%' and "
					+ "year like '"+movieYear+"%' and director like '"+directorName+"%'";
			 ResultSet results = statement.executeQuery(query);
			 ArrayList<Movie> movies = new ArrayList<Movie>();
			 
			 if (results.next()) {
					do {
						
						String title = results.getString("title");
						String director = results.getString("director");
						int year = results.getInt("year");
						String firstName = results.getString("first_name");
						String lastName = results.getString("last_name");
						
						Movie movie = new Movie();
	
						movie.setTitle(title);
						movie.setDirector(director);
						movie.setYear(year);
						movie.setStarFirstName(firstName);
						movie.setStarLastName(lastName);
						movies.add(movie);
					
					} while (results.next());
				}
				results.close();
				statement.close();
				dbcon.close();
				System.out.println(movies);
				request.setAttribute("movies", movies);
				
				
		} 
		catch (SQLException ex) 
		{
			while (ex != null) 
			{
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			} // end while
		} // end catch SQLException

		catch (java.lang.Exception ex) 
		{
			System.out.println("<HTML>" + "<HEAD><TITLE>" + "MovieDB: Error"
					+ "</TITLE></HEAD>\n<BODY>" + "<P>SQL error in doGet: "
					+ ex.getMessage() + "</P></BODY></HTML>");
			return;
		}
		
		request.getRequestDispatcher("/searchlist.jsp").forward(request,response);

	}
}

