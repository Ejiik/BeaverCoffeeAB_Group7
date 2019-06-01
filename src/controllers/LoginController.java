package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import database.Database;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import models.Customer;
import models.Employee;
import models.Employer;

public class LoginController implements Initializable{
	@FXML
	private ChoiceBox<String> input_login_username;
	@FXML
	private ChoiceBox<String> input_login_type;
	@FXML
	private Button btnLogin;
	
	private String userType;
	ObservableList<String> choices;
	List<String> usernames = new ArrayList<String>();
	
	private Database db = new Database();
	
	
	public void start(){
		try {
			userType = input_login_type.getSelectionModel().getSelectedItem();
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/Menu.fxml"));
			loader.setController(new Controller(userType));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("BeaverCoffee AB");
			stage.setScene(scene);
			stage.show();
			
			((Stage)input_login_type.getScene().getWindow()).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		input_login_username.setDisable(true);
		btnLogin.setDisable(true);
		
		input_login_type.setItems(FXCollections.observableArrayList(Arrays.asList("Employee", "Employer", "Customer")));
	
		input_login_type.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				input_login_username.setDisable(false);
				if(newValue != null){
					usernames.clear();
					switch(newValue){
					case("Employee"):
						List<Employee> employees = db.getEmployees();
						for(int i = 0;i < employees.size();i++){
							usernames.add(employees.get(i).getEmployeeID());
						}
						choices = FXCollections.observableArrayList(usernames);
						input_login_username.setItems(choices);
						break;
					case("Employer"):
						usernames.add("New employer");
						List<Employer> employers = db.getEmployers();
						for(int i = 0;i < employers.size();i++){
							usernames.add(employers.get(i).getEmployerID());
						}
						choices = FXCollections.observableArrayList(usernames);
						input_login_username.setItems(choices);
						break;
					case("Customer"):
						usernames.add("Not a member");
						List<Customer> customers = db.getCustomers();
						for(int i = 0;i < customers.size();i++){
							usernames.add(customers.get(i).getCustomerID());
						}
						choices = FXCollections.observableArrayList(usernames);
						input_login_username.setItems(choices);
						break;
					}
				}
			}
			
		});
		input_login_username.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String newValue) {
				if((newValue != null) && (newValue.equals("New employer"))){
					input_login_type.getSelectionModel().clearSelection();
					input_login_username.getSelectionModel().clearSelection();
					input_login_username.setDisable(true);
					FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gui/AddEmployerWindow.fxml"));
					loader.setController(new Controller());
					try {
						Parent root = loader.load();
						Scene scene = new Scene(root);
						Stage stage = new Stage();
						stage.setTitle("Add new Employer");
						stage.setScene(scene);
						stage.show();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					btnLogin.setDisable(false);
				}
			}
		});
	}

	
}
