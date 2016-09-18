//an agricultural firm (a farm) that produces one food product
public class AgricultureFirm extends Firm {

	public AgricultureFirm(String name, Good prod, int assets) {
		super(name, prod, assets);
	}
	
	@Override
	public FoodItem getProduct() {
		return (FoodItem) super.getProduct();
	}

}
