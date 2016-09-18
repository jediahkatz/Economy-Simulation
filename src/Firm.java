import java.math.BigDecimal;
import java.util.ArrayList;

//Firms are the primary units of production in an economy
//e.g. a shop, a farm, a factory
//Firms hold their own wealth - do not use the Bank
public class Firm implements MoneyHolder {
	private String name;
	private BigDecimal assets;
	private BigDecimal monthlyRevenue = BigDecimal.ZERO; //keeps track of each month's revenue
	private Good product;
	private ArrayList<Job> jobs = new ArrayList<Job>();
	private BigDecimal monthlyExpenses = BigDecimal.ZERO; //monthly labor costs
	
	public Firm(String name, Good prod, int assets) {
		this.name = name;
		product = prod;
		this.assets = new BigDecimal(assets);
	}
	
	public void addJob(Job j) {
		jobs.add(j);
		monthlyExpenses = BigDecimal.ZERO;
		for(Job job : jobs) {
			monthlyExpenses = monthlyExpenses.add(job.getMonthlyPayment());
		}
	}
	
	public String getName() {
		return name;
	}
	
	public BigDecimal getRevenue() {
		return monthlyRevenue;
	}
	
	//return the product sold by this firm (and its attributes)
	public Good getProduct() { 
		return product;
	}
	
	public void monthlyActions() {
		payEmployees();
		monthlyRevenue = BigDecimal.ZERO;
	}

	public void payEmployees() { //pay each employee & subtract from Firm assets
		for(Job j : jobs) {
			j.pay();
			subtractMoney(j.getMonthlyPayment());
			Log.writeFirmLog(name + " paid the " + j.getEmployee().getName() + " household $" + j.getMonthlyPayment() + " for " + j.getHours() + " hours of work.");
		}
	}
	
	public BigDecimal getProfits() {
		return monthlyRevenue.subtract(monthlyExpenses);
	}
	
	public void addMoney(BigDecimal amount) {
		assets = assets.add(amount);
		monthlyRevenue = monthlyRevenue.add(amount);
	}
	
	public void subtractMoney(BigDecimal amount) throws OutOfMoneyException {
		if(amount.compareTo(assets) == 1) throw new OutOfMoneyException(name + " tried to spend more money than it has!");
		assets = assets.subtract(amount);
	}
	
	public BigDecimal getAssets() {
		return assets;
	}

	@Override
	public BigDecimal getMoney() {
		return assets;
	}


}
