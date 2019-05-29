package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;

import database.Database;

public class Communication implements Initializable {

	@FXML
	TextArea txtAreaQuery;
	@FXML
	Label lblCmd;
	@FXML
	Button btnCmd;
	@FXML
	TextArea txtAreaInfo;
	@FXML
	ChoiceBox<String> cbAction;
	@FXML
	ChoiceBox<String> cbCollection;
	@FXML
	TextField field0;
	@FXML
	TextField field1;
	@FXML
	TextField field2;
	@FXML
	TextField field3;
	@FXML
	TextField field4;
	@FXML
	TextField field5;
	@FXML
	TextField field6;
	@FXML
	TextArea fieldBig;
	@FXML
	TextField fieldNumeric;
	@FXML
	CheckBox checkBox0;
	@FXML
	ChoiceBox<String> cbProducts;
	@FXML
	Button btnAddProd;
	@FXML
	Button btnRemoveProd;
	
	List<Product> products = new ArrayList<Product>();
	
	Database db = new Database();
	
	public void decodeCommand(){
		Document doc = new Document();
		LocalDateTime ldt = LocalDateTime.now().plusDays(1);
		DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
		String formdDate = formmat1.format(ldt);
		if(!txtAreaQuery.getText().isEmpty()){
			System.out.println("-" + txtAreaQuery.getText() + "-");
			String[] query = txtAreaQuery.getText().split(", ");
			
			switch(query[0]){
			case "add":
				switch(query[1]){
				
				case "employee":
					doc.append("name", query[2]);
					doc.append("SSN", query[3]);
					doc.append("employeeID", query[4]);
					doc.append("location", Arrays.asList(query[5],query[6]));
					doc.append("position", query[7]);
					doc.append("startDate", formdDate);
					break;
				case "order":
					doc.append("date", formdDate);
					List<String> products = new ArrayList<String>();
					for(int i=5;i<query.length;i++){
						products.add(query[i]);
					}
					doc.append("products", products);
					break;
				case "customer":
					doc.append("registrationDate", formdDate);
					doc.append("name", query[2]);
					doc.append("occupation", query[3]);
					doc.append("SSN", query[4]);
					doc.append("location", Arrays.asList(query[5], query[6]));
					doc.append("country", query[7]);
					break;
				case "employer":
					doc.append("name", query[2]);
					doc.append("SSN", query[3]);
					break;
				case "comment":
					doc.append("employerID", query[2]);
					doc.append("employeeID", query[3]);
					doc.append("date", formdDate);
					doc.append("comment", txtAreaQuery.getText().substring(33));
					break;
				case "product":
					doc.append("type", query[2]);
					doc.append("name", query[3]);
					doc.append("price", Integer.parseInt(query[4]));
					List<String> ingredients = new ArrayList<String>();
					for(int i=5;i<query.length-1;i++){
						ingredients.add(query[i]);
					}
					doc.append("ingredients", ingredients);
					doc.append("units", query[query.length-1]);
					break;
				}
				
				db.insertDoc(doc, query[1]);
				txtAreaInfo.setText("Entry added");
				break;
			case "get":
				txtAreaInfo.clear();
				MongoCursor<Document> cursor = db.getCollection(query[1]).find().iterator();
				try{
					while(cursor.hasNext()){
						
						String[] document = cursor.next().toJson().split(",");
						for(int i=0;i<document.length;i++){
							txtAreaInfo.appendText(document[i] + "\n");
						}
						txtAreaInfo.appendText("\n");
					}
				} finally{
					cursor.close();
				}
				break;
			case "update":
				
				break;
			case "delete":
				MongoCollection<Document> collection = db.getCollection(query[1]);
				if(query.length == 2){
					collection.drop();
					break;
				}
				collection.deleteOne(Filters.eq(query[2], query[3]));
				txtAreaInfo.setText("Entry deleted");
			}
		}else{
			String action = cbAction.getSelectionModel().getSelectedItem();
			String collection = cbCollection.getSelectionModel().getSelectedItem();
			Boolean acceptableResult = true;
			switch(action){
			case"Add":
				switch(collection){
				
				case "employee":
					if(field0.getText().isEmpty() || field1.getText().isEmpty() || field2.getText().isEmpty() 
							|| field3.getText().isEmpty() || field4.getText().isEmpty()){
						txtAreaInfo.setText("Please fill all required fields!");
					}else{
						if(field0.getText().split(" ").length == 1){
							txtAreaInfo.setText("Please enter both first and surname!");
						}else{
							String[] tempStrArr = field0.getText().split(" ");
							String employeeID = tempStrArr[0].substring(0, 2) + tempStrArr[1].substring(0, 2) + field1.getText().substring(2, 6);
							System.out.println(employeeID);
							
							doc.append("name", field0.getText());
							doc.append("SSN", field1.getText());
							doc.append("employeeID", employeeID);
							doc.append("location", Arrays.asList(field2.getText(),field3.getText()));
							doc.append("position", field4.getText());
							doc.append("startDate", formdDate);
						}
					}
					break;
				case "order":
					if(field0.getText().isEmpty() || fieldBig.getText().isEmpty()){
						txtAreaInfo.setText("Please fill all required fields!");
					}else{
						String[] productNames = fieldBig.getText().split("\n");
						List<Product> products = new ArrayList<>();
						int total = 0;
						
						List<Product> prods = db.getProducts();
						
						for(int i = 0;i<prods.size();i++){
								for(int j=0;j<productNames.length;j++){
									if(prods.get(i).getName() == productNames[j]){
										if(prods.get(i).getInStock()){
											products.add(prods.get(i));
											total += prods.get(i).getPrice();
										}else{
											if(acceptableResult){
												acceptableResult = false;
											}
											if(txtAreaInfo.getText().isEmpty()){
												txtAreaInfo.setText("Product(s) out of stock: \n" + prods.get(i).getName() + "\n");
											}else{
												txtAreaInfo.appendText(prods.get(i).getName() + "\n");
											}
										}
									}
								}
							}
						
						doc.append("orderID", field0.getText().substring(0, 3) + formdDate.substring(4));
						doc.append("date", formdDate);
						doc.append("total", total);
						if(field1.getText().isEmpty()){
							doc.append("customer", "n/a");
						}else{
							doc.append("customer", field1.getText());
						}
						doc.append("cashier", field0.getText());
						doc.append("products", products);
					}
					break;
				case "customer":
					if(field0.getText().isEmpty() || field1.getText().isEmpty() || 
							field2.getText().isEmpty() || field5.getText().isEmpty()){
						txtAreaInfo.setText("Please fill all required fields!");
					}else{
						if(field0.getText().split(" ").length == 1){
							txtAreaInfo.setText("Please enter both first and surname!");
						}else{
							String adress;
							String zipCode;
							doc.append("registrationDate", formdDate);
							doc.append("name", field0.getText());
							doc.append("occupation", field1.getText());
							doc.append("SSN", field2.getText());
							if(field3.getText().isEmpty()){
								adress ="n/a";
							}else{
								adress = field3.getText();
							}
							if(field4.getText().isEmpty()){
								zipCode = "n/a";
							}else{
								zipCode = field4.getText();
							}
							doc.append("location", Arrays.asList(adress, zipCode));
							doc.append("country", field5.getText());
						}
					}
					break;
				case "employer":
					if(field0.getText().isEmpty() || field1.getText().isEmpty()){
						txtAreaInfo.setText("Please fill all required fields!");
					}else{
						if(field0.getText().split(" ").length == 1){
							txtAreaInfo.setText("Please enter both first and surname!");
						}else{
							String[] tempStrArr = field0.getText().split(" ");
							String employerID = tempStrArr[0].substring(0, 2) + tempStrArr[1].substring(0, 2) + field1.getText().substring(2, 6);
							System.out.println(employerID);
							doc.append("name", field0.getText());
							doc.append("employerID", employerID);
							doc.append("SSN", field1.getText());
						}
					}
					break;
				case "comment":
					if(field0.getText().isEmpty() || field1.getText().isEmpty() || 
							fieldBig.getText().isEmpty()){
						txtAreaInfo.setText("Please fill all required fields!");
					}else{
						doc.append("employerID", field0.getText());
						doc.append("employeeID", field1.getText());
						doc.append("date", formdDate);
						doc.append("comment", fieldBig.getText());
					}
					break;
				case "product":
					Product product = new Product();
					if(field0.getText().isEmpty() || field1.getText().isEmpty() 
							|| field3.getText().isEmpty() || fieldNumeric.getText().isEmpty()){
						txtAreaInfo.setText("Please fill all required fields!");
					}else{
						String priceStr = fieldNumeric.getText();
						int price = formatPrice(priceStr);
						
						product.setType(field0.getText());
						product.setName(field1.getText());
						product.setPrice(price);
//						doc.append("type", field0.getText());
//						doc.append("name", field1.getText());
//						doc.append("price", price);
						List<String> ingredients = Arrays.asList(fieldBig.getText().split("\n"));
						product.setIngredients(ingredients);
						product.setUnits(Integer.parseInt(field3.getText()));
//						doc.append("ingredients", ingredients);
//						doc.append("units", Integer.parseInt(field3.getText()));
						db.insertProduct(product);
					}
					break;
				}
				if(!doc.isEmpty() && acceptableResult){
					db.insertDoc(doc, collection);
					txtAreaInfo.setText("Entry added");
				}
				break;
			case"Get":
				if(!field0.getText().isEmpty() && !field1.getText().isEmpty() && !collection.isEmpty()){
					txtAreaInfo.clear();
					MongoCursor<Document> cursor = db.getCollection(collection).find(Filters.eq(field0.getText(), field1.getText())).iterator();
					try{
						while(cursor.hasNext()){
							
							String[] document = cursor.next().toJson().split(",");
							for(int i=0;i<document.length;i++){
								txtAreaInfo.appendText(document[i] + "\n");
							}
							txtAreaInfo.appendText("\n");
						}
					} finally{
						cursor.close();
					}
				} else if(field0.getText().isEmpty() && field1.getText().isEmpty() && !collection.isEmpty()){
					txtAreaInfo.clear();
					MongoCursor<Document> cursor = db.getCollection(collection).find().iterator();
					try{
						while(cursor.hasNext()){
							
							String[] document = cursor.next().toJson().replace("{", "").replaceAll("\"", "").replaceAll("}", "").split(",");
							for(int i=0;i<document.length;i++){
								txtAreaInfo.appendText(document[i] + "\n");
							}
							txtAreaInfo.appendText("\n");
						}
					} finally{
						cursor.close();
					}
				}
				break;
			case"Update":
				if(!field0.getText().isEmpty() && !field1.getText().isEmpty() && !field3.getText().isEmpty()
						&& !field4.getText().isEmpty() && !cbCollection.getSelectionModel().getSelectedItem().isEmpty() && !field3.getText().contentEquals("price")
						&& !field3.getText().contentEquals("ingredients") && !field3.getText().contentEquals("units")){
					db.updateDoc(collection, field0.getText(), field1.getText(), field3.getText(), field4.getText(), checkBox0.isSelected());
					txtAreaInfo.setText("Update(s) made!");
				}else if(field3.getText().contentEquals("price")){
					db.updateDoc(collection, field0.getText(), field1.getText(), field3.getText(), formatPrice(field4.getText()), checkBox0.isSelected());
					txtAreaInfo.setText("Update(s) made!");
				}else if(field3.getText().contentEquals("ingredients")){
					db.updateDoc(collection, field0.getText(), field1.getText(), field3.getText(), Arrays.asList(fieldBig.getText().split("\n")), checkBox0.isSelected());
					txtAreaInfo.setText("Update(s) made!");
				}else if(field3.getText().contentEquals("units")){
					db.updateDoc(collection, field0.getText(), field1.getText(), field3.getText(), Integer.parseInt(field4.getText()), checkBox0.isSelected());
					txtAreaInfo.setText("Update(s) made!");
				}
				break;
			case"Delete":
				if(!field0.getText().isEmpty() && !field1.getText().isEmpty() && !cbCollection.getSelectionModel().getSelectedItem().isEmpty()){
					db.deleteDoc(cbCollection.getSelectionModel().getSelectedItem(), field0.getText(), field1.getText());
					txtAreaInfo.setText("Entry deleted!");
				}
				break;
			}
		}
		
		
	}
	
