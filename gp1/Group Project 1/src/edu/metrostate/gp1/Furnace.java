package edu.metrostate.gp1;

/**
 * This class represents a single furnace.
 * 
 * @author Jacob Gerval and Marcus Behr
 */
public class Furnace extends Appliance {
	private int output; // in BTU's

	public int getOutput() {
		return output;
	}

	public void setOutput(int output) {
		this.output = output;
	}

	/**
	 * This method represents the furnances; name, model, price, and outputt.
	 * 
	 * @param name
	 *            brand of the furnace.
	 * @param model
	 *            the model of the furnace.
	 * @param price
	 *            the price of the furnace.
	 * @param output
	 *            the price of the furnace.
	 */
	public Furnace(String name, String model, double price, int output) {
		super(name, model, price);
		this.output = output;
	}
}
