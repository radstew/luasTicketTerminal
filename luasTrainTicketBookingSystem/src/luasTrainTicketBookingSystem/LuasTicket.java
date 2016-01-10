package luasTrainTicketBookingSystem;

import java.util.Scanner;

public class LuasTicket {
	
	static Scanner input = new Scanner(System.in);
	
	// Transactions Record
	static int numberOfTransactions;
	static int numberOfTicketsSold;
	static double totalCashEntered;
	static double averageCashPerTransaction;
	static double currentCashEntered;
	static double currentTransactionPrice;
	static double tempPrice;
	static int currentNumberOfTickets;
	static double balanceRemining;
	static double customerChange;

		
	// Ticket prices
	static double zoneOne = 1.9;
	static double zoneTwo = 2.3;
	static double zoneThree = 2.7;
	static double zoneFour = 2.9;
	static double zoneFiveToEight = 3.1;
	
	// Ticket types
	static double returnTicket =  1.8;
	
	static double studentTicket = 0.8;
	static double childTicket = 0.5;
	
	// admin data
	static String currentLuasStop = "Milltown";
	static String adminPassword = "1234"; 
	
	// test data
	static String testOutput;
	static String testResult;
	static int loopsNum;
	static boolean [] testBooleanOutput;
	static String [] testStringInput;

	
	/** helper function to check if character/s in user input are in correct format
	 * returns true if all characters in user input are either integer or dot 
	 * dot is not first character and all dot's are separated with one or more integers
	 * return false otherwise 
	 * 
	 * (String) -> (boolean)
	 * 
	 * correctFormat(234.5)
	 * >>> true
	 * 
	 * correctFormat(12)
	 * >>> true
	 * 
	 * correctFormat(abc)
	 * >>> false
	 */
	static boolean correctFormat(String userInput) {
		int loopLength = userInput.length();
		char dotChar = '.';
		int dotIndx = 0;
		
		for (int charIndx = 0; charIndx < loopLength; charIndx++) {	
			char charInput = userInput.charAt(charIndx);
			if (!Character.isDigit(charInput) && charInput != dotChar) {
				return false;
			}
			else if (charInput == dotChar) {
				if ((charIndx - dotIndx) < 2 && charIndx != 1) {
					return false;
				}
				else {
					dotIndx = charIndx;
				}
			}
		}
		return true;
	}
	
	/** helper function to check if inserted amount of money is in correct format 
	 * returns true if decimal is maximum at dec argument and false otherwise
	 * 
	 * (String, int) -> (boolean)
	 * 
	-- in this case maximum 2 decimal places --
	
	isMaxDecimal("123.23", 2)
	>>> true
	
	isMaxDecimal("2.123.23", 2)
	>>> true
	
	isMaxDecimal("1.2323", 2)
	>>> false
	*/
	static boolean isMaxDecimal(String userInput, int dec) {
		int loopLength = userInput.length();
		char testChar = '.';
		int decimalPlaces = dec;
		
		for (int charIndx = (loopLength -1); charIndx >= 0; charIndx--) {
			char charInput = userInput.charAt(charIndx);

			if (charInput == testChar) {
				if (decimalPlaces >= 0) {
					return true;
				}
				else {
					return false;
				}
			}
			decimalPlaces -= 1;
		}
		return true;
	}

	/** Reset single transaction values, print welcome message and continue to ticket option
	 * or to admin menue which is hidden and secured by password 
	 * Enter "admin" at main screen for admin menue
	 * password: 1234 
	 */
	static void mainScreen() {
		currentCashEntered = 0;
		tempPrice = 0;
		currentTransactionPrice = 0;
		currentNumberOfTickets = 0;
		averageCashPerTransaction = (totalCashEntered / numberOfTransactions);
		
		System.out.println("  █▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀█");
		System.out.println("  █░░╦─╦╔╗╦─╔╗╔╗╔╦╗╔╗░░█");
		System.out.println("  █░░║║║╠─║─║─║║║║║╠─░░█");
		System.out.println("  █░░╚╩╝╚╝╚╝╚╝╚╝╩─╩╚╝░░█");
		System.out.println("  █▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄█");
		
		System.out.println("  at " + currentLuasStop + " Luas Stop");
		System.out.println("Press any key to continue \n ");
		String userChoice = input.next();
		
		switch(userChoice) {
			case "admin":{
				System.out.println("Enter password: ");
				String password =  input.next();
				
				switch(password) {
					case "1234": {
						adminMenue();
						break;
					}
					default: {
						System.out.println("wrong password \n access denied \n");
						mainScreen();
						break;
					}
				}
			}
			default:{
				buyTicketZone();
				break;
			}
		}
	}
	
