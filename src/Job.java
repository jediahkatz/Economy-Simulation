import java.math.BigDecimal;

public class Job {
	private BigDecimal hourlyWage;
	private BigDecimal hoursPerMonth; //hours worked in a month
	private Household employee; //the Household that receives the money
	
	public Job(Household h, BigDecimal wage, BigDecimal hours) {
		employee = h;
		hourlyWage = wage;
		hoursPerMonth = hours;
	}
	
	//pay the employee for a month's work
	public void pay() {
		employee.addMoney(getMonthlyPayment());
	}
	
	public BigDecimal getHours() {
		return hoursPerMonth;
	}
	
	public Household getEmployee() {
		return employee;
	}
	
	public BigDecimal getMonthlyPayment() {
		return hoursPerMonth.multiply(hourlyWage);
	}
}
