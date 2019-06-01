package controllers;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import database.Database;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
	private ChoiceBox<String> update_order_cashierID;
	@FXML
	private ChoiceBox<String> update_order_customerID;
	@FXML
	private ChoiceBox<String> update_order_productList;
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
	@FXML
	private TextField update_employer_country;
	
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
	private ChoiceBox<String> update_comment_employee;
	@FXML
	private ChoiceBox<String> update_comment_employer;
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
	
	public int formatPrice(String priceIn){
		String price = priceIn;
		int res;
		if(price.contains(".")){
			String[] tempArr = price.split("\\.");
			if(tempArr[1].length() == 1){
				price = tempArr[0] + tempArr[1];
				price.concat("0");
			}else if(tempArr[1].length() >2){
				int tempInt = Integer.parseInt(tempArr[1].substring(0, 2));
				if(Character.getNumericValue(tempArr[1].charAt(2)) > 4){
					tempInt++;
				}
				price = tempArr[0] + tempInt;
			}else{
				price = tempArr[0] + tempArr[1];
			}
		}else{
			price.concat("00");
		}
		res = Integer.parseInt(price);
		return res;
	}
	public String currentFormattedDate(){
		LocalDateTime ldt = LocalDateTime.now().plusDays(1);
		DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		return formmat1.format(ldt);
	}
	
	public void UpdateProduct() {
		String priceStr = update_product_price.getText();
		int price = formatPrice(priceStr);
		product.setName(update_product_name.getText());
		product.setType(update_product_type.getText());
		product.setPrice(price);
		product.setUnits(Integer.parseInt(update_product_units.getText()));
		product.setIngredients(Arrays.asList(update_list_product_ingredients.getText().split("\n")));
		db.updateProduct(product);
		((Stage)update_product_price.getScene().getWindow()).close();
	}
	
	public void UpdateOrder() {
		String[] productNames = update_list_order_products.getText().split("\n");
		List<Product> products = new ArrayList<>();
		int total = 0;
		
		List<Product> prods = db.getProducts();
		
		for(int i = 0;i<prods.size();i++){
				for(int j=0;j<productNames.length;j++){
					if(prods.get(i).getName().equals(productNames[j])){
						products.add(prods.get(i));
						total += prods.get(i).getPrice();
					}
				}
			}
		order.setDate(currentFormattedDate());
		order.setTotal(total);
		order.setCashier(update_order_cashierID.getSelectionModel().getSelectedItem());
		if(update_order_customerID.getSelectionModel().isEmpty()){
			order.setCustomer("n/a");
		}else{
			order.setCustomer(update_order_customerID.getSelectionModel().getSelectedItem());
		}
		order.setProducts(products);
		db.updateOrder(order);
		((Stage)update_order_cashierID.getScene().getWindow()).close();
	}
	
	public void UpdateEmployer() {
		employer.setName(update_employer_name.getText());
		employer.setPersNbr(update_employer_birthdate.getText());
		employer.setCountry(update_employer_country.getText());
		db.updateEmployer(employer);
		((Stage)update_employer_name.getScene().getWindow()).close();
	}
	
	public void UpdateEmployee() {
		employee.setName(update_employee_name.getText());
		employee.setPosition(update_employee_position.getText());
		employee.setAddress(update_employee_address.getText());
		employee.setZipcode(update_employee_zipcode.getText());
		employee.setCountry(update_employee_country.getText());		
		employee.setPersNbr(update_employee_birthdate.getText());
		db.updateEmployee(employee);
		((Stage)update_employee_name.getScene().getWindow()).close();
	}
	
	public void UpdateCustomer() {
		customer.setName(update_customer_name.getText());
		customer.setOccupation(update_customer_occupation.getText());
		customer.setAddress(update_customer_address.getText());
		customer.setZipcode(update_customer_zipcode.getText());
		customer.setCountry(update_customer_country.getText());
		customer.setPersNbr(update_customer_birthdate.getText());
		db.updateCustomer(customer);
		((Stage)update_customer_name.getScene().getWindow()).close();
	}
	
	public void UpdateComment() {
		comment.setEmployerID(update_comment_employer.getSelectionModel().getSelectedItem());
		comment.setEmployeeID(update_comment_employee.getSelectionModel().getSelectedItem());
		comment.setComment(update_comment_comment.getText());
		db.updateComment(comment);
		((Stage)update_comment_employer.getScene().getWindow()).close();
	}
	
	public void DeleteComment() {
		System.out.println("Delete Comment");
	}
	
	public void DeleteCustomer() {
		System.out.println("Delete Customer");
	}
	
	public void DeleteEmployee() {
		System.out.println("Delete Employee");
	}
	
	public void DeleteEmployer() {
		System.out.println("Delete Employer");
	}
	
	public void DeleteOrder() {
		System.out.println("Delete Order");
	}
	
	public void DeleteProduct() {
		System.out.println("Delete Product");
	}
	
	
	public void addProduct(){
		
		System.out.println("whaa");
		List<Product> products = db.getProducts();
		String prodChoice = update_order_productList.getSelectionModel().getSelectedItem();
		Product product = new Product();
		for(int i=0;i<products.size();i++){
			if(products.get(i).getName().equals(prodChoice)){
				product = products.get(i);
			}
		}
		if(product.getInStock()){
			update_list_order_products.appendText(product.getName() + "\n");
		}
	}
	public void removeProduct(){
		String prodChoice = update_order_productList.getSelectionModel().getSelectedItem();
		update_list_order_products.getText().replace(prodChoice + "\n", "");
	}
	
	public void processOrder(){
		UpdateOrder();
		JOptionPane.showMessageDialog(null, db.processOrder(order));
	}
	public void initialize(URL path, ResourceBundle arg1) {
		String fxmlFile = path.getPath().substring(path.getPath().lastIndexOf('/')+1);
		System.out.println(fxmlFile);
		
		if(fxmlFile.equals("EditProductWindow.fxml")) {	
			update_product_price.textProperty().addListener(new ChangeListener<String>() {
	            @Override
	            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
	                	update_product_price.setText(oldValue);
	                }
	            }
	        });
			System.out.println(product.getId());
			update_product_name.setText(product.getName());
			update_product_type.setText(product.getType());
			update_product_price.setText(String.valueOf(product.getPrice()));
			update_product_units.setText(String.valueOf(product.getUnits()));
			for(String i : product.getIngredients()) {
				update_list_product_ingredients.appendText(i+"\n");
			}
		}
		else if(fxmlFile.equals("EditOrderWindow.fxml")) {
			System.out.println(fxmlFile);
			List<Customer> customers = db.getCustomers();
			List<String> customerNames = new ArrayList<String>();
			List<Employee> employees = db.getEmployees();
			List<String> emplNames = new ArrayList<String>();
			
			List<Product> prods = db.getProducts();
			List<String> prodNames = new ArrayList<String>();
			for(int i=0;i<prods.size();i++){
				prodNames.add(prods.get(i).getName());
				System.out.println("Found: " + prods.get(i).getName() + ", Added: " + prodNames.get(i));
			}
			
			for(int i=0;i<customers.size();i++){
				customerNames.add(customers.get(i).getCustomerID());
			}
			for(int i=0;i<employees.size();i++){
				emplNames.add(employees.get(i).getEmployeeID());
			}
			update_order_productList.setItems(FXCollections.observableArrayList(prodNames));
			update_order_customerID.setItems(FXCollections.observableArrayList(customerNames));
			update_order_cashierID.setItems(FXCollections.observableArrayList(emplNames));
			
			update_order_customerID.getSelectionModel().select(order.getCustomer());
			update_order_cashierID.getSelectionModel().select(order.getCashier());
			for(int i=0;i<order.getProducts().size();i++){
				System.out.println(order.getProducts().get(i).getName());
				update_list_order_products.appendText(order.getProducts().get(i).getName() + "\n");
			}
		}
		else if(fxmlFile.equals("EditEmployerWindow.fxml")) {
			System.out.println(employer.getId());
			update_employer_name.setText(employer.getName());
			update_employer_country.setText(employer.getCountry());
			update_employer_birthdate.setText(employer.getPersNbr());
			
		}
		else if(fxmlFile.equals("EditEmployeeWindow.fxml")) {
			System.out.println(employee.getId());
			update_employee_name.setText(employee.getName());
			update_employee_position.setText(employee.getPosition());
			update_employee_address.setText(employee.getAddress());
			update_employee_zipcode.setText(employee.getZipcode());
			update_employee_country.setText(employee.getCountry());		
			update_employee_birthdate.setText(employee.getPersNbr());
		}
		else if(fxmlFile.equals("EditCustomerWindow.fxml")) {
			System.out.println(customer.getId());
			update_customer_name.setText(customer.getName());
			update_customer_occupation.setText(customer.getOccupation());
			update_customer_address.setText(customer.getAddress());
			update_customer_zipcode.setText(customer.getZipcode());
			update_customer_country.setText(customer.getCountry());
			update_customer_birthdate.setText(customer.getPersNbr());
		}
		else if(fxmlFile.equals("EditCommentWindow.fxml")) {
			System.out.println(comment.getId());
			List<Employer> employers = db.getEmployers();
			List<String> emplrNames = new ArrayList<String>();
			List<Employee> employees = db.getEmployees();
			List<String> emplNames = new ArrayList<String>();
			
			for(int i=0;i<employers.size();i++){
				emplrNames.add(employers.get(i).getEmployerID());
			}
			for(int i=0;i<employees.size();i++){
				emplNames.add(employees.get(i).getEmployeeID());
			}
			update_comment_employer.setItems(FXCollections.observableArrayList(emplrNames));
			update_comment_employee.setItems(FXCollections.observableArrayList(emplNames));
			
			update_comment_employer.getSelectionModel().select(comment.getEmployerID());
			update_comment_employee.getSelectionModel().select(comment.getEmployeeID());
			update_comment_comment.setText(comment.getComment());
		}
	}
}