    /**Customer choose the zone where want travel to,
     *  price for chosen zone is added to transaction price and continue to ticket option
     *  print message if chosen zone does not exist in the system
     */
	static void buyTicketZone() {
		System.out.println("==========================================");
		System.out.println(" Choose your destination: \n    Enter 1 for zone 1 \n    Enter 2 for zone 2 \n "
				+ "   Enter 3 for zone 3 \n    Enter 4 for zone 4 \n    Enter 5 for zone 5, 6, 7 or 8  ");
		System.out.println("==========================================");
		String customerDestination = input.next();
		
		switch(customerDestination) {
			case "1": {
				tempPrice += zoneOne;
				break;
			}
			case "2": {
				tempPrice += zoneTwo;
				break;
			}
			case "3": {
				tempPrice += zoneThree;
				break;
			}
			case "4": {
				tempPrice += zoneFour;
				break;
			}
			case "5": {
				tempPrice += zoneFiveToEight;
				break;
			}
			default:{
				System.out.println("Wrong input, try again");
				buyTicketZone();
				break;
			}
		}
		buyTicketSingleOrReturn();
	}	
	
	/** Customer choose the single or return ticket,
     *  price is calculated and added to transaction price and continue to ticket option
     *  print message if chosen ticket type does not exist in the system
     */
	static void buyTicketSingleOrReturn() {
		System.out.println("==========================================");
		System.out.println("   Choose ticket type: \n Enter 1 for Singe Ticket \n Enter 2 for Return Ticket");
		System.out.println("==========================================");
		String singeOrReturnTicket = input.next();
		
		switch (singeOrReturnTicket) {
			case "1": {
				buyTicketType();
				break;
			}
			case "2": {
				tempPrice *= returnTicket;
				buyTicketType();
				break;
			}
			default :{
				System.out.println("******************************************");
				System.out.println("Wrong input, try again");
				System.out.println("******************************************");
				buyTicketSingleOrReturn();
				break;
			}
		}
	}
	
	/** Customer choose one of  the ticket type (Adult, Student, Child),
	 *  price is calculated and added to transaction price and continue to number of ticket option
	 *  print message if chosen ticket type does not exist in the system
	*/
	static void buyTicketType() {
		System.out.println("==========================================");
		System.out.println("   Choose ticket type: \n "
				+ "Enter 1 for Adult Ticket \n Enter 2 for Student Ticket \n Enter 3 for Child Ticket");
		System.out.println("==========================================");
		String ticketType = input.next();
		
		switch(ticketType) {
			case "1" :{
				buyTicketQuantity();
				break;
			}
			case "2" :{
				tempPrice *= studentTicket;
				buyTicketQuantity();
				break;
			}
			case "3" :{
				tempPrice *= childTicket;
				buyTicketQuantity();
				break;
			}
			default:{
				System.out.println("******************************************");
				System.out.println("Wrong input, try again");
				System.out.println("******************************************");
				buyTicketType();
				break;
			}
		}
	}
	
	/** Customer choose the number of tickets,
     *  price is calculated and added to transaction price and continue to Finish Transaction Screen
     *  print message if chosen number can not be calculated 
	 */
	static void buyTicketQuantity() {
		System.out.println("==========================================");
		System.out.println(" Enter number of tickets \n     you want to buy: ");
		System.out.println("==========================================");
		String numberOfTickets = input.next();
				
		if (correctFormat(numberOfTickets) && isMaxDecimal(numberOfTickets, 0)) {
			int convertedNumberOfTickets = Integer.parseInt(numberOfTickets);
			tempPrice *= convertedNumberOfTickets;	
			currentNumberOfTickets += convertedNumberOfTickets;
			finishTransaction();
		}
		else {
			System.out.println("******************************************");
			System.out.println("Wrong input, try again");
			System.out.println("******************************************");
			buyTicketQuantity();
		}
	}
	
	/** Print information for customer
	 * Customer can choose finish transaction or add more ticket/s
	 * also customer can cancel transaction and back to main screen
	 * print message if customer choice is not in the system options
	 */ 
	static void finishTransaction() {
		System.out.println("==========================================");
		System.out.println(" Enter 1 to finish transaction \n "
				+ "Enter 2 if you wish to add any ticket/s \n "
				+ "Enter 3 to cancel and back to Main Screen");
		System.out.println("==========================================");
		String customerChoice = input.next();		
		
		switch(customerChoice) {
			case "1":{
				validateCash();
				break;
			}
			case "2":{
				currentTransactionPrice += tempPrice;
				tempPrice = 0;
				buyTicketZone();
				break;
			}
			case "3":{
				System.out.println("******************************************");
				System.out.println("Transaction Canceled");
				System.out.println("******************************************");
				mainScreen();
				break;
			}
			default:{
				System.out.println("******************************************");
				System.out.println("Wrong input, try again");
				System.out.println("******************************************");
				finishTransaction();
				break;
			}
		}
	}
		
