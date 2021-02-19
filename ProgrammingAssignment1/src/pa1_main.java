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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
public class pa1_main {
	public static void main(String [] args) throws IOException, FileNotFoundException {
		
		Scanner s = new Scanner(System.in);
		//Ask for CSV file containing account information
		System.out.println("Enter the name of the file containg checking account information.");
		String fileName = s.nextLine();
		File file = new File(fileName);
		Bank disneyBank = new Bank(file);
		String str = "";
		System.out.println("Type what you would like to do e.g. Pay, Deposit, Withdraw or Transaction History, or Exit");
		str = s.next();
		
		//User Interface
		while  (str != "Exit") {
			//Calls Bank pay method
			if(str.toLowerCase().equals("pay")) {
				System.out.println("Enter your account number");
				int senderNum = s.nextInt();
				System.out.println("Enter the account number for the person you are paying.");
				int recieverNum = s.nextInt();
				System.out.println("Enter the amount being paid");
				double amount = s.nextDouble();
				disneyBank.pay(amount, senderNum, recieverNum);
			}
			//Calls Bank deposit method
			else if(str.toLowerCase().equals("deposit")) {
				System.out.println("Enter your account number");
				int accNum = s.nextInt();
				System.out.println("Enter the amount being deposited.");
				double amount = s.nextDouble();
				disneyBank.deposit(amount, accNum);
			}
			//Calls Bank withdraw method
			else if(str.toLowerCase().equals("withdraw")) {
				System.out.println("Enter your account number");
				int accNum = s.nextInt();
				System.out.println("Enter the amount being withdrawn.");
				double amount = s.nextDouble();
				disneyBank.withdraw(amount, accNum);
			}
			//Calls Bank Log method
			else if(str.toLowerCase().equals("log")) {
				disneyBank.getBankLog();
			}
			else
				System.out.println("Try again.");
			System.out.println("\n" +"\n" +"Type what you would like to do e.g. Pay, Deposit, Withdraw or Transaction History, or Exit");
			str = s.next();
		}
		s.close();
	}
}