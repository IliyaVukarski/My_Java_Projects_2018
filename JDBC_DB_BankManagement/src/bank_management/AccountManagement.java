package bank_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountManagement {
	private final String db_CONNECTION;
	private final String db_USERNAME;
	private final String db_PASSWORD;

	public AccountManagement(String db_CONNECTION, 
							 String db_USERNAME, 
							 String db_PASSWORD) {
		this.db_CONNECTION = db_CONNECTION;
		this.db_USERNAME = db_USERNAME;
		this.db_PASSWORD = db_PASSWORD;
	}
	
	public boolean hasAccess(Account account) 
	    throws SQLException {
		boolean flag = true;
		try(Connection connection = 
				DriverManager.getConnection(db_CONNECTION, 
										    db_USERNAME, 
											db_PASSWORD)) {
			List<Credentials> userInfo = new ArrayList<>();
			Statement statement = 
						connection.createStatement();
			ResultSet resultSet = 
				statement.executeQuery("SELECT "
									 + "username, "
									 + "password "
									 + "FROM "
						             + "db_bank_management."
									 + "credentials ");
			while(resultSet.next()) {
				userInfo.add(
						new Credentials(
							resultSet.getString("username"), 
							resultSet.getString("password")));
			}
			
			for(int index = 0; index < userInfo.size(); index++) {
				if(account.getCredentials().getUsername().
					equalsIgnoreCase(
						userInfo.get(index).getUsername()) &&
				   account.getCredentials().getPassword().
						equalsIgnoreCase(
						userInfo.get(index).getPassword())) {
					System.out.println("Access granted!");
					flag = true;
					continue;
				}else if(account.getCredentials().getUsername().
							equalsIgnoreCase(
							userInfo.get(index).getUsername()) &&
						account.getCredentials().getPassword() != 
							(userInfo.get(index).getPassword())) {
					System.out.println("Access denied! "
									  +"Passwords don't match.");
					flag = false;
				}
			}
		}
		return flag;
	}
	
	public void printInfoAboutAccount(Account account)
		throws SQLException {
		Account currentAccount = null;
		ResultSet resultSet = null;
		try(Connection connection = 
			DriverManager.getConnection(db_CONNECTION, 
									    db_USERNAME, 
									    db_PASSWORD)) {
			PreparedStatement preparedStatement = 
				connection.prepareStatement("SELECT "
							  + "client_name, "
							  + "government_id, "
							  + "account_balance "
							  + "FROM "
							  + "db_bank_management."
							  + "accounts "
							  + "WHERE "
							  + "client_name = ? ");
			preparedStatement.setString(1, 
								account.getClientName());
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				currentAccount = new Account(
				resultSet.getString("client_name"), 
				resultSet.getString("government_id"), 
				resultSet.getDouble("account_balance"));
			}
		}
		
		System.out.println("Mr/Mrs " 
						+ currentAccount.getClientName() + 
						   " with id " 
						+ currentAccount.getGovernmentId() +
						   " has balance of " 
						+ currentAccount.getAccountBalance());
	}
	
	public void openAccount(Account account) 
		throws SQLException {
		try(Connection connection = 
			DriverManager.getConnection(db_CONNECTION, 
									    db_USERNAME, 
									    db_PASSWORD)) {
			List<String> allUsers = new ArrayList<>();
			Statement statement = connection.createStatement();
			ResultSet resultSet = 
				statement.executeQuery("SELECT "
								     + "username "
								     + "FROM "
								     + "db_bank_management."
									 + "credentials ");
			while(resultSet.next()) {
				allUsers.add(resultSet.getString("username"));
			}
			
			if(!allUsers.contains(
					account.getCredentials().getUsername())) {
				insertAccount(account);
				System.out.printf("Successfully opened "
								 +"new account for client %s "
								 +"with username %s, "
								 +"on date %s.", 
									account.getClientName(), 
									account.getCredentials().
									getUsername(),
									account.getCredentials().
									getTimeRegister());
			}else { 
				System.out.println(
							"This username already "
						  + "exists in the system! "
						  + "Please choose a different one.");
			}
		}
	}
	
	public void closeAccount(Account account) 
		throws SQLException {
		String deletedUser = 
				account.getCredentials().getUsername();
		String deletedClient = account.getClientName();
		if(hasAccess(account)) {
			try(Connection connection = 
				DriverManager.getConnection(db_CONNECTION, 
										    db_USERNAME,
									        db_PASSWORD)) {
				PreparedStatement preparedStatement = 
							connection.prepareStatement(
								  "DELETE "
							    + "db_bank_management."
							    + "accounts, "
							    + "db_bank_management."
								+ "credentials "
							    + "FROM "
							    + "accounts "
							    + "INNER JOIN "
								+ "credentials "
							    + "WHERE "
								+ "credentials.username = ? "
							    + "AND "
							    + "accounts.client_name = ? ");
				preparedStatement.setString(1, deletedUser);
				preparedStatement.setString(2, deletedClient);
				preparedStatement.execute();
				System.out.printf("Account of client %s "
								+ "with username %s "
								+ "has been closed "
								+ "successfully. ", 
								deletedClient, deletedUser);
			}
		}
	}
	
	public void deposit(double amount, Account account) 
		throws SQLException {
		if(hasAccess(account)) {
			if(amount > 0) {
				try(Connection connection = 
					DriverManager.getConnection(db_CONNECTION, 
											    db_USERNAME, 
											    db_PASSWORD)) {
					PreparedStatement preparedStatement = 
					connection.prepareStatement(
								    "UPDATE "
								  + "db_bank_management."
								  + "accounts "
								  + "SET "
								  + "account_balance = "
								  + "account_balance + ? "
							      + "WHERE "
								  + "client_name = ? ");
					preparedStatement.setDouble(1, 
									amount);
					preparedStatement.setString(2, 
									account.getClientName());
					preparedStatement.execute();
					printInfoAboutAccount(account);
				}
			}else {
				System.out.println("Can't accept "
								 + "negative amount!");
			}
		}else {
			System.out.println("Access denied! ");
		}
	}
	
	public void withdraw(double amount, Account account) 
		throws SQLException {
		if(hasAccess(account)) {
			try(Connection connection = 
				DriverManager.getConnection(db_CONNECTION, 
									        db_USERNAME, 
											db_PASSWORD)) {
				List<Account> accounts = new ArrayList<>();
				Statement statement = 
							  connection.createStatement();
				ResultSet resultSet = 
				  statement.executeQuery("SELECT "
									   + "client_name, "
									   + "government_id, "
									   + "account_balance "
									   + "FROM "
									   + "db_bank_management."
									   + "accounts ");
				while(resultSet.next()) {
					accounts.add(
						new Account(
						resultSet.getString("client_name"), 
						resultSet.getString("government_id"),
						resultSet.getDouble("account_balance")));
				}
				
				for(int index = 0; index < accounts.size(); index++) {
					if(account.getClientName().
						equalsIgnoreCase(
							accounts.get(index).getClientName())) {
						if(amount > 0 && 
							accounts.get(index).
							getAccountBalance() >= amount) {
							PreparedStatement preparedStatement = 
							connection.prepareStatement(
											 "UPDATE "
										   + "db_bank_management."
										   + "accounts "
										   + "SET "
										   + "account_balance = "
										   + "account_balance - ? "
										   + "WHERE "
										   + "client_name = ? ");
							preparedStatement.setDouble(1, 
														   amount);
							preparedStatement.setString(2, 
										  account.getClientName());
							preparedStatement.execute();
							printInfoAboutAccount(account);
							break;
						}else {
							System.out.println("You don't have "
											 + "enough money in "
											 + "your account! ");
						}
					}else {
						System.out.println("Client doesn't "
										 + "exist in the system");
					}
				}
			}
		}else {
			System.out.println("Access denied! ");
		}
	}
	
	public void transfer(double amount, 
						 Account remitter, 
						 Account recipient) 
						 throws SQLException {
		if(hasAccess(remitter)) {
			try(Connection connection = 
				DriverManager.getConnection(db_CONNECTION, 
									        db_USERNAME, 
											db_PASSWORD)) {
				List<Account> accounts = new ArrayList<>();
				
				Statement statement = 
						connection.createStatement();
				ResultSet resultSet = 
				statement.executeQuery("SELECT "
								     + "* "
								     + "FROM "
								     + "db_bank_management."
									 + "accounts ");
				while(resultSet.next()) {
					accounts.add(
					new Account(
					resultSet.getString("client_name"),
					resultSet.getString("government_id"),
					resultSet.getDouble("account_balance")));
				}
				
				for(int index = 0; index < accounts.size(); index++) {
					if(remitter.getClientName().
						equalsIgnoreCase(accounts.get(index).
						getClientName())) {
						if(amount > 0 && accounts.get(index).
							getAccountBalance() >= amount) {
							PreparedStatement preparedStatement = 
							connection.prepareStatement(
									"UPDATE " 
								  + "db_bank_management."
								  + "accounts " 
								  + "SET " 
							      + "account_balance = "
								  + "account_balance - ? "
								  + "WHERE " 
								  + "client_name = ? ");
							preparedStatement.setDouble(1, 
												amount);
							preparedStatement.setString(2, 
											remitter.getClientName());
							preparedStatement.execute();
							printInfoAboutAccount(accounts.get(index));
						}else {
							System.out.println("You don't have "
											  +"enough money "
											  +"in your account! ");
						}
					}
				}
				
				for(int index = 0; index < accounts.size(); index++) {
					if(recipient.getClientName().equalsIgnoreCase(
					   accounts.get(index).getClientName())) {
						if(amount > 0) {
							
							PreparedStatement preparedStatement2 = 
							connection.prepareStatement(
							  "UPDATE "
							+ "db_bank_management.accounts " 
							+ "SET " 
							+ "account_balance = account_balance + ? "
							+ "WHERE "
							      + "client_name = ? ");
							preparedStatement2.setDouble(1, 
											amount);
							preparedStatement2.setString(2, 
											recipient.getClientName());
							preparedStatement2.execute();
							printInfoAboutAccount(accounts.get(index));
						}else {
							System.out.println("Can't accept "
											  +"negative amount!");
						}
					}
				}
			}
		}else {
			System.out.println("Access denied!");
		}
	}
	
	public void insertAccount(Account account) 
		throws SQLException {
		int credential_id = 
			insertCredentials(account.getCredentials());
		try(Connection connection = 
			DriverManager.getConnection(db_CONNECTION, 
									    db_USERNAME, 
									    db_PASSWORD)) {
			PreparedStatement preparedStatement = 
				connection.prepareStatement(
								"INSERT INTO "
							  + "db_bank_management."
							  + "accounts("
							  + "client_name, "
							  + "government_id, " 
							  + "account_balance, " 
							  + "credentials_id) "
							  + "VALUES "
							  + "(?, ?, ?, ?) ");
			preparedStatement.setString(1,
							account.getClientName());
			preparedStatement.setString(2, 
							account.getGovernmentId());
			preparedStatement.setDouble(3, 
						  account.getAccountBalance());
			preparedStatement.setInt(4, credential_id);
			preparedStatement.execute();
		}
	}
	
	
	public int insertCredentials(Credentials credentials)
	    throws SQLException {
		LocalDateTime timeOfRegister = LocalDateTime.now();
		credentials.setTimeRegister(timeOfRegister);
		String date = timeOfRegister.toString();
		try(Connection connection = 
			DriverManager.getConnection(db_CONNECTION, 
									    db_USERNAME, 
									    db_PASSWORD)) {
			PreparedStatement preparedStatement = 
				connection.prepareStatement(
								"INSERT INTO "
							  + "db_bank_management."
							  + "credentials("
							  + "username, "								
							  + "password, "								
							  + "time_register) "
							  + "VALUES "
						      + "(?, ?, ?) ");
			preparedStatement.setString(1, 
							credentials.getUsername());
			preparedStatement.setString(2, 
							credentials.getPassword());
			preparedStatement.setString(3, 
							date);
			preparedStatement.execute();
			
			try(ResultSet resultSet =
					preparedStatement.
					executeQuery("SELECT "
							   + "LAST_INSERT_ID()")) {
				resultSet.next();
				return resultSet.getInt(1);
			}
		}
	}
	
	public void calculateAssets() 
		throws SQLException {
		double accountAssets = 0;
		try(Connection connection = 
			DriverManager.getConnection(db_CONNECTION, 
										db_USERNAME, 
										db_PASSWORD)) {
			Statement statement = 
						connection.createStatement();
			ResultSet resultSet =
				statement.executeQuery(
					"SELECT "
				  + "SUM(account_balance) "
				  + "AS account_assets "
			      + "FROM "
				  + "db_bank_management."
				  + "accounts ");
			while(resultSet.next()) {
				accountAssets = 
					resultSet.getDouble("account_assets");
			}
		}
		System.out.println("The current bàlance "
						 + "of the account assets is " 
						 + accountAssets);
	}
}