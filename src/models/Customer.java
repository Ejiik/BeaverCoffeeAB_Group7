package models;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

public class Customer {
	private ObjectId id;
	private String customerID;
	private String registrationDate;
	private String name;
	private String occupation;
	private String persNbr;
	private List<String> location;
	private String country;
	private ClubCard clubCard;
	
	public Customer(String customerID, String name, String occupation, String persNbr, List<String> location,
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
	public List<String> getLocation() {
		return location;
	}
	public void setLocation(List<String> location) {
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

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
}
