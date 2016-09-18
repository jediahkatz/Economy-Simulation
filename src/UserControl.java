import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

//allows the user to increment the month, choose to write the log to a file,
//see every Agent's wealth, 
public class UserControl {

	private UserControl() {
		// this class cannot be instantiated, and its methods are all static
	}

	public static void begin() {
		InputStreamReader inputStream = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(inputStream);

		System.out.println(
				"Welcome to this economic model! Type START to simulate the first month.\nThe log of all actions will be printed to the console.");

		try {
			while (!reader.readLine().toLowerCase().equals("start")) {
				// cannot advance until "start" is typed
			}
		} catch (IOException e) {
			System.out.println("Sorry, an error occurred. Please re-run the program.");
			System.exit(0); // just quit the program in the case of an IOException
		}
	}

	// prompt either: next month, write to a file, view households' wealth, quit simulation
	public static boolean prompt() {
		InputStreamReader inputStream = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(inputStream);

		System.out.println(
				"\nType NEXT to advance to the next month.\nType SAVE to save the entire log to a text file.\nType WEALTH to view the wealth of all actors.\nType EXIT to quit the simulation.");
		boolean goodValue = false;

		while (!goodValue) {
			String input = null;
			try {
				input = reader.readLine();
			} catch (IOException e) {
				System.out.println("Sorry, an error occurred. Please re-run the program.");
				System.exit(0); // just quit the program in the case of an IOException
			}

			switch (input.toLowerCase()) {
			case "save": //write log to file
				saveLog(reader);
				return prompt();
				
			case "wealth": //see list of everyone's wealth
				getWealth();
				return prompt();
				
			case "exit": //exit program
				return false;
				
			case "next": //continue simulation for a month
				return true;
				
			default:
				break;
			} //end of switch statement
		} //end of while loop
		
		return true;
	} //end of prompt() method
	
	//write the entire log to a file
	private static void writeLog(String filename, int whichLog) {
		FileOutputStream file = null;
		try {
			file = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			System.out.println("Sorry, an error occurred. Please re-run the program.");
			System.exit(0); // just quit the program in the case of an IOException
		}
		PrintWriter writer = new PrintWriter(file, true);
		ArrayList<String> log;
		
		switch(whichLog) {
		case 0:
			log = Log.getLog();
			break;
		case 1:
			log = Log.getHouseholdLog();
			break;
		case 2: 
			log = Log.getFirmLog();
			break;
		case 3:
			log = Log.getGovLog();
			break;
		case 4:
			log = Log.getBankLog();
			break;
		case 5:
			log = Log.getMonthLog();
			break;
		default:
			log = Log.getLog();
		}
		
		
		for(String s : log) {
			writer.println(s);
		}
		
		try {
			file.close();
		} catch (IOException e) {
			System.out.println("Sorry, an error occurred. Please re-run the program.");
			System.exit(0); // just quit the program in the case of an IOException
		}
	}
	
	private static void saveLog(BufferedReader reader) {
		boolean fileExists = false;
		String input = null;
		
		System.out.println("Which log would you like to save?\nYou can type \"households\", \"firms\", \"government\", \"bank\", \"all\", or \"last month\".");
		boolean goodValue = false;
		int whichLog = 0;

		while (!goodValue) {
			try {
				input = reader.readLine();
			} catch (IOException e) {
				System.out.println("Sorry, an error occurred. Please re-run the program.");
				System.exit(0); // just quit the program in the case of an IOException
			}

			switch (input.toLowerCase()) {
			case "all": 
				whichLog = 0; goodValue = true;
				break;
			case "households": 
				whichLog = 1; goodValue = true;
				break;
			case "firms":
				whichLog = 2; goodValue = true;
				break;
			case "government":
				whichLog = 3; goodValue = true;
				break;
			case "bank":
				whichLog = 4; goodValue = true;
				break;
			case "last month":
				whichLog = 5; goodValue = true;
				break;
			default:
				break;
			} //end of switch statement
		} //end of while loop
		
		System.out.println("Please enter the full filepath of your text file.\nIf it does not exist, it will be created.\nWARNING: EXTANT DATA ON THE FILE WILL BE ERASED.");
		while (!fileExists) {
			try {
				input = reader.readLine();
			} catch (IOException e) {
				System.out.println("Sorry, an error occurred. Please re-run the program.");
				System.exit(0); // just quit the program in the case of an IOException
			}

			try {
				writeLog(input, whichLog);
				fileExists = true;
			} catch (Exception e) {
				System.out.println("Sorry, that was an invalid file or it could not be created. Please try again!");
			}
		} //end of while loop
	} //end of saveLog() method
	
	private static void getWealth() {
		Household[] households = Manager.getHouseholds();
		Firm[] firms = Manager.getFirms();
		Government gov = Manager.getGov();
		
		for(Household h : households) {
			System.out.println("The " + h.getName() + " household has $" + h.getLiquidity() + " on hand and $" + h.getBankValue() + " in the bank.");
		}
		for(Firm f : firms) {
			System.out.println(f.getName() + " has $" + f.getMoney() + " in assets.");
		}
		System.out.println("The government has $" + gov.getMoney() + " in the treasury.");
	}
}
