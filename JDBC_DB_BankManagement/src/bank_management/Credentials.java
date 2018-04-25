package bank_management;
import java.time.LocalDateTime;

public class Credentials {
	private String username;
	private String password;
	private LocalDateTime timeRegister;
	
	public Credentials() {
		
	}
	
	public Credentials(String username,
					   String password) {
		this.username = username;
		this.password = password;
	}
	
	public Credentials(String username, 
					  String password, 
				LocalDateTime timeRegister) {
		this.username = username;
		this.password = password;
		this.timeRegister = timeRegister;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getTimeRegister() {
		return timeRegister;
	}
	public void setTimeRegister(
				LocalDateTime timeRegister) {
		this.timeRegister = timeRegister;
	}
}
