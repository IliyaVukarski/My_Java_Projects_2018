package article;

import java.time.LocalDateTime;

public class AdministratorUser extends UserCredentials {
	public AdministratorUser() {
		
	}
	
	public AdministratorUser(String username, String password) {
		super(username, password);
	}
	
	public AdministratorUser(String username, String password, LocalDateTime dateOfRegister) {
		super(username, password, dateOfRegister);
	}
}
