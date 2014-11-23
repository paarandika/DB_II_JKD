package ucsc.db;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoCon {

	private static final String DATABASE = "transactions";
	private static final int PORT = 27017;
	private static final String LOCALHOST = "localhost";
	private static DB mongoDB;

	public static DB getMongoInstance() {
		if (mongoDB == null) {
			MongoClient mongo;
			try {
				mongo = new MongoClient(LOCALHOST, PORT);
				mongoDB = mongo.getDB(DATABASE);
				return mongoDB;
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return mongoDB;
		}

	}

}
