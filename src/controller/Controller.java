package controller;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import database.Database;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Controller implements Initializable {

	Database db = new Database();
	
	@FXML
	private TextField input_product_name;
	@FXML
	private TextField input_product_type;
	@FXML
	private TextField input_product_price;
	@FXML
	private TextField input_product_units;
	@FXML
	private TextArea list_product_ingredients;
	
	@FXML
	private TextField input_customer_name;
	@FXML
	private TextField input_customer_occupation;
	@FXML
	private TextField input_customer_address;
	@FXML
	private TextField input_customer_zipcode;
	@FXML
	private TextField input_customer_country;
	@FXML
	private TextField input_customer_birthdate;
	
	@FXML
	private TextField input_order_cashierID;
	@FXML
	private TextField input_order_customerID;
	@FXML 
	private TextArea list_order_products;
	@FXML
	private ChoiceBox<String> input_order_productList;
	
	@FXML
	private TextField input_employee_name;
	@FXML
	private TextField input_employee_position;
	@FXML
	private TextField input_employee_address;
	@FXML
	private TextField input_employee_zipcode;
	@FXML
	private TextField input_employee_birthdate;
	
	@FXML
	private TextField input_employer_name;
	@FXML
	private TextField input_employer_birthdate;
	
	@FXML
	private ChoiceBox<String> input_comment_employer;
	@FXML
	private ChoiceBox<String> input_comment_employee;
	@FXML
	private TextArea input_comment_comment;
	@FXML
	private TableView menu_table_view;
	
	public Controller() {
		
	}
	
	public void ShowNewScene(ActionEvent event) throws IOException {
		String source = ((Button)event.getSource()).getText();
		System.out.println(source + " Window");
		FXMLLoader loader;
		
		Parent root = null;
		
		if(source.equals("Add Product")) {
			loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/AddProductWindow.fxml"));
			loader.setController(this);
			root = loader.load();
		}
		else if(source.equals("Add Customer")) {
			loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/AddCustomerWindow.fxml"));
			loader.setController(new Controller());
			root = loader.load();
		}
		else if(source.equals("Add Employee")) {
			loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/AddEmployeeWindow.fxml"));
			loader.setController(new Controller());
			root = loader.load();
		}
		else if(source.equals("Add Order")) {
			loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/AddOrderWindow.fxml"));
			loader.setController(new Controller());
			root = loader.load();
		}
		else if(source.equals("Add Comment")) {
			loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/AddCommentWindow.fxml"));
			loader.setController(new Controller());
			root = loader.load();
		}
		else if(source.equals("Add Employer")) {
			loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/AddEmployerWindow.fxml"));
			loader.setController(new Controller());
			root = loader.load();
		}
		
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setTitle(source);
		stage.setScene(scene);
		stage.show();	
		
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
	
	public void addProduct(){
		
		System.out.println("whaa");
		List<Product> products = db.getProducts();
		String prodChoice = input_order_productList.getSelectionModel().getSelectedItem();
		Product product = new Product();
		for(int i=0;i<products.size();i++){
			if(products.get(i).getName().equals(prodChoice)){
				product = products.get(i);
			}
		}
		if(product.getInStock()){
			list_order_products.appendText(product.getName() + "\n");
		}
	}
	
	public void removeProduct(){
		String prodChoice = input_order_productList.getSelectionModel().getSelectedItem();
		list_order_products.getText().replace(prodChoice + "\n", "");
	}
	
	/**
	 * Method for adding a product to the database
	 */
	public void AddProduct() {
		System.out.println("Added Product");
		Product product = new Product();
		if(input_product_name.getText().isEmpty() || input_product_type.getText().isEmpty() 
				|| input_product_units.getText().isEmpty() || input_product_price.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Please fill all required fields!");
		}else{
			String priceStr = input_product_price.getText();
			int price = formatPrice(priceStr);
			
			product.setType(input_product_type.getText());
			product.setName(input_product_name.getText());
			product.setPrice(price);
			List<String> ingredients = Arrays.asList(list_product_ingredients.getText().split("\n"));
			product.setIngredients(ingredients);
			product.setUnits(Integer.parseInt(input_product_units.getText()));
			db.insertProduct(product);
			((Stage)input_product_name.getScene().getWindow()).close();
		}
		
	}
	/**
	 * Method for adding a customer to the database
	 */
	public void AddCustomer() {
		System.out.println("Added Customer");
		Customer customer = new Customer();
		if(input_customer_name.getText().isEmpty() || input_customer_occupation.getText().isEmpty() || 
				input_customer_birthdate.getText().isEmpty() || input_customer_country.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Please fill all required fields!");
		}else{
			if(input_customer_name.getText().split(" ").length == 1){
				JOptionPane.showMessageDialog(null, "Please enter both first and last name!");
			}else{
				String adress;
				String zipCode;
				customer.setRegistrationDate(currentFormattedDate());
				customer.setName(input_customer_name.getText());
				customer.setOccupation(input_customer_occupation.getText());
				customer.setPersNbr(input_customer_birthdate.getText());
				if(input_customer_address.getText().isEmpty()){
					adress ="n/a";
				}else{
					adress = input_customer_address.getText();
				}
				if(input_customer_zipcode.getText().isEmpty()){
					zipCode = "n/a";
				}else{
					zipCode = input_customer_zipcode.getText();
				}
				customer.setLocation(Arrays.asList(adress, zipCode));
				customer.setCountry(input_customer_country.getText());
				db.insertCustomer(customer);
				((Stage)input_customer_name.getScene().getWindow()).close();
			}
		}
	}
	/**
	 * Method for adding a order to the database
	 */
	public void AddOrder() {
		System.out.println("Added Order");
		Order order = new Order();
		if(input_order_cashierID.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Please fill all required fields!");
		}else{
			String[] productNames = list_order_products.getText().split("\n");
			List<Product> products = new ArrayList<>();
			int total = 0;
			
			List<Product> prods = db.getProducts();
			
			for(int i = 0;i<prods.size();i++){
					for(int j=0;j<productNames.length;j++){
						if(prods.get(i).getName() == productNames[j]){
							products.add(prods.get(i));
							total += prods.get(i).getPrice();
						}
					}
				}
			
			order.setOrderID(input_order_cashierID.getText().substring(0, 3) + currentFormattedDate().substring(4));
			order.setDate(currentFormattedDate());
			order.setTotal(total);
			if(input_order_customerID.getText().isEmpty()){
				order.setCustomer("n/a");
			}else{
				order.setCustomer(input_order_customerID.getText());
			}
			
			order.setCashier(input_order_cashierID.getText());
			order.setProducts(products);
			db.insertOrder(order);
			((Stage)input_order_cashierID.getScene().getWindow()).close();
		}
	}
	/**
	 * Method for adding a employee to the database
	 */
	public void AddEmployee() {
		System.out.println("Added Employee");
		Employee employee = new Employee();
		if(input_employee_name.getText().isEmpty() || input_employee_birthdate.getText().isEmpty() || input_employee_address.getText().isEmpty() 
				|| input_employee_zipcode.getText().isEmpty() || input_employee_position.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Please fill all required fields!");
		}else{
			if(input_employee_name.getText().split(" ").length == 1){
				JOptionPane.showMessageDialog(null, "Please enter both first and last name!");
			}else{
				String[] tempStrArr = input_employee_name.getText().split(" ");
				String employeeID = tempStrArr[0].substring(0, 2) + tempStrArr[1].substring(0, 2) + input_employee_birthdate.getText().substring(2, 6);
				System.out.println(employeeID);
				
				employee.setName(input_employee_name.getText());
				employee.setPersNbr(input_employee_birthdate.getText());
				employee.setEmployeeID(employeeID);
				employee.setLocation(Arrays.asList(input_employee_address.getText(),input_employee_zipcode.getText()));
				employee.setPosition(input_employee_position.getText());
				employee.setStartDate(currentFormattedDate());
				db.insertEmployee(employee);
				((Stage)input_employee_name.getScene().getWindow()).close();
			}
		}
	}
	/**
	 * Method for adding a employer to the database
	 */
	public void AddEmployer() {
		System.out.println("Added Employer");
		Employer employer = new Employer();
		if(input_employer_name.getText().isEmpty() || input_employer_birthdate.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Please fill all required fields!");
		}else{
			if(input_employer_name.getText().split(" ").length == 1){
				JOptionPane.showMessageDialog(null, "Please enter both first and last name!");
			}else{
				String[] tempStrArr = input_employer_name.getText().split(" ");
				String employerID = tempStrArr[0].substring(0, 2) + tempStrArr[1].substring(0, 2) + input_employer_birthdate.getText().substring(2, 6);
				System.out.println(employerID);
				employer.setName(input_employer_name.getText());
				employer.setEmployerID(employerID);
				employer.setPersNbr(input_employer_birthdate.getText());
				db.insertEmployer(employer);
				((Stage)input_employer_name.getScene().getWindow()).close();
			}
		}
	}
	/**
	 * Method for adding a comment to a employee
	 */
	public void AddComment() {
		System.out.println("Added Comment");
		Comment comment = new Comment();
		if(input_comment_employer.getSelectionModel().isEmpty() || input_comment_employee.getSelectionModel().isEmpty() || 
				input_comment_comment.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Please fill all required fields!");
		}else{
			comment.setEmployerID(input_comment_employer.getSelectionModel().getSelectedItem());
			comment.setEmployeeID(input_comment_employee.getSelectionModel().getSelectedItem());
			comment.setDate(currentFormattedDate());
			comment.setComment(input_comment_comment.getText());
			((Stage)input_comment_employer.getScene().getWindow()).close();
		}
	}
	
	public void ShowOrderCollection() {
		System.out.println("Show Order Collection");
		
		menu_table_view.setVisible(true);
		menu_table_view.getColumns().clear();
		
		TableColumn id = new TableColumn("ID");
		TableColumn cashierID = new TableColumn("CashierID");
		TableColumn customerID = new TableColumn("CustomerID");
		TableColumn product = new TableColumn("Product(s)");
		TableColumn date = new TableColumn("Date");
		
		menu_table_view.getColumns().addAll(id,cashierID,customerID,product,date);
		
	}

	public void ShowEmployeeCollection() {
		System.out.println("Show Employee Collection");
		
		menu_table_view.setVisible(true);
		menu_table_view.getColumns().clear();
		
		TableColumn id = new TableColumn("ID");
		TableColumn name = new TableColumn("Name");
		TableColumn position = new TableColumn("Position");
		TableColumn address = new TableColumn("Address");
		TableColumn zipcode = new TableColumn("Zip-Code");
		TableColumn country = new TableColumn("Country");
		
		menu_table_view.getColumns().addAll(id,name,position,address,zipcode,country);
	}
	
	public void ShowProductCollection() {
		System.out.println("Show Product Collection");
		
		menu_table_view.setVisible(true);
		menu_table_view.getColumns().clear();
		
		TableColumn id = new TableColumn("ID");
		TableColumn name = new TableColumn("Name");
		TableColumn type = new TableColumn("Type");
		TableColumn price = new TableColumn("Price");
		TableColumn units = new TableColumn("Units");
		TableColumn ingredients = new TableColumn("Ingredients");
		
		menu_table_view.getColumns().addAll(id,name,type,price,units,ingredients);
		
	}
	
	@Override
	public void initialize(URL path, ResourceBundle arg1) {
		String fxmlFile = path.getPath().substring(path.getPath().lastIndexOf('/')+1);
		// TODO Auto-generated method stub
		System.out.println(fxmlFile);
		if(fxmlFile.equals("AddOrderWindow.fxml")){
			List<Product> prods = db.getProducts();
			List<String> prodNames = new ArrayList<String>();
			for(int i=0;i<prods.size();i++){
				prodNames.add(prods.get(i).getName());
				System.out.println("Found: " + prods.get(i).getName() + ", Added: " + prodNames.get(i));
			}
			input_order_productList.setItems(FXCollections.observableArrayList(prodNames));
		}
	}
}
