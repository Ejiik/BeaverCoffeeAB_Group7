package database;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import models.ClubCard;
import models.Comment;
import models.Customer;
import models.Employee;
import models.Employer;
import models.Order;
import models.Product;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;

public class Database {
	MongoClient mongoClient;
	MongoDatabase db;
	
	public Database(){
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoClientSettings settings = MongoClientSettings.builder().codecRegistry(pojoCodecRegistry).build();
		mongoClient = MongoClients.create(settings);
		db = mongoClient.getDatabase("beaverCoffee");
		
	}
	
	/**
	 * Resets the DB, deleting all entries
	 */
	public void resetDB(){
		db.drop();
		db = mongoClient.getDatabase("beaverCoffee");
	}
	
	/**
	 * Accesses a collection, or creates a new one if it doesn't exist.
	 * @param coll - Name of collection
	 */
	public MongoCollection<Document> getCollection(String coll){
		MongoCollection<Document> collection = db.getCollection(coll);
		
		return collection;
	}
	/**
	 * Returns an iterable list of all Collection names.
	 */
	public MongoIterable<String> getCollectionNames(){
		return db.listCollectionNames();
	}

	/**
	 * Removes the specified amount of units from the specified product.
	 * @param product
	 * @param amount
	 */
	public void removeUnit(Product product){
		MongoCollection<Document> coll = db.getCollection("product");
		int newAmount = product.getUnits() - 1;
		coll.updateOne(eq("name", product.getName()), set("units", newAmount));
		if(newAmount == 0){
			coll.updateOne(eq("name", product.getName()), set("inStock", false));
		}
		
	}

	
	/**
	 * Returns a List<Product> containing all Product-Objects in the database.
	 * @return
	 */
	public List<Product> getProducts(){
		MongoCollection<Product> products = db.getCollection("product", Product.class);
		List<Product> res = new ArrayList<>();
		MongoCursor<Product> cursor = products.find().iterator();
		try{
			while(cursor.hasNext()){
				res.add(cursor.next());
			}
		}finally{
			cursor.close();
		}
		
		return res;
	}
	
	/**
	 * Returns a List<Order> containing all Order-Objects in the database.
	 * @return
	 */
	public List<Order> getOrders(){
		MongoCollection<Order> orders = db.getCollection("order", Order.class);
		List<Order> res = new ArrayList<Order>();
		MongoCursor<Order> cursor = orders.find().iterator();
		try{
			while(cursor.hasNext()){
				res.add(cursor.next());
			}
		}finally{
			cursor.close();
		}
		
		return res;
	}
	
	/**
	 * Returns a List<Employee> containing all Employee-Objects in the database.
	 * @return
	 */
	public List<Employee> getEmployees(){
		MongoCollection<Employee> employees = db.getCollection("employee", Employee.class);
		List<Employee> res = new ArrayList<Employee>();
		MongoCursor<Employee> cursor = employees.find().iterator();
		try{
			while(cursor.hasNext()){
				res.add(cursor.next());
			}
		}finally{
			cursor.close();
		}
		
		return res;
	}
	
	/**
	 * Returns a List<Employer> containing all Employer-Objects in the database.
	 * @return
	 */
	public List<Employer> getEmployers(){
		MongoCollection<Employer> employers = db.getCollection("employer", Employer.class);
		List<Employer> res = new ArrayList<Employer>();
		MongoCursor<Employer> cursor = employers.find().iterator();
		try{
			while(cursor.hasNext()){
				res.add(cursor.next());
			}
		}finally{
			cursor.close();
		}
		
		return res;
	}
	/**
	 * Returns a List<Comment> containing all Comment-Objects in the database.
	 * @return
	 */
	public List<Comment> getComments(){
		MongoCollection<Comment> comments = db.getCollection("comment", Comment.class);
		List<Comment> res = new ArrayList<Comment>();
		MongoCursor<Comment> cursor = comments.find().iterator();
		try{
			while(cursor.hasNext()){
				res.add(cursor.next());
			}
		}finally{
			cursor.close();
		}
		
		return res;
	}
	/**
	 * Returns a List<Customer> containing all Customer-Objects in the database.
	 * @return
	 */
	public List<Customer> getCustomers(){
		MongoCollection<Customer> customers = db.getCollection("customer", Customer.class);
		List<Customer> res = new ArrayList<Customer>();
		MongoCursor<Customer> cursor = customers.find().iterator();
		try{
			while(cursor.hasNext()){
				res.add(cursor.next());
			}
		}finally{
			cursor.close();
		}
		
		return res;
	}
	
