import java.io.File;
import java.io.IOException;
import java.util.*;
/**
 * This class will represent the basic functionalities of a Bank.
 * @author Elijah Pele
 * @version 3.0
 * @since March 03, 2021
 */
public class Bank {
	private final String BANK_NAME = "Disney Bank";
	private List<Customer> customerList;
	private File logFile;
	private File updatedCSV;
	
	/**
	 * Will create a bank and initialize its updatedACcountList and its bankLog.
	 */
	public Bank() {
		File logFile = new File("BankLog.txt");
		File updatedCSVFile = new File("UpdatedCustomerList.csv");
		this.logFile = logFile;
		this.updatedCSV = updatedCSVFile;
		
	}
	
	/**
	 * Will call BankFileReader to create the List of Customers for the bank.
	 */
	public void readCustomerFile() {
		BankFileReader reader = new BankFileReader();
		this.customerList = reader.run();
	}
	
	/**
	 * Will run the console where users and employees can perform transactions.
	 * @throws IOException
	 */
	public void runConsole() throws IOException {
		Console console = new Console();
		console.run(this.customerList, this.logFile, this.updatedCSV);
	}
}