package models;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class Employer {
	private ObjectId id;
	private String name;
	private String employerID;
	private String persNbr;
	private String country = "n/a";
	private List<Comment> comments;
	
	public Employer(String name, String employerID, String persNbr) {
		super();
		this.name = name;
		this.employerID = employerID;
		this.persNbr = persNbr;
	}
	
	public Employer(){
		comments = new ArrayList<Comment>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmployerID() {
		return employerID;
	}
	public void setEmployerID(String employerID) {
		this.employerID = employerID;
	}
	public String getPersNbr() {
		return persNbr;
	}
	public void setPersNbr(String persNbr) {
		this.persNbr = persNbr;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
