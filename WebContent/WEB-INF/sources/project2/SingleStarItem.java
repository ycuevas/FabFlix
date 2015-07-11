package project2;

import java.util.ArrayList;
import java.util.List;

public class SingleStarItem {
	private Star star = null;
	private List<Movie> movies = null;
	
	public SingleStarItem() {
		movies = new ArrayList<Movie>();
		star = new Star();
	}
	
	public Star getStar() {
		return star;
	}
	
	public List<Movie> getMovies() {
		return movies;
	}
	
	public void setStar(Star star) {
		this.star = star;
	}
	
	public void addMovie(Movie movie) {
		this.movies.add(movie);
	}
	
	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}
}
