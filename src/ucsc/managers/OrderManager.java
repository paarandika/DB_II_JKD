package ucsc.managers;

import java.util.Date;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

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
			table.insert(document);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}

}
