package project2;

import java.util.Date;

public class Star {
	private int id;
	private String photoUrl;
	private Date dateOfBirth;
	private String lastName;;
	private String firstName;
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setPhotoUrl(String url){
		this.photoUrl = url;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setDateOfBirth(Date dob) {
		this.dateOfBirth = dob;
	}
	
	
	public int getId() {return this.id; }
	public String getPhotoUrl() { return this.photoUrl; }
	public String getLastName() { return this.lastName; }
	public String getFirstName() { return this.firstName; }
	public Date getDateOfBirth() { return this.dateOfBirth; }
}
