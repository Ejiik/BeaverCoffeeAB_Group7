package controller;

import java.util.Date;
import java.util.List;

public class Employee {
	private String name;
	private int persNbr;
	private String employeeID;
	private String[] location;
	private String position;
	private Date startDate;
	private Date endDate;
	private List<Comment> comments;
	
	public Employee(String name, int persNbr, String employeeID, String[] location, String position, Date startDate, Date endDate){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPersNbr() {
		return persNbr;
	}

	public void setPersNbr(int persNbr) {
		this.persNbr = persNbr;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String[] getLocation() {
		return location;
	}

	public void setLocation(String[] location) {
		this.location = location;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
}
