package com.pizzashop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pizzashop.entities.Pizza;
import com.pizzashop.utils.DBUtils;

public class OrderDao implements AutoCloseable {

	private Connection connection;

	public OrderDao() throws SQLException {
		connection = DBUtils.getConnection();
	}

	public void insertOrder(int mid, int cid) throws SQLException {
		String sql = "INSERT INTO orders(cid,mid) VALUES(?,?)";
		try (PreparedStatement insertStmt = connection.prepareCall(sql)) {
			insertStmt.setInt(1, cid);
			insertStmt.setInt(2, mid);

			insertStmt.executeUpdate();
		}
	}

	public List<Pizza> getOrderHist(int cid) throws SQLException {
		String sql = "SELECT m.* FROM menu m INNER JOIN orders o ON m.mid = o.mid WHERE o.cid = ? ";
		List<Pizza> orderHist = new ArrayList<Pizza>();
		try (PreparedStatement stmt = connection.prepareCall(sql)) {
			stmt.setInt(1, cid);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Pizza pizza = new Pizza();
				pizza.setMid(rs.getInt(1));
				pizza.setName(rs.getString(2));
				pizza.setDescription(rs.getString(3));
				pizza.setPrice(rs.getDouble(4));

				orderHist.add(pizza);
			}
		}
		return orderHist;

	}

	@Override
	public void close() throws SQLException {
		if (connection != null)
			connection.close();

	}

}
