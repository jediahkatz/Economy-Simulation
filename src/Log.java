import java.util.ArrayList;

//The Log class keeps track of every activity that occurs in the economic model
//there are separate logs for each facet of the economy <--- unused
//and one big log that has everything
public class Log {
	private static ArrayList<String> householdLog = new ArrayList<String>();
	private static ArrayList<String> firmLog = new ArrayList<String>();
	private static ArrayList<String> govLog = new ArrayList<String>();
	private static ArrayList<String> bankLog = new ArrayList<String>();
	private static ArrayList<String> allLog = new ArrayList<String>();
	private static ArrayList<String> monthLog = new ArrayList<String>(); //log just from one month
	
	private Log() {
		//this class cannot be instantiated and its variables and methods are all static
	}
	
	public static ArrayList<String> getLog() {
		return allLog;
	}
	
	public static ArrayList<String> getHouseholdLog() {
		return householdLog;
	}
	
	public static ArrayList<String> getFirmLog() {
		return firmLog;
	}
	
	public static ArrayList<String> getBankLog() {
		return bankLog;
	}
	
	public static ArrayList<String> getGovLog() {
		return govLog;
	}
	
	public static ArrayList<String> getMonthLog() {
		return monthLog;
	}
	
	public static void clearMonthLog() {
		monthLog.clear();
	}
	
	public static void writeHouseholdLog(String line) {
		householdLog.add(line);
		allLog.add(line);
		monthLog.add(line);
	}
	
	public static void writeFirmLog(String line) {
		firmLog.add(line);
		allLog.add(line);
		monthLog.add(line);
	}
	
	public static void writeGovernmentLog(String line) {
		govLog.add(line);
		allLog.add(line);
		monthLog.add(line);
	}
	
	public static void writeBankLog(String line) {
		bankLog.add(line);
		allLog.add(line);
		monthLog.add(line);
	}
	
	public static void writeDate(String date) {
		householdLog.add(date);
		bankLog.add(date);
		govLog.add(date);
		firmLog.add(date);
		allLog.add(date);
		monthLog.add(date);
	}
	
	public static void printBankLog() {
		for(String s : bankLog) {
			System.out.println(s);
		}
	}
	
	public static void printHouseholdLog() {
		for(String s : householdLog) {
			System.out.println(s);
		}
	}
	
	public static void printFirmLog() {
		for(String s : firmLog) {
			System.out.println(s);
		}
	}
	
	public static void printGovernmentLog() {
		for(String s : govLog) {
			System.out.println(s);
		}
	}
	
	public static void printLog() {
		for(String s : monthLog) {
			System.out.println(s);
		}
	}

}
