package models;

import org.bson.types.ObjectId;

public class ClubCard {
	private String cardNumber;
	private String country;
	private int numberOfCoffee;
	
	public ClubCard(String cardNumber, String country, int numberOfCoffee) {
		super();
		this.cardNumber = cardNumber;
		this.country = country;
		this.numberOfCoffee = numberOfCoffee;
	}
	public ClubCard(){
		
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getNumberOfCoffee() {
		return numberOfCoffee;
	}
	public void setNumberOfCoffee(int numberOfCoffee) {
		this.numberOfCoffee = numberOfCoffee;
	}
	public void addCoffee(){
		numberOfCoffee += 1;
	}

}