	/** Calculate and print balance of transaction and check if customer insert is in correct format
	 * Print message if insert is in wrong format and back to main screen when transaction is finish
	 */
	static void validateCash() {
		currentTransactionPrice += tempPrice;
		tempPrice = 0;
//		currentTransactionPrice = Math.round(currentTransactionPrice * 100) / 100.00;
		while (currentCashEntered < currentTransactionPrice) {
			balanceRemining = Math.round((currentTransactionPrice - currentCashEntered) * 100) / 100.00;
			System.out.println("==========================================");
			System.out.println("Insert remining balance: \n " + balanceRemining);
			System.out.println("==========================================");
			String  insert = input.next();
			
			if(correctFormat(insert) && isMaxDecimal(insert, 2)) {
				double convertedInsert = Double.parseDouble(insert);
				currentCashEntered += convertedInsert;
			}
			else {
				System.out.println("******************************************");
				System.out.println(
						"Wrong currency! \n inserted value must be numeric \n and maximum of two decimal places.");
				System.out.println("******************************************");
				validateCash();
			}
		}
			
		String finishTransactionMessage = ".";
			if  (currentCashEntered > currentTransactionPrice) {
				customerChange = Math.round((currentCashEntered - currentTransactionPrice) * 100) / 100.00;
				finishTransactionMessage = " and " + customerChange + " change.";
			}
		System.out.println("Pickup your " + currentNumberOfTickets + " ticket/s" + finishTransactionMessage  + 
				"\n    Have nice Trip \n ");
			
		numberOfTransactions += 1;
		numberOfTicketsSold += currentNumberOfTickets;
		totalCashEntered += currentTransactionPrice;
		mainScreen();
	}
	
	static void adminMenue() {
		System.out.println("******************************************");
		System.out.println("Number of transactions in this machine: " + numberOfTransactions); 
		System.out.println("Total ticket sold in this machine: " + numberOfTicketsSold);
		System.out.println("Total cash entered into this machine: " + totalCashEntered);
		System.out.println("Average cah per transaction: " + averageCashPerTransaction);
		System.out.println("****************************************** \n ");
		System.out.println("******************************************");
		System.out.println(" Enter t for system tests \n "
				+ " any letter to back to main screen");
		System.out.println("****************************************** \n ");
		
		String userInput = input.next();

		switch (userInput) {
			case "t": {
			testSuite();
				break;
			}
			default:{
				mainScreen();	
				break;
			}
		}		
	}
	
	
	public static void main(String[] args) {

		mainScreen();
	}
	
//	Test Suite 
	static void testSuite() {
		
//		Test helper function correctFormat()
		testStringInput = new String[] {"234.5", "12", "abc"};
		testBooleanOutput = new boolean[] {true, true, false};
		loopsNum = testStringInput.length;
		System.out.println("******************************************");
		for(int listIndx = 0; listIndx < loopsNum; listIndx++) {
			if (correctFormat(testStringInput[listIndx]) == testBooleanOutput[listIndx]) {
				testOutput = "pass";
			}
			else {
				testOutput = "fail. Expected: " + testBooleanOutput[listIndx] + " / outcome: " + correctFormat(testStringInput[listIndx]);
			}
			System.out.println("Result for correctFormat(" + testStringInput[listIndx] + ") test: " + testOutput);
		}	
		System.out.println("");
		
//		Test helper function isMaxDecimal() for max 2 decimal places
		testStringInput = new String[] {"123.23", "2.123.23", "1.2323"};
		testBooleanOutput = new boolean[] {true, true, false};
		loopsNum = testStringInput.length;
		
		for(int listIndx = 0; listIndx < loopsNum; listIndx++) {
			if (isMaxDecimal(testStringInput[listIndx], 2) == testBooleanOutput[listIndx]) {
				testOutput = "pass";
			}
			else {
				testOutput = "fail. Expected: " + testBooleanOutput[listIndx] + " / outcome: " + isMaxDecimal(testStringInput[listIndx], 2);
			}
			System.out.println("Result for isMaxDecimal(" + testStringInput[listIndx] + ") test: " + testOutput);
		}	
		System.out.println("****************************************** \n ");
		mainScreen();
	}
	
}


