package edu.metrostate.gp1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class UserInterface {
	private static Store store;
	private static UserInterface userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	private static final int EXIT = 0;
	private static final int ADD_CUSTOMER = 1;
	private static final int ADD_APPLIANCE = 2;
	private static final int ADD_TO_INVENTORY = 3;
	private static final int PURCHASE = 4;
	private static final int LIST_CUSTOMERS = 5;
	private static final int LIST_APPLIANCES = 6;
	private static final int DISPLAY_TOTAL = 7;
	private static final int ENROLL_REPAIR_PLAN = 8;
	private static final int WITHDRAW_REPAIR_PLAN = 9;
	private static final int BILL_REPAIR_PLAN = 10;
	private static final int LIST_REPAIR_PLAN_CUSTOMERS = 11;
	private static final int LIST_BACKORDERS = 12;
	private static final int SAVE = 13;
	private static final int HELP = 14;

	/**
	 * This is the switch statement used to determine off of what the user will see
	 * next when running this program. The user input is validated throught the
	 * getCommand method.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void start() throws IOException, ClassNotFoundException {
		displayMenu();
		int command = getCommand();
		while (command != EXIT) {
			switch (command) {
			case ADD_CUSTOMER:
				addCustomer();
				break;
			case ADD_APPLIANCE:
				addAppliance();
				break;
			case ADD_TO_INVENTORY:
				addInventory();
				break;
			case PURCHASE:
				purchase();
				break;
			case LIST_CUSTOMERS:
				listCustomers();
				break;
			case LIST_APPLIANCES:
				listAppliances();
				break;
			case LIST_BACKORDERS:
				listBackOrders();
				break;
			case ENROLL_REPAIR_PLAN:
				enrollRepairPlan();
				break;
			case LIST_REPAIR_PLAN_CUSTOMERS:
				listRepairPlans();
				break;
			case WITHDRAW_REPAIR_PLAN:
				withdrawRepairPlan();
				break;
			case BILL_REPAIR_PLAN:
				billRepairPlan();
				break;
			case DISPLAY_TOTAL:
				displayTotal();
				break;
			case SAVE:
				save();
				break;
			case HELP:
				retrieve();
				break;
			}
			displayMenu();
			command = getCommand();
		}
		System.out.println("Goodbye.");
	}

	/**
	 * Provides logic for the switch statement. Determines if the process needs to
	 * be killed. This method also validates the input of the user. Provides logic
	 * for the switch statement. Determines if the process needs to be killed. This
	 * method also validates the input of the user.
	 * 
	 * @throws Number Format Exception
	 * @return int
	 */
	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(scanInput(""));
				if (value <= HELP) {
					return value;
				}
			} catch (NumberFormatException nfe) {
				System.out.print("Invalid entry please try again.");
			}
		} while (true);
	}

	public void displayMenu() {
		System.out.println("Enter a number between 0 and 14 as explained below: \n");
		System.out.println("[" + ADD_CUSTOMER + "] Add a customer.");
		System.out.println("[" + ADD_APPLIANCE + "] Add an appliance.");
		System.out.println("[" + ADD_TO_INVENTORY + "] Add a appliance to inventory.");
		System.out.println("[" + PURCHASE + "] Purchase an appliance.");
		System.out.println("[" + LIST_CUSTOMERS + "] Display all customers.");
		System.out.println("[" + LIST_APPLIANCES + "] Display all appliances.");
		System.out.println("[" + DISPLAY_TOTAL + "] Display total sales.");
		System.out.println("[" + ENROLL_REPAIR_PLAN + "] Enroll customer in repair plan.");
		System.out.println("[" + WITHDRAW_REPAIR_PLAN + "] Withdraw customer from repair plan.");
		System.out.println("[" + BILL_REPAIR_PLAN + "] Bill repair plan customers.");
		System.out.println("[" + LIST_REPAIR_PLAN_CUSTOMERS + "] Display repair plan customers.");
		System.out.println("[" + LIST_BACKORDERS + "] Display backorders.");
		System.out.println("[" + SAVE + "] Save store information.");
		System.out.println("[" + HELP + "] Help.");
		System.out.println("[" + EXIT + "] Exit Application.");
	}

	/**
	 * This methods adds a customer to the customer list. It then confirms with the
	 * user that the user was created and then prompts the user if they would like
	 * to add another customer. This methods adds a customer to the customer list.
	 * It then confirms with the user that the user was created and then prompts the
	 * user if they would like to add another customer. The company keeps track of
	 * the name and phone number. The system creates a customer id, which it
	 * remembers.
	 * 
	 * @throws Number Format Exception
	 */
	public void addCustomer() {
		do {
			String name = scanInput("Enter the customer's name: ");
			String address = scanInput("Enter the address: ");
			String phoneNumber = scanInput("Enter the phone number: ");
			Customer customer = store.addCustomer(name, address, phoneNumber);
			if (customer == null) {
				System.out.println("Could not add customer.");
			}
			System.out.println(customer.getName() + " added!");
		} while (yesOrNo("Would you like to add another customer?"));
	}

	/**
	 * This method prompts the user to add an appliance and then adds the appliance
	 * to the appliance linked list. This method prompts the user to add an
	 * appliance and then adds the appliance to the appliance linked list.
	 */
	public void addAppliance() {
		String type = scanInput("Type of Appliance: ").toLowerCase();
		String name = scanInput("Appliance brand: ");
		String model = scanInput("Model: ");
		String price = scanInput("Price: ");

		store.addAppliance(type, name, model, price);
	}

	/**
	 * This method prompts the user to add to add an appliance to the inventory. The
	 * user may add any of the different types of appliances. The user enters the
	 * quantity, which gets added to the total in stock. Any back orders are
	 * processed in the chronological order and the total from sales is updated. The
	 * user refers to an appliance using the appliance id.
	 * 
	 * @throws Number Format Exception
	 */
	public void addInventory() {
		try {
			String name = scanInput("Enter appliance brand name: ");
			String model = scanInput("Enter appliance brand model: ");

			String ID = name + model;
			String quantityResponse = scanInput("Enter total units added: ");
			int quantity = Integer.parseInt(quantityResponse);
			store.addToInventory(ID.toLowerCase(), quantity);
		} catch (NumberFormatException e) {
			System.out.println("Please enter a number for quantity!");
		}
	}

	/**
	 * This method prompts for them to enter their customer ID, brand, model, and
	 * the amount of the appliance they want to purchase. It then adds that purchase
	 * to the customer.
	 * 
	 * @throws NumberFormatException
	 */
	public void purchase() {
		try {
			String customerID = scanInput("Enter customer ID: ").toLowerCase();
			String brand = scanInput("Enter appliance brand: ");
			String model = scanInput("Enter appliance model: ");
			String quantity = scanInput("Enter quantity of purchase: ");

			int quantityAsInteger = Integer.parseInt(quantity);

			store.purchase(customerID, (brand + model).toLowerCase(), quantityAsInteger);
		} catch (NumberFormatException e) {
			System.out.println("Please enter a number for quantity!");
		}
	}

	/**
	 * prompts the store to list customers
	 */
	public void listCustomers() {
		store.listCustomers();
	}

	/**
	 * prompts the store to list appliances
	 */
	public void listAppliances() {
		store.listAppliances();
	}

	/**
	 * prompts the store to list back orders
	 */
	public void listBackOrders() {
		store.listBackOrders();
	}

	/**
	 * This method prompts the user for their customer ID, appliance. It then checks
	 * to see if they enter a washer or dryer and will be told if they are eligble
	 * or not for a repair plan.
	 */
	public void enrollRepairPlan() {
		String customerID = scanInput("Enter customer ID: ");
		String applianceType = scanInput("Enter type of appliance: ");
		if (applianceType.toLowerCase().equals("washer") || applianceType.toLowerCase().equals("dryer")) {
			String brand = scanInput("Enter brand name: ");
			String model = scanInput("Enter model: ");
			store.enrollRepairPlan(customerID, brand, model, applianceType);
		} else {
			System.out.println("Only Washers and Dryers are elligible for a repair plan");
		}

	}

	/**
	 * prompts the store to list repair plans
	 */
	public void listRepairPlans() {
		store.listRepairPlans();
	}

	/**
	 * customer will be withdrawn from repair plan
	 */
	public void withdrawRepairPlan() {
		String customerID = scanInput("Enter customer ID: ");
		String brand = scanInput("Enter brand of appliance being removed: ");
		String model = scanInput("Enter model of appliance being removed: ");

		String applianceID = brand + model;

		store.withdrawRepairPlan(customerID, applianceID.toLowerCase());
	}

	/**
	 * prompts the store to charge repair plan customers
	 */
	public void billRepairPlan() {
		store.billRepairPlan();
	}

	/**
	 * displays totals in the store, purchase and repair plan respectively
	 */
	public void displayTotal() {
		store.displayTotal();
	}

	/**
	 * Gets a token after prompting the user.
	 * 
	 * @param prompt what the user selects.
	 * @return a token
	 * 
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
	 * Validates the prompt to continue with adding customers or adding appliances.
	 * 
	 * @param String the answer from the user.
	 * @return boolean
	 */
	public boolean yesOrNo(String answer) {
		String input = scanInput(answer + "Yes or no");
		if (input.toLowerCase().equals("y") || input.toLowerCase().equals("yes")) {
			return true;
		}
		return false;
	}

	public UserInterface() throws ClassNotFoundException, IOException {
		if (yesOrNo("Load information from disk?")) {
			retrieve();
		} else {
			store = store.instance();
			if (yesOrNo("Do you want to generate a test bed and invoke the functionality using asserts?")) {
				new AutomatedTester(store);
			}
		}
	}

	public void test() {
		System.out.print("here");
	}

	public static UserInterface instance() throws ClassNotFoundException, IOException {
		if (userInterface == null) {
			store = new Store();
			return userInterface = new UserInterface();
		} else {
			return userInterface;
		}
	}

	/**
	 * Retrieves the file. If no file is found an exception will be thrown.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static void retrieve() throws ClassNotFoundException, IOException {
		store.retrieve();
	}

	/**
	 * Saves file data.
	 * 
	 * @throws IOException
	 */
	private void save() throws IOException {
		store.save();
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		UserInterface.instance().start();
	}
}
