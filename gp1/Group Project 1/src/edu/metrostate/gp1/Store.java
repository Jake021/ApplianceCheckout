package edu.metrostate.gp1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Store implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomerList customerList;
	private ApplianceList applianceList;
	private BackOrderList backOrderList;
	private RepairPlanList repairPlanList;
	private CustomerIdServer customerIDs;
	private double purchases;
	private double repairPlanCharges;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Store store;
	
	/**
	 * constructor for Store object, also instantiates instances of all collection classes
	 */
	public Store() {
		customerList = CustomerList.instance();
		applianceList = ApplianceList.instance();
		backOrderList = BackOrderList.instance();
		repairPlanList = RepairPlanList.instance();
		customerIDs = CustomerIdServer.instance();
	}

	
	/**
	 * instance for Store, follows singleton pattern
	 * @return store
	 */
	public static Store instance() {
		if (store == null) {
			return store = new Store();
		}

		else {
			return store;
		}
	}
	/**
	 * Creates Customer object from input by user, then adds this object to CustomerList class
	 * @param name
	 * @param address
	 * @param phone
	 * @return Customer object created
	 */
	public Customer addCustomer(String name, String address, String phone) {
		Customer customer = new Customer(name, address, phone);
		if (customerList.insertCustomer(customer)) {
			return customer;
		}
		return null;
	}
	/**
	 * used to add an appliance to the director, will create the correct appliance based on type
	 * description input by user, adds appliance to ApplianceList class 
	 * @param type
	 * @param name
	 * @param model
	 * @param price
	 * @return Appliance object, the created object from switch statement
	 */
	public Appliance addAppliance(String type, String name, String model, String price) {
		try {

			String monthlyAmount = "";
			Double monthlyAmountDouble;
			Double priceDouble = Double.valueOf(price);

			switch (type) {
			case "washer":
				monthlyAmount = scanInput("Monthly amount for repair plan: ");
				monthlyAmountDouble = Double.valueOf(monthlyAmount);
				Washer washer = new Washer(name, model, priceDouble, monthlyAmountDouble);
				if (applianceList.insertAppliance(washer))
					return washer;
				break;
			case "dryer":
				monthlyAmount = scanInput("Monthly amount for repair plan: ");
				monthlyAmountDouble = Double.valueOf(monthlyAmount);
				Dryer dryer = new Dryer(name, model, priceDouble, monthlyAmountDouble);
				if (applianceList.insertAppliance(dryer))
					return dryer;
				break;
			case "refrigerator":
				String capacity = scanInput("Enter capacity: ");
				int capacityInteger = Integer.parseInt(capacity);
				Refrigerator refrigerator = new Refrigerator(name, model, priceDouble, capacityInteger);
				if (applianceList.insertAppliance(refrigerator))
					return refrigerator;
				break;
			case "furnace":
				String heatOutput = scanInput("Enter heat output: ");
				int heatOutputInteger = Integer.parseInt(heatOutput);
				Furnace furnace = new Furnace(name, model, priceDouble, heatOutputInteger);
				if (applianceList.insertAppliance(furnace)) {
					return furnace;
				}
				break;
			default:
				System.out.println("Please enter the corect type of appliance. These are: \n"
						+ "washer, dryer, refrigerator, furnace.");
			}
		} catch (NumberFormatException e) {
			System.out.println("There must be a number input for price values!");
		}
		return null;
	}
	/**
	 * this is used to get inventory value from the BackOrderList class
	 * @param ID
	 * @return
	 */
	public int getStock(String ID) {
		return applianceList.searchAppliance(ID).getStock();
	}
	
	/**
	 * this is used to set inventory value from the BackOrderList class
	 * @param ID
	 * @param value
	 */
	public void setStock(String ID, int value) {
		applianceList.searchAppliance(ID).setStock(value);
	}
	
	/**
	 * method adds items to inventory, first checks whether there are backorders to be processed, if so
	 * then these backorders are fulfilled. Otherwise it will just add to inventory
	 * @param ID
	 * @param quantity
	 */
	public void addToInventory(String ID, int quantity) {
		try {
			//check if there are any backorders to fulfill
			if (backOrderList.getSize() > 0) {
				applianceList.searchAppliance(ID).setStock(applianceList.searchAppliance(ID).getStock() + quantity);
				backOrderList.processBackOrders(ID);
		
				//update total sales in store
				purchases += backOrderList.getBackOrderTotal();
				backOrderList.setBackOrderTotal(0); // the total from processed back orders has been added to purchase,
													// this can be reset
			} else {
				applianceList.searchAppliance(ID).setStock(quantity);
			}
		} catch (Exception e) {
			System.out.println("Something went wrong. "
					+ "Make sure brand name and model name are spelled correctly");
		}

	}
	
	/**
	 * this method will attempt to purchase an appliance. checks if there are existent back orders. if so, then the
	 * appliance is added to back order. if not, then the inventory is checked to see if there is enough for the
	 * amount requested, if not, the item is sent to the inventory. 
	 * 
	 * The final case is an item having sufficient inventor and then being purchased
	 * @param customerID
	 * @param applianceID
	 * @param purchaseAmount
	 */
	public void purchase(String customerID, String applianceID, int purchaseAmount) {
		try {
			//check if there are any back orders before this purchase, if true, leave method
			if (checkBackOrders(customerID, applianceID, purchaseAmount)) {
				return;
			} else if (applianceList.searchAppliance(applianceID).getStock() < purchaseAmount) {
				//there is not enough inventory, create a new back order for this purchase
				BackOrder backOrder = new BackOrder(customerList.searchCustomer(customerID),
						applianceList.searchAppliance(applianceID), purchaseAmount);
				backOrderList.insertBackOrder(backOrder);
				System.out.println("Not enough stock, this customer added to back orders.");
			} else {
				//there is enough inventory and no back orders, purchase can be made
				double total = (applianceList.searchAppliance(applianceID).getPrice() * purchaseAmount);
				double currentBalance = (customerList.searchCustomer(customerID).getBalance());
				int currentStock = (applianceList.searchAppliance(applianceID).getStock());
				
				//update customer balance
				customerList.searchCustomer(customerID).setBalance(currentBalance + total);
				
				//update appliance inventory and total purchases in store
				applianceList.searchAppliance(applianceID).setStock(currentStock - purchaseAmount);
				purchases += total;
			}

		} catch (NullPointerException e) {
			System.out.println(
					"Please make sure appliance brand, model and customer ID \nare typed correctly and these items"
							+ " are in the system.");
		}
	}

		/**
		 * Checks for backorders already in the system if a purchase is being attempted
		 * @param customerID
		 * @param applianceID
		 * @param purchaseAmount
		 * @return true if backorder exists, false if not
		 */
	public boolean checkBackOrders(String customerID, String applianceID, int purchaseAmount) {
		if (backOrderList.getSize() > 0) {
			if (backOrderList.searchBackOrders(applianceID) != null) {
				BackOrder backOrder = new BackOrder(customerList.searchCustomer(customerID),
						applianceList.searchAppliance(applianceID), purchaseAmount);
				backOrderList.insertBackOrder(backOrder);
				System.out.println("Other customers are waiting in back orders, this customer is now in the queue");
				return true;
			}

		}
		return false;
	}
	
	/**
	 * method used to update customers from both BackOrderList and RepairPlanList classes when charges
	 * to their account is made
	 * @param ID
	 * @param total
	 */
	public void updateCustomer(String ID, double total) {
		customerList.searchCustomer(ID).setBalance(total + customerList.searchCustomer(ID).getBalance());
	}
	
	/**
	 * Lists all customers in the directory
	 */
	public void listCustomers() {
		customerList.listCustomers();
	}
	
	/**
	 * Lists all appliances in the directory
	 */
	public void listAppliances() {
		String type = scanInput("Enter type of appliance to list(type \"all\" for all " + "types of appliances): ");
		applianceList.listAppliances(type);
	}
	
	/**
	 * Lists all back orders in the directory
	 */
	public void listBackOrders() {
		backOrderList.listBackOrders();
	}
	
	/**
	 * This method will attempt to enroll a customer to a repair plan, as long as the repair plan
	 * is an elligible appliance and both customer and appliance objects exists in directory
	 * @param customerID
	 * @param brand
	 * @param model
	 * @param type
	 */
	public void enrollRepairPlan(String customerID, String brand, String model, String type) {

		try {
			Appliance appliance = applianceList.searchAppliance(brand + model);
			Customer customer = customerList.searchCustomer(customerID);

			if (type.toLowerCase().equals("dryer")) {
				RepairPlan repairPlan = new RepairPlan(customer, (Dryer) appliance);
				repairPlanList.insertRepairPlan(repairPlan);
			} else {
				RepairPlan repairPlan = new RepairPlan(customer, (Washer) appliance);
				repairPlanList.insertRepairPlan(repairPlan);
			}
		} catch (Exception e) {
			System.out.println("Something went wrong. Make sure customerID, brand, and model are typed correctly.");
		}

	}
	
	/**
	 * Lists all repair plans in the directory
	 */
	public void listRepairPlans() {
		repairPlanList.listRepairPlans();
	}
	
	/**
	 * used to withdraw a customer from a repair plan, calls the removeRepairPlan method in the 
	 * RepairPlanList class
	 * @param customerID
	 * @param applianceID
	 */
	public void withdrawRepairPlan(String customerID, String applianceID) {
		try {
			repairPlanList.removeRepairPlan(customerID, applianceID);
		} catch (NullPointerException e) {
			System.out.println("Something went wrong. Make sure customerID, brand, and model are typed correctly.");
		}
	}
	
	/**
	 * This will bill all current customers who have a repair plan, calls the billRepairPlan method
	 * in the RepairPlanList class
	 */
	public void billRepairPlan() {
		double totalCharged = repairPlanList.billRepairPlan();
		repairPlanCharges += totalCharged;
		System.out.println("Customers have been billed! \nDisplay customers to see balance changes.");
	}

		/**
		 * Displays total sales from purchases and repair plans respectively
		 */
	public void displayTotal() {
		double total = purchases + backOrderList.getBackOrderTotal();
		double grandTotal = total + repairPlanCharges;
		System.out.println("Purchases in store: $" + total + "\nRepair plan charges: $" + repairPlanCharges
				+ "\nGrand total: " + grandTotal);
	}

	/**
	 * This method is called whenever a user must input information into the system
	 * @param answer
	 * @return String contaning user input
	 */
	public String scanInput(String answer) {
		do {
			try {
				System.out.println(answer);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException E) {
				System.exit(0);
			}
		} while (true);
	}

	/**
	 * Used to deserialize objects from file and read them into their respective classes/collection classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void retrieve() throws ClassNotFoundException, IOException {
		try {
			customerIDs.retrieve();
		} catch (Exception e) {

		}
		try {
			customerList.retrieve();
		} catch (Exception e) {

		}
		try {
			applianceList.retrieve();
		} catch (Exception e) {

		}
		try {
			backOrderList.retrieve();
		} catch (Exception e) {

		}
		try {
			repairPlanList.retrieve();
		} catch (Exception e) {

		}
		try {
			FileInputStream file = new FileInputStream("StoreData");
			ObjectInputStream input = new ObjectInputStream(file);
			Object purchasesIn = input.readObject();
			Object repairChargesIn = input.readObject();
			purchases = (double) purchasesIn;
			repairPlanCharges = (double) repairChargesIn;
		} catch (Exception e) {

		}
	}
	
	/**
	 * Used to serialize objects from all classes to file
	 * @throws IOException
	 */
	public void save() throws IOException {
		try {
			customerIDs.save();
		} catch (Exception e) {

		}
		try {
			customerList.save();
		} catch (Exception e) {

		}
		try {
			applianceList.save();
		} catch (Exception e) {

		}
		try {
			backOrderList.save();
		} catch (Exception e) {

		}
		try {
			repairPlanList.save();
		} catch (Exception e) {

		}
		try {
			FileOutputStream file;
			file = new FileOutputStream("StoreData");
			ObjectOutputStream output = new ObjectOutputStream(file);

			output.writeObject(purchases);
			output.writeObject(repairPlanCharges);
			file.close();
		} catch (Exception e) {

		}
	}
}