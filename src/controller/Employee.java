package controller;

import java.util.Date;
import java.util.List;

public class Employee {
	private String name;
	private String persNbr;
	private String employeeID;
	private List<String> location;
	private String position;
	private String startDate;
	private String endDate;
	private List<Comment> comments;
	
	public Employee(String name, int persNbr, String employeeID, String[] location, String position, Date startDate, Date endDate){
		
	}

	public Employee(){
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersNbr() {
		return persNbr;
	}

	public void setPersNbr(String persNbr) {
		this.persNbr = persNbr;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public List<String>  getLocation() {
		return location;
	}

	public void setLocation(List<String> location) {
		this.location = location;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
}
