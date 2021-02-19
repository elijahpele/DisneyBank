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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Checking {
	private String firstName;
	private String lastName;
	private int accountNumber;
	private boolean checkingAccount;
	private boolean savingsAccount;
	private double balance;
	private String interestRate;
	private File transactionsLog;
	
	public Checking() {}
	
	public Checking(String firstName, String lastName, int accountNumber, boolean checkingAccount, boolean savingsAccount, double balance, String interestRate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountNumber = accountNumber;
		this.checkingAccount = checkingAccount;
		this.savingsAccount = savingsAccount;
		this.balance = balance;
		this.interestRate = interestRate;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = Integer.parseInt(accountNumber);
	}
	public void setIsCheckingAccount(String isChecking) {
		 if(isChecking.toLowerCase() == "true") {
			 this.checkingAccount = true;
		 }
		 else this.checkingAccount = false;
	}
	public void setIsSavingsAccount(String isChecking) {
		 if(isChecking.toLowerCase() == "true") {
			 this.savingsAccount = true;
		 }
		 else this.savingsAccount = false;
	}
	public void setBalance(String balance) {
		this.balance = Double.parseDouble(balance);
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = (interestRate);
	}
	//Creates each account its own log file.
	public void setLog() throws IOException{
		File f = new File(Integer.toString(this.accountNumber)+".txt");
		this.transactionsLog = f;
	}
	//Will log transactions the accounts individual file.
	public void addToIndividualLog(String line) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(this.transactionsLog, true));
		bw.append(line);
		bw.newLine();
		bw.close();
	}
	public String getFirstName() {
		return this.firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public int getAccountNumber() {
		return this.accountNumber;
	}
	public boolean getIsCheckingAccount() {
		return this.checkingAccount;
	}
	public boolean getIsSavingsAccount() {
		return this.savingsAccount;
	}
	public double getBalance() {
		return this.balance;
	}
	public String getInterestRate() {
		return this.interestRate;
	}
	public File getTransLog() {
		return this.transactionsLog;
	}
}
