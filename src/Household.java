import java.math.BigDecimal;

//Households are the primary units of consumption in an economy
public class Household implements MoneyHolder {
	private String name;
	private BigDecimal liquidity; // cash on hand
	private Bank bank; // the bank at which this person has an account
	private int bankAccountID; // ID to access Bank account
	private Job job = null; //this household's source of income - will be set when job is created
	private HouseholdDemand demand = null; //manages demand for all goods - will be set when job is set

	public Household(String name, int liquidity, Bank bank, int bankAccID) {
		this.name = name;
		this.liquidity = new BigDecimal(liquidity);
		this.bank = bank;
		bankAccountID = bankAccID;
	}
	
	//buy a food item and transfer the money to the company
	//all food bought in a month is eaten, and enough food must be bought to meet food point requirements
	public void buyFood(AgricultureFirm firm, int quantity) {
		FoodItem product = firm.getProduct();
		BigDecimal price = product.getPrice(quantity);
		firm.addMoney(price);
		subtractMoney(price);
		Log.writeHouseholdLog("The " + name + " household paid " + firm.getName() + " $" + price + " for " + quantity + " " + product.getUnits() + " of " + product.getName() + ". (" + quantity * product.getFoodPts() + " food points)");
	}
	
	//buy any item and transfer the money to the company
	//all goods bought are consumed, and enough goods must be bought to meet food requirements
	public void buy(Firm firm, int quantity) {
		Good product = firm.getProduct();
		BigDecimal price = product.getPrice(quantity);
		firm.addMoney(price);
		subtractMoney(price);
		Log.writeHouseholdLog("The " + name + " household paid " + firm.getName() + " $" + price + " for " + quantity + " " + product.getUnits() + " of " + product.getName() + ".");
	}
	
	//buy enough food to meet food demand
	//based on prices & preferences
	public void buyAllFood(AgricultureFirm[] foodFirms) {
		int[] quantities = demand.monthlyFoodDemand(foodFirms);
		
		for(int i=0; i<foodFirms.length; i++) {
			buyFood(foodFirms[i], quantities[i]);
		}
	}
	
	public void buyClothing(Firm clothingFirm) {
		int q = demand.clothingDemand();
		buy(clothingFirm, q);
	}
	
	public void buyOtherGoods(Firm[] firms) {
		int q = demand.otherDemand();
		for(int i=0; i<firms.length; i++) {
			buy(firms[i], q);
		}
	}

	public void addMoney(BigDecimal amount) {
		liquidity = liquidity.add(amount);
	}
	
	//subtract money from liquidity, and throw an exception if we try to take more money than we have on hand
	public void subtractMoney(BigDecimal amount) throws OutOfMoneyException {
		if(amount.compareTo(liquidity) == 1) {
			throw new OutOfMoneyException("The " + name + " household tried to spend more money than it has!");
		}
		liquidity = liquidity.subtract(amount);
	}

	public String getName() {
		return name;
	}

	// transfer some amount of money from liquidity to the bank account
	public void depositMoney(BigDecimal amount) throws OutOfMoneyException {
		if(amount.compareTo(liquidity) == 1) throw new OutOfMoneyException("The " + name + " household tried to deposit more money than it has!");
		subtractMoney(amount);
		bank.deposit(amount, bankAccountID);
		Log.writeBankLog("The " +  name + " household deposited $" + amount + " at the bank.");
	}

	// transfer some amount of money from liquidity to the bank account
	public void depositMoney(double amnt) throws OutOfMoneyException {
		BigDecimal amount = new BigDecimal(amnt);
		if(amount.compareTo(liquidity) == 1) throw new OutOfMoneyException("The " + name + " household tried to deposit more money than it has!");
		subtractMoney(amount);
		bank.deposit(amount, bankAccountID);
		Log.writeBankLog("The " + name + " household deposited $" + amount + " at the bank.");
	}
	
	// transfer some amount of money from the bank account to liquidity
		public void withdrawMoney(BigDecimal amount) throws OutOfMoneyException {
			if(amount.compareTo(getBankValue()) == 1) throw new OutOfMoneyException("The " + name + " household tried to withdraw more money than it has!");
			addMoney(amount);
			bank.withdraw(amount, bankAccountID);
			Log.writeBankLog("The " + name + " household withdrew $" + amount + " from the bank.");
		}
	
	public BigDecimal getLiquidity() {
		return liquidity;
	}
	
	public BigDecimal getBankValue() {
		return bank.getBalance(bankAccountID);
	}
	
	//wealth = liquidity + money in bank
	public BigDecimal getWealth() {
		return getLiquidity().add(getBankValue());
	}
	
	public BigDecimal getMonthlyIncome() {
		return job.getMonthlyPayment();
	}
	
	public void setJob(Job j) {
		job = j;
		demand = new HouseholdDemand(this);
	}

	public void monthlyActions() {
		// a household's monthly actions:
		// -receive wages (this is handled by the Firms)
		// -buy food, & other necessities
		// -deposit or withdraw money at the bank so that liquidity is equal to 6 months' income (not in this method because it must happen last)
		// -set demand based on wealth, income, prices (this is handled by the HouseholdDemand)
		
		depositOrWithdraw();
		buyAllFood(Manager.getFoodFirms());
		buyClothing(Manager.getClothingFirm());
		buyOtherGoods(Manager.getOtherFirms());
	}
	
	// deposit or withdraw money at the bank so that liquidity is equal to 6 months' income
	public void depositOrWithdraw() {
		BigDecimal target = getMonthlyIncome().multiply(new BigDecimal(6));
		if(liquidity.compareTo(target) == 1) {
			depositMoney(liquidity.subtract(target));
		} else {
			withdrawMoney(target.subtract(target.subtract(liquidity)));
		}
	}

	@Override
	public BigDecimal getMoney() {
		return getWealth();
	}

}
