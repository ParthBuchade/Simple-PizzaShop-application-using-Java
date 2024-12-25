package com.pizzashop.menu;

import java.sql.SQLException;
import java.util.Scanner;

import com.pizzashop.dao.CustomerDao;
import com.pizzashop.entities.Admin;
import com.pizzashop.entities.Customer;

public class MainMenu {
	public static int menu(Scanner sc) {
		System.out.println("******Welcome to Pizza Store*******");
		System.out.println("0. EXIT");
		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.println("3. Admin");
		System.out.println("***********************************");
		System.out.println("Enter your choice - ");
		int choice = sc.nextInt();
		return choice;

	}

	public static void registerCust(Scanner sc) {
		Customer cust = new Customer();
		cust.acceptCustomer(sc);
		try (CustomerDao custDao = new CustomerDao()) {
			custDao.insertCustomer(cust);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Customer loginCustomer(Scanner sc) {
		String email, password;
		System.out.print("Enter email id - ");
		email = sc.next();
		System.out.print("Enter password - ");
		password = sc.next();
		try (CustomerDao custDao = new CustomerDao()) {
			Customer cust = custDao.getCustomer(email, password);
			return cust;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Admin loginAdmin(Scanner sc) {
		String username, password;
		Admin ad = new Admin();
		System.out.print("Enter username/Email- ");
		username = sc.next();
		System.out.println("Enter password - ");
		password = sc.next();
		if (username.equals(ad.getUsername()) && password.equals(ad.getPassword())) {
			return ad;
		}
		return null;
	}

	public static void main(String[] args) {
		int choice = 0;
		Scanner sc = new Scanner(System.in);
		while ((choice = menu(sc)) != 0) {
			switch (choice) {
			case 1:
				Customer cust = loginCustomer(sc);
				if (cust == null)
					System.out.println("Credentials invalid...:(");
				else {
					System.out.println("login successful...:)");
					SubMenu.subMenu(cust, sc);
				}

				break;
			case 2:
				registerCust(sc);
				break;

			case 3:
				Admin add = loginAdmin(sc);
				if (add == null)
					System.out.println("Invalid Credentials...:(");
				else {
					System.out.println("Admin login successful...:)");
				}
				break;
			default:
				System.out.println("Wrong choice...");
				break;
			}
		}
		System.out.println("THANK YOU FOR VISITING...:)");

	}

}
