package article;
import java.time.LocalDateTime;

public class RegularUser extends UserCredentials {
	public RegularUser() {
		
	}
	
	public RegularUser(String username, String password) {
		super(username, password);
	}
	
	public RegularUser(String username, String password, LocalDateTime dateOfRegister) {
		super(username, password, dateOfRegister);
	}
}
