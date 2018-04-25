package online_store;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainStoreManagement {
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/db_online_store";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "sofiyabulgaria1989";
	
	public static void main(String[] args) throws SQLException {
		DatabaseManagemet databaseManagemet =
				new DatabaseManagemet(DB_CONNECTION, DB_USERNAME, DB_PASSWORD);
	
		List<String> commands = new ArrayList<>();
		commands.add("END");
		commands.add("INSERT_PRODUCT");
		commands.add("INSERT_ACCOUNT");
		commands.add("BUY_PRODUCT");
		Scanner scanner = new Scanner(System.in);
		String[] inputCommand = null;
		while(true) {
			Product product = new Product();
			Account account = new Account();
			Credentials credentials = new Credentials();
			System.out.println("END, INSERT_PRODUCT, INSERT_ACCOUNT, BUY_PRODUCT");
			inputCommand = scanner.next().split(" ");
			boolean isValid = true;
			if(inputCommand[0].equalsIgnoreCase(commands.get(0))) {
				break;
			}
			
			for(String comm : inputCommand) {
				if(comm.equalsIgnoreCase(commands.get(1))) {
					System.out.println("Enter product name");
					String productName = scanner.next();
					System.out.println("Enter product price");
					double productPrice = 0;
					do {
						if(scanner.hasNextDouble()) {
							productPrice = scanner.nextDouble();
							isValid = true;
						}else {
							System.out.println("Enter valid value");
							scanner.next();
							isValid = false;
						}
					}while(!isValid);

					product.setProductName(productName);
					product.setProductPrice(productPrice);
					product.setProductPriceWithTax(productPrice + productPrice * 0.2);
					databaseManagemet.insertProduct(product);
				}else if(comm.equalsIgnoreCase(commands.get(2))) {
					System.out.println("Enter name");
					String name = scanner.next();
					System.out.println("Enter account IBAN");
					String accountIBAN = scanner.next();
					System.out.println("Enter account balance");
					double balance = 0;
					do {
						if(scanner.hasNextDouble()) {
							balance = scanner.nextDouble();
							isValid = true;
						}else {
							System.out.println("Enter valid value");
							scanner.next();
							isValid = false;
						}
					}while(!isValid);
					List<Product> productsBought = new ArrayList<Product>();
					System.out.println("Enter username");
					String username = scanner.next();
					System.out.println("Enter password");
					String password = scanner.next();
					
					credentials.setUsername(username);
					credentials.setPassword(password);
					
					account.setName(name);
					account.setAccountIBAN(accountIBAN);
					account.setBalance(balance);
					account.setProductsBought(productsBought);
					account.setCredentials(credentials);
					databaseManagemet.insertAccount(account);
				}else if(comm.equalsIgnoreCase(commands.get(3))) {
					System.out.println("Enter product name");
					String productName = scanner.next();
					System.out.println("Enter account name");
					String name = scanner.next();
					
					System.out.println("Enter username");
					String username = scanner.next();
					System.out.println("Enter password");
					String password = scanner.next();

					product.setProductName(productName);
					credentials.setUsername(username);
					credentials.setPassword(password);
					account.setName(name);
					account.setCredentials(credentials);
					databaseManagemet.tryToBuy(product, account);
					databaseManagemet.printAllProducts(account);
				}else {
					System.out.println("Unknown command");
				}
			}
		}
	}
}