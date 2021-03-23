/**
 * Child class to Account
 * @author Elijah Pele
 * @version 3.0
 * @since March 03, 2021
 */
public class Credit extends Account{
	
	private double limit;
	
	/**
	 * We call the parents constructor to create an Account of type Savings.
	 * @param accID
	 * @param amount
	 */
	public Credit(long accID, double amount) {
		super(accID,amount);
		super.setAccountType("Credit");

		}
	
	public Credit() {
	}
	public Credit(long accID, double amount, double limit) {
		super(accID, amount);
		super.setAccountType("Credit");
		this.limit = limit;
	}
	/**
	 * Prints the account type and its ID.
	 * @returns s String
	 */
	public String getAccDetails() {
		String s = "Credit-"+this.getAccountID();
		return s;
	}
	
	/**
	 * Prints the details of the accounts balance, its type and its ID.
	 * @returns s String
	 */
	public String getBalanceDetails() {
		String s = "Balance for Credit-"+this.getAccountID()+":$"+this.getBalance();
		return s;
	}
	
	/**
	 * Returns the max limit on the Credit Account.
	 * @return limit
	 */
	public double getLimit() {
		return this.limit;
	}
	public void setLimit(double limit) {
		this.limit = limit;
	}

}


