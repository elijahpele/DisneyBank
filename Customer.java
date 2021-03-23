import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a customer at a bank and all the transactions they are allowed to make.
 * @author Elijah Pele
 * @version 3.0
 * @since March 03, 2021
 */
public class Customer extends Person {
	
	private String id;
	private Account[] acc;
	private File log;
	
	public Customer() {
		acc = new Account[3];
		acc[0]= new Checking();
		acc[1] = new Savings();
		acc[2] = new Credit();
	}
	
	/**
	 * Creates a customer based on the information provided by the CSV file.
	 * @param first
	 * @param last
	 * @param dob
	 * @param id
	 * @param addy
	 * @param pNum
	 */
	public Customer(String first, String last, String dob, String addy, String pNum) {
		super(first, last, dob, addy, pNum);
		acc = new Account[3];
		acc[1] = new Savings();
	}
	public Customer(String first, String last, String dob, String id, String addy, String pNum) {
		super(first, last, dob, addy, pNum);
		this.id = id;
		acc = new Account[3];
		acc[0]= new Checking();
		acc[1] = new Savings();
		acc[2] = new Credit();
	}
	
	/**
	 * Withdraws money from the given account as long as the amount is not negative and there are sufficient funds available.
	 * @param n
	 * @param amount
	 * @return s
	 * @throws IOException 
	 */
	public String withdraw(int n, String a) throws IOException {			
		String s = "";
		if(a.equals("")) {
			s = "Input Error: Empty String.";
			return s;
		}
		double amount = Double.parseDouble(a);
		boolean success = false;
		if(n==2) {
			if(acc[n].getBalance()+amount> 0) {
				success = false;		
				}
			else if(amount<0) {
				success = false;
				}

			else if(acc[n].getBalance()+amount< 0) {
				acc[n].setBalance(acc[n].getBalance()+amount);
				success= true;		
			}
		}
		if(n==1 || n==0) {
			if(amount<0) {
				success = false;
				s = "Witdraw Error: Can not withdraw a negative amount.";
				return s;
				}
			else if(amount>acc[n].getBalance()) {
				success = false;
				s = "Input Error: Insufficient Funds.";
				return s;
			}
			else {
				acc[n].setBalance(acc[n].getBalance() -amount);
				success = true;
			}
		}
		if(success == false) {
			s = "Withdraw Input Error: Please try again..";
			return s;
		}
		else {
			this.log("Wihdraw of $"+amount+" from "+acc[n].getAccDetails());
			s = this.fullName()+" made a wihdraw of $"+amount+" from "+acc[n].getAccDetails()+"."+this.fullName()+" "+acc[n].getBalanceDetails();
			//System.out.println(this.fullName()+" made a wihdraw of $"+amount+" from "+acc[n].getAccDetails()+"."+this.fullName()+" "+acc[n].getBalanceDetails());
		}
		return s;
	}
	
	/**
	 * Deposits money in to a given account as long as the amount is not negative and the limit on the account is not reached.
	 * @param n
	 * @param amount
	 * @return s
	 * @throws IOException 
	 */
	public String deposit(int n, String a) throws IOException {
		String s = "";
		if(a.equals("")) {
			s = ("Input Error: Empty String.");
			return s;
		}
		boolean success = false;
		double amount = Double.parseDouble(a);
		if(n==2) {
			if(amount<0) {
				success=false;
			}
			else if(-1*amount+acc[n].getBalance()< -1*((Credit)acc[n]).getLimit()) {
				s = "Deposit Over Lmit: Can not deposit this amount in to credit account.";
				success=false;
				return s;
			}
			else {
				acc[n].setBalance(acc[n].getBalance()-amount);
				success= true;
			}
		}
		if(n==0 || n==1) {
			if(amount<0) {
				success = false;
				s = "Deposit Error: Can not deposit a negative amount.";
				return s;
			}
			else{
				acc[n].setBalance(acc[n].getBalance() + amount);
				success = true;
			}
		}
		if(success == false) {
			s = "Deposit Input Error: Try Again.";
		}
		else {
			s = this.fullName()+" made a deposit of $"+amount+" on "+acc[n].getAccDetails()+"."+this.fullName()+" "+acc[n].getBalanceDetails();
			this.log("Deposit of $"+amount+" on "+acc[n].getAccDetails());
			//System.out.println(this.fullName()+" made a deposit of $"+amount+" on "+acc[n].getAccDetails()+"."+this.fullName()+" "+acc[n].getBalanceDetails());
		}
		return s;
	}
	
