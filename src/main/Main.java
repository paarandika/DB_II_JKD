package main;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ucsc.beans.Customer;
import ucsc.beans.Item;
import ucsc.beans.Manufacturer;
import ucsc.beans.Order;
import ucsc.beans.Product;
import ucsc.db.SQLCon;
import ucsc.managers.Fetch;
import ucsc.managers.OrderManager;
import ucsc.read.ReadCSV;

public class Main {

	public static void main(String[] args) {
	
		ReadCSV rc=new ReadCSV();
		rc.readCustomer();
		rc.readProduct();
		Fetch fetch=new Fetch();
		OrderManager manager=new OrderManager();
		int i=1;
		for (int x=0;x<i;x++){
			Order order=fetch.fetch();
			manager.addOrder(order);
			
		}

	}

}
