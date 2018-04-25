package bank_management;

public class Account {
	private String clientName;
	private String governmentId;
	private double accountBalance;
	private Credentials credentials;
	
	public Account() {
		
	}
	
	public Account(String clientName, 
		       String governmentId, 
		       double accountBalance) {
		this.clientName = clientName;
		this.governmentId = governmentId;
		this.accountBalance = accountBalance;
	}
	
	public Account(String clientName, 
				  String governmentId, 
				  double accountBalance, 
				  Credentials credentials) {
								
		this.clientName = clientName;
		this.governmentId = governmentId;
		this.accountBalance = accountBalance;
		this.credentials = credentials;
	}
	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getGovernmentId() {
		return governmentId;
	}

	public void setGovernmentId(
					String governmentId) {
		this.governmentId = governmentId;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(
						double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(
					Credentials credentials) {
		this.credentials = credentials;
	}
}
