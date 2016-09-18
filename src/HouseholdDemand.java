import java.math.BigDecimal;
import java.math.RoundingMode;

//Every household has a HouseholdDemand object
//This object is a math-y class that determines how much
//of each product a household wants in a given month,
//based on income, wealth, preferences, prices, etc.
public class HouseholdDemand {
	private Household household;
	private int foodDemanded; //number of nutrition points demanded per month
	private int clothingDemand;
	private int otherDemand; //number of points for demanding non-food goods (all calculated same way)
	
	public HouseholdDemand(Household h) {
		household = h;
		foodDemanded = calcFoodDemanded();
		otherDemand = calcOtherDemand();
		clothingDemand = calcClothingDemand();
	}
	
	public int clothingDemand() {
		return clothingDemand;
	}
	
	public int otherDemand() {
		return otherDemand;
	}
	
	private int calcOtherDemand() {
		//formula for other demand: D = 1 + ((income/1000) - 0.8)^2 + ((wealth/100000) - 0.5)^2
		//used for water & energy
		int d = (int) (Math.sqrt(household.getWealth().doubleValue()) + Math.sqrt(household.getMonthlyIncome().doubleValue()));
		return d;
	}
	
	//Helper method that calculates the number of food points demanded in a month
	private int calcFoodDemanded() {
		//formula for food points demanded: FP = 100log(total_wealth) + 19.75sqrt(monthly_income) + random [300,500]
		int fp = (int) (100*Math.log10(household.getWealth().doubleValue()) + 19.75*Math.sqrt(household.getMonthlyIncome().doubleValue()) + 201*Math.random() + 300);
		return fp;
	}
	
	private int calcClothingDemand() {
		//clothing demand = 1 + ((income/1000) - 0.8)^3  rounded down
		int demand = 1 + (int) (Math.floor(household.getMonthlyIncome().divide(new BigDecimal("1000")).subtract(new BigDecimal("0.8")).pow(3).doubleValue()));
		return demand;
	}
	
	//Takes in a list of all food-producing firms to choose from
	//Returns an int array with the number of units of food to buy from each firm
	//Indexes in the final array correspond to the indexes of firms in the argument array
	public int[] monthlyFoodDemand(AgricultureFirm[] foodFirms) {
		foodDemanded = calcFoodDemanded();
		
		//how I calculate the amounts to buy:
		//first, for each food item, I calculate the price per food point of each item
		//then, I sum the inverse (1/x) of all those prices and find what percent of the sum each price is
		//I then get each item to satisfy that percent of the total food point requirement
		
		BigDecimal pricePerPointSum = BigDecimal.ZERO;
		for(int i=0; i<foodFirms.length; i++) {
			pricePerPointSum = pricePerPointSum.add(BigDecimal.ONE.divide(foodFirms[i].getProduct().getPricePerPoint(), 5, RoundingMode.HALF_UP));
		}
		
		int[] quantitiesToBuy = new int[foodFirms.length];
		
		for(int i=0; i<foodFirms.length; i++) {
			BigDecimal percentToBuy = BigDecimal.ONE.divide(foodFirms[i].getProduct().getPricePerPoint(), 5, RoundingMode.HALF_UP).divide(pricePerPointSum, 5, RoundingMode.HALF_UP);
			BigDecimal foodPointsToBuy = percentToBuy.multiply(new BigDecimal(foodDemanded));
			
			quantitiesToBuy[i] = foodFirms[i].getProduct().getQuantity(foodPointsToBuy);
		}

		return quantitiesToBuy;
	}
}

