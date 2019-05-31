package controllers;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

import database.Database;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.*;

public class UpdateController implements Initializable{
	
	Database db = new Database();
	
	private Product product;
	private Order order;
	private Employer employer;
	private Employee employee;
	private Customer customer;
	private Comment comment;
	
	//PRODUCT
	@FXML
	private TextField update_product_name;
	@FXML
	private TextField update_product_type;
	@FXML
	private TextField update_product_price;
	@FXML
	private TextField update_product_units;
	@FXML
	private TextArea update_list_product_ingredients;
	
	//ORDER
	@FXML
	private ChoiceBox update_order_cashierID;
	@FXML
	private ChoiceBox update_order_customerID;
	@FXML
	private ChoiceBox update_order_productList;
	@FXML
	private Button btn_update_order_add;
	@FXML
	private Button btn_update_order_delLast;
	@FXML
	private TextArea update_list_order_products;
	
	//EMPLOYER
	@FXML
	private TextField update_employer_name;
	@FXML
	private TextField update_employer_birthdate;
	
	//EMPLOYEE
	@FXML
	private TextField update_employee_name;
	@FXML
	private TextField update_employee_position;
	@FXML
	private TextField update_employee_address;
	@FXML
	private TextField update_employee_zipcode;
	@FXML
	private TextField update_employee_country;
	@FXML
	private TextField update_employee_birthdate;
	
	//CUSTOMER
	@FXML 
	private TextField update_customer_name;
	@FXML 
	private TextField update_customer_occupation;
	@FXML
	private TextField update_customer_address;
	@FXML
	private TextField update_customer_zipcode;
	@FXML
	private TextField update_customer_country;
	@FXML
	private TextField update_customer_birthdate;
	
	//COMMENT
	@FXML
	private ChoiceBox update_comment_employee;
	@FXML
	private ChoiceBox update_comment_employer;
	@FXML
	private TextArea update_comment_comment;
	
	public UpdateController(Object ob) {
		if(ob instanceof Product) 		
			product = (Product) ob;
		else if(ob instanceof Order)
			order = (Order) ob;
		else if(ob instanceof Employer)
			employer = (Employer) ob;
		else if(ob instanceof Employee)
			employee = (Employee) ob;
		else if(ob instanceof Customer)
			customer = (Customer) ob;
		else if(ob instanceof Comment)
			comment = (Comment) ob;
	}
	
	public void UpdateProduct() {
		
	}
	
	public void UpdateOrder() {
		
	}
	
	public void UpdateEmployer() {
		
	}
	
	public void UpdateEmployee() {
		
	}
	
	public void UpdateCustomer() {
		
	}
	
	public void UpdateComment() {
		
	}
	
	public void initialize(URL path, ResourceBundle arg1) {
		String fxmlFile = path.getPath().substring(path.getPath().lastIndexOf('/')+1);
		System.out.println(fxmlFile);
		
		if(fxmlFile.equals("UpdateProductWindow.fxml")) {			
			System.out.println(product.getName());
			update_product_name.setText(product.getName());
			update_product_type.setText(product.getType());
			update_product_price.setText(String.valueOf(product.getPrice()));
			update_product_units.setText(String.valueOf(product.getUnits()));
			for(String i : product.getIngredients()) {
				update_list_product_ingredients.appendText(i+"\n");
			}
		}
		else if(fxmlFile.equals("UpdateOrderWindow.fxml")) {
			System.out.println(fxmlFile);
			//CHOICE BOXES ERIK PLEASE:D
			
		}
		else if(fxmlFile.equals("UpdateEmployerWindow.fxml")) {
			System.out.println(fxmlFile);
			update_employer_name.setText(employer.getName());
			update_employer_birthdate.setText(employer.getPersNbr());
			
		}
		else if(fxmlFile.equals("UpdateEmployeeWindow.fxml")) {
			System.out.println(employee.getPersNbr());
			update_employee_name.setText(employee.getName());
			update_employee_position.setText(employee.getPosition());
			update_employee_address.setText(employee.getAddress());
			update_employee_zipcode.setText(employee.getZipcode());
//			update_employee_country.settext(employee.getCountry());		
			update_employee_birthdate.setText(employee.getPersNbr());
		}
		else if(fxmlFile.equals("UpdateCustomerWindow.fxml")) {
			System.out.println(customer.getName());
			update_customer_name.setText(customer.getName());
			update_customer_occupation.setText(customer.getOccupation());
			update_customer_address.setText(customer.getAddress());
			update_customer_zipcode.setText(customer.getZipcode());
			update_customer_country.setText(customer.getCountry());
			update_customer_birthdate.setText(customer.getPersNbr());
		}
		else if(fxmlFile.equals("UpdateCommentWindow.fxml")) {
			System.out.println(comment.getComment());
			//CHOICE BOXES ERIK PLEASE:D
		}
	}
}
