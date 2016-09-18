import java.math.BigDecimal;

//a bank that holds BankAccounts
//right now the Bank is an omniscient entity that just holds households' money
public class Bank {
	private BankAccount[] accounts; //a bank account's ID is its index in the array
	
	//nAccounts should be equal to number of households
	public Bank(int nAccounts) {
		accounts = new BankAccount[nAccounts];
		for(int i=0; i<nAccounts; i++) {
			accounts[i] = new BankAccount();
		}
	}
	
	public BigDecimal getBalance(int id) {
		return accounts[id].getMoney();
	}
	
	public void deposit(BigDecimal amount, int id) {
		accounts[id].addMoney(amount);
	}
	
	public void withdraw(BigDecimal amount, int id) {
		accounts[id].subtractMoney(amount);
	}
}
