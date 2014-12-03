package ucsc.managers;

import java.util.Date;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

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
				{$match:{"month":12}},
				{$group:{"_id":"$customer",
         			"sum":{$sum:"$totalAmount"},
         			"customer":{$first:"$customer"}}},
				{ $sort : { sum:-1 } },
				{$limit:10}
			]);
		 */
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
		BasicDBObject customer = new BasicDBObject("$first","$customer");
		group.put("_id", "$customer");
		group.put("sum",sum);
		group.put("customer",customer);
		BasicDBObject group2 = new BasicDBObject("$group",group);
		BasicDBObject sort = new BasicDBObject("$sort",new BasicDBObject("sum",-1));
		BasicDBObject limit = new BasicDBObject("$limit",10);
		AggregationOutput output =table.aggregate(project2,match,group2,sort,limit);
	    for(DBObject result : output.results()) {
	        System.out.println(result);
	    }
	}

}
