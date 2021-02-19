/*
 * Name: Elijah Pele
 * Date: 01/29/2021
 * 
 * Course: CS3331
 * Instructor: Daniel Mejia
 * Assignment: Lab 1
 * 
 *	The purpose of this assignment was to create a Java program that would simulate a
	bank program that would first parse a CSV file before creating a series of new 
	checking accounts. Users would be allowed to pay other users, deposit, and withdraw money,
	check their current balance, and request transaction history reports.
 *	
 *	Honesty Statement: I confirm that the work of this assignment is completely my own. By turning in this assignment,
 	I declare that I did not receive unauthorized assistance. Moreover, all deliverables including, 
 	but not limited to the source code, lab report and output files were written and produced by me alone.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bank {
	private List<Checking> checkingList;
	private File transactionLog;
	
	//Constructor
	public Bank(File csvFile) throws FileNotFoundException {
		this.checkingList = new ArrayList<Checking>();
		
		try {
			File f = new File("BankLog.txt");
			this.transactionLog = f;
			BufferedReader br = new BufferedReader(new FileReader(csvFile));
			
			//Skip the first Row
			br.readLine();
			
			//Start reading at second row
			String line = br.readLine();
			
			while(line != null) {
				
				//Create CheckingAccount
				Checking newAccount = new Checking();
					
				String [] client = line.split(",");
				//Populate fields in the account
				newAccount.setFirstName(client[0]);
				newAccount.setLastName(client[1]);
				newAccount.setAccountNumber(client[2]);
				newAccount.setIsCheckingAccount(client[3]);
				newAccount.setIsSavingsAccount(client[4]);
				newAccount.setBalance(client[5]);
				newAccount.setInterestRate(client[6]);
				newAccount.setLog();
				//Add to Checking List
				checkingList.add(newAccount);
				//Move to the next Row
				line = br.readLine();
			}
			
			br.close();
					}
		//Program terminates if the file is not found
		catch(FileNotFoundException e) {
			System.out.println("File not found. Please try again.");
			System.exit(1);
		}
		catch (Exception e) {
			System.out.println("File not found. Please try again.");
			//throw FileNotFoundException();
	        e.printStackTrace();
	    }
	}
	
	//Adds a log of a transaction to the Banks main log. This method will be used locally
	private void addToBankLog(String line) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(this.transactionLog, true));
		bw.append(line);
		bw.newLine();
		bw.close();
	}
	
	//This method returns the users current balance
	public void inquireBalance(int accNum) {
		boolean found = false;
		Checking foundAccount = new Checking();
		for(Checking account:this.checkingList) {
			if(accNum == account.getAccountNumber()) { 
				found = true;
				foundAccount = account;
			}
		}
		if(found = true)
			System.out.println("Balance for " +foundAccount.getFirstName() +" " +foundAccount.getLastName() +" is $" +foundAccount.getBalance() +".");
		else 
			System.out.println("Account was not found.");
		}
	
	//This method allows two users to make a payment to each other
	public void pay(double amount, int senderAccountNumber, int recieverAccountNumber) throws IOException {
		Checking sender = new Checking();
		Checking reciever = new Checking();
		
		//Checks for negative input and  if the sender has enough money to make the payment.
		if(amount >= 0) {
			for(Checking account:this.checkingList) {
				if (account.getAccountNumber() == senderAccountNumber) {
					sender = account;
				}
				if(account.getAccountNumber()== recieverAccountNumber) {
					reciever = account;
				}
			}	
			double senderNewBalance = sender.getBalance() - amount;
			double recieverNewBalance = reciever.getBalance() + amount;
			if(senderNewBalance < 0){
				System.out.println("Payment failed: Insufficient Funds.");
			}
			
			//Processes transaction if the above criteria are met
			else {
				sender.setBalance(Double.toString(senderNewBalance));
				reciever.setBalance(Double.toString(recieverNewBalance));
				System.out.println(sender.getFirstName() +" paid " +reciever.getFirstName() +" $" +amount);
				sender.addToIndividualLog("Paid " +reciever.getFirstName() +" $" +amount+".");
				reciever.addToIndividualLog("Recieved $" +amount +" from " +sender.getFirstName() +" " +sender.getLastName());	}
				addToBankLog(sender.getFirstName() +" paid " +reciever.getFirstName() +" $" +amount);
		}
		else
			System.out.println("Payment failed: can not pay a negative amount.");
	}
			
	//This method allows a user to deposit money into their account	
	public void deposit(double amount, int accNum) throws IOException {
		if(amount>=0){
			for(Checking account:this.checkingList) {
				if(accNum == account.getAccountNumber()) {
					account.setBalance(Double.toString(account.getBalance() + amount));
					account.addToIndividualLog("Deposited $" +amount);
					this.addToBankLog(account.getFirstName() +" " +account.getLastName() +" deposited $" +amount);
					System.out.println(account.getFirstName()+ " deposited $" +amount);
				}
			}
		}
		else
			System.out.println("Deposit failed: Can not deposit negative amount.");
		
	}
	
	//This method allows for a user to withdraw money from their account
	public void withdraw(double amount, int accNum) throws IOException {
		//Checks withdraw amount is positive
		if(amount>=0) {
			for(Checking account:this.checkingList) {
				if(accNum == account.getAccountNumber()) {
					double newBalance = account.getBalance() - amount;
					//Checks if the account has enough money to be withdrawn from.
					if(newBalance<0)
						System.out.println("Withdrawal failed: Insufficient funds.");
					else {
						account.setBalance(Double.toString(newBalance));
						account.addToIndividualLog("Withdrew $"+amount);
						addToBankLog(account.getFirstName() +" " +account.getLastName() +" withdrew $" +amount);

						System.out.println(account.getFirstName()+ " withdrew $" +amount);
					}
				}
			}
		}
	}
	
	//Returns a print statement containing transaction logs for all users
	public void getBankLog() throws IOException{
		File f = this.transactionLog;
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line = br.readLine();
		System.out.println("\n" +"Bank Log");
		System.out.println("------------------------------");
		while(line != null) {
			System.out.println("+"+line);
			line = br.readLine();
		}
		br.close();
		System.out.println("------------------------------");
	}
	
	//Return a file and a print statement containing an individuals transaction logs
	public File individualTransLog(int accNum) throws IOException {
		for(Checking account: this.checkingList) {
			if(accNum == account.getAccountNumber()) {
				System.out.println("\n" +"\n" +account.getFirstName() +" " +account.getLastName() +" Log:");
				System.out.println("------------------------------");
				File f = account.getTransLog();
				BufferedReader br = new BufferedReader(new FileReader(f));
				String line = br.readLine();
				while(line != null) {
					System.out.println("+"+line);
					line = br.readLine();
				}
				br.close();
				System.out.println("------------------------------");
				return f;
			}
		}
		return null;
	}
}