import java.math.BigDecimal;

//any good for sale - only one firm can sell one particular good
public class Good {
	private String name; //name of good
	private String units; //name of unit of measurement (ie. unit for corn is ears, unit for water is gallons)
	private BigDecimal price; //price per unit of item
	
	public Good(String name, String price, String units) {
		this.name = name;
		this.price = new BigDecimal(price);
		this.units = units;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUnits() {
		return units;
	}
	
	public BigDecimal getPrice(int quantity) {
		return price.multiply(new BigDecimal(quantity));
	}
	
	public BigDecimal getPrice() {
		return price;
	}
}