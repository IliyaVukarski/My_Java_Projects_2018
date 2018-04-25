package online_store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.Current;

public class DatabaseManagemet {
	private final String db_Connection;
	private final String db_Username;
	private final String db_Password;
	
	public DatabaseManagemet(String db_Connection, String db_Username,
			String db_Password) {
		this.db_Connection = db_Connection;
		this.db_Username = db_Username;
		this.db_Password = db_Password;
	}
	
	public void addNewUser(Account account) throws SQLException {
		try(Connection connection = 
						DriverManager.getConnection(db_Connection, 
													db_Username, 
													db_Password)) {
			List<String> allUsers = new ArrayList<>();
			Statement statement = connection.createStatement();
			ResultSet resultSet = 
							statement.executeQuery("SELECT "
												 + "username "
												 + "FROM "
												 + "db_online_store.credentials ");
			while(resultSet.next()) {
				allUsers.add(resultSet.getString("username"));
			}
			if(!allUsers.contains(account.getCredentials().getUsername())) {
				insertAccount(account);
				System.out.println("Successfully added new user to database");
			}else {
				System.out.println("This username already exists in the system!" +
								   "Please choose another one!");
			}
		}
	}
	
	public void insertAccount(Account account) throws SQLException {
		int credentials_id = insertNewUser(account.getCredentials());
		try(Connection connection = 
					DriverManager.getConnection(db_Connection, 
												db_Username, 
												db_Password)) {
			PreparedStatement preparedStatement = 
					connection.prepareStatement("INSERT INTO "
											  + "db_online_store.account(name, acc_iban, balance, credentials_id) "
											  + "VALUES "
											  + "(?, ?, ?, ?) ");
			
			preparedStatement.setString(1, account.getName());
			preparedStatement.setString(2, account.getAccountIBAN());
			preparedStatement.setDouble(3, account.getBalance());
			preparedStatement.setInt(4, credentials_id);
			preparedStatement.execute();
		}
	}
	
	public int insertNewUser(Credentials credentials) throws SQLException {
		try(Connection connection = DriverManager.getConnection(db_Connection, db_Username, db_Password)) {
			LocalDateTime timeOfRegister = LocalDateTime.now();
			credentials.setTimeOfRegister(timeOfRegister);
			String date = timeOfRegister.toString();
			PreparedStatement preparedStatement = 
					connection.prepareStatement("INSERT INTO "
											  + "db_online_store.credentials(username, password, time_register) "
											  + "VALUES "
											  + "(?, ?, ?) ");
			preparedStatement.setString(1, credentials.getUsername());
			preparedStatement.setString(2, credentials.getPassword());
			preparedStatement.setString(3, date);
			preparedStatement.execute();
			
			try(ResultSet resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()")) {
				resultSet.next();
				return resultSet.getInt(1);
			}
		}
	}
	
	public List<Product> selectAllProducts() throws SQLException {
		List<Product> allProducts = new ArrayList<Product>();
		try(Connection connection = DriverManager.getConnection(db_Connection, db_Username, db_Password)) {
			Statement statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery("SELECT "
													   + "product_name, "
													   + "product_price,"
													   + "product_price_with_tax "
													   + "FROM "
													   + "db_online_store.product ");
			while(resultSet.next()) {
				allProducts.add(new Product(resultSet.getString("product_name"),
						resultSet.getDouble("product_price"),
						resultSet.getDouble("product_price_with_tax")));
			}
		}
		return allProducts;
	}
	
