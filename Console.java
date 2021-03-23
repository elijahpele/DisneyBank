import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
/**
 * Handles users performing different types of transactions.
 * @author Elijah Pele
 * @version 3.0
 * @since March 03, 2021
 */
public class Console {
	
	public void run(List<Customer> customers,File logFile, File updatedCSVFile) throws IOException {
		System.out.println("Welcome to the DisneyBank!");
		System.out.println(".");
		System.out.println(".");
		String input = "";
		while(input.equals("G") ==false) {
			Scanner s = new Scanner(System.in);
			System.out.println("[Main Bank Window]");
			System.out.println("A. Open new Account.");
			System.out.println("B. Log in.");
			System.out.println("C. Manager Login.");
			System.out.println("D. Transaction Reader");
			System.out.println("G. Exit");
			input = s.nextLine();
			//OpenACcount
			if(input.equals("A")) {
				AccountGenerator ag = new AccountGenerator(customers);
				customers = ag.generateNewCustomer();
			}
			//LogIn
			else if(input.equals("B")) { 
				String in = "";
				//s = new Scanner(System.in);
				System.out.println();
				Customer acc = getCustomerByIdNum(customers);
				System.out.println(".");
				System.out.println(".");
				System.out.println("Welcome "+acc.fullName()+"!");
				System.out.println(".");
				while(in.equals("G") ==false) {
					//String accNum = validateAccount(customers);
					in = chooseTransactionPrintStatement();
					System.out.println();
					String in2;
					if(in.equals("A")) {
						System.out.println("Choose which account to withdraw from.");
						int i = getAccountInCustomer();
						accountValidator(i,acc);
						System.out.println("Enter amount to withdraw.");
						String amount = s.nextLine();
						System.out.println(".");
						System.out.println(".");
						in2 = acc.withdraw(i, amount);
						System.out.println(in2);
						bankLog(in2,logFile);
					}
					else if(in.equals("B")) {
						System.out.println("Choose which account to deposit to.");
						int i = getAccountInCustomer();
						accountValidator(i,acc);
						System.out.println("Enter amount to deposit.");
						String amount = s.nextLine();
						System.out.println(".");
						System.out.println(".");
						in2 = acc.deposit(i, amount);
						System.out.println(in2);
						bankLog(in2,logFile);
					}
					else if(in.equals("C")) {
						System.out.println("Enter the recievers first and last name.");
						String str = s.nextLine();
						Customer reciever = getCustomerByName(customers, str);
						System.out.println(".");
						System.out.println(".");
						System.out.println("Choose which account you would like to pay " +reciever.fullName() +" with.");
						int i = getAccountInCustomer();
						accountValidator(i,acc);
						System.out.println("To which account will " +reciever.fullName() +" recieve their money?");
						System.out.println(".");
						System.out.println(".");
						int j = getAccountInCustomer();
						System.out.println("Enter the amount you would like to pay "+reciever.fullName());
						String amount = s.nextLine();
						System.out.println(".");
						System.out.println(".");
						String [] inputArr = acc.pay(reciever, i, j, amount);
						System.out.println(inputArr[0]);
						System.out.println(inputArr[1]);
						bankLog(inputArr[0],logFile);
						bankLog(inputArr[1], logFile);
					}
					else if(in.equals("D")) {
						System.out.println("Choose which account you would like to inquire.");
						int i = getAccountInCustomer();
						accountValidator(i,acc);
						in2 = acc.inquire(i);
						System.out.println(in2);
						bankLog(in2,logFile);
					}
					else if(in.equals("E")) {
						System.out.println("Choose which account to transfer from.");
						int from = getAccountInCustomer();
						accountValidator(from,acc);
						System.out.println("Choose which account to transfer to.");
						int to = getAccountInCustomer();
						//accountValidator(to,acc);
						System.out.println("Enter the amount you would like to transfer.");
						String amount = s.nextLine();
						in2 = acc.transfer(from, to, amount);
						System.out.println(in2);
						bankLog(in2, logFile);
					}
					else if(in.equals("F")){
						System.out.println("What kind of account would you like to open?");
						System.out.println("A. Checking");
						System.out.println("B. Credit");
						in2 = s.nextLine();
						AccountGenerator ag = new AccountGenerator(customers);
						if(in2.equals("A"))
							ag.addCheckingAccount(acc);
						else if(in2.equals("B"))
							ag.addCreditAccount(acc);
						else
							System.out.println("Invalid Input.");	
					}
					else if(in.equals("G")) {
						System.out.println("...Exiting");
						System.out.println();
						//add to outer whileloop
						/*
						PrintWriter writer = new PrintWriter(logFile);
						writer.print("");
						writer.close();	
						PrintWriter writer2 = new PrintWriter(updatedCSVFile);
						writer.print("");
						writer.close();	
						System.exit(1);
						*/
					}				
					else{
						System.out.println(".");
						System.out.println(".");
						System.out.println("Inavlid Input. Try again..");
						System.out.println(".");
						System.out.println(".");
					}
					updateCSV(customers,updatedCSVFile);
					//if(input.equals("G")==false)
						//input = runAgain();
				}
			}	
			else if(input.equals("C")) {
				String in2 = "";
				while(in2.equals("G") ==false) {
					System.out.println(".");
					System.out.println("[Manager Window]");
					System.out.println("A. Inquire Account by name.");
					System.out.println("B. Inquire Account by type/number.");
					System.out.println("C. Write Bank Statement.");
					System.out.println("G. Exit");
					in2 = s.nextLine();
					if(in2.equals("A")) {
						Customer cus = inquireAccountByName(customers);
					}
					else if(in2.equals("B")) {
						Customer cus = inquireAccountByType(customers);
					}
					else if(in2.equals("C")) {
						System.out.println("Enter customers full name.");
						String name = s.nextLine();
						Customer cus = getCustomerByName(customers,name);
						BankStatement bs = new BankStatement(cus);
						File statementFile = bs.write();
					}
					else if(in2.equals("G")) {
						System.out.println("Exiting..");
						System.out.println();
					}
					else {
						System.out.println("Invalid Input");
					}
					//if(input.equals("G")==false)
						//in2 = runAgain();
				}
			}
			//TransactionReader
			else if(input.equals("D")) {
				System.out.println("Enter name of transaction file.");
				String file = "Transactions.txt";//s.nextLine();
				File f = new File(file);
				BufferedReader br = new BufferedReader(new FileReader(f));
				String line = br.readLine();
				line = br.readLine();
				while(line != null) {
					String [] trans = line.split(",");
					if(trans[3].equals("pays")) {
						String name = trans[4]+" "+trans[5];
						Customer reciever = getCustomerByName(customers, name);
						String senderName = trans[0]+" "+trans[1];
						Customer sender = getCustomerByName(customers, senderName);
						int from = getAccountInCustomer(trans[2]);
						int to = getAccountInCustomer(trans[6]);
						String amount = trans[7];
						String []arr = (sender.pay(reciever, from, to, amount));
						bankLog(arr[0], logFile);
						bankLog(arr[1], logFile);
					}
					if(trans[3].equals("transfers")) {
						String name = trans[4]+" "+trans[5];
						Customer c1 = getCustomerByName(customers, name);
						int from = getAccountInCustomer(trans[2]);
						int to = getAccountInCustomer(trans[6]);
						String amount = (trans[7]);
						bankLog(c1.transfer(from, to, amount), logFile);
					}
					if(trans[3].equals("inquires")) {
						String name = trans[0]+" "+trans[1];
						Customer c1 = getCustomerByName(customers, name);
						int i = getAccountInCustomer(trans[2]);
						bankLog(c1.inquire(i), logFile);
					}
					if(trans[3].equals("withdraws")) {
						String name = trans[0]+" "+trans[1];
						Customer c1 = getCustomerByName(customers, name);
						int from = getAccountInCustomer(trans[2]);
						String amount = (trans[7]);
						bankLog(c1.withdraw(from, amount), logFile);
					}
					if(trans[3].equals("deposits")) {
						String name = trans[4]+" "+trans[5];
						Customer c1 = getCustomerByName(customers, name);
						int to = getAccountInCustomer(trans[6]);
						String amount = (trans[7]);
						bankLog(c1.deposit(to, amount), logFile);
					}
					line = br.readLine();
				}	
				System.out.println();
				System.out.println("Transaction File read!");
				System.out.println();
			}
			else if(input.equals("G")) {
				System.out.println("Thank you come again!");
				System.out.println("...Exiting");
				PrintWriter writer = new PrintWriter(logFile);
				writer.print("");
				writer.close();	
				PrintWriter writer2 = new PrintWriter(updatedCSVFile);
				writer.print("");
				writer.close();
				System.exit(1);
			}
			else {
				System.out.println("Invalid Input");
			}
			updateCSV(customers,updatedCSVFile);
			//input = runAgain();
		}


	}
	
	
	
