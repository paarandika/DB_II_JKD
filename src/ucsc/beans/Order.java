package ucsc.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable{
	private String orderID;
	private List<Item> itemsPurchased;
	private Customer customer;
	private Date orderTime;
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public List<Item> getItemsPurchased() {
		return itemsPurchased;
	}
	public void setItemsPurchased(List<Item> itemsPurchased) {
		this.itemsPurchased = itemsPurchased;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

}
