package com.pizzashop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pizzashop.entities.Customer;
import com.pizzashop.utils.DBUtils;

public class CustomerDao implements AutoCloseable {
	private Connection connection;

	public CustomerDao() throws SQLException {
		connection = DBUtils.getConnection();
	}

	public void insertCustomer(Customer cust) throws SQLException {
		String sql = "INSERT INTO customer (name,email,password,mobile) VALUES(?,?,?,?)";
		try (PreparedStatement insertStmt = connection.prepareStatement(sql)) {
			insertStmt.setString(1, cust.getName());
			insertStmt.setString(2, cust.getEmail());
			insertStmt.setString(3, cust.getPassword());
			insertStmt.setString(4, cust.getMobile());

			insertStmt.executeUpdate();
		}
	}

	public Customer getCustomer(String email, String password) throws SQLException {
		String sql = "SELECT *FROM customer WHERE email = ? and password=?";
		try (PreparedStatement selectStmt = connection.prepareCall(sql)) {
			selectStmt.setString(1, email);
			selectStmt.setString(2, password);
			ResultSet rs = selectStmt.executeQuery();
			if (rs.next()) {
				Customer cust = new Customer();
				cust.setCid(rs.getInt(1));
				cust.setName(rs.getString(2));
				cust.setEmail(email);
				cust.setPassword(password);
				cust.setMobile(rs.getString(5));
				return cust;
			}

		}
		return null;
	}

	@Override
	public void close() throws SQLException {
		if (connection != null)
			connection.close();

	}
}
