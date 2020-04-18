package edu.metrostate.gp1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to maintain a collection of customers.
 *
 * @author Jacob Gerval and Marcus Behr
 */
public class CustomerList implements Serializable {
	private static final long serialVersionUID = 1L;
	private static CustomerList customerList;
	private static List<Customer> customers = new LinkedList<Customer>();

	/**
	 * No args constructor.
	 */
	public CustomerList() {

	}

	public static CustomerList instance() {
		if (customerList == null) {
			return customerList = new CustomerList();
		} else {
			return customerList;
		}

	}

	/**
	 * Inserts a customer into the collection.
	 * 
	 * @param customer
	 *            the customer to be inserted
	 * @return true if the customer could be inserted
	 */
	public boolean insertCustomer(Customer customer) {
		return customers.add(customer);
	}

	/**
	 * Finds a customer from the collection.
	 * 
	 * @param String
	 *            ID of the customer being searched for.
	 * @return true if the customer was found
	 */
	public Customer searchCustomer(String ID) {

		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).getID().equals(ID)) {
				return customers.get(i);
			}
		}
		return null;
	}

	/**
	 * Lists the active customers.
	 */
	public void listCustomers() {
		Iterator iterator = customers.iterator();
		while (iterator.hasNext()) {
			Customer temp = (Customer) iterator.next();
			System.out.println(
					"Name: " + temp.getName().toUpperCase() + "\n" + "Address: " + temp.getAddress().toUpperCase()
							+ "\n" + "Phone: " + temp.getPhone() + "\n" + "\nCurrent balance: $" + temp.getBalance());
		}
	}

	/**
	 * Saves file data.
	 * 
	 * @throws IOException
	 */
	public void save() throws IOException {
		FileOutputStream file;
		file = new FileOutputStream("CustomerData");
		ObjectOutputStream output = new ObjectOutputStream(file);

		output.writeObject(customers);
		file.close();
	}

	/**
	 * Retrives the file. If no file is found an exception will be thrown.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void retrieve() throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream("CustomerData");
		ObjectInputStream input = new ObjectInputStream(file);
		Object customersIn = input.readObject();
		customers = (LinkedList<Customer>) customersIn;
	}
}
