import java.math.BigDecimal;

public class BankAccount implements MoneyHolder {
	private BigDecimal value;
	
	public BankAccount() { //create a new empty BankAccount
		value = BigDecimal.ZERO;
	}
	
	public BigDecimal getMoney() {
		return value;
	}
	
	public void addMoney(BigDecimal amount) {
		value = value.add(amount);
	}
	
	public void subtractMoney(BigDecimal amount) {
		value = value.subtract(amount);
	}
}
