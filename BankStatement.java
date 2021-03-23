import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * Will write a Bank Statement given a Customer.
 * @author Elijah Pele
 * @version 3.0
 * @since March 03, 2021
 */

public class BankStatement {
	Customer customer;
	public BankStatement(Customer c) {
		this.customer = c;
		}
	public File write() throws IOException {
		File f = new File(customer.getLastName()+"_"+customer.getFirstName()+"_BankStatement"+".txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
		bw.append("Disney Bank");
		bw.newLine();
		bw.newLine();
		bw.newLine();
		bw.append("Name: "+customer.fullName());
		bw.newLine();
		bw.append("Address: "+customer.getAddress());
		bw.newLine();
		bw.append("Phone: "+customer.getPhoneNum());
		bw.newLine();
		bw.write("------------------------------------------------------------------------------");
		bw.newLine();
		for(int i =0; i<2; i++) {
			bw.append(customer.getAccount(i).getAccDetails());
			bw.write("\t"+"Starting Balance: $"+customer.getAccount(i).getStartingBalance());
			bw.write("\t"+"Ending Balance: $"+customer.getAccount(i).getBalance());
			bw.newLine();
			bw.newLine();
		}
		bw.append(customer.getAccount(2).getAccDetails());
		bw.write("\t"+"Starting Balance: $"+customer.getAccount(2).getStartingBalance());
		bw.write("\t"+"Ending Balance: $"+customer.getAccount(2).getBalance());
		bw.newLine();
		bw.write("            "+"\t"+"Credit Limit: $"+customer.getAccount(2).getLimit());
		bw.newLine();
		bw.write("------------------------------------------------------------------------------");
		bw.newLine();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(customer.getLog()));		
			String line = br.readLine();
			while(line != null) {
				bw.append(line);
				bw.newLine();
				line = br.readLine();
			}
		}
		catch(FileNotFoundException e) {
			System.out.println("(Transaction log is unavailable.)");
		}
		System.out.println("Bank Statement Filled!");
		bw.close();
		return f;

	}
}