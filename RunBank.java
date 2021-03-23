import java.io.IOException;

/**
 * This class will run the main method for the Bank project.
 * @author Elijah Pele
 * @version 3.0
 * @since March 03, 2021
 */
	public class RunBank  {
		
		public static void main(String [] args) throws IOException   {
			Bank disneyBank = new Bank();
			disneyBank.readCustomerFile();
			disneyBank.runConsole();
		}
	}
			