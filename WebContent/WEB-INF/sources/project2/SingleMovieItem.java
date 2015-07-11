package project2;

import java.util.ArrayList;
import java.util.List;

public class SingleMovieItem {
	private Movie movie = null;
	private List<Star> stars = null;
	
	public SingleMovieItem() {
		movie = null;
		stars = new ArrayList<Star>();
	}
	
	public Movie getMovie() {
		return this.movie;
	}
	
	public List<Star> getStars() {
		return this.stars;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	public void setStarsList(List<Star> stars) {
		this.stars = stars;
	}
	
	public void addStarToList(Star star) {
		this.stars.add(star);
	}
}
