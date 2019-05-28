package controller;

public class Employer {
	private String name;
	private String employerID;
	private String persNbr;
	
	public Employer(String name, String employerID, String persNbr) {
		super();
		this.name = name;
		this.employerID = employerID;
		this.persNbr = persNbr;
	}
	
	public Employer(){
		
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
}
