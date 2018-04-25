package article;
import java.time.LocalDateTime;
import java.util.List;

public abstract class UserCredentials {
	private String username;
	private String password;
	private LocalDateTime dateOfRegister;
	
	public UserCredentials() {
		
	}
	public UserCredentials(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public UserCredentials(String username, String password,
			LocalDateTime dateOfRegister) {
		super();
		this.username = username;
		this.password = password;
		this.dateOfRegister = dateOfRegister;
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

	public LocalDateTime getDateOfRegister() {
		return dateOfRegister;
	}

	public void setDateOfRegister(LocalDateTime dateOfRegister) {
		this.dateOfRegister = dateOfRegister;
	}
	
	public void addNewUser(List<UserCredentials> userCredentialsRepo, UserCredentials credentials) {
		if(!userCredentialsRepo.contains(credentials.getUsername())) {
			LocalDateTime timeOfRegister = LocalDateTime.now();
			String date = timeOfRegister.toString();
			if(credentials instanceof RegularUser) {
				userCredentialsRepo.add(new RegularUser(credentials.getUsername(), credentials.getPassword(), timeOfRegister));
				System.out.printf("Successfully added new user %s to database register on %s.", credentials.getUsername(), date);
			}else if(credentials instanceof AdministratorUser) {
				userCredentialsRepo.add(new AdministratorUser(credentials.getUsername(), credentials.getPassword(), timeOfRegister));
				System.out.printf("Successfully added new user %s to database register on %s.", credentials.getUsername(), date);
			}else {
				System.out.println("Error! No such type of user in the system! ");
			}
		}else {
			System.out.println("This username already exists in the system! ");
		}
	}
	
	public void removeUser(List<UserCredentials> userCredentialsRepo, UserCredentials credentials) {
		String deletedUser = credentials.getUsername();
		for(int index = 0; index < userCredentialsRepo.size(); index++) {
			if(userCredentialsRepo.get(index).getUsername().equalsIgnoreCase(credentials.getUsername()) &&
					userCredentialsRepo.get(index).getPassword().equalsIgnoreCase(credentials.getPassword())) {
				userCredentialsRepo.remove(index);
				System.out.printf("Successfully removed user %s from database.", deletedUser);
			}else if(userCredentialsRepo.get(index).getUsername().equalsIgnoreCase(credentials.getUsername()) &&
					userCredentialsRepo.get(index).getPassword() != (credentials.getPassword())) {
				System.out.println("Wrong password! ");
				continue;
			}
		}
	}
	
	public void changePassword(List<UserCredentials> userCredentialsRepo, UserCredentials credentials, String newPassword) {
		for(int index = 0; index < userCredentialsRepo.size(); index++) {
			if(userCredentialsRepo.get(index).getUsername().equalsIgnoreCase(credentials.getUsername()) &&
					userCredentialsRepo.get(index).getPassword().equalsIgnoreCase(credentials.getPassword())) {
				userCredentialsRepo.get(index).setPassword(newPassword);
				System.out.printf("User %s has successfully changed his password! ", credentials.getUsername());
			}else if(userCredentialsRepo.get(index).getUsername().equalsIgnoreCase(credentials.getUsername()) &&
					userCredentialsRepo.get(index).getPassword() != (credentials.getPassword())) {
				System.out.println("Wrong password");
				continue;
			}
		}
	}
	
	public boolean authorizeUser(UserCredentials credentials, Article article) {
		boolean authorizeUser = true;
		if(article.getUserPosting().getUsername().equalsIgnoreCase(credentials.getUsername()) &&
				article.getUserPosting().getPassword().equalsIgnoreCase(credentials.getPassword())) {
			System.out.println("Access granted. ");
			authorizeUser = true;
		}else if(article.getUserPosting().getUsername() != (credentials.getUsername()) || 
				article.getUserPosting().getPassword() != (credentials.getPassword())) {
			System.out.println("Access denied. ");
			authorizeUser = false;
		}
		return authorizeUser;
	}
	
	public boolean checkIfUserExistsInTheSystem(List<UserCredentials> userCredentialsRepo, UserCredentials credentials) {
		boolean exists = true;
		for(int index = 0; index < userCredentialsRepo.size(); index++) {
			if(userCredentialsRepo.get(index).getUsername().equalsIgnoreCase(credentials.getUsername()) &&
					userCredentialsRepo.get(index).getPassword().equalsIgnoreCase(credentials.getPassword())) {
				System.out.println("The user exists in the system.");
				exists = true;
			}else if(userCredentialsRepo.get(index).getUsername().equalsIgnoreCase(credentials.getUsername()) &&
					userCredentialsRepo.get(index).getPassword() != (credentials.getPassword())) {
				System.out.println("The user doesn't exist in the system or the possword doesn't match! ");
				exists = false;
			}
		}
		return exists;
	}
}
