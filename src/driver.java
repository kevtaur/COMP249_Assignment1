// -----------------------------------------------------
// Assignment 1
// Part 2
// Written by: Kevin Ve 40032669
// -----------------------------------------------------


import java.util.Scanner;

/** <p>Kevin Ve 40032669</p>
 *  <p>COMP249</p>
 *  <p>Assignment 1</p>
 *  <p>Due Date: January 31, 2020</p>
 *  <H1>Main driver class</H1>
 *  <p>User manages an inventory of Appliances with 5 different actions to be chosen at a main menu.</p>
 *  <ol>
 *  <li>Create new Appliances</li>
 *  <li>Edit an existing Appliance</li>
 *  <li>Display list of Appliances under a brand</li>
 *  <li>Display list of Appliances under a certain price</li>
 *  <li>Exit program</li>
 *  </ol>
 *  <p>User must pass a password check for menu choices 1 and 2.</p>
 *  
 *  @see Appliance
 *  @author KevinVe
 */
public class driver {
	public static void main(String[] args) {
		final String password = "c249"; //the password to be checked against
		final int MAX_PASSWORD_ATTEMPTS = 3; //maximum number of password attempts
		int failedAttempts = 0; //keeps track of number of failed attempts	
		
		Scanner keyIn = new Scanner(System.in);			

		System.out.print("Welcome!\n"
				   + "What is the maximum amount of appliances your store can/will contain? ");
		int maxAppliances = keyIn.nextInt();
		Appliance[] inventory = new Appliance[maxAppliances]; //main array of appliances of user inputed size
		int inventoryCount = 0; //keeps index of last added appliance
		
		/*for testing purposes
		inventory[0] = new Appliance(Appliance.Type.Fridge, "LG", 100.0);
		inventory[1] = new Appliance(Appliance.Type.Fridge, "LG", 60.0);
		inventory[2] = new Appliance(Appliance.Type.Fridge, "LG", 100.0);
		inventory[3] = new Appliance(Appliance.Type.Microwave, "LG", 60.0);
		inventory[4] = new Appliance(Appliance.Type.Microwave, "HP", 60.0);
		inventory[5] = new Appliance(Appliance.Type.Microwave, "HP", 100.0);
		*/
		
		//main loop, exists when user enters 5 in the main menu
		int menuChoice;
		do {
			displayMainMenu();
			menuChoice = keyIn.nextInt(); 
			
			switch(menuChoice) {
			case (1): //Menu Choice 1 checks for password then begins appliance creation process
				if (passCheck(password, MAX_PASSWORD_ATTEMPTS, keyIn)) { 
					createNewAppliances(inventory, inventoryCount, keyIn); //begin creation process
					
				} else if (failedAttempts >= 3) { //user failed password check too many times
					menuChoice = 5; //ejects user from program
					System.out.print("\nProgram detected suspicious activities and will terminate immediately!");
					
				} else //user failed password check but has attempts remaining
					System.out.println("You have " + (4- ++failedAttempts) + " attempts remaining.");
				break;
				
			case (2): //Menu Choice 3 checks for password then begins appliance editing process
				if (passCheck(password, MAX_PASSWORD_ATTEMPTS, keyIn)) {
					boolean toExit = false; //used to ask user if they would like to give up on entering a valid serial number 
					boolean validSerialNumber = false; 
					long serialNumberHolder;
					
					do {
						System.out.print("Please enter Serial Number: ");
						serialNumberHolder = keyIn.nextLong();
						
						if (getIndexOfAppliance(inventory, serialNumberHolder) == -1) { //if serial number is invalid
							System.out.print("Not a valid serial number. Would you like to try again (y/n)? ");
							if (keyIn.next().equals("y")) toExit = true; //ask if user would like to give up on process
							
						} else
							validSerialNumber = true; //user has entered a valid serial number
						
					} while (!validSerialNumber || toExit); 
					
					if (!toExit) //user decided not to exit menu choice
						editAppliance(inventory, serialNumberHolder, keyIn); //begin editing process
				}
				break;
				
			case (3): //Menu Choice 3 displays list of appliances whose brand is what user asked for
				String inputStr = "";
				System.out.print("Which brand are you looking for? ");
				inputStr = keyIn.next();
				
				for (int i = 0; i < Appliance.getNumberOfInstances(); i++) //loop through each appliance in inventory
					if (inventory[i].getBrand().equals(inputStr))
						System.out.println(inventory[i].toString());
				break;
				
			case (4): //Menu Choice 4 displays list of appliances whose price is less than asked for
				double inputDbl = 0.0;
				System.out.print("Under which price? ");
				inputDbl = keyIn.nextDouble();
				
				for (int i = 0; i < Appliance.getNumberOfInstances(); i++) //loop through each appliance in inventory
					if (inventory[i].getPrice() < inputDbl)
						System.out.println(inventory[i].toString());
				break;
				
			case (5): //Menu Choice 5 quits program
				System.out.print("Thank you for using this program.");
				break;
				
			default: //if user did not choose a valid menu option
				System.out.println("Not a valid option.");
			}
		} while (menuChoice != 5); //if user chooses 5 then quit main loop and program
	}
	
