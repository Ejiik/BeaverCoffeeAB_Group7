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
	
	public void ShowAddProduct(ActionEvent event) throws IOException {
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
			
		}
		else if(source.equals("Add Order")) {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("gui/AddOrderWindow.fxml"));
		}
		
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setTitle("Add Product");
		stage.setScene(scene);
		stage.show();	
	}
	
	/*
	 * Method for adding a product to the database
	 */
	public void AddProduct() {
		System.out.println("Added Product");
	}
	/*
	 * Method for adding a customer to the database
	 */
	public void AddCustomer() {
		System.out.println("Added Customer");
	}
	/*
	 * Method for adding a order to the database
	 */
	public void AddOrder() {
		System.out.println("Added Customer");
	}
}
