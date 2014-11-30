package ucsc.read;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.ArrayUtils;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import ucsc.db.SQLCon;

public class ReadCSV {

	public void readCustomer() {

		String csvFile = "bin/customer.csv";
		BufferedReader br = null;
		String line = "";
		Connection con = SQLCon.getSQLCon();

		try {

			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
			while ((line = br.readLine()) != null) {

				String[] stub0 = line.split("\"");
				String[] stub1 = { stub0[1] };
				String[] stub2 = stub0[0].split(",");
				String[] stub3 = { stub0[2].replaceAll(",", "") };
				String[] fin = (String[]) ArrayUtils.addAll(stub2, stub1);
				fin = (String[]) ArrayUtils.addAll(fin, stub3);

				String insertTableSQL = "INSERT INTO customer"
						+ "(name, nic, email, company, address, telephone) VALUES"
						+ "(?,?,?,?,?,?)";

				try {
					PreparedStatement preparedStatement = con.prepareStatement(
							insertTableSQL, Statement.RETURN_GENERATED_KEYS);
					preparedStatement.setString(1, fin[0]);
					preparedStatement.setString(2, fin[1]);
					preparedStatement.setString(3, fin[2]);
					preparedStatement.setString(4, fin[3]);
					preparedStatement.setString(5, fin[4]);
					preparedStatement.setString(6, fin[5]);
					preparedStatement.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void readProduct() {

		String csvFile = "bin/product.csv";
		BufferedReader br = null;
		String line = "";
		Connection con = SQLCon.getSQLCon();

		try {

			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();
			int manNo = -1;
			int catNo = -1;
			while ((line = br.readLine()) != null) {

				String[] fin = line.split(",");

				String insertManufacturer = "INSERT INTO manufacturer"
						+ "(name, country) VALUES" + "(?,?)";

				try {
					PreparedStatement preparedStatement = con
							.prepareStatement(insertManufacturer,
									Statement.RETURN_GENERATED_KEYS);
					preparedStatement.setString(1, fin[4]);
					preparedStatement.setString(2, fin[5]);
					preparedStatement.executeUpdate();
					ResultSet rs = preparedStatement.getGeneratedKeys();
					rs.next();
					manNo = rs.getInt(1);
				} catch (MySQLIntegrityConstraintViolationException e) {
					Statement stmt = con.createStatement();
					String sql = "SELECT id FROM manufacturer WHERE name='"
							+ fin[4] + "'";
					ResultSet rs = stmt.executeQuery(sql);
					rs.next();
					manNo = rs.getInt("id");
				} catch (Exception e) {
					e.printStackTrace();
				}

				String insertCategory = "INSERT INTO Category"
						+ "(name) VALUES" + "(?)";

				try {
					PreparedStatement preparedStatement = con.prepareStatement(
							insertCategory, Statement.RETURN_GENERATED_KEYS);
					preparedStatement.setString(1, fin[1]);
					preparedStatement.executeUpdate();
					ResultSet rs = preparedStatement.getGeneratedKeys();
					rs.next();
					catNo = rs.getInt(1);
				} catch (MySQLIntegrityConstraintViolationException e) {
					Statement stmt = con.createStatement();

					String sql = "SELECT id FROM category WHERE name='"
							+ fin[1] + "'";
					ResultSet rs = stmt.executeQuery(sql);
					rs.next();
					catNo = rs.getInt("id");
				} catch (Exception e) {
					e.printStackTrace();
				}

				String insertProduct = "INSERT INTO product"
						+ "(barcode, name, price, category, manufacturer) VALUES"
						+ "(?,?,?,?,?)";

				try {
					PreparedStatement preparedStatement = con.prepareStatement(
							insertProduct, Statement.RETURN_GENERATED_KEYS);
					preparedStatement.setString(1, fin[0]);
					preparedStatement.setString(2, fin[2]);
					preparedStatement.setFloat(3, Float.parseFloat(fin[3]));
					preparedStatement.setInt(4, catNo);
					preparedStatement.setInt(5, manNo);
					preparedStatement.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
