package project2;


public class Movie {
	private int id;
	private String title;
	private int year;
	private String director;
	private String bannerUrl;
	private String trailerUrl;
	private String starFirstName;
	private String starLastName;
	
	public Movie() {
		title = null;
		director = null;
		bannerUrl = null;
		trailerUrl = null;
		starFirstName = null;
		starLastName = null;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setYear(int year){
		this.year = year;
	}
	
	public void setDirector(String director){
		this.director = director;
	}
	
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	
	public void setTrailerUrl(String trailerUrl){
		this.trailerUrl = trailerUrl;
	}
	public void setStarFirstName(String starFirstName){
		this.starFirstName = starFirstName;
	}
	public void setStarLastName(String starLastName){
		this.starLastName = starLastName;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getId() {
		return id;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getDirector() {
		return director;
	}
	
	public String getBannerUrl() {
		return bannerUrl;
	}
	
	public String getTrailerUrl() {
		return trailerUrl;
	}
	public String getStarFirstName(){
		return this.starFirstName;
	}
	public String getStarLastName(){
		return this.starLastName;
	}
}
