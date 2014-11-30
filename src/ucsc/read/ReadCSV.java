package ucsc.read;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.ArrayUtils;





import ucsc.db.SQLCon;

public class ReadCSV {

	public void readCustomer() {

		String csvFile = "bin/customer.csv";
		BufferedReader br = null;
		String line = "";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String[] stub0 = line.split("\"");
				String[] stub1 = {stub0[1]};
				String[] stub2=stub0[0].split(",");
				String[] stub3={stub0[2].replaceAll("," ,"")};
				String[] fin=(String[]) ArrayUtils.addAll(stub2,stub1);
				fin=(String[]) ArrayUtils.addAll(fin,stub3);
				Connection con=SQLCon.getSQLCon();
				
				String insertTableSQL = "INSERT INTO customer"
						+ "(name, nic, email, company, address, telephone) VALUES"
						+ "(?,?,?,?,?,?)";
				
				try {
					PreparedStatement preparedStatement = con.prepareStatement(insertTableSQL,Statement.RETURN_GENERATED_KEYS);
					preparedStatement.setString(1, fin[0]);
					preparedStatement.setString(2, fin[1]);
					preparedStatement.setString(3, fin[2]);
					preparedStatement.setString(4, fin[3]);
					preparedStatement.setString(5, fin[4]);
					preparedStatement.setString(6, fin[5]);
					preparedStatement .executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	}

}