	public List<Account> allAcounts() throws SQLException {
		List<Account> allAccounts = new ArrayList<Account>();
		try(Connection connection = DriverManager.getConnection(db_Connection, db_Username, db_Password)) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT "
														+ "name, "
														+ "acc_iban, "
														+ "balance "
														+ "FROM "
														+ "db_online_store.account ");
			while(resultSet.next()) {
				allAccounts.add(new Account(resultSet.getString("name"),
										    resultSet.getString("acc_iban"),
										    resultSet.getDouble("balance")));
			}
		}
		return allAccounts;
	}
	
	public void tryToBuy(Product product, Account account) throws SQLException {
		try(Connection connection = DriverManager.getConnection(db_Connection, db_Username, db_Password)) {
			
			
			List<Product> allProducts = selectAllProducts();
			List<Account> allAccounts = allAcounts();
			
			Product currentProduct = null;
			
			Account currentAccount = null;
			
			if(hasAccess(account)) {
				for(int index = 0; index < allProducts.size(); index++) {
					if(product.getProductName().equalsIgnoreCase(allProducts.get(index).getProductName())) {
						currentProduct = allProducts.get(index);
					}
				}
				
				for(int index = 0; index < allAccounts.size(); index++) {
					if(account.getName().equalsIgnoreCase(allAccounts.get(index).getName())) {
						currentAccount = allAccounts.get(index);
						break;
					}
				}
				
				if(currentAccount.getBalance() >= currentProduct.getProductPrice()) {
					PreparedStatement preparedStatement1 = 
							connection.prepareStatement("UPDATE "
													  + "db_online_store.account "
													  + "SET "
													  + "balance = balance - ?, "
													  + "total_price = total_price + ? "
													  + "WHERE "
													  + "account.name = ? ");
					preparedStatement1.setDouble(1, currentProduct.getProductPriceWithTax());
					preparedStatement1.setDouble(2, currentProduct.getProductPriceWithTax());
					preparedStatement1.setString(3, account.getName());
					preparedStatement1.execute();
					
					PreparedStatement preparedStatement2 = 
							connection.prepareStatement("UPDATE "
													  + "db_online_store.account "
													  + "SET "
													  + "product_bought = ? "
													  + "WHERE "
													  + "account.name = ? ");
					preparedStatement2.setString(1, currentProduct.getProductName());
					preparedStatement2.setString(2, account.getName());
					preparedStatement2.execute();
				}
			}else {
				System.out.println("Operation terminated");
			}
		}
	}
	
	public boolean hasAccess(Account account) throws SQLException {
		boolean authorize = true;
		try(Connection connection = DriverManager.getConnection(db_Connection, db_Username, db_Password)) {
			List<Credentials> allUsers = new ArrayList<>();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT "
													   + "username, "
													   + "password "
													   + "FROM "
													   + "db_online_store.credentials ");
			while(resultSet.next()) {
				allUsers.add(new Credentials(resultSet.getString("username"),
						resultSet.getString("password")));
			}
			for(int index = 0; index < allUsers.size(); index++) {
				if(account.getCredentials().getUsername().equalsIgnoreCase(allUsers.get(index).getUsername()) &&
						account.getCredentials().getPassword().equalsIgnoreCase(allUsers.get(index).getPassword())) {
					authorize = true;
					System.out.println("Access granted! ");
				}else if(account.getCredentials().getUsername() != (allUsers.get(index).getUsername()) || 
						account.getCredentials().getPassword() != (allUsers.get(index).getPassword())) {
					authorize = false;
					System.out.println("Access denied! ");
				}
			}
		}
		return authorize;
	}
	
	public void insertProduct(Product product) throws SQLException {
		double tax = product.getProductPrice() * 0.2;
		double productPriceWithTax = product.getProductPrice() + tax;
		try(Connection connection = 
				DriverManager.getConnection(db_Connection, db_Username, db_Password)) {
			PreparedStatement preparedStatement = 
					connection.prepareStatement("INSERT INTO "
											  + "db_online_store.product(product_name, product_price, product_price_with_tax) "
											  + "VALUES "
											  + "(?, ?, ?) ");
			preparedStatement.setString(1, product.getProductName());
			preparedStatement.setDouble(2, product.getProductPrice());
			preparedStatement.setDouble(3, productPriceWithTax);
			preparedStatement.execute();
		}
	}
	
	public void printAllProducts(Account account) throws SQLException {
		ResultSet resultSet = null;
		try(Connection connection = DriverManager.getConnection(db_Connection, db_Username, db_Password)) {
			PreparedStatement preparedStatement = 
					connection.prepareStatement(
								 "SELECT "
							   + "account.product_bought "
							   + "FROM "
							   + "db_online_store.account "
							   + "WHERE name = ?");
			preparedStatement.setString(1, account.getName());
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				System.out.println(resultSet.getString("product_bought"));
			}
		}
	}
}
