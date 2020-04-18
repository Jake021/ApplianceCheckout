package edu.metrostate.gp1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BackOrderList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static BackOrderList backOrder;
	private static List<BackOrder> backOrders = new LinkedList<BackOrder>();
	private double backOrderTotal;

	public double getBackOrderTotal() {
		return backOrderTotal;
	}

	public void setBackOrderTotal(double total) {
		this.backOrderTotal = total;
	}

	public int getSize() {
		return backOrders.size();
	}

	public BackOrderList() {

	}

	public static BackOrderList instance() {
		if (backOrder == null) {
			return backOrder = new BackOrderList();
		} else {
			return backOrder;
		}
	}

	/**
	 * inserts back order, unless the appliance class is of type furnace
	 * 
	 * @param backOrder
	 * @return true if appliance has been added, false if not
	 */
	public boolean insertBackOrder(BackOrder backOrder) {
		// excluding furnace from backOrders
		if (backOrder.getAppliance().getClass().getName().equals("edu.metrostate.gp1.Furnace")) {
			System.out.println("Furnaces can't be added to back order. Check back when more stock is in store.");
			return false;
		}
		return backOrders.add(backOrder);
	}

	/**
	 * searches back order for specific appliance ID,
	 * 
	 * @param ID
	 * @return backOrder matching appliance ID
	 */
	public BackOrder searchBackOrders(String ID) {

		for (int i = 0; i < backOrders.size(); i++) {
			if (backOrders.get(i).getAppliance().getApplianceID().equals(ID)) {
				return backOrders.get(i);
			}
		}
		return null;
	}

	/**
	 * This method process backOrders when items are added to inventory
	 * 
	 * @param ID
	 */
	public void processBackOrders(String ID) {
		int i = 0;
		/*
		 * iterate through the back order list until the end is reached, if a hit is
		 * found and the back order is removed, then i is reset to 0 in order to account
		 * for the change in back order size
		 */
		while (i < backOrders.size()) {

			if (backOrders.get(i).getAppliance().getApplianceID().equals(ID)) {

				int desiredQuantity = backOrders.get(i).getQuantity();
				int oldStock = Store.instance().getStock(ID);
				if (desiredQuantity <= Store.instance().getStock(ID)) {

					double total = desiredQuantity * (backOrders.get(i).getAppliance().getPrice());

					// update the appliance stock and customer balance in store class
					Store.instance().updateCustomer(backOrders.get(i).getCustomer().getID(), total);
					Store.instance().setStock(ID, oldStock - desiredQuantity);
					/*
					 * this is the total amount of sales that has been processed during this method
					 * and will get sent to the store class
					 */
					backOrderTotal += total;

					// output to user that back order has been fulfilled
					System.out.println("Back order placed on " + backOrders.get(i).getTimeOrdered().get(Calendar.MONTH)
							+ "/" + +backOrders.get(i).getTimeOrdered().get(Calendar.DAY_OF_MONTH) + "/"
							+ backOrders.get(i).getTimeOrdered().get(Calendar.YEAR) + " by customer "
							+ backOrders.get(i).getCustomer().getName().toUpperCase() + " fulfilled!");

					removeBackOrder(backOrders.get(i).getCustomer(), backOrders.get(i).getAppliance());
					i = 0;
				} else {
					return;// breaking because the latest order cannot be fulfilled, therefore the ones
							// after it will not be
				}
			} else {
				i++;
			}
		}

	}

	/**
	 * remove back order from back order list, once the back order is found, the
	 * loop will break as to not remove unfulfilled back orders
	 * 
	 * @param customer
	 * @param appliance
	 */
	public void removeBackOrder(Customer customer, Appliance appliance) {
		for (int i = 0; i < backOrders.size(); i++) {
			if (backOrders.get(i).getCustomer().getID().toLowerCase().equals(customer.getID())
					&& backOrders.get(i).getAppliance().getApplianceID().equals(appliance.getApplianceID())) {
				backOrders.remove(backOrders.get(i));
				break;
			}
		}
	}

	/**
	 * list back orders in the back orders list
	 */
	public void listBackOrders() {
		Iterator iterator = backOrders.iterator();
		while (iterator.hasNext()) {
			BackOrder temp = (BackOrder) iterator.next();
			System.out.println("Appliance brand: " + temp.getAppliance().getName().toUpperCase() + "\nAppliance model: "
					+ temp.getAppliance().getModel().toUpperCase() + "\nQuantity: " + temp.getQuantity()
					+ "\nCustomer: " + temp.getCustomer().getName().toUpperCase());
		}
	}

	/**
	 * Saves file data.
	 * 
	 * @throws IOException
	 */
	public void save() throws IOException {
		FileOutputStream file;
		file = new FileOutputStream("BackOrderData");
		ObjectOutputStream output = new ObjectOutputStream(file);

		output.writeObject(backOrders);
		file.close();
	}

	/**
	 * Retrives the file. If no file is found an exception will be thrown.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void retrieve() throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream("BackOrderData");
		ObjectInputStream input = new ObjectInputStream(file);
		Object backOrdersIn = input.readObject();
		backOrders = (LinkedList<BackOrder>) backOrdersIn;
	}
}
