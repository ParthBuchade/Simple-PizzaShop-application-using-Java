package com.pizzashop.menu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.pizzashop.dao.OrderDao;
import com.pizzashop.dao.PizzaDao;
import com.pizzashop.entities.Customer;
import com.pizzashop.entities.Pizza;

public class SubMenu {

	public static int menu(Scanner sc) {
		System.out.println("*******************************************************");
		System.out.println("0. Logout");
		System.out.println("1. Pizza Menu");
		System.out.println("2. Order Pizza(Only enter id of pizza to place order)");
		System.out.println("3. Order History");
		System.out.println("*******************************************************");
		System.out.print("Enter choice - ");
		int choice = sc.nextInt();
		return choice;
	}

	public static void displayPizza() {
		List<Pizza> pizzaList = new ArrayList<Pizza>();
		try (PizzaDao pz = new PizzaDao()) {
			pizzaList = pz.getAllPizza();
			for (Pizza p : pizzaList)
				System.out.println(p);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void orderHistory(Customer cust) {
		List<Pizza> ord;
		try (OrderDao od = new OrderDao()) {
			ord = od.getOrderHist(cust.getCid());
			if (ord != null) {
				for (Pizza p : ord)
					System.out.println(p);
			} else
				System.out.println("No order placed yet");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void placeOrder(Scanner sc, Customer cust) {
		System.out.print("Enter pizza id above - ");
		int mid = sc.nextInt();
		try (OrderDao od = new OrderDao()) {
			od.insertOrder(mid, cust.getCid());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void subMenu(Customer cust, Scanner sc) {
		System.out.println("Welcome " + cust.getName());
		int choice = 0;
		while ((choice = menu(sc)) != 0) {
			switch (choice) {
			case 1:
				displayPizza();
				break;
			case 2:
				placeOrder(sc, cust);
				System.out.println("order placed...:)");
				break;

			case 3:
				System.out.println("order history :");
				orderHistory(cust);
				break;

			default:
				System.out.println("wrong choice...");
				break;
			}
		}
		System.out.println("Logout Successful...");
	}

}
