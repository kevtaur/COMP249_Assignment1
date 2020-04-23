// -----------------------------------------------------
// Assignment 1
// Part 1
// Written by: Kevin Ve 40032669
// -----------------------------------------------------

/** <p>Kevin Ve 40032669</p>
 *  <p>COMP249</p>
 *  <p>Assignment 1</p>
 *  <p>Due Date: January 31, 2020</p>
 *  <H1>Appliance</H1>
 *  <p>Represents an Appliance</p>
 *  @author KevinVe
 */

public class Appliance {
	//Different kinds of possible Types
	public enum Type {
		Fridge, AirConditioner, Washer, Dryer, Freezer, Stove, Dishwasher, WaterHeaters, Microwave;
		
		 //returns true if input string is a Type, false otherwise
		public static boolean contains(String str) {
			for (int i = 0; i < Appliance.Type.values().length; i++) {
				if (Appliance.Type.values()[i].equals(str))
					return true;
			}
			return false;
		}
	}
	
	//Attributes
	private Type type;
	private String brand;
	private double price;
	private long serial_number;
	private static int num_of_instances = 0;
	
	/** Creates a default Appliance
	 */
	public Appliance() {
		type = Type.Fridge;
		brand = "";
		price = 0.0;
		serial_number = (long) 1000000 + num_of_instances++; //use next serial number then increment
	}
	
	/** Creates an Appliance with set type, brand, price and unique serial number
	 *  @param type Must be of enum Type
	 *  @param brand Any String
	 *  @param price Any double
	 */
	public Appliance(Type type, String brand, double price) {
		this.type = type;
		this.brand = brand;
		this.price = price;
		serial_number = (long) 1000000 + num_of_instances++;
	}
	
	/** Creates a copy of input appliance with a unique serial number
	 * @param app The appliance to be copied
	 */
	public Appliance (Appliance app) {
		type = app.getType();
		brand = app.getBrand();
		price = app.getPrice();
		serial_number = (long) 1000000 + num_of_instances++;
	}
	
	//Mutators
	public void setType(Type t) {type = t;}
	public void setBrand(String s) {brand = s;}
	public void setPrice(Double d) {price = d;}
	//serial_number and num_of_instances cannot be mutated manually
	
	//Accessors
	public Type getType() {return type;}
	public String getBrand() {return brand;}
	public Double getPrice() {return price;}
	public Long getSerialNumber() {return serial_number;}
	public static int getNumberOfInstances() {return num_of_instances;}

	/** Returns true if input Appliance has the same type, brand and price as this Appliance
	 *  @param app The appliance to be compared with
	 *  @return true if input Appliance has the same type, brand and price as this Appliance
	 */
	public boolean equals(Appliance app) {
		if (app.getType() == type && app.getBrand() == brand && app.getPrice() == price)
			return true;
		else 
			return false;
	}
	
	/** Returns readable string of this appliances attributes
	 *  @return A read-friendly printable String
	 */
	public String toString() {
		return "Serial Number: " + serial_number + "\nType:  " + type + "\nBrand: " + brand + "\nPrice: $" + price + "\n";
	}
}
