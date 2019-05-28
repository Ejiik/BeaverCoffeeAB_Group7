package controller;

import java.util.Date;

public class Comment {
	private String employerID;
	private String employeeID;
	private Date date;
	private String comment;
	
	public Comment(String employerID, String employeeID, String comment) {
		super();
		this.employerID = employerID;
		this.employeeID = employeeID;
		this.comment = comment;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