	/**
	 * Pays another customer in the bank using a specified account to send money from.
	 * @param c
	 * @param n
	 * @param amount
	 * @return
	 * @throws IOException 
	 */
	public String[] pay(Customer c, int n, int m, String a) throws IOException {
		String [] s = {"",""};
		if(c==null) {
			s[0]="Could not find the account that you wish to pay.";
			return s;
		}
		if(a.equals("")) {
			s[0] = "Input Error: Empty String.";
			return s;
		}
		double amount = Double.parseDouble(a);
		boolean success = false;
		if(c.fullName().equals(this.fullName())) {
			s[0] = "Input Error: You can not pay yourself.";
			return s;
		}
		if(n==2) {
			if(acc[n].getBalance()+amount> 0) {
				success = false;		
				}
			else if(amount<0) {
				success = false;
				}

			else if(acc[n].getBalance()+amount< 0) {
				acc[n].setBalance(acc[n].getBalance()+amount);
				c.acc[m].setBalance(c.acc[m].getBalance()+amount);
				success= true;		
			}
		}
		if(n==0 ||n==1) {
			if(amount<0) {
				s[0]="Pay Error: Can not pay a negative amount.";
				success = false;	
				return s;
			}
			else if(amount>acc[n].getBalance()) {
				success = false;
				s[0] = "Pay Error: Insufficient funds to pay.";
				return s;
				}
			else {
				c.acc[m].setBalance(c.acc[m].getBalance() +amount);
				acc[n].setBalance(acc[n].getBalance() -amount);
				success = true;
			}
		}
		if(success == false) {
			s[0] = "Pay Input Error: Try Again..";
		}
		else {
			s[0]= this.fullName()+" paid "+c.fullName()+" $"+amount+" from "+acc[n].getAccDetails()+"."+this.fullName()+" "+acc[n].getBalanceDetails();
			s[1] = c.fullName()+" recieved "+" $"+amount+" from "+this.fullName()+". " +c.fullName()+" "+ c.getAccount(m).getBalanceDetails();
			this.log("Paid "+c.fullName()+" $"+amount+" from "+acc[n].getAccDetails());
			c.log("Recieved "+" $"+amount+" from "+this.fullName());
			//System.out.println(this.fullName()+" paid "+c.fullName()+" $"+amount+" from "+acc[n].getAccDetails()+"."+this.fullName()+" "+acc[n].getBalanceDetails());
			//System.out.println(c.fullName()+" recieved "+" $"+amount+" from "+this.fullName()+". " +c.fullName()+" "+ c.getAccount(0).getBalanceDetails());
		}
		return s;
	}
	
	/**
	 * Inquires the balance on a given account.
	 * @param n
	 * @return s
	 * @throws IOException 
	 */
	public String inquire(int n) throws IOException {
		String s = "";
		if(n < 0 || n>2) {
			s = "Invalid Input";
			return s;
		}
		double amount = acc[n].getBalance();
		this.log("Balance inquiry on "+acc[n].getAccDetails());
		s = this.fullName()+" made a balance inquiry on "+acc[n].getAccDetails()+"."+this.fullName()+" "+acc[n].getBalanceDetails();
		//System.out.println(this.fullName()+" made a balance inquiry on "+acc[n].getAccDetails()+"."+this.fullName()+" "+acc[n].getBalanceDetails());
		return s;
	}
	
