package bank_management;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankManagement {
	private static final String DB_CONNECTION = 
	 "jdbc:mysql://localhost:3306/db_bank_management"
	+"?autoReconnect=true&useSSL=false";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = 
								"sofiyabulgaria1989";
	
	public static void main(String[] args)
		throws SQLException {
		AccountManagement accountManagement = 
				new AccountManagement(DB_CONNECTION, 
									    DB_USERNAME, 
									    DB_PASSWORD);
		List<String> commands = new ArrayList<>();
		commands.add("END");
		commands.add("OPEN");
		commands.add("CLOSE");
		commands.add("DEPOSIT");
		commands.add("WITHDRAW");
		commands.add("TRANSFER");
		commands.add("CHECKASSETS");
		boolean isValid = true;
		while(true) {
			Account account = new Account();
			Credentials credentials = new Credentials();
			Scanner scanner = new Scanner(System.in);
			System.out.println(
				"Choose type of operation between "
			  + "END, OPEN, CLOSE, DEPOSIT, "
			  + "WITHDRAW, TRANSFER, CHECKASSETS");
			String[] userInputCommand = scanner.next().split(" ");
			
			if(userInputCommand[0].
			   equalsIgnoreCase(commands.get(0))) {
				break;
			}
			
			for(String com : userInputCommand) {
				if(com.equalsIgnoreCase(commands.get(1))) {
					System.out.println(
						"Enter username, password, client name," 
					  + "government id, account balance");
					credentials.setUsername(scanner.next());
					credentials.setPassword(scanner.next());
					account.setCredentials(credentials);
					account.setClientName(scanner.next());
					account.setGovernmentId(scanner.next());
					do {
						if(scanner.hasNextDouble()) {
							account.setAccountBalance(
							scanner.nextDouble());
							isValid = true;
						}else {
							System.out.println("Enter valid value");
							isValid = false;
							scanner.next();
						}
					}while(!isValid);
					accountManagement.openAccount(account);
				}else if(com.equalsIgnoreCase(commands.get(2))) {
					System.out.println(
						"Enter username, password and client name");
					credentials.setUsername(scanner.next());
					credentials.setPassword(scanner.next());
					account.setCredentials(credentials);
					account.setClientName(scanner.next());
					accountManagement.closeAccount(account);
				}else if(com.equalsIgnoreCase(commands.get(3))) {
					System.out.println(
						"Enter username, password and client name");
					credentials.setUsername(scanner.next());
					credentials.setPassword(scanner.next());
					account.setCredentials(credentials);
					account.setClientName(scanner.next());
					System.out.println("Enter amount to deposit");
					double amount = 0;
					do {
						if(scanner.hasNextDouble()) {
							amount = scanner.nextDouble();
							isValid = true;
						}else {
							System.out.println("Enter valid value");
							isValid = false;
							scanner.next();
						}
					}while(!isValid);
					accountManagement.deposit(amount, account);
				}else if(com.equalsIgnoreCase(commands.get(4))) {
					System.out.println(
						"Enter username, password and client name");
					credentials.setUsername(scanner.next());
					credentials.setPassword(scanner.next());
					account.setCredentials(credentials);
					account.setClientName(scanner.next());
					System.out.println("Enter amount to withdraw");
					double amount = 0;
					do {
						if(scanner.hasNextDouble()) {
							amount = scanner.nextDouble();
							isValid = true;
						}else {
							System.out.println("Enter valid value");
							isValid = false;
							scanner.next();
						}
					}while(!isValid);
					accountManagement.withdraw(amount, account);
				}else if(com.equalsIgnoreCase(commands.get(5))) {
					System.out.println(
						 "Enter username, password " 
						+"and client name of the remitter");
					credentials.setUsername(scanner.next());
					credentials.setPassword(scanner.next());
					account.setCredentials(credentials);
					account.setClientName(scanner.next());
					Account accRecipient = new Account();
					System.out.println("Enter amount to transfer");
					double amount = 0;
					do {
						if(scanner.hasNextDouble()) {
							amount = scanner.nextDouble();
							isValid = true;
						}else {
							System.out.println("Enter valid value");
							isValid = false;
							scanner.next();
						}
					}while(!isValid);
					System.out.println(
						"Enter client name of the recipient.");
					accRecipient.setClientName(scanner.next());
					accountManagement.transfer(
								amount, account, accRecipient);
				}else if(com.equalsIgnoreCase(commands.get(6))) {
					accountManagement.calculateAssets();
				}else {
					System.out.println("Wrong command.");
				}
			}
		}
	}
}