package database;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;

import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;

public class Database {
	MongoClient mongoClient;
	MongoDatabase db;
	
	public Database(){
		mongoClient = MongoClients.create();
		db = mongoClient.getDatabase("beaverCoffee");
	}
	
	/**
	 * Accesses a collection, or creates a new one if it doesn't exist.
	 * @param coll - Name of collection
	 */
	public MongoCollection<Document> collection(String coll){
		MongoCollection<Document> collection = db.getCollection(coll);
		
		return collection;
	}
	
	/**
	 * Inserts document into a collection
	 * @param doc
	 * @param coll
	 */
	public void insertDoc(Document doc, MongoCollection<Document> coll){
		coll.insertOne(doc);
	}
}