	/**
	 * Allows individuals to transfer funds across their personal accounts.
	 * @param send
	 * @param rec
	 * @param amount
	 * @return s
	 * @throws IOException 
	 */
	public String transfer(int send, int rec, String a) throws IOException {
		String s = "";
		if(a.equals("")) {
			s = "Input Error: Empty String.";
			return s;
		}
		double amount = Double.parseDouble(a);
		boolean success = false;
		if(send==rec) {
			s = "Input Error: Transferer and transferee must be different accounts.";
			return s;
		}
		if(send==2) {
				if(acc[send].getBalance()+amount> 0) {
					success = false;		
					}
				else if(amount<0) {
					success = false;
				}
				else if(acc[send].getBalance()+amount< 0) {
					acc[send].setBalance(acc[send].getBalance()+amount);
					acc[rec].setBalance(amount+acc[rec].getBalance());
					success= true;		
				}	
		}
		else if(rec==2) {
			if(amount<0) {
				success=false;
			}
			else if(-1*amount+acc[rec].getBalance()< ((Credit)acc[rec]).getLimit()) {
				success=false;
			}
			else {
				acc[rec].setBalance(acc[rec].getBalance()-amount);
				acc[send].setBalance(acc[send].getBalance()-amount);

				success= true;
			}
		}    
		else if(send==0&&rec==1 || send==1&&rec==0) {
			if(amount<0) {
				success = false;
			}
			if(amount>acc[send].getBalance()) {
				success = false;
			}
			else {
				acc[rec].setBalance(amount+acc[rec].getBalance());
				acc[send].setBalance(acc[send].getBalance()-amount);
				success = true;
			}
		}
		if(success==false) {
			s ="Transfer Input Error: Try Again..";
		}
		else {
			this.log("Transfered $"+amount+" from "+acc[send].getAccDetails()+" to "+acc[rec].getAccDetails());
			s = this.fullName()+" transfered $"+amount+" from "+acc[send].getAccDetails()+" to "+acc[rec].getAccDetails()+". "+this.fullName()+" "+acc[rec].getBalanceDetails()+". "+this.fullName()+" "+acc[send].getBalanceDetails();
			//System.out.println(this.fullName()+" transfered $"+amount+" from "+acc[send].getAccDetails()+" to "+acc[rec].getAccDetails()+". "+this.fullName()+" "+acc[rec].getBalanceDetails()+". "+this.fullName()+" "+acc[send].getBalanceDetails());	
			}
		return s;
	}
	
	/**
	 * Finds and returns an account in the Accounts array specified by a given index.
	 * @param n
	 * @return account
	 */
	public Account getAccount(int n) {
		if(this.acc[n] == null)
			return null;
		return this.acc[n];
	}
	
	public void setAccount(int n, String accountNumber, String a, String creditLimit) {
		long accNum = Long.parseLong(accountNumber);
		double amount = Double.parseDouble(a); 
		double limit = Double.parseDouble(creditLimit);
		if(n==2) {
			Account credit = new Credit(accNum, amount, limit);
			acc[2] = credit;
		}
	}
	
	/**
	 * Fills in all the information needed to set up a new account.
	 * @param n
	 * @param accountNumber
	 * @param a
	 */
	public void setAccount(int n, String accountNumber, String a) {
		long accNum = Long.parseLong(accountNumber);
		double amount = Double.parseDouble(a); 
		if(n==0) {
			Account checking = new Checking(accNum, amount);
			acc[0] = checking;
		}
		if(n==1) {
			Account savings = new Savings(accNum, amount);
			acc[1] = savings;
		}
		if(n==2) {
			Account credit = new Credit(accNum, amount);
			acc[2] = credit;
		}
	}
	public void setLog() throws IOException{
		File f = new File(this.getLastName()+"_"+this.getFirstName()+"_Log"+".txt");
		this.log = f;
	}
	public File getLog() throws IOException{
		return this.log;
	}
	
	public void log(String line) throws IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
		LocalDateTime now = LocalDateTime.now();  
		BufferedWriter bw = new BufferedWriter(new FileWriter(this.log, true));
		bw.append(line+" --- "+dtf.format(now));
		bw.newLine();
		bw.close();
	}
	public void setID(String id) {
		this.id = id;
	}
	
	/**
	 * Returns the accounts ID.
	 * @return iD
	 */
	public String getiD() {
		return this.id;
	}
	
	public void openCheckingAccount() {
		acc[0] = new Checking();
	}
	public void openCreditAccount() {
		acc[2] = new Credit();
	}
}
