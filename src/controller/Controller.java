package controller;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class Controller {

	public Controller() {
		
	}
	
	public void ShowNewScene(ActionEvent event) throws IOException {
		String source = ((Button)event.getSource()).getText();
		System.out.println(source + " Window");
		
		Parent root = null;
		
		if(source.equals("Add Product")) {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/AddProductWindow.fxml"));
		}
		else if(source.equals("Add Customer")) {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/AddCustomerWindow.fxml"));
		}
		else if(source.equals("Add Employee")) {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/AddEmployeeWindow.fxml"));
		}
		else if(source.equals("Add Order")) {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/AddOrderWindow.fxml"));
		}
		else if(source.equals("Add Comment")) {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/AddCommentWindow.fxml"));
		}
		else if(source.equals("Add Employer")) {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/AddEmployerWindow.fxml"));
		}
	
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setTitle(source);
		stage.setScene(scene);
		stage.show();	
	}
	
	/**
	 * Method for adding a product to the database
	 */
	public void AddProduct() {
		System.out.println("Added Product");
	}
	/**
	 * Method for adding a customer to the database
	 */
	public void AddCustomer() {
		System.out.println("Added Customer");
	}
	/**
	 * Method for adding a order to the database
	 */
	public void AddOrder() {
		System.out.println("Added Customer");
	}
	/**
	 * Method for adding a employee to the database
	 */
	public void AddEmployee() {
		System.out.println("Added Employee");
	}
	/**
	 * Method for adding a employer to the database
	 */
	public void AddEmployer() {
		System.out.println("Added Employer");
	}
	/**
	 * Method for adding a comment to a employee
	 */
	public void AddComment() {
		System.out.println("Added Comment");
	}
}