	/**Asks user for password with a maximum number of attempts allowed. 
	 * @param password The correct password to be compared with
	 * @param MAX_ATTEMPTS Maximum number of allowed attempts
	 * @param keyIn Used for input
	 * @return true if password is correct. false if password entered failed MAX_ATTEMPT times
	 */
	public static boolean passCheck(String password, int MAX_ATTEMPTS, Scanner keyIn) {
		int attempts = 0;
		boolean success = false;
		do {
			System.out.print("Please enter your password: ");
			if (password.equals(keyIn.next())) { //user gets the password correect
				attempts = 0;
				success = true;
				System.out.println("Password Successful. ");
			} else if (attempts == MAX_ATTEMPTS-1){ //user fails maximum number of times
				attempts++;
				System.out.println("Too many attempts. You will be returned to the main menu.");
			} else { //user fails 
				attempts++;
				System.out.print("Please try again. ");
			}
		} while (success == false && attempts < MAX_ATTEMPTS); //loop quits when user enters password or fails too many times
		return success;
	}
	
	/**Asks user for a valid integer as defined by being between 0 and a given maximum
	 * @param max Maximum int to be compared 
	 * @param keyIn Used for input
	 * @return int between 0 and max
	 */
	public static int validateInput(int max, Scanner keyIn) {
		int choice = keyIn.nextInt();
		while (choice > max || choice < 0) {
			System.out.print("Please try again. ");
			choice = keyIn.nextInt();
		}
		return choice;
	}
	
	/**Asks user for a type. Loops it until user enters a valid type or quits
	 * @see Appliance.Type List of valid types
	 * @param keyIn Used for input
	 * @return Appliance.Type
	 */
	public static Appliance.Type validateType(Scanner keyIn) {
		String tempType;
		while (true) { //loops forever
			System.out.print("Please enter a Type: ");
			tempType = keyIn.next();
			for (Appliance.Type x : Appliance.Type.values()) //loops through each value of enum Type
				if (x.name().equals(tempType))
					return Appliance.Type.valueOf(tempType); //only quits if user input is a valid type
			System.out.print(tempType + " is not a type. Please try again. ");
		}
	}
	
	/**Menu Option 1 creates a new appliance and puts it in the inventory array
	 * @param inventory Array of appliances to hold new appliance
	 * @param inventoryCount Used as index of next empty element for the new appliance
	 * @param keyIn Used for inputs
	 */
	public static void createNewAppliances(Appliance[] inventory, int inventoryCount, Scanner keyIn) {
		int toCreate; //number of appliances to be created this instance of menu choice
		int created = 0; //number of appliances made thus far
		String tempType;
		String tempBrand;
		double tempPrice;
		
		System.out.print("How many appliances do you want to create? ");
		toCreate = keyIn.nextInt();
		if (inventory.length < toCreate + inventoryCount) { //if appliances to be created will exceed maximum inventory size
			System.out.print("That exceeds the maximum inventory size! The most you can create is " + (inventory.length - inventoryCount) + ".");
		} else {
			while (created < toCreate) { //while there are still some appliances to be made
				System.out.print("Please enter a Type: ");
				tempType = keyIn.next();
				System.out.print("Please enter a Brand: ");
				tempBrand = keyIn.next();
				System.out.print("Please enter a Price: ");
				tempPrice = keyIn.nextDouble();
				inventory[inventoryCount] = new Appliance(Appliance.Type.valueOf(tempType), tempBrand, tempPrice);
				inventoryCount++;
				created++;
			}
		}
	}
	
	/**Menu Option 2 edits an appliance given the inventory and a valid serial number
	 * @param inventory Array of appliances including appliance to be edited
	 * @param serialNumber Serial number of appliance to be edited
	 * @param keyIn Used for inputs
	 */
	public static void editAppliance(Appliance[] inventory, long serialNumber, Scanner keyIn) {
		int i = getIndexOfAppliance(inventory, serialNumber); //index of appliance in the inventory array
		int choice = 0;
		do {
			System.out.print("\n" + inventory[i].toString() + "Which information would you like to change?\n"
					+ "   1.\tType\n"
					+ "   2.\tBrand\n"
					+ "   3.\tPrice\n"
					+ "   4.\tQuit\n"
					+ "Enter your choice > ");
			choice = validateInput(4, keyIn);
			switch (choice) {
			case (1): //set new Type
				inventory[i].setType(validateType(keyIn)); //type needs to be validated first
				break;
			case (2): //set new Brand
				System.out.print("Please enter a Brand: ");
				inventory[i].setBrand(keyIn.next());
				break;
			case (3): //set new Price
				System.out.print("Please enter a Price: ");
				inventory[i].setPrice(keyIn.nextDouble());
				break;
			case (4): //Quit to main menu
				System.out.println("Returning to main menu.");
				break;
			default: //displays message only if no valid option was given
				System.out.print("Please try again. Enter your choice > ");
			}
		} while (choice != 4);
	}
	/**Prints main menu to console
	 */
	public static void displayMainMenu() {
		System.out.print("\nWhat do you want to do?\n"
				   + "   1.\tEnter new appliances (password required)\n"
				   + "   2.\tChange information of an appliance (password required)\n"
				   + "   3.\tDisplay all appliances by a specific brand\n"
				   + "   4.\tDisplay all appliances under a certain a price\n"
				   + "   5.\tQuit\n"
				   + "Please enter your choice > ");
	}
	
	/**Gets the index of an appliance in a array for usage
	 * Returns -1 if serial number of appliance does not exist in the inventory
	 * @param a Appliance[] to be searched through
	 * @param serial Serial number to be used for search
	 * @return index of Appliance in an Appliance[] of a serial number. If serial number doesnt exist then return -1
	 */
	public static int getIndexOfAppliance(Appliance[] a, long serial) {
		for(int i = 0; i < Appliance.getNumberOfInstances(); i++)
			if (a[i].getSerialNumber() == (long) serial)
				return i;
		return -1;
	}
}
