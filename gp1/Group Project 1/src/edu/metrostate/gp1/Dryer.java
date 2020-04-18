package edu.metrostate.gp1;

/**
 * This class represents a single dryer.
 *
 * @author Jacob Gerval and Marcus Behr
 */
public class Dryer extends Appliance {
	private double monthlyAmount;

	public double getMonthlyAmount() {
		return monthlyAmount;
	}

	public void setMonthlyAmount(double monthlyAmount) {
		this.monthlyAmount = monthlyAmount;
	}

	/**
	 * This method represents the dryer appliance.
	 * 
	 * @param name
	 *            as a string.
	 * @param model
	 *            as a string.
	 * @param price
	 *            as a double.
	 * @param monthlyAmount
	 *            as a double.
	 */
	public Dryer(String name, String model, double price, double monthlyAmount) {
		super(name, model, price);
		this.monthlyAmount = monthlyAmount;
	}
}
