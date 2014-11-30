package ucsc.managers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ucsc.beans.Customer;
import ucsc.beans.Item;
import ucsc.beans.Manufacturer;
import ucsc.beans.Order;
import ucsc.beans.Product;
import ucsc.db.SQLCon;

public class Fetch {

	private int MAX_QUANTITY = 10;
	private int MAX_ITEMS = 10;

	public Order fetch() {
		Order order = new Order();
		Random rand = new Random();
		Customer customer = fetchCustomer();
		List<Item> list = new ArrayList<Item>();
		int items = rand.nextInt(MAX_ITEMS);
		for (int x = 0; x < items; x++) {
			Item item = new Item();
			Product product = fetchProduct();
//			System.out.println(product.getName());
			int quantity = rand.nextInt(MAX_QUANTITY);
			item.setQuantity(quantity);
			item.setAmount(quantity * product.getPrice());
//			System.out.println("q:"+quantity+"   am: "+item.getAmount());
			item.setProduct(product);
			list.add(item);
		}
		order.setCustomer(customer);
		order.setItemsPurchased(list);
		order.setOrderTime(randDate());
		return order;
	}

	public Customer fetchCustomer() {
		Customer customer = new Customer();
		Connection con = SQLCon.getSQLCon();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "SELECT * FROM customer ORDER BY RAND() LIMIT 1";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			customer.setName(rs.getString("name"));
			customer.setNic(rs.getString("nic"));
			customer.setEmail(rs.getString("email"));
			customer.setCompany(rs.getString("company"));
			customer.setAddress(rs.getString("address"));
			customer.setTelephone(rs.getString("telephone"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customer;
	}

	public Product fetchProduct() {
		Product product = new Product();
		Connection con = SQLCon.getSQLCon();
		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "SELECT product.barcode, product.name, product.price, category.name AS c_name, manufacturer.name AS m_name, manufacturer.country FROM product INNER JOIN manufacturer ON product.manufacturer=manufacturer.id INNER JOIN category ON product.category=category.id ORDER BY RAND() LIMIT 1";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			product.setBarcode(rs.getString("barcode"));
			product.setName(rs.getString("name"));
			product.setPrice(rs.getFloat("price"));
			Manufacturer manufacturer = new Manufacturer();
			manufacturer.setName(rs.getString("m_name"));
			manufacturer.setCountry(rs.getString("country"));
			product.setManufacturer(manufacturer);
			product.setCategory(rs.getString("c_name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}
	
	public Date randDate(){
		Random rand = new Random();
		Date date=new Date();
		long today=date.getTime();
		long yearAgo=today-31556900000l;
		long random=rand.nextLong();
		random=random%31556900000l;
		if(random<0){
			random*=-1;
		}
		random+=yearAgo;
		date= new Date(random);
		return date;
	}
}
