package edu.metrostate.gp1;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class represents an order for a customer that has put on hold until the
 * quanitity of appliances is greater than 0.
 * 
 * @author Jacob Gerval and Marcus Behr
 */
public class BackOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	Customer customer;
	Appliance appliance;
	private int quantity;
	private Calendar timeOrdered;

	public Customer getCustomer() {
		return customer;
	}

	public Appliance getAppliance() {
		return appliance;
	}

	public int getQuantity() {
		return quantity;
	}

	public Calendar getTimeOrdered() {
		return timeOrdered;
	}

	/**
	 * No arg constructor
	 */
	public BackOrder() {

	}

	/**
	 * This method represents order for a specific customer that has been put on
	 * hold until the required quantity of appliances for the sale are in the
	 * inventory.
	 * 
	 * @param customer
	 *            the customer requesting the purchase
	 * @param appliance
	 *            the appliance that the customer wants to purchase
	 * @param quantity
	 *            the number of appliances requested for purchase
	 */
	public BackOrder(Customer customer, Appliance appliance, int quantity) {
		this.customer = customer;
		this.appliance = appliance;
		this.quantity = quantity;
		timeOrdered = new GregorianCalendar();
	}
}
