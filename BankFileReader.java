import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/**
 * Will parse a CSV file and create a List of type Customer for each row in the file. 
 * @author Elijah Pele
 * @version 3.0
 * @since March 03, 2021
 */
public class BankFileReader {
	
	/**
	 * Parses a CSV, creates new Customers then adds them to a List.
	 * @return List<Customer>
	 */
	public List<Customer> run(){
		List<Customer> customerList = new ArrayList<Customer>();
		try {
			Scanner s = new Scanner(System.in);
			//System.out.println("Enter the name of the file to read from.");
			//String file = s.nextLine();
			File f = new File("NewBankFile.txt");
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			String [] colNames = line.split(",");
			//Populate fields in the account
			int fNameIndex =0;
			int lNameIndex =0;
			int dobIndex =0;
			int idIndex =0;
			int addressIndex1 =0;
			int addressIndex2 =0;
			int addressIndex3 =0;
			int phoneIndex =0;
			int checkingIndex =0;
			int savingsIndex =0;
			int creditIndex =0;
			int checkingBalanceIndex =0;
			int savingsBalanceIndex =0;
			int creditBalanceIndex =0;
			int creditMaxIndex = 0;
			int j = 0;
			for(int i=0; i<colNames.length; i++) {
				if(colNames[i].equals("First Name")) {
					fNameIndex =j;
				}
				if(colNames[i].equals("Last Name")) {
					lNameIndex = j;
				}
				if(colNames[i].equals("Date of Birth")) {
					dobIndex = j;

				}
				if(colNames[i].equals("Identification Number")) {
					idIndex = j;
				}
				if(colNames[i].equals("Address")) {
					addressIndex1 = j;
					j++;
					addressIndex2 = j;
					j++;
					addressIndex3 = j;
				}
				if(colNames[i].equals("Phone Number")) {
					phoneIndex = j;
				}
				if(colNames[i].equals("Checking Account Number")) {
					checkingIndex = j;
				}
				if(colNames[i].equals("Savings Account Number")) {
					savingsIndex = j;
				}
				if(colNames[i].equals("Credit Account Number")) {
					creditIndex = j;
				}
				if(colNames[i].equals("Checking Starting Balance")) {
					checkingBalanceIndex = j;
				}
				if(colNames[i].equals("Savings Starting Balance")) {
					savingsBalanceIndex = j;
				}
				if(colNames[i].equals("Credit Starting Balance")) {
					creditBalanceIndex =j;
				}
				if(colNames[i].equals("Credit Max")) {
					creditMaxIndex =j;
				}
				j++;
			}
		
		line = br.readLine();	
		while(line != null) {
			String [] client = line.split(",");
			Customer newAccount = new Customer(client[fNameIndex], client[lNameIndex], client[dobIndex], client[idIndex], client[addressIndex1]+client[addressIndex2]+client[addressIndex3], client[phoneIndex]);
			newAccount.setAccount(0, client[checkingIndex], client[checkingBalanceIndex]);
			newAccount.setAccount(1, client[savingsIndex], client[savingsBalanceIndex]);
			newAccount.setAccount(2, client[creditIndex], client[creditBalanceIndex],client[creditMaxIndex]);
			newAccount.setLog();
			customerList.add(newAccount);
			line = br.readLine();
			}
	}
		catch(IOException e) {
			System.out.println("File not found. Please try again.");
			run();
		}
		
		return customerList;
	}
}
