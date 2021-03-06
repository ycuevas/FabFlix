

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
import project2.SingleMovieItem;
import project2.Star;

/**
 * Servlet implementation class SearchList
 */
public class SearchList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String movieTitle = "";
	String movieYear = "";
	String directorName = "";
	String starFirstName = "";
	String starLastName = "";
	String query = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchList() {
        super();
        // TODO Auto-generated constructor stub
    }

    private String getStarsQueryString(int movieId) {
    	return "SELECT * FROM stars where id in ( SELECT star_id from stars_in_movies where movie_id = " + movieId + ")";
    }
	
	
    private String getQueryS(HttpServletRequest request) {
    	movieTitle = request.getParameter("title");
		movieYear = request.getParameter("year");
		directorName = request.getParameter("DirectorName");
		starFirstName = request.getParameter("firstName");
		starLastName = request.getParameter("lastName");
		
		
		
		String sortOrder = request.getParameter("sortOrder");
		
		if (sortOrder == null)
		{
			sortOrder = "";
		}
		
		String query = "select distinct id, banner_url, director, title, trailer_url, year" 
				+ " from movie_set where title like '" + movieTitle + "%' and first_name like '" + starFirstName + "%' and "
				+ "last_name like '"+starLastName+"%' and "
				+ "year like '" + movieYear + "%' and director like '" + directorName + "%'";
		

	
		if (sortOrder == null) {
			query = query + " order by title";
		} else if (sortOrder.equals("TITLEASC")) {
			query = query + " order by title";
		} else if (sortOrder.equals("TITLEDESC")) {
			query = query + " order by title desc";
		} else if (sortOrder.equals("YEARASC")) {
			query = query + " order by year";
		} else if (sortOrder.equals("YEARDESC")) {
			query = query + " order by year desc";
		}
		

		request.setAttribute("sortOrder", sortOrder);
		
		return query;
	}
	
	// MIGHT USE THIS FOR ERROR CHECKING. JUST ADDED TEMPORARILY.
	private Integer isInteger(String val){
		Integer retVal;
		 try {
			    retVal = Integer.parseInt(val);
			  } catch (NumberFormatException nfe) {
			    retVal = 0;
			  }
		return retVal;
	}

	private int updateNumberOfMoviesShown(HttpServletRequest request) {
		String pageSize = request.getParameter("pageSize");
		if (pageSize == null) {
			request.setAttribute("pageSize", 10);
		} else {
			request.setAttribute("pageSize", pageSize);
			return Integer.parseInt(pageSize);
		}
		return 10;
	}
	
	private ArrayList<Movie> getMovieListFromResultSet(ResultSet rs) {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		try{
			if (rs.next()) {
				do {
					int id = rs.getInt("id");
					String title = rs.getString("title");
					String director = rs.getString("director");
					int year = rs.getInt("year");
					String bannerUrl = rs.getString("banner_url");
					String trailerUrl = rs.getString("trailer_url");
					Movie movie = new Movie();
					movie.setId(id);
					movie.setTitle(title);
					movie.setDirector(director);
					movie.setYear(year);
					movie.setBannerUrl(bannerUrl);
					movie.setTrailerUrl(trailerUrl);
					movies.add(movie);
				} while (rs.next());
			}
		} catch (SQLException ex) {
			
		}
		return movies;
	}

	
	
	private ArrayList<Star> getStarsFromResultSet(ResultSet rs) 
    {
    	ArrayList<Star> stars = new ArrayList<Star>();
    	try{
			if (rs.next()) {
				do {
					Star star = new Star();
					int id = rs.getInt("id");
					String fName = rs.getString("first_name");
					String lName = rs.getString("last_name");
					String url = rs.getString("photo_url");
					star.setFirstName(fName);
					star.setLastName(lName);
					star.setPhotoUrl(url);
					star.setId(id);
					stars.add(star);
					
				} while (rs.next());
			}
		} catch (SQLException ex) {
			
		}
    	return stars;
    }
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try 
		{
			
			int page = 1;
			int recordsPerPage = 10;
			String pageSize = request.getParameter("pageSize");

			ArrayList<SingleMovieItem> movieItems = new ArrayList<SingleMovieItem>();
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
				statement.executeUpdate("CREATE OR REPLACE VIEW movie_set as select distinct movies.id, stars.first_name, "
						+ "stars.last_name, movies.banner_url, movies.director, movies.title,"
						+ "movies.trailer_url, movies.year"
						+ "from stars_in_movies, stars, movies "
						+ "where stars_in_movies.movie_id = movies.id "
						+ "and stars.id = stars_in_movies.star_id;");
			}
			String query = getQueryS(request);
		
			ResultSet results = statement.executeQuery(query);
			ArrayList<Movie> movies = getMovieListFromResultSet(results);

				results.close();
				statement.close();
				for(Movie movie: movies) {
					SingleMovieItem singleItem = new SingleMovieItem();
					singleItem.setMovie(movie);
					Statement stmt = dbcon.createStatement();
					query = getStarsQueryString(movie.getId());
					ResultSet rs2 = stmt.executeQuery(query);
					ArrayList<Star> stars = getStarsFromResultSet(rs2);
					singleItem.setStarsList(stars);
					stmt.close();
					stmt = dbcon.createStatement();
					
					movieItems.add(singleItem);
					rs2.close();
					
				}
				
				dbcon.close();
				int numMovies = movies.size();
				int numPages = numMovies / recordsPerPage;
				if(numPages == 0) 
					numPages = 1;
				// If we exceeded maximum number of pages set it to the last page.
				if(page >= numPages){
					request.setAttribute("currentPage", Integer.toString(numPages));
				}
				
				if(movies.size() > 0 && movies.size() >= recordsPerPage)
				{
					int startIndex = (page-1)*recordsPerPage;
					int endIndex = startIndex + recordsPerPage;
					if(endIndex > movies.size()) 
					{
						endIndex = movies.size() -1;
					}
					List<SingleMovieItem> subList = movieItems.subList(startIndex, endIndex);
					request.setAttribute("movies", subList);
					
				} 
				else 
				{
					request.setAttribute("movies", movieItems);
				}
				request.setAttribute("templateString", "/project4/servlet/SearchList?");
				request.setAttribute("numberOfPages", Integer.toString(numPages));
				request.setAttribute("currentPage", Integer.toString(page));
				response.setStatus(HttpServletResponse.SC_OK);
				request.getRequestDispatcher("/list.jsp").forward(request, response);
				
				
		} 
		catch (SQLException ex) {
			while (ex != null) {
				System.out.println("SQL Exception:  " + ex.getMessage());
				ex = ex.getNextException();
			} 
		} 
		catch (java.lang.Exception ex) {

			return;
		}
		
	}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
}

