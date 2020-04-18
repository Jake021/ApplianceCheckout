package edu.metrostate.gp1;

import java.io.Serializable;

/**
 * This class represents an applaince.
 * 
 * @author Jacob Gerval and Marcus Behr
 */
public abstract class Appliance implements Serializable {
	private String name;
	private String model;
	private int stock; // in units available
	private int backOrders;
	private double price;
	private String type;
	private String applianceID;
	private boolean hasRepairPlan = false;

	public String getName() {
		return name;
	}

	public String getModel() {
		return model;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getBackOrders() {
		return backOrders;
	}

	public double getPrice() {
		return price;
	}

	public String getType() {
		return type;
	}

	public String getApplianceID() {
		return applianceID;
	}

	/**
	 * No arg constructor.
	 */
	public Appliance() {

	}

	/**
	 * Represents the a single unit that is an appliance.
	 * 
	 * @param name
	 *            brand of the appliance.
	 * @param model
	 *            of the appliance.
	 * @param price
	 *            of the appliance.
	 */

	public Appliance(String name, String model, double price) {
		this.name = name;
		this.model = model;
		this.price = price;
		applianceID = (name + model).toLowerCase();
	}

}
