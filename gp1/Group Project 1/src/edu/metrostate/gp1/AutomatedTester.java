package edu.metrostate.gp1;

import java.util.Random;

public class AutomatedTester {
	private Store store;
	
	/**
	 * Stores the Library object and invokes the test() method to test Member
	 * creation.
	 * 
	 * @param library the Library object
	 */
	public AutomatedTester(Store store) {
		this.store = store;
		testCustomers();
	}
	
	/**
	 * tests customer, appliance creation
	 */
	public int testCustomers() {
		String[] names = {"marcus", "jake", "loel","ibrahim", "ryan"};
		String[] address = {"1401 portland", "567 shoreview", "123 fake street", "456 pleasant", "1456 drugal"};
		String [] phones = {"1234567", "0987654", "2463863", "1235672", "1019837"};
		Customer[] customers = new Customer[5];
		
		for (int i = 0; i < names.length; i++) {
			customers[i] = store.addCustomer(names[i], address[i], phones[i]);
			
			assert customers[i].getName().equals(names[i]);
			assert customers[i].getAddress().equals(address[i]);
			assert customers[i].getPhone().equals(phones[i]);
			
		}
		return 0;
		
	}
	
	public void testAppliances() {
		String[] types = {"washer", "washer", "dryer", "dryer", "refrigerator", "refrigerator", "furnace", "furnace"};
		String[] brand = {"kenmore","sears","bronson","target","tjmaxx","walmart","marshalls", "kmart"};
		String[] names = {"a1", "a2", "a3", "a4", "a5", "a6", "a7","a8"};
		String[] prices = {"235","245","123","455","234","4534","2342","121"};
		Appliance[] appliances = new Appliance[20];
		
		for (int i = 0; i < 20; i++) {
			appliances[i] = store.addAppliance(types[new Random().nextInt(7)],brand[new Random().nextInt(7)],
					names[new Random().nextInt(7)], prices[new Random().nextInt(7)]);
			
			
			
		}
	}
	
	
	
	
}
