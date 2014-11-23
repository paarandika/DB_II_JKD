package main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ucsc.beans.Customer;
import ucsc.beans.Item;
import ucsc.beans.Manufacturer;
import ucsc.beans.Order;
import ucsc.beans.Product;
import ucsc.managers.OrderManager;

public class Main {

	public static void main(String[] args) {
		Order order=new Order();
		Customer customer=new Customer();
		customer.setNic("poo123");
		customer.setName("poo");
		customer.setEmail("emailpoo");
		customer.setTelephone("poop");
		Manufacturer manufacturer=new Manufacturer();
		manufacturer.setName("poopoo");
		manufacturer.setCountry("poopland");
		Product product=new Product();
		product.setBarcode("1234poo");
		product.setCategory("goo");
		product.setManufacturer(manufacturer);
		product.setPrice((float) 23.00);
		order.setOrderTime(new Date().getTime());
		Item item=new Item();
		item.setQuantity(5);
		item.setProduct(product);
		item.setAmount(115.00f);
		List<Item> list=new ArrayList<Item>();
		list.add(item);
		order.setItemsPurchased(list);
		order.setCustomer(customer);
		OrderManager manager=new OrderManager();
		manager.addOrder(order);
		System.out.println("done");

	}

}
