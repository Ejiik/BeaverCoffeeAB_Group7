package models;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;

public final class Product{
	private ObjectId id;
	private String productID;
	private String type;
	private String name;
	private int price;
	private List<String> ingredients;
	private int units;
	private boolean inStock = false;
	
	public Product(String type, String name, int price, List<String> ingredients, int units, boolean inStock) {
		super();
		this.type = type;
		this.name = name;
		this.price = price;
		this.ingredients = ingredients;
		this.units = units;
		this.inStock = inStock;
	}
	
	public Product(){
		
	}
	
	public ObjectId getId(){
		return id;
	}
	
	public void setId(final ObjectId id){
		this.id=id;
	}
	public String getProductID(){
		return productID;
	}
	public void setProductID(String productID){
		this.productID=productID;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type=type;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public int getPrice(){
		return price;
	}
	public void setPrice(final int price){
		this.price=price;
	}
	public List<String> getIngredients(){
		return ingredients;
	}
	public void setIngredients(List<String> ingredients){
		this.ingredients=ingredients;
	}
	public int getUnits(){
		return units;
	}
	
	/**
	 * Adds the specified amount of units to the product type.
	 * @param units
	 */
	public void setUnits(int units){
		this.units=units;
		if(this.units>0){
			inStock = true;
		}
	}
	public boolean getInStock(){
		return inStock;
	}
	public void setInStock(boolean inStock){
		this.inStock=inStock;
	}
}
