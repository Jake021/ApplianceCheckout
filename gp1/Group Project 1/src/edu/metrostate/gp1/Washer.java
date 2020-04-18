package edu.metrostate.gp1;

/**
 * This class represents a single washer.
 * 
 * @author Jacob Gerval and Marcus Behr
 */
public class Washer extends Appliance {
	private double monthlyAmount;

	public double getMonthlyAmount() {
		return monthlyAmount;
	}

	public void setMonthlyAmount(double monthlyAmount) {
		this.monthlyAmount = monthlyAmount;
	}

	/**
	 * This method represents a washers; name, model, price, and montly payment
	 * amount.
	 * 
	 * @param name
	 *            Brand of the washer.
	 * @param model
	 *            of the washer.
	 * @param price
	 *            of the washer.
	 * @param monthlyAmount
	 *            of the washer.
	 */
	public Washer(String name, String model, double price, double monthlyAmount) {
		super(name, model, price);
		this.monthlyAmount = monthlyAmount;
	}
}
