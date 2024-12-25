package com.pizzashop.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
	private final static String url = "jdbc:mysql://localhost:3306/ pizzastore_db";
	private final static String username = "root";
	private final static String password = "root";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);

	}

}
