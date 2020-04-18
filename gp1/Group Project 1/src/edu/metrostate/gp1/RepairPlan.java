package edu.metrostate.gp1;

import java.io.Serializable;

/**
 * This class represents the repair plans.
 * 
 * @author Jacob Gerval and Marcus Behr
 */
public class RepairPlan implements Serializable {
	private Customer customer;
	private Appliance appliance;
	private double monthlyAmount;

	public Customer getCustomer() {
		return customer;
	}

	public Appliance getAppliance() {
		return appliance;
	}

	public double getMonthlyAmount() {
		return monthlyAmount;
	}

	public RepairPlan() {

	}

	/**
	 * Denotes the repair plans for the customers appliance (dryer) and the montly
	 * amount.
	 * 
	 * @param customer
	 *            the customer
	 * @param dryer
	 *            the appliance.
	 */
	public RepairPlan(Customer customer, Dryer dryer) {
		this.customer = customer;
		this.appliance = dryer;
		monthlyAmount = dryer.getMonthlyAmount();
	}

	/**
	 * Denotes the repair plans for the customers appliance (washer) and the montly
	 * amount.
	 * 
	 * @param customer
	 *            the customer
	 * @param washer
	 *            the appliance.
	 */
	public RepairPlan(Customer customer, Washer washer) {
		this.customer = customer;
		this.appliance = washer;
		monthlyAmount = washer.getMonthlyAmount();
	}

}
