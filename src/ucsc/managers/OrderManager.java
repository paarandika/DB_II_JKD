package ucsc.managers;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import ucsc.beans.Customer;
import ucsc.beans.Item;
import ucsc.beans.Order;
import ucsc.db.MongoCon;

public class OrderManager {
	public boolean addOrder(Order order){
		try {
			DB db=MongoCon.getMongoInstance();
			DBCollection table = db.getCollection("order");
			BasicDBList list = new BasicDBList();
			int k =0;
			for(Item item : order.getItemsPurchased()){
				BasicDBObject man = new BasicDBObject();
				man.put("name", item.getProduct().getManufacturer().getName());
				man.put("country", item.getProduct().getManufacturer().getCountry());
				BasicDBObject pro = new BasicDBObject();
				pro.put("barcode", item.getProduct().getBarcode());
				pro.put("memberName", item.getProduct().getMemberName());
				pro.put("price", item.getProduct().getPrice());
				pro.put("category", item.getProduct().getCategory());
				pro.put("manufacturer", man);
				BasicDBObject it = new BasicDBObject();
				it.put("quantity", item.getQuantity());
				it.put("amount", item.getAmount());
				it.put("product", pro);
				list.put(k, it);
				k+=1;
				
			}
			BasicDBObject cus = new BasicDBObject();
			cus.put("name", order.getCustomer().getName());
			cus.put("nic", order.getCustomer().getNic());
			cus.put("email", order.getCustomer().getEmail());
			cus.put("addres", order.getCustomer().getAddress());
			cus.put("telephone", order.getCustomer().getTelephone());
			cus.put("company", order.getCustomer().getCompany());
			
			BasicDBObject document = new BasicDBObject();
			document.put("itemsPurchased", list);
			document.put("customer", cus);
			document.put("orderTime", order.getOrderTime());
			document.put("totalAmount", order.getToatalAmount());
			table.insert(document);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	public void getMaxCust(int ref){
		/*
		 * db.order.aggregate([
				{$project:{"totalAmount":1,
           			"customer":"$customer.name",
           			"month":{$month:"$orderTime"}}},
				{$match:{"month":1}},
				{$group:{"_id":"$customer",
         			"sum":{$sum:"$totalAmount"},
         			"customer":{$first:"$customer"}}},
				{ $sort : { sum:-1 } },
				{$limit:10}
			]);
		 */
		System.out.println("\n****Top 10 buying customers****");
		DB db=MongoCon.getMongoInstance();
		DBCollection table = db.getCollection("order");
		BasicDBObject project = new BasicDBObject();
		BasicDBObject month = new BasicDBObject("$month","$orderTime");
		project.put("totalAmount",1);
		project.put("customer", "$customer.name");
		project.put("month",month);
		BasicDBObject project2 = new BasicDBObject("$project",project);
		BasicDBObject match = new BasicDBObject("$match",new BasicDBObject("month",ref));
		BasicDBObject group = new BasicDBObject();
		BasicDBObject sum = new BasicDBObject("$sum","$totalAmount");
		group.put("_id", "$customer");
		group.put("sum",sum);
		BasicDBObject group2 = new BasicDBObject("$group",group);
		BasicDBObject sort = new BasicDBObject("$sort",new BasicDBObject("sum",-1));
		BasicDBObject limit = new BasicDBObject("$limit",10);
		AggregationOutput output =table.aggregate(project2,match,group2,sort,limit);
	    for(DBObject result : output.results()) {
	        System.out.println(result);
	    }
	}
	public void getIncome(int ref){
		/*
		 * db.order.aggregate([
{$project:{"totalAmount":1,
           "day":{$dayOfMonth:"$orderTime"},
           "month":{$month:"$orderTime"}}},
{$match:{"month":1}},
{$group:{"_id":"$day",
         "sum":{$sum:"$totalAmount"},
         "day":{$first:"$day"}}},
{$project:{"_id":0,
           "sum":1,
           "day":1}}
])
		 */
		System.out.println("\n****Income By day****");
		DB db=MongoCon.getMongoInstance();
		DBCollection table = db.getCollection("order");
		BasicDBObject project = new BasicDBObject();
		BasicDBObject month = new BasicDBObject("$month","$orderTime");
		project.put("totalAmount",1);
		project.put("day", new BasicDBObject("$dayOfMonth","$orderTime"));
		project.put("month",month);
		BasicDBObject project2 = new BasicDBObject("$project",project);
		BasicDBObject match = new BasicDBObject("$match",new BasicDBObject("month",ref));
		BasicDBObject group = new BasicDBObject();
		BasicDBObject sum = new BasicDBObject("$sum","$totalAmount");
		BasicDBObject day = new BasicDBObject("$first","$day");
		group.put("_id", "$day");
		group.put("sum",sum);
		group.put("day", day);
		BasicDBObject group2 = new BasicDBObject("$group",group);
		BasicDBObject project3= new BasicDBObject();
		project3.put("_id", 0);
		project3.put("sum",1);
		project3.put("day",1);
		BasicDBObject project4= new BasicDBObject("$project",project3);
		BasicDBObject sort = new BasicDBObject("$sort",new BasicDBObject("day",1));
		AggregationOutput output =table.aggregate(project2,match,group2,project4,sort);
	    for(DBObject result : output.results()) {
	        System.out.println(result);
	    }
	}
	
	
	//ordermanager get order details
	
	public ArrayList<Order> getOrder()
	{
		DB db=MongoCon.getMongoInstance();
		

		
		ArrayList<Order> orders= new ArrayList();

		
		BasicDBObject query = new BasicDBObject();
		BasicDBObject field = new BasicDBObject();
		field.put("itemsPurchased",1);
		DBCursor cursor = db.getCollection("order").find(query,field);
		
		field.put("customer",1);
		DBCursor cursorCus = db.getCollection("order").find(query,field);
		
		while (cursor.hasNext()) {
		    BasicDBObject obj = (BasicDBObject) cursor.next();
		    BasicDBObject objCus = (BasicDBObject) cursorCus.next();
		    
			Order order = new Order();
			
		    Gson gson = new Gson();
		    
		    Customer cs = new Customer();
		    
			ArrayList<Item> items = new ArrayList();
			
				//convert the json string back to object
			JsonArray object = gson.fromJson(obj.get("itemsPurchased").toString(), JsonArray.class);
			
			JsonObject objectCus = gson.fromJson(objCus.get("customer").toString(), JsonObject.class);
			
			cs.setName(objectCus.get("name").getAsString());
			order.setCustomer(cs);
			//Item purchase list
			for (int i = 0; i < object.size(); i++) {
				
				Item item=new Item();
				
				JsonObject jo = new JsonObject();
				jo = (JsonObject) object.get(i);
				
				JsonObject joPro = new JsonObject();
				joPro = (JsonObject) jo.get("product");
				
				Order od = new Order();
				item.setQuantity(jo.get("quantity").getAsInt());
				item.setMemberName(joPro.get("memberName").getAsString());
				items.add(item);
				//System.out.println(joPro.get("memberName").getAsString());
			}
			
			order.setItemsPurchased(items);
			orders.add(order);
			//System.out.println(objectCus.get("name").getAsString());	
			
		}
		
		return orders;
	}

}
