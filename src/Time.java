//keeps track of the month & year
public class Time {
	int year;
	int month;
	static final String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

	//precondition: year is positive
	//invalid year gets set to 0
	public Time(int year) {
		if(year < 0) year = 0;
		this.year = year;
		month = 1;
	}
	
	//precondition: year is positive, month is between 1 and 12 inclusive
	//invalid year gets set to 0
	//invalid month gets set to 1 (January)
	public Time(int year, int month) {
		if(year < 0) year = 0;
		if(month < 1) month = 1;
		else if(month > 12) month = 1;
		
		this.year = year;
		this.month = month;
	}
	
	public void incrementMonth() {
		if(month == 12) {
			month = 1;
			year++;
		} else {
			month++;
		}
	}
	
	public String getDate() {
		return months[month-1] + " " + year;
	}
}
