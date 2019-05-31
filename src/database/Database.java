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
	public void removeUnit(Product product, int amount){
		MongoCollection<Document> coll = db.getCollection("product");
		coll.updateOne(eq("name", product.getName()), set("units", product.getUnits()-amount));
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
		MongoCollection<Customer> coll = db.getCollection("customer", Customer.class);
		MongoCursor<Customer> cursor = coll.find().iterator();
	}
	public void updateEmployee(Employee employee){
		MongoCollection<Employee> coll = db.getCollection("employee", Employee.class);
		MongoCursor<Employee> cursor = coll.find().iterator();
	}
	public void updateEmployer(Employer employer){
		MongoCollection<Employer> coll = db.getCollection("employer", Employer.class);
		MongoCursor<Employer> cursor = coll.find().iterator();
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
		System.out.print(customer.getId());
		coll.insertOne(customer);
		
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
}
