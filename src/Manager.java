import java.math.BigDecimal;

//contains lists of all firms and households, for consumer & taxation purposes
//sets up all firms, households, government, etc. and creates jobs
public class Manager {
	private static final Time date = new Time(2000);
	
	private static AgricultureFirm[] foodFirms = new AgricultureFirm[7];
	private static Firm clothingFirm;
	private static Firm[] otherFirms = new Firm[2];
	private static Firm[] allFirms = new Firm[10];
	private static Household[] households = new Household[50];
	private static Job[] jobs = new Job[50];
	private static Bank bank;
	private static Government gov;
	
	public static void main(String[] args) {
		Manager.createActors();
		UserControl.begin();
		
		do {
			Log.clearMonthLog();
			Manager.monthlyActions();
			Log.printLog();
		} while(UserControl.prompt());
		
		System.out.println("Thanks for testing this economic model!");
			
	}
	
	private Manager() {
		//this class cannot be instantiated, and its fields & methods are all static
	}
	
	public static Government getGov() {
		return gov;
	}
	
	public static Household[] getHouseholds() {
		return households;
	}
	
	//create all firms, households, etc
	public static void createActors() {
		Bank b = new Bank(50);
		bank = b;
		Government g = new Government(000000); //the government has a lot of money!
		gov = g;
		createHouseholds(bank);
		createFirms();
		createJobs(households, allFirms);
	}
	
	//do EVERYONE's monthly actions
	public static void monthlyActions() {
		
		Log.writeDate(" - " + date.getDate() + " - ");
		
		for(int i=0; i<households.length; i++) {
			households[i].monthlyActions();
		}
		
		gov.monthlyActions();
		
		for(int i=0; i<allFirms.length; i++) {
			allFirms[i].monthlyActions();
		}
		
		date.incrementMonth();
	}
	
	//create food and other firms and put them in their respective arrays
	private static void createFirms() {
		createFoodFirms();
		createOtherFirms();
		for(int i=0; i<7; i++) {
			allFirms[i] = foodFirms[i];
		}
		for(int i=7; i<9; i++) {
			allFirms[i] = otherFirms[i-7];
		}
		allFirms[9] = clothingFirm;
	}
	
	//create all jobs, assign them to households, and assign them to firms
	private static void createJobs(Household[] h, Firm[] firms) {
		for(int i=0; i<50; i++) {
			jobs[i] = new Job(h[i], new BigDecimal(8 + 0.5 * i), new BigDecimal(120));
			firms[i % 10].addJob(jobs[i]);
			h[i].setJob(jobs[i]); //let the households point to the jobs
		}
	}
	
	private static void createHouseholds(Bank bank) {
		//just a long list of names for my households, taken from wikipedia
		String[] householdNames = {"Smith","Johnson","Williams","Brown","Jones","Miller","Davis","Garcia","Rodriguez","Wilson","Martinez","Anderson","Taylor","Thomas","Hernandez","Moore","Martin","Jackson","Thompson","White","Lopez","Lee","Gonzalez","Harris","Clark","Lewis","Robinson","Walker","Perez","Hall","Young","Allen","Sanchez","Wright","King","Scott","Green","Baker","Adams","Nelson","Hill","Ramirez","Campbell","Mitchell","Roberts","Carter","Phillips","Evans","Turner","Torres"};
		
		for(int i=0; i<50; i++) {
			households[i] = new Household(householdNames[i], 100000 + 2000 * i, bank, i);
		}
	}
	
	public static AgricultureFirm[] getFoodFirms() {
		return foodFirms;
	}
	
	public static Firm[] getFirms() {
		return allFirms;
	}
	
	public static Firm[] getOtherFirms() {
		return otherFirms;
	}
	
	public static Firm getClothingFirm() {
		return clothingFirm;
	}
	
	//create all non-food Goods and their respective Firms
	private static void createOtherFirms() {
		Good water = new Good("water", "2", "gallons");
		Good energy = new Good("energy", "2.5", "kilowatt hours");
		Good clothing = new Good("clothing", "50", "items");
		Firm waterFirm = new Firm("Town Water", water, 830000);
		Firm energyFirm = new Firm("ElectriCo", energy, 970000);
		Firm clothes = new Firm("JVM Clothing Co.", clothing, 410000);
		Firm[] firms = {waterFirm, energyFirm};
		otherFirms = firms;
		clothingFirm = clothes;
	}
	
	//create all FoodItems and their respective AgricultureFirms
	private static void createFoodFirms() {
		FoodItem corn = new FoodItem("corn", "3.2", "ears", 2);
		FoodItem bread = new FoodItem("bread", "8", "loaves", 4);
		FoodItem lamb = new FoodItem("lamb", "24", "racks", 10);
		FoodItem rice = new FoodItem("rice", "3", "bags", 3);
		FoodItem beef = new FoodItem("beef", "20", "pounds", 9);
		FoodItem bananas = new FoodItem("bananas", "13", "bushels", 7);
		FoodItem waffles = new FoodItem("waffles", "6.5", "stacks", 3);
		AgricultureFirm cornFirm = new AgricultureFirm("Corn Co.", corn, 300000);
		AgricultureFirm breadFirm = new AgricultureFirm("Panera Bread", bread, 350000);
		AgricultureFirm lambFirm = new AgricultureFirm("Sal's Cuts", lamb, 500000);
		AgricultureFirm riceFirm = new AgricultureFirm("Empire Wok", rice, 370000);
		AgricultureFirm beefFirm = new AgricultureFirm("Jim's Steaks", beef, 460000);
		AgricultureFirm bananasFirm = new AgricultureFirm("United Fruit", bananas, 510000);
		AgricultureFirm cheeseFirm = new AgricultureFirm("Wafels & Dinges", waffles, 410000);
		AgricultureFirm[] f = {cornFirm, breadFirm, lambFirm, riceFirm, beefFirm, bananasFirm, cheeseFirm};
		foodFirms = f;
	}
}