package controller;

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
		country = country;
	}
	public int getNumberOfCoffee() {
		return numberOfCoffee;
	}
	public void setNumberOfCoffee(int numberOfCoffee) {
		this.numberOfCoffee = numberOfCoffee;
	}
}
