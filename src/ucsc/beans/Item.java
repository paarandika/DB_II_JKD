package ucsc.beans;

import java.io.Serializable;

import com.mongodb.BasicDBObject;

public class Item extends BasicDBObject{
	private Product product;
	private int quantity;
	private float amount;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}

}