	public void resetInputFields(){
		field0.setVisible(false);
		field1.setVisible(false);
		field2.setVisible(false);
		field3.setVisible(false);
		field4.setVisible(false);
		field5.setVisible(false);
		field6.setVisible(false);
		fieldBig.setVisible(false);
		checkBox0.setVisible(false);
		cbProducts.setVisible(false);
		btnAddProd.setVisible(false);
		btnRemoveProd.setVisible(false);
		field0.clear();
		field1.clear();
		field2.clear();
		field3.clear();
		field4.clear();
		field5.clear();
		field6.clear();
		fieldBig.clear();
		fieldBig.setLayoutX(294);
		fieldBig.setLayoutY(97);
		fieldBig.setPrefWidth(129);
		fieldBig.setPrefHeight(56);
		fieldNumeric.setVisible(false);
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
	
	public void toggleCmdPrompt(){
		if(txtAreaQuery.isVisible()){
			lblCmd.setVisible(false);
			txtAreaQuery.setVisible(false);
			txtAreaQuery.clear();
		}else{
			lblCmd.setVisible(true);
			txtAreaQuery.setVisible(true);
		}
	}
	
	public void setInputFields(String action, String collection){

		if(action == "Get" || action == "Delete" || action == "Update"){
			switch(action){
			case"Get":
				field0.setVisible(true);
				field0.setPromptText("ID-key (All if empty)");
				field1.setVisible(true);
				field1.setPromptText("value (All if empty)");
				break;
			case "Delete":
				field0.setVisible(true);
				field0.setPromptText("ID-key");
				field1.setVisible(true);
				field1.setPromptText("value");
				break;
			case "Update":
				field0.setVisible(true);
				field0.setPromptText("ID-key");
				field1.setVisible(true);
				field1.setPromptText("value");
				field3.setVisible(true);
				field3.setPromptText("Property to change");
				field4.setVisible(true);
				field4.setPromptText("new value");
				checkBox0.setVisible(true);
				if(cbCollection.getSelectionModel().getSelectedItem() == "product"){
					fieldBig.setVisible(true);
					fieldBig.setPrefHeight(86);
					fieldBig.setLayoutY(96);
					fieldBig.setPromptText("ingredients");
				}else{
					fieldBig.setVisible(false);
					fieldBig.setPrefHeight(56);
					fieldBig.setLayoutY(97);
					fieldBig.clear();
				}
				break;
			}
		}else{
			switch(collection){
			case"employee":
				field0.setVisible(true);
				field1.setVisible(true);
				field2.setVisible(true);
				field3.setVisible(true);
				field4.setVisible(true);
				field0.setPromptText("name*");
				field1.setPromptText("YYMMDDXXXX*");
				field2.setPromptText("adress*");
				field3.setPromptText("zip code*");
				field4.setPromptText("position*");
				break;
			case"order":
				field0.setVisible(true);
				field1.setVisible(true);
				fieldBig.setVisible(true);
				cbProducts.setVisible(true);
				btnAddProd.setVisible(true);
				btnRemoveProd.setVisible(true);
				field0.setPromptText("cashier ID*");
				field1.setPromptText("customer ID");
				fieldBig.setPromptText("products*");
				fieldBig.setPrefHeight(86);
				fieldBig.setLayoutY(66);
				List<Product> prods = db.getProducts();
				ArrayList<String> prodNames = new ArrayList<String>();
				System.out.println(prods.get(0).getName());
				for(int i=0;i<prods.size();i++){
					prodNames.add(prods.get(i).getName());
					System.out.println("Found: " + prods.get(i).getName() + ", Added: " + prodNames.get(i));
				}
				cbProducts.setItems(FXCollections.observableArrayList(prodNames));
				break;
			case"customer":
				field0.setVisible(true);
				field1.setVisible(true);
				field2.setVisible(true);
				field3.setVisible(true);
				field4.setVisible(true);
				field5.setVisible(true);
				field6.setVisible(true);
				field0.setPromptText("name*");
				field1.setPromptText("occupation*");
				field2.setPromptText("YYMMDDXXXX*");
				field3.setPromptText("adress");
				field4.setPromptText("zip code");
				field5.setPromptText("club card nr*");
				field6.setPromptText("country*");
				break;
			case"employer":
				field0.setVisible(true);
				field1.setVisible(true);
				field0.setPromptText("name*");
				field1.setPromptText("YYMMDDXXXX*");
				break;
			case"comment":
				field0.setVisible(true);
				field1.setVisible(true);
				fieldBig.setVisible(true);
				field0.setPromptText("employer ID*");
				field1.setPromptText("employee ID*");
				fieldBig.setPromptText("comment*");
				fieldBig.setLayoutX(14);
				fieldBig.setPrefWidth(409);
				break;
			case"product":
				field0.setVisible(true);
				field1.setVisible(true);
				fieldNumeric.setVisible(true);
				fieldBig.setVisible(true);
				fieldBig.setPrefHeight(86);
				fieldBig.setLayoutY(66);
				field3.setVisible(true);
				field0.setPromptText("type*");
				field1.setPromptText("name*");
				fieldNumeric.setPromptText("price*");
				fieldBig.setPromptText("ingredients");
				field3.setPromptText("units*");
				break;
			}
		}
	}
	
	public void addProduct(){
		products = db.getProducts();
		String prodChoice = cbProducts.getSelectionModel().selectedItemProperty().getName();
		Product product = new Product();
		for(int i=0;i<products.size();i++){
			if(products.get(i).getName().equals(prodChoice)){
				product = products.get(i);
			}
		}
		if(product.getInStock()){
			fieldBig.appendText(product.getName() + "\n");
		}
	}
	public void removeProduct(){
		String prodChoice = cbProducts.getSelectionModel().selectedItemProperty().getName();
		fieldBig.getText().replace(prodChoice + "\n", "");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		cbAction.setItems(FXCollections.observableArrayList("Add", "Get", "Update", "Delete"));
		cbCollection.setItems(FXCollections.observableArrayList("employee", "order", "customer", "employer", "comment", "product"));
		lblCmd.setVisible(false);
		txtAreaQuery.setVisible(false);
		resetInputFields();
		
		fieldNumeric.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                	fieldNumeric.setText(oldValue);
                }
            }
        });
		
		cbAction.getSelectionModel().select(0);
		cbAction.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				resetInputFields();
				setInputFields(newValue, cbCollection.getSelectionModel().getSelectedItem());
			}
			
		});
		cbCollection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					resetInputFields();
					setInputFields(cbAction.getSelectionModel().getSelectedItem(), newValue);
			}
		});
		cbCollection.getSelectionModel().select(1);
	}
}
