
import project2.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import project2.Movie;
import project2.Star;

/**
 * Servlet implementation class SingleStar
 */
public class SingleStar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SingleStar() {
        super();
        // TODO Auto-generated constructor stub
    }

    private String getStarQueryString(HttpServletRequest request) {
    	String query = "";
    	String starIdString = request.getParameter("starId");
		if(starIdString != null) {
			try {
				int starId = Integer.parseInt(starIdString);
				query = "SELECT * FROM stars where id = " + starId;
				
			} catch (Exception ex) {;
				
			}
		}
    	return query;
    }
    
    private String getMoviesQueryString(HttpServletRequest request) {
    	String query = "";
    	String starIdString = request.getParameter("starId");
		if(starIdString != null) {
			try {
				int starId = Integer.parseInt(starIdString);
				query = "SELECT * FROM movies where id in ( select movie_id from stars_in_movies where star_id = " + starId +" )";
				
			} catch (Exception ex) {;
				
			}
		}
    	return query;
    }
    private  Star getStarFromResultSet(ResultSet rs) {
    	Star star = new Star();
    	try{
			if (rs.next()) {
				do {
					int id = rs.getInt("id");
					String fName = rs.getString("first_name");
					String lName = rs.getString("last_name");
					String url = rs.getString("photo_url");
					Date dob = (Date)rs.getObject("dob");
					star.setFirstName(fName);
					star.setLastName(lName);
					star.setPhotoUrl(url);
					star.setId(id);
					star.setDateOfBirth(dob);
				} while (rs.next());
			}
		} catch (SQLException ex) {
			
		}
    	return star;
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
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			SingleStarItem singleStar = new SingleStarItem();
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
			Connection dbcon = ds.getConnection();
			Statement statement = dbcon.createStatement();
			String query = getStarQueryString(request);
			ResultSet rs = statement.executeQuery(query);
			Star star = getStarFromResultSet(rs);
			
			rs.close();
			statement.close();
			statement = dbcon.createStatement();
			
			query = getMoviesQueryString(request);
			ResultSet rs2 = statement.executeQuery(query);
			ArrayList<Movie> movies = getMovieListFromResultSet(rs2);
			singleStar.setMovies(movies);
			singleStar.setStar(star);
			rs2.close();
			statement.close();
			dbcon.close();
			
			
			request.setAttribute("singleStar", singleStar);
			response.setStatus(HttpServletResponse.SC_OK);
			request.getRequestDispatcher("/singlestar.jsp")
					.forward(request, response);
		} catch(Exception ex) {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