	/**
	 * Updates values of the existing Product-object in the database, matching the inparameter Product-object,
	 * with values of the inparameter Product.
	 * @param product
	 */
	public void updateProduct(Product product){
		MongoCollection<Product> coll = db.getCollection("product", Product.class);
		MongoCursor<Product> cursor = coll.find().iterator();
		try{
			while(cursor.hasNext()){
				Product prod = cursor.next();
				if(prod.getId().equals(product.getId())){
					System.out.println("equal values");
					coll.updateOne(eq("name",prod.getName()), combine(set("type", product.getType()),set("name", product.getName()),set("price", product.getPrice()),set("ingredients",product.getIngredients()),set("units",product.getUnits()),set("inStock", product.getInStock())));

				}else{
					System.out.println("Not equal values");
				}
			}
		} finally{
			
		}
	}
	/**
	 * Updates values of the existing Customer-object in the database, matching the inparameter Customer-object,
	 * with values of the inparameter Customer.
	 * @param product
	 */
	public void updateCustomer(Customer customer){
		System.out.println(customer.getId());
		MongoCollection<Customer> coll = db.getCollection("customer", Customer.class);
		coll.updateOne(eq("_id", customer.getId()), combine(set("name", customer.getName()), set("occupation", customer.getOccupation()), set("persNbr", customer.getPersNbr()), set("address", customer.getAddress()), set("zipcode",customer.getZipcode()), set("country", customer.getCountry()), set("clubCard", customer.getClubCard())));
	}
	public void updateEmployee(Employee employee){
		System.out.println(employee.getId());
		MongoCollection<Employee> coll = db.getCollection("employee", Employee.class);
		coll.updateOne(eq("_id", employee.getId()), combine(set("name", employee.getName()), set("persNbr", employee.getPersNbr()), set("address", employee.getAddress()), set("zipcode", employee.getZipcode()), set("position",employee.getPosition()), set("comments", employee.getComments())));
	}
	public void updateEmployer(Employer employer){
		System.out.println(employer.getId());
		MongoCollection<Employer> coll = db.getCollection("employer", Employer.class);
		coll.updateOne(eq("_id", employer.getId()), combine(set("name", employer.getName()), set("persNbr", employer.getPersNbr()), set("comments", employer.getComments())));
	}
	public void updateOrder(Order order){
		MongoCollection<Order> coll = db.getCollection("order", Order.class);
		coll.updateOne(eq("orderID", order.getOrderID()), combine(set("total", order.getTotal()), set("customer", order.getCustomer()), set("cashier", order.getCashier()), set("products", order.getProducts())));
	}
	public void updateComment(Comment comment){
		System.out.println(comment.getId());
		MongoCollection<Comment> coll = db.getCollection("comment", Comment.class);
		coll.updateOne(eq("_id", comment.getId()), combine(set("employerID", comment.getEmployerID()), set("employeeID", comment.getEmployeeID()), set("comment", comment.getComment())));
	}
	
	/**
	 * inserts the given Product into the database.
	 * @param product
	 * @return 0: product inserted, 1: product already exist and was updated
	 */
	public int insertProduct(Product product){
		MongoCollection<Product> coll = db.getCollection("product", Product.class);
		MongoCursor<Product> cursor = coll.find().iterator();
		
		try{
			while(cursor.hasNext()){
				Product prod = cursor.next();
				if(prod.getName().equals(product.getName())){
					prod.setUnits(product.getUnits());
					if(prod.getUnits() > 0){
						prod.setInStock(true);
					}
					updateProduct(prod);
					return 1;
				}
			}
		}finally{
			cursor.close();
		}
		
		coll.insertOne(product);
		return 0;
	}
	
	/**
	 * inserts the given Product into the database.
	 * @param product
	 * @return 0: product inserted, 1: product already exist and was updated
	 */
	public int insertComment(Comment comment){
		MongoCollection<Comment> comColl = db.getCollection("comment", Comment.class);
		MongoCollection<Employee> emplColl = db.getCollection("employee", Employee.class);
		MongoCollection<Employer> emplrColl = db.getCollection("employer", Employer.class);
		MongoCursor<Employee> emplCursor = emplColl.find().iterator();
		MongoCursor<Employer> emplrCursor = emplrColl.find().iterator();
		
		try{
			while(emplCursor.hasNext()){
				Employee empl = emplCursor.next();
				if(empl.getEmployeeID().equals(comment.getEmployeeID())){
					empl.getComments().add(comment);
				}
			}
		}finally{
			emplCursor.close();
		}try{
			while(emplrCursor.hasNext()){
				Employer emplr = emplrCursor.next();
				if(emplr.getEmployerID().equals(comment.getEmployeeID())){
					emplr.getComments().add(comment);
				}
			}
		}finally{
			emplrCursor.close();
		}
		comColl.insertOne(comment);
		return 0;
	}
	
