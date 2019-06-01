package models;

import java.util.Date;

import org.bson.types.ObjectId;

public class Comment {
	private ObjectId id;
	private String employerID;
	private String employeeID;
	private String date;
	private String comment;
	
	public Comment(String employerID, String employeeID, String comment) {
		super();
		this.employerID = employerID;
		this.employeeID = employeeID;
		this.comment = comment;
	}
	
	public Comment(){
		
	}
	
	public String getEmployerID() {
		return employerID;
	}
	public void setEmployerID(String employerID) {
		this.employerID = employerID;
	}
	public String getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
}
