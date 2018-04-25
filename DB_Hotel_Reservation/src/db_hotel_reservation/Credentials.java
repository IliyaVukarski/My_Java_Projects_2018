package db_hotel_reservation;

import java.time.LocalDateTime;

public class Credentials {
	private String username;
	private String password;
	private LocalDateTime timeOfRegister;
	
	public Credentials() {
		
	}
	
	public Credentials(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Credentials(String username, String password,
			LocalDateTime timeOfRegister) {
		this.username = username;
		this.password = password;
		this.timeOfRegister = timeOfRegister;
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

	public LocalDateTime getTimeOfRegister() {
		return timeOfRegister;
	}

	public void setTimeOfRegister(LocalDateTime timeOfRegister) {
		this.timeOfRegister = timeOfRegister;
	}
}
