import project2.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class SingleMovie
 */
public class SingleMovie extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SingleMovie() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private String getMovieQueryString(HttpServletRequest request){
    	String query = "";
    	String movieIdString = request.getParameter("movieId");
		if(movieIdString != null) {
			try {

				int movieId = Integer.parseInt(movieIdString);
				query = "SELECT * FROM movies where id = " + movieId;
			} catch (Exception ex) {
				
			}
		}
    	return query;
    }
    
    private String getGenresQueryString(int movieId) {
    	return "SELECT name FROM genres where id in ( SELECT genre_id from genres_in_movies where movie_id = " + movieId + ")";
    }
    
    private ArrayList<String> getGenresFromResultSet(ResultSet rs) {
		ArrayList<String> genres = new ArrayList<String>();
		try{
			if (rs.next()) {
				do {
					String name = rs.getString("name");
					genres.add(name);
				} while (rs.next());
			}
		} catch (SQLException ex) {
			
		}
		return genres;
	}
    
    private String getStarsQueryString(HttpServletRequest request) {
    	String query = "";
    	String movieIdString = request.getParameter("movieId");
		if(movieIdString != null) {
			try {
				int movieId = Integer.parseInt(movieIdString);
				query = "SELECT * FROM stars where id in ( SELECT star_id from stars_in_movies where movie_id = " + movieId + ")";
				
			} catch (Exception ex) {;
				
			}
		}
    	return query;
    	
    }

    private  Movie getMovieFromResultSet(ResultSet rs) {
		Movie movie = new Movie();
		try{
			if (rs.next()) {
				do {
					int id = rs.getInt("id");
					String title = rs.getString("title");
					String director = rs.getString("director");
					int year = rs.getInt("year");
					String bannerUrl = rs.getString("banner_url");
					String trailerUrl = rs.getString("trailer_url");
					movie.setId(id);
					movie.setTitle(title);
					movie.setDirector(director);
					movie.setYear(year);
					movie.setBannerUrl(bannerUrl);
					movie.setTrailerUrl(trailerUrl);
					
				} while (rs.next());
			}
		} catch (SQLException ex) {
			
		}
		return movie;
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
		// TODO Auto-generated method stub
		
		try {
			SingleMovieItem singleMovie = new SingleMovieItem();
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");
			Connection dbcon = ds.getConnection();
			Statement statement = dbcon.createStatement();
			String query = getMovieQueryString(request);
			ResultSet rs = statement.executeQuery(query);
			Movie movie = getMovieFromResultSet(rs);
			
			rs.close();
			statement.close();
			statement = dbcon.createStatement();
			
			query = getStarsQueryString(request);
			ResultSet rs2 = statement.executeQuery(query);
			ArrayList<Star> stars = getStarsFromResultSet(rs2);
			singleMovie.setMovie(movie);
			singleMovie.setStarsList(stars);
			rs2.close();
			statement.close();
			statement = dbcon.createStatement();
			query = getGenresQueryString(movie.getId());
			ResultSet rs3 = statement.executeQuery(query);
			ArrayList<String> genres = getGenresFromResultSet(rs3);
			singleMovie.setGenreList(genres);
			rs3.close();
			statement.close();
			dbcon.close();
			
			request.setAttribute("singleMovie", singleMovie);
			response.setStatus(HttpServletResponse.SC_OK);
			request.getRequestDispatcher("/singlemovie.jsp")
					.forward(request, response);
		} catch (Exception ex) {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
