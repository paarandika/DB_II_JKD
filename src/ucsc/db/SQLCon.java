package ucsc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLCon {

	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String M_CONN_STRING ="jdbc:mysql://localhost/db_ass_ii";
	private static Connection con;
	
	public static Connection getSQLCon() {
		if(con==null){
			try {
				con=DriverManager.getConnection(M_CONN_STRING,USERNAME, PASSWORD);
			} catch (SQLException e) {
				System.err.println("Error message: " + e.getMessage());
				System.err.println("Error code: " + e.getErrorCode());
				System.err.println("SQL state: " + e.getSQLState());
			}
			return con;
		}else{
			return con;
		}
	}
}
