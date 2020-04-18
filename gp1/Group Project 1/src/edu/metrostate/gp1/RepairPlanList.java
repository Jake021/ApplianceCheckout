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
 * This class represents all of the customers repair lists.
 *
 * @author Jacob Gerval and Marcus Behr
 */
public class RepairPlanList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static RepairPlanList repairPlanList;
	private static List<RepairPlan> repairPlans = new LinkedList<RepairPlan>();

	/**
	 * No args constructor.
	 */
	public RepairPlanList() {

	}

	public static RepairPlanList instance() {
		if (repairPlanList == null) {
			return repairPlanList = new RepairPlanList();
		} else {
			return null;
		}
	}

	/**
	 * inserts repair plan to RepairPlanList
	 * 
	 * @param repairPlan
	 * @return
	 */
	public boolean insertRepairPlan(RepairPlan repairPlan) {
		return repairPlans.add(repairPlan);
	}

	/**
	 * Removes a pairplan from a customer.
	 * 
	 * @param customerID
	 * @param applianceID
	 */
	public void removeRepairPlan(String customerID, String applianceID) {
		for (int i = 0; i < repairPlans.size(); i++) {
			if (repairPlans.get(i).getCustomer().getID().toLowerCase().equals(customerID)
					&& repairPlans.get(i).getAppliance().getApplianceID().toLowerCase().equals(applianceID)) {
				repairPlans.remove(i);
				break;
			}
		}
	}

	/**
	 * Will list all RepairPlan objects in repairPlanList
	 */
	public void listRepairPlans() {
		Iterator iterator = repairPlans.iterator();
		while (iterator.hasNext()) {
			RepairPlan temp = (RepairPlan) iterator.next();
			System.out.println("Customer name: " + temp.getCustomer().getName().toUpperCase() + "\nCustomer phone: "
					+ temp.getCustomer().getPhone() + "\nCustomer ID: " + temp.getCustomer().getID().toUpperCase()
					+ "\nBalance: $" + temp.getCustomer().getBalance() + "\nAppliance brand: "
					+ temp.getAppliance().getName().toUpperCase() + "\nAppliance model: "
					+ temp.getAppliance().getModel().toUpperCase());
		}
	}

	/**
	 * charges every customer enrolled in a repair plan based on price of repair
	 * plan for respective appliance
	 * 
	 * @return
	 */
	public double billRepairPlan() {
		double total = 0;
		for (int i = 0; i < repairPlans.size(); i++) {
			double amountCharged = repairPlans.get(i).getMonthlyAmount();
			double currentBalance = repairPlans.get(i).getCustomer().getBalance();
			Store.instance().updateCustomer(repairPlans.get(i).getCustomer().getID(), amountCharged);
			total += amountCharged;
		}
		return total;
	}

	/**
	 * serializes object to file
	 * 
	 * @throws IOException
	 */
	public void save() throws IOException {
		FileOutputStream file;
		file = new FileOutputStream("RepairPlanData");
		ObjectOutputStream output = new ObjectOutputStream(file);

		output.writeObject(repairPlans);
		file.close();
	}

	/**
	 * deserializes objects found in file and updates repairPlans list
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void retrieve() throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream("RepairPlanData");
		ObjectInputStream input = new ObjectInputStream(file);
		Object repairPlansIn = input.readObject();
		repairPlans = (LinkedList<RepairPlan>) repairPlansIn;
	}
}
