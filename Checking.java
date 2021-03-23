/**
 * Child class to Account
 * @author Elijah Pele
 * @version 3.0
 * @since March 03, 2021
 */
public class Checking extends Account {
	
	/**
	 * Will call its parents constructor to create a new Account of type Checking
	 * @param accID
	 * @param amount
	 */
	public Checking(long accID, double amount) {
		super(accID,amount);
		super.setAccountType("Checking");
	}
	public Checking() {
		super.setAccountType("Checking");
	}
	
	/**
	 * Prints the account type and its ID.
	 * @returns s String
	 */
	public String getAccDetails() {
		String s = "Checking-"+this.getAccountID();
		return s;
	}
	
	/**
	 * Prints the details of the accounts balance, its type and its ID.
	 * @returns s String
	 */
	public String getBalanceDetails() {
		String s = "Balance for Checking-"+this.getAccountID()+":$"+this.getBalance();
		return s;
	}

}
