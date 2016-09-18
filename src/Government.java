import java.math.BigDecimal;
import java.math.RoundingMode;

//Government - taxes the rich (mainly the firms) and redistributes to the poor
//so that the poor don't run out of money and the economy
//is sustainable permanently
public class Government implements MoneyHolder {
	private BigDecimal treasury;
	
	Government(int treasury) {
		this.treasury = new BigDecimal(treasury);
		this.treasury = BigDecimal.ZERO;
	}
	
	public void monthlyActions() {
		Firm[] firms = Manager.getFirms();
		Household[] households = Manager.getHouseholds();
		
		for(int i=0; i<firms.length; i++) {
			tax(firms[i]);
		}
		
		distribute(households);
	}
	
	public void addMoney(BigDecimal amount) {
		treasury = treasury.add(amount);
	}
	
	//money is redistributed progressively based on households' wealth
	//i.e., less wealth = higher welfare
	private void distribute(Household[] households) {
		BigDecimal invWealthSum = BigDecimal.ZERO;
		BigDecimal[] percentages = new BigDecimal[households.length];
		BigDecimal total = treasury;
		
		for(int i=0; i<households.length; i++) {
			invWealthSum = invWealthSum.add(BigDecimal.ONE.divide(households[i].getWealth(), 20, RoundingMode.HALF_UP));
		}
		for(int i=0; i<percentages.length; i++) {
			percentages[i] = BigDecimal.ONE.divide(households[i].getWealth(), 20, RoundingMode.HALF_UP).divide(invWealthSum, 5, BigDecimal.ROUND_HALF_UP);
			int amount = (int) Math.floor(total.multiply(percentages[i]).doubleValue());
			households[i].addMoney(new BigDecimal(amount));
			subtractMoney(new BigDecimal(amount));
			Log.writeGovernmentLog("The government paid $" + amount + " of welfare to the " + households[i].getName() + " household.");
		}
	}
	
	public void subtractMoney(BigDecimal amount) throws OutOfMoneyException {
		if(amount.compareTo(treasury) == 1) throw new OutOfMoneyException("The government tried to spend more money than it has!");
		treasury = treasury.subtract(amount);
	}
	
	//firms are taxed 25% of profits and 5% of assets
	//therefore firms are allowed to profit until 2% of 
	//their assets is worth more than 75% of their monthly profits
	private void tax(Firm firm) {
		BigDecimal taxAmount = firm.getProfits().multiply(new BigDecimal("0.20"));
		taxAmount = taxAmount.add(firm.getAssets().multiply(new BigDecimal("0.02")));
		taxAmount = taxAmount.setScale(2, RoundingMode.HALF_UP); //limit precision
		firm.subtractMoney(taxAmount);
		addMoney(taxAmount);
		Log.writeGovernmentLog("The government took $" + taxAmount + " in taxes from " + firm.getName() + ".");
	}

	@Override
	public BigDecimal getMoney() {
		return treasury;
	}
}
