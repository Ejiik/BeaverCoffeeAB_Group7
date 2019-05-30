package database;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import controller.Product;
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
	 * Accesses a collection, or creates a new one if it doesn't exist.
	 * @param coll - Name of collection
	 */
	public MongoCollection<Document> getCollection(String coll){
		MongoCollection<Document> collection = db.getCollection(coll);
		
		return collection;
	}
	
	public MongoIterable<String> getCollectionNames(){
		return db.listCollectionNames();
	}
	
	public void deleteDoc(String collection, String key, String value){
		MongoCollection<Document> coll = db.getCollection(collection);
		coll.deleteOne(eq(key, value));
	}
	
	public void removeUnit(Product product){
		MongoCollection<Document> coll = db.getCollection("product");
		coll.updateOne(eq("name", product.getName()), set("units", product.getUnits()-1));
	}
	
//	public MongoCollection<Product> getProducts(){
//		MongoCollection<Product> products = db.getCollection("product", Product.class);
//		
//		return products;
//	}
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
	
	public void updateDoc(String collection, String keyID, String keyValue, String property, String newValue, boolean all){
		MongoCollection<Document> coll = db.getCollection(collection);
		if(all){
			coll.updateMany(eq(keyID, keyValue), set(property, newValue));
		}else{
			coll.updateOne(eq(keyID, keyValue), set(property, newValue));
		}
	}
	
	public void updateDoc(String collection, String keyID, String keyValue, String property, int newValue, boolean all){
		MongoCollection<Document> coll = db.getCollection(collection);
		if(all){
			coll.updateMany(eq(keyID, keyValue), set(property, newValue));
		}else{
			coll.updateOne(eq(keyID, keyValue), set(property, newValue));
		}
	}
	public void updateDoc(String collection, String keyID, String keyValue, String property, List<String> newValue, boolean all){
		MongoCollection<Document> coll = db.getCollection(collection);
		if(all){
			coll.updateMany(eq(keyID, keyValue), set(property, newValue));
		}else{
			coll.updateOne(eq(keyID, keyValue), set(property, newValue));
		}
	}
	
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
	 * Inserts document into a collection
	 * @param doc
	 * @param coll
	 */
	public void insertDoc(Document doc, String collection){
		MongoCollection<Document> coll = db.getCollection(collection);
		coll.insertOne(doc);
	}
	
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
}