	/**
	 * Verifies that the index and the account ID match the account that is being used.
	 * @param n Index for the account that is being used.
	 * @param c Customer that is being validated
	 */
	public static void accountValidator(int n, Customer c) {
		Scanner s = new Scanner(System.in);
		System.out.println();
		System.out.println("Enter the account number.");
		long num = s.nextLong();
		System.out.println();
		if(num==c.getAccount(n).getAccountID())
			return;
		System.out.println(".");
		System.out.println(".");
		System.out.println("Invalid account number, try again..");
		System.out.println(".");
		System.out.println(".");
		accountValidator(n,c);
	}
	
	
	public static void bankLog(String line,File log) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(log, true));
		bw.append(line);
		bw.newLine();
		bw.close();
	}
	
	/**
	 * Helper method that prints all the different accounts that can be chosen to work with.
	 */
	public static void chooseAccountPrintStatement() {
		System.out.println("A. Checking.");
		System.out.println("B. Savings.");
		System.out.println("C. Credit.");
	}
	
	/**
	 * Helper method that prints all the possible transactions that can be applied to an account.
	 * @return s String that contains the input for which transaction will be executed.
	 */
	public static String chooseTransactionPrintStatement() {
		String trans = "";
		Scanner s4 = new Scanner(System.in);
		System.out.println();
		System.out.println("Choose a transaction. (e.g. to deposit enter \"B\")");
		System.out.println("A. Withdraw.");
		System.out.println("B. Deposit.");
		System.out.println("C. Pay Someone.");
		System.out.println("D. Balance Inquiry.");
		System.out.println("E. Transfer.");
		System.out.println("F. Open a new account");
		System.out.println("G. Exit.");
		trans = s4.nextLine();
		if(trans.equals("A")||trans.equals("B")||trans.equals("C")||trans.equals("D")||trans.equals("E")||trans.equals("F")||trans.equals("G")||trans.equals("H")) {
			return trans;
		}
		else {
			System.out.println(".");
			System.out.println(".");
			System.out.println("Invalid input. Try again..");
			System.out.println(".");
			System.out.println(".");
		}
		return trans = chooseTransactionPrintStatement();
	}
	
	
	/**
	 * Searches the list of Customers and returns the Customer that matches the inputed identification number.
	 * @param cus a list of all the customers in the bank.
	 * @return Customer Customer found based on Identification number.
	 */
	public static Customer getCustomerByIdNum(List<Customer> cus) {
		Customer customer = new Customer();
		boolean found = false;
		Scanner s2 = new Scanner(System.in);
		System.out.println("Enter your customer ID.");
		String iD = s2.nextLine();
		for(Customer acc:cus) {
			if(acc.getiD().equals(iD)) {
				customer = acc;
				found = true;
				return customer;
				}
		}
		if(found==false) {
			System.out.println(".");
			System.out.println(".");
			System.out.println("Account not found, try again.");
			System.out.println(".");
			System.out.println(".");
			}
		return customer = getCustomerByIdNum(cus);
	}
	
	/**
	 * Searches the list of Customers and returns a Customer that matches the inputed first and last name.
	 * @param cus a list of all the customers in the bank.
	 * @return Customer Customer found based on first and last name.
	 */
	public static Customer getCustomerByName(List<Customer> cus, String fullName) {
		Customer customer = new Customer();
		boolean found = false;
		for(Customer acc:cus) {
			if(acc.fullName().equals(fullName)) {
				customer = acc;
				found = true;
			}
		}
		if(found==false) {
				System.out.println(".");
				System.out.println(".");
				System.out.println("Account not found.");
				System.out.println(".");
				System.out.println(".");	
		}
		return customer;
	}
	
	
	
	
	public static int getAccountInCustomer(String s){
		int n =-1;
		if(s.equals("Checking"))
			n =0;
		if(s.equals("Savings"))
			n = 1;
		if(s.equals("Credit"))
			n=2;
		return n;
	}
	
	/**
	 * Returns an integer based on which account is being used e.g. Checking, Savings, or Credit.
	 * @return n The index of the account that is being used.
	 */
	public static int getAccountInCustomer() {
		int n = -1;
		Scanner s = new Scanner(System.in);
		chooseAccountPrintStatement();
		String c = s.nextLine();
		if(c.equals("A")){
			return 0;
		}
		if(c.equals("B")){
			return 1;
		}
		if(c.equals("C")){
			return 2;
		}
		System.out.println(".");
		System.out.println(".");
		System.out.println("Invalid input. Try again..");
		System.out.println(".");
		System.out.println(".");
		return n = getAccountInCustomer();
	}
	
	
	/**
	 * Searches the list of Customers and returns a Customer that matches the inputed first and last name.
	 * @param cus a list of all the customers in the bank.
	 * @return Customer Customer found based on name.
	 */
	public static Customer inquireAccountByName(List<Customer> cus) {
		System.out.println("Who's account would you like to inquire.");
		Scanner s = new Scanner(System.in);
		String str = s.nextLine();
		Customer customer = new Customer();
		boolean found = false;
		for(Customer acc:cus) {
			if(acc.fullName().equals(str)) {
				customer = acc;
				found = true;
				System.out.println("Name:" +customer.fullName());
				System.out.println("Date of Birth:" +customer.getDob());
				System.out.println("Identifiaction Number:" +customer.getiD());
				System.out.println("Address:" +customer.getAddress());
				System.out.println("Phone Number:" +customer.getPhoneNum());
				System.out.println(customer.getAccount(0).getAccDetails()+": $"+customer.getAccount(0).getBalance());
				System.out.println(customer.getAccount(1).getAccDetails()+": $"+customer.getAccount(1).getBalance());					
				System.out.println(customer.getAccount(2).getAccDetails()+": $"+customer.getAccount(2).getBalance());
				System.out.println("Credit Limit: $"+customer.getAccount(2).getLimit());
				return customer;
			}
		}
		if(found==false) {
				System.out.println(".");
				System.out.println(".");
				System.out.println("Account not found, try again.");
				System.out.println(".");
				System.out.println(".");	
		}
		return customer = inquireAccountByName(cus);
	}
	
	/**
	 * Searches the list of Customers and returns the customer that matches the inputed account type.
	 * @param cus list of all the Customers in bank.
	 * @return CUstomer Customer found based on account type.
	 */
	public static Customer inquireAccountByType(List<Customer> cus) {
		System.out.println("What account type?");
		chooseAccountPrintStatement();
		Scanner s = new Scanner(System.in);
		String str = s.nextLine();
		
		int i=0;
		
		if(str.equals("A")) {
			i = 0;
		}
		if(str.equals("B")) {
			i=1;
		}
		if(str.equals("C")) {
			i=2;
		}
		System.out.println("What is the account number?");
		long num = s.nextLong();
		Customer customer = new Customer();
		boolean found = false;
		for(Customer acc:cus) {
			if(acc.getAccount(i).getAccountID()==num) {
				customer = acc;
				found = true;
				System.out.println("Name:" +customer.fullName());
				System.out.println("Date of Birth:" +customer.getDob());
				System.out.println("Identifiaction Number:" +customer.getiD());
				System.out.println("Address:" +customer.getAddress());
				System.out.println("Phone Number:" +customer.getPhoneNum());
				System.out.println(customer.getAccount(0).getAccDetails()+": $"+customer.getAccount(0).getBalance());
				System.out.println(customer.getAccount(1).getAccDetails()+": $"+customer.getAccount(1).getBalance());					
				System.out.println(customer.getAccount(2).getAccDetails()+": $"+customer.getAccount(2).getBalance());
				System.out.println("Credit Limit: $"+customer.getAccount(2).getLimit());
				return customer;
			}
		}
		if(found==false) {
				System.out.println(".");
				System.out.println(".");
				System.out.println("Account not found, try again.");
				System.out.println(".");
				System.out.println(".");	
		}
		return customer = inquireAccountByType(cus);
	}


	
	
	/**
	 * Will keep the console running as long as the user wants to make another transaction.
	 * @return String
	 */
	public static String runAgain() {
		Scanner scr=new Scanner(System.in);
		System.out.println(".");
		System.out.println(".");
		System.out.println("\n" +"Would you like to make another transaction?");
		System.out.println("A. Yes");
		System.out.println("B. No");
		String s=scr.nextLine();
		if(s.equals("A")) {
			return"A";
		}
		if(s.equals("B")) {
			System.out.println("Thank you come again!");
			System.out.println("...Exiting");
			return "G";
		}
		else{
			System.out.println("Invalid input: Try Again...");
		}
		return runAgain();	
	}
	
	
	/**
	 * Updates a CSV file with the latest values for each customer.
	 * @param cus List of Customers
	 * @param f File that will be updated
	 * @throws IOException
	 */
	public static void updateCSV(List<Customer> cus, File f) throws IOException {
		FileWriter fw = new FileWriter(f);
		fw.append("First Name");
		fw.append(",");
		fw.append("Last Name");
		fw.append(",");
		fw.append("Date of Birth");
		fw.append(",");
		fw.append("Identificaion Number");
		fw.append(",");
		fw.append("Address");
		fw.append(",");
		fw.append("Phone Number");
		fw.append(",");
		fw.append("Checking Account Number");
		fw.append(",");
		fw.append("Savings Account Number");
		fw.append(",");
		fw.append("Credit Account Number");
		fw.append(",");
		fw.append("Checking Starting Balance");
		fw.append(",");
		fw.append("Savings Starting Balance");
		fw.append(",");
		fw.append("Credit Starting Balance");
		fw.append(",");
		fw.append("Credit Max");
		fw.append("\n");
		for(Customer c:cus) {
			fw.append(c.getFirstName());
			fw.append(",");
			fw.append(c.getLastName());
			fw.append(",");
			fw.append(c.getDob());
			fw.append(",");
			fw.append(c.getiD());
			fw.append(",");
			fw.append(c.getAddress());
			fw.append(",");
			fw.append(c.getPhoneNum());
			fw.append(",");
			for(int i =0; i<3; i++) {
				if(c.getAccount(i)==null)
					fw.append("null");
				else
					fw.append(String.valueOf(c.getAccount(i).getAccountID()));

				fw.append(",");
			}
			for(int i =0; i<3; i++) {
				if(c.getAccount(i)==null)
					fw.append("null");
				else
					fw.append(String.valueOf(c.getAccount(i).getBalance()));
				fw.append(",");
			}
			if(c.getAccount(2)== null)
				fw.append("null");
			else {
				String str = String.valueOf(c.getAccount(2).getLimit());
				fw.append(str);
			};
			fw.append("\n");				
		}
		fw.flush();
		fw.close();
	}
	
}

