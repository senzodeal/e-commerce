package cn.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DaoConnection {
	private static Connection connection = null;

	public static Connection getConnection() {

		try {
			if (connection == null) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_cart", "root", "");
				// System.out.print("connected");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
}
