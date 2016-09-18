import java.math.BigDecimal;

//has a collection of money, which can be added to or subtracted from
//MoneyHolders include firms, households, government, & bank accounts
public interface MoneyHolder {
	BigDecimal getMoney();
	void addMoney(BigDecimal amount);
	void subtractMoney(BigDecimal amount) throws OutOfMoneyException;
}
