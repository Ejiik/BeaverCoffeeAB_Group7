package models;

import java.util.Date;
import java.util.List;

public class Order {
	private String orderID;
	private String date;
	private int total;
	private String customer;
	private String cashier;
	private List<Product> products;
	
	public Order(String orderID, int total, String customer, String cashier, List<Product> products) {
		super();
		this.orderID = orderID;
		this.total = total;
		this.customer = customer;
		this.cashier = cashier;
		this.products = products;
	}
	
	public Order(){
		
	}
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
}
