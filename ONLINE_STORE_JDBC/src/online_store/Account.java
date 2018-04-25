package online_store;

import java.util.List;

public class Account {
	private String name;
	private String accountIBAN;
	private double balance;
	private List<Product> productsBought;
	private Credentials credentials;

	public Account() {
		
	}
	
	public Account(String name, String accountIBAN, double balance) {
		this.name = name;
		this.accountIBAN = accountIBAN;
		this.balance = balance;
	}
	
	public Account(String name, String accountIBAN, double balance,
			List<Product> productsBought, Credentials credentials) {
		this.name = name;
		this.accountIBAN = accountIBAN;
		this.balance = balance;
		this.productsBought = productsBought;
		this.credentials = credentials;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountIBAN() {
		return accountIBAN;
	}

	public void setAccountIBAN(String accountIBAN) {
		this.accountIBAN = accountIBAN;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public List<Product> getProductsBought() {
		return productsBought;
	}

	public void setProductsBought(List<Product> productsBought) {
		this.productsBought = productsBought;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
}