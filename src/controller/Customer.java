package controller;

import java.util.Date;

public class Customer {
	private String customerID;
	private String registrationDate;
	private String name;
	private String occupation;
	private String persNbr;
	private String[] location;
	private String country;
	private ClubCard clubCard;
	
	public Customer(String customerID, String name, String occupation, String persNbr, String[] location,
			ClubCard clubCard) {
		super();
		this.customerID = customerID;
		this.name = name;
		this.occupation = occupation;
		this.persNbr = persNbr;
		this.location = location;
		this.clubCard = clubCard;
	}
	
	public Customer(){
		
	}
	
	public String getCustomerID() {
		return customerID;
	}
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getPersNbr() {
		return persNbr;
	}
	public void setPersNbr(String persNbr) {
		this.persNbr = persNbr;
	}
	public String[] getLocation() {
		return location;
	}
	public void setLocation(String[] location) {
		this.location = location;
	}
	public ClubCard getClubCard() {
		return clubCard;
	}
	public void setClubCard(ClubCard clubCard) {
		this.clubCard = clubCard;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
