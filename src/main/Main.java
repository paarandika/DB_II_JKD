package main;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.neo4j.graphdb.Node;

import ucsc.beans.Customer;
import ucsc.beans.Item;
import ucsc.beans.Manufacturer;
import ucsc.beans.Order;
import ucsc.beans.Product;
import ucsc.db.SQLCon;
import ucsc.managers.Fetch;
import ucsc.managers.OrderAnalyzer;
import ucsc.managers.OrderManager;
import ucsc.read.ReadCSV;

public class Main {

	public static void main(String[] args) {
	
		ReadCSV rc=new ReadCSV();
		rc.readCustomer();
		rc.readProduct();
		Fetch fetch=new Fetch();
		OrderManager manager=new OrderManager();
		Scanner sc = new Scanner(System.in);
		System.out.print("Number of random records to add to MongoDB : ");
	    int i = sc.nextInt();
		for (int x=0;x<i;x++){
			Order order=fetch.fetch();
			manager.addOrder(order);
			
		}
		System.out.print("Month to check income by day(input month as integer) : ");
	    i = sc.nextInt();
	    manager.getIncome(i);
	    System.out.print("Month to check top 10 buyers(input month as integer) : ");
	    i = sc.nextInt();
	    manager.getMaxCust(i);
		
				System.out.println("Order Analyzer Started");
				OrderAnalyzer oa = new  OrderAnalyzer();
				oa.startAnalyzer();
				
				List<Order> orders= new ArrayList();
				
				orders = manager.getOrder();
				
				for (int j = 0; j < orders.size(); j++) {
					String name=orders.get(j).getCustomer().getName();
					//Label label2 = DynamicLabel.label(name);
					
					oa.addAbset("Buyer",orders.get(j).getCustomer().getName());
					
					Item p = new Item();
					List<Item> items= new ArrayList();
					items = orders.get(j).getItemsPurchased();
					
					for (int k = 0; k < items.size(); k++) {
						p = items.get(k);
						String itemName = p.getMemberName();
						Node node = oa.getOrder(name, "Buyer", name);
						int q = p.getQuantity();
						oa.addAbset("Product",itemName,node,q);
					}
					
				}
				
				System.out.println("Order Analyzer Successfully Ended");
		
	}

}
