package edu.metrostate.gp1;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is used to manager a customer.
 *
 * @author Jacob Gerval and Marcus Behr
 */
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String phone;
	private String customerString = "c";
	private String ID;
	private double balance = 0;
	private List<RepairPlan> repairPlans = new LinkedList<RepairPlan>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * The representation of a single customer.
	 * 
	 * @param name
	 * @param address
	 * @param phone
	 */
	public Customer(String name, String address, String phone) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		ID = customerString + (CustomerIdServer.instance().getId());
	}

	/**
	 * Returns the balance.
	 * 
	 * @param double
	 */
	public void addBalance(double amount) {
		setBalance(getBalance() + amount);
	}

}