	public int insertCustomer(Customer customer){
		MongoCollection<Customer> coll = db.getCollection("customer", Customer.class);
		MongoCursor<Customer> cursor = coll.find().iterator();

		try{
			while(cursor.hasNext()){
				Customer cust = cursor.next();
				if((cust.getCustomerID() != null) && cust.getCustomerID().equals(customer.getCustomerID())){
					updateCustomer(customer);
					return 1;
				}
			}
		}finally{
			cursor.close();
		}
		coll.insertOne(customer);
		customer.setClubCard(new ClubCard(customer.getId().toString(),customer.getCountry(),0));
		updateCustomer(customer);
		System.out.println(customer.getClubCard().getNumberOfCoffee());
		
		return 0;
	}
	
	public int insertEmployee(Employee employee){
		MongoCollection<Employee> coll = db.getCollection("employee", Employee.class);
		MongoCursor<Employee> cursor = coll.find().iterator();

		try{
			while(cursor.hasNext()){
				Employee empl = cursor.next();
				if(empl.getEmployeeID().equals(employee.getEmployeeID())){
					updateEmployee(employee);
					return 1;
				}
			}
		}finally{
			cursor.close();
		}
		
		coll.insertOne(employee);
		return 0;
	}
	
	public int insertEmployer(Employer employer){
		MongoCollection<Employer> coll = db.getCollection("employer", Employer.class);
		MongoCursor<Employer> cursor = coll.find().iterator();

		try{
			while(cursor.hasNext()){
				Employer emplr = cursor.next();
				if(emplr.getEmployerID().equals(employer.getEmployerID())){
					updateEmployer(employer);
					return 1;
				}
			}
		}finally{
			cursor.close();
		}
		
		coll.insertOne(employer);
		return 0;
	}
	
	public int insertOrder(Order order){
		MongoCollection<Order> coll = db.getCollection("order", Order.class);

		coll.insertOne(order);
		return 0;
	}
	
	/**
	 * Processes an order, removing products from DB, applying discounts, adding beverages to ClubCards
	 * @param order
	 * @return String containing success/fail-message with info
	 */
	public String processOrder(Order order){
		MongoCollection<Customer> customerColl = db.getCollection("customer", Customer.class);
		List<Product> productsDB = getProducts();
		List<Product> productsOrder = order.getProducts();
		MongoCursor<Customer> cursor = customerColl.find().iterator();
		Product product = null;
		
		for(int i=0;i<productsDB.size();i++){
			for(int j=0;j<productsOrder.size();j++){
				if(productsDB.get(i).getId().equals(productsOrder.get(j).getId())){
					product = productsDB.get(i);
					break;
				}
			}
			for(int j=0;j<productsOrder.size();j++){
				if(productsDB.get(i).getId().equals(productsOrder.get(j).getId())){
					if(productsDB.get(i).getInStock()){
						product.setUnits(product.getUnits()-1);
						if(product.getUnits() < 1){
							return product.getName() + " insufficient units in stock for given order!";
						}
					} else{
						return productsOrder.get(j).getName() + " out of stock! process failed.";
					}
				} 
			}
		}
		for(int i=0;i<productsOrder.size();i++){
			removeUnit(productsOrder.get(i));
		}
		
		try{
			while(cursor.hasNext()){
				Customer customer = cursor.next();
				if(customer.getCustomerID().equals(order.getCustomer())){
					if(order.getCashier().equals(order.getCustomer())){
						order.setTotal((order.getTotal()) + (int)(order.getTotal()*(10.0f/100.0f)));
					}
					for(int i=0;i<productsOrder.size();i++){
						if(productsOrder.get(i).getType().equals("beverage")){
							customer.getClubCard().addCoffee();
						}
					}
					System.out.println(customer.getClubCard().getNumberOfCoffee());
					if((customer.getClubCard().getNumberOfCoffee()>0)&&((customer.getClubCard().getNumberOfCoffee()/10)>0)){
						System.out.println(customer.getClubCard().getNumberOfCoffee()/10);
						int freeDrinks = customer.getClubCard().getNumberOfCoffee()/10;
						for(int i=0;i<productsOrder.size();i++){
							if(productsOrder.get(i).getType().equals("beverage")){
								order.setTotal(order.getTotal() - productsOrder.get(i).getPrice());
								freeDrinks--;
							}
							if(freeDrinks == 0){
								break;
							}
						}
						System.out.println(customer.getClubCard().getNumberOfCoffee()%10);
						customer.getClubCard().setNumberOfCoffee(customer.getClubCard().getNumberOfCoffee()%10);
					}
				}
			}
		}finally{
			cursor.close();
		}
		order.setProcessed(true);
		return "Process successful!";
	}
}
