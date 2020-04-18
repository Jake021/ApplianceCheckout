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

public class ApplianceList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ApplianceList applianceList;
	public static List<Appliance> appliances = new LinkedList<Appliance>();
	
	public ApplianceList() {
		
	}
	
	public static ApplianceList instance() {
		if (applianceList == null) {
			return applianceList = new ApplianceList();
		}
		else {
			return applianceList;
		}
	}
	
	
	public boolean insertAppliance(Appliance appliance) {
		return appliances.add(appliance);
	}
	
	public Appliance searchAppliance(String ID) {

		for (int i = 0; i < appliances.size(); i++) {
			if (appliances.get(i).getApplianceID().equals(ID)) {
				return appliances.get(i);
			}
		}
		return null;
	}
	
	public void listAppliances(String type) {
		Iterator iterator = appliances.iterator();
		while(iterator.hasNext()) {
			Appliance temp = (Appliance) iterator.next();
			if(temp.getClass().getName().toLowerCase().equals("edu.metrostate.gp1."+ type.toLowerCase()) || type.toLowerCase().equals("all")) {
				System.out.println("Brand name: " + temp.getName().toUpperCase() + 
						"\nModel: " + temp.getModel().toUpperCase() + "\nPrice: " +
						temp.getPrice() + "\nQuantity available: " + 
						temp.getStock() + "\n");
			};
		}
	}
	
	public void save() throws IOException {
		FileOutputStream file;
		file = new FileOutputStream("ApplianceData");
		ObjectOutputStream output = new ObjectOutputStream(file);
		
		output.writeObject(appliances);
		file.close();
	}
	
	public static void retrieve() throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream("ApplianceData");
		ObjectInputStream input = new ObjectInputStream(file);
		Object appliancesIn = input.readObject();
		appliances = (LinkedList<Appliance>) appliancesIn;
	}
}
