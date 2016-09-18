import java.math.BigDecimal;
import java.math.RoundingMode;

//a food item that can be sold
public class FoodItem extends Good {
	private int foodPts; //every household demands a certain number of food points per month
	
	public FoodItem(String name, String price, String units, int nutr) {
		super(name, price, units);
		foodPts = nutr;
	}
	
	public int getFoodPts() {
		return foodPts;
	}
	
	//returns the quantity needed to yield a number of food points
	public int getQuantity(int fpNeeded) {
		double quantity = (double) fpNeeded/foodPts;
		return (int) Math.ceil(quantity);
	}
	
	//returns the quantity needed to yield a number of food points
		public int getQuantity(BigDecimal fpNeeded) {
			BigDecimal quantity = fpNeeded.divide(new BigDecimal(foodPts), 5, RoundingMode.HALF_UP);
			return (int) Math.ceil(quantity.doubleValue());
		}
	
	public BigDecimal getPricePerPoint() {
		return super.getPrice(1).divide(new BigDecimal(foodPts), 5, RoundingMode.HALF_UP);
	}
}
