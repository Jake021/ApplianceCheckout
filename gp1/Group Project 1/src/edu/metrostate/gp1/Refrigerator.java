package edu.metrostate.gp1;

/**
 * This class represents a single refrigerator.
 * 
 * @author Jacob Gerval and Marcus Behr
 */
public class Refrigerator extends Appliance {
	private int capacity; // in liters

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * This method represents a refrigerators; name, model, price, and capacity.
	 * 
	 * @param name
	 *            brand of the refrigerator.
	 * @param model
	 *            the model of the refrigerator.
	 * @param price
	 *            the price of the refrigerator.
	 * @param capacity
	 *            the capacity of the refrigerator.
	 */
	public Refrigerator(String name, String model, double price, int capacity) {
		super(name, model, price);
		this.capacity = capacity;
	}
}
