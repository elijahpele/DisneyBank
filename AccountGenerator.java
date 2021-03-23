import java.io.IOException;
import java.util.List;
import java.util.Scanner;
/**
 *A class that will create new Customers and new account types e.g Credit Account.
 * @author Elijah Pele
 * @version 3.0
 * @since March 03, 2021
 */
public class AccountGenerator {
	private int availableCustomerID = 0;
	private int availableCheckingID = 0;
	private int availableCreditID = 0;
	private int availableSavingsID = 0;
	private List<Customer> customers;
	
	/**
	 * Creates an AccountGenerator that parses a given List of customers and finds the next available Customer ID, and Account ID.
	 * @param c list of type Customer
	 */
	public AccountGenerator(List<Customer> c) {
		
		for(Customer customer:c) {
			int id =0;
			int chID =0;
			int sID = 0;
			int crID =0;
			id = Integer.parseInt(customer.getiD());
			if(customer.getAccount(0) != null)
				chID = (int) customer.getAccount(0).getAccountID();
			if(customer.getAccount(1) != null)
				sID = (int) customer.getAccount(1).getAccountID();
			if(customer.getAccount(2)!=null)
				crID = (int) customer.getAccount(2).getAccountID();

			if(id>=this.availableCustomerID) {
				this.availableCustomerID =id+1;
			}
			if(chID>=this.availableCheckingID) {
				this.availableCheckingID = chID+1;
			}
			if(sID>=this.availableSavingsID) {
				this.availableSavingsID = sID++;
			}
			if(crID>=this.availableCreditID) {
				this.availableCreditID = crID+1;
			}
		}
		this.customers = c;
		
	}
	
	/**
	 * Adds a new customer to the banks database.
	 * @return List<Customer> updated Customer List
	 * @throws IOException
	 */
	public List<Customer> generateNewCustomer() throws IOException{
		Scanner s = new Scanner(System.in);
		String fName;
		String lName;
		String dob;
		String addy;
		String pNum;
		System.out.println("Creating a new user.");
		System.out.println("First name.");
		fName = s.nextLine();
		System.out.println("Last name.");
		lName = s.nextLine();
		System.out.println("Date of birth in (dd-Month-yy) format.");
		dob = s.nextLine();
		System.out.println("State e.g TX");
		addy = s.nextLine();
		System.out.println("City");
		addy = s.nextLine()+", "+addy;
		System.out.println("Home Address");
		addy = s.nextLine()+", "+addy;
		addy ="\""+addy+"\"";
		System.out.println("Phone number e.g (915)555-5555");
		pNum = s.nextLine();
		Customer newCus = new Customer(fName, lName, dob, addy, pNum);
		String str = String.valueOf(this.availableCustomerID);		
		newCus.setID(str);
		this.availableCustomerID++;
		str = String.valueOf(this.availableSavingsID);
		this.availableSavingsID++;
		newCus.setAccount(1, str,"0");
		newCus.setLog();
		System.out.println("Succesfully opened a new account. Your customer ID is: "+newCus.getiD());;
		System.out.println("Your new savings account ID is: "+newCus.getAccount(1).getAccountID());
		this.customers.add(newCus);
		return this.customers;
	}
	
	/**
	 * Creates a new checking account for a Customer.
	 * @param c List of Customers for the bank
	 */
	public void addCheckingAccount(Customer c){
		String str = String.valueOf(this.availableCheckingID);
		this.availableCheckingID++;
		System.out.println("How much money would you like to deposit in to your new checking account?");
		Scanner s = new Scanner(System.in);
		String amount = s.nextLine();
		c.setAccount(0,str, amount);
		System.out.println("New Checking Account Created!");
		System.out.println(c.getAccount(0).getBalanceDetails());
	}
	
	/**
	 * Creates a new credit account for a customer.
	 * @param c List of Customers for the bank
	 */
	public void addCreditAccount(Customer c){
		String id = String.valueOf(this.availableCreditID);
		this.availableCreditID++;
		System.out.println("Choose your limit for your credit card.(Between $100-$10,000)");
		Scanner s = new Scanner(System.in);
		String limit = s.nextLine();
		String startingAmount = "-"+limit;
		c.setAccount(2,id,startingAmount,limit);
		System.out.println("New Credit Account Created!");
		System.out.println(c.getAccount(2).getBalanceDetails());
	}
}
