package edu.metrostate.gp1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

public class CustomerIdServer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int idCounter;
	private static CustomerIdServer server;

	// Private constructor for singleton pattern.
	private CustomerIdServer() {
		idCounter = 1;
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */
	public static CustomerIdServer instance() {
		if (server == null) {
			return (server = new CustomerIdServer());
		} else {
			return server;
		}
	}

	/**
	 * Getter for ID.
	 * 
	 * @return id of the member
	 */
	public int getId() {
		return idCounter++;
	}
	
	/**
	 * Saves file data.
	 * 
	 * @throws IOException
	 */
	public void save() throws IOException {
		FileOutputStream file;
		file = new FileOutputStream("CustomerIDData");
		ObjectOutputStream output = new ObjectOutputStream(file);
		
		output.writeObject(idCounter);
	}
	
	/**
	 * Retrives the file. If no file is found an exception will be thrown.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void retrieve() throws IOException, ClassNotFoundException {		
		FileInputStream file = new FileInputStream("CustomerIDData");
		ObjectInputStream input = new ObjectInputStream(file);
		int idsIN =  (int) input.readObject();
		idCounter = idsIN;
	}
}
