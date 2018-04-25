package student_jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabaseManagement {
	private static final String DB_CONN_STRING = 
		"jdbc:mysql://localhost:3306/db_repository";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD =
								"sofiyabulgaria1989";
	
	public static void main(String[] args)
		throws SQLException {
		/*MySqlDataBase mySqlDataBase = 
				new MySqlDataBase(DB_CONN_STRING, 
								  DB_USERNAME, 
								  DB_PASSWORD);
		Employee employee = 
		new Employee("Ilcho", "Vukarski", 3000);
		Address address = 
		new Address("Sofia", "lulin_1", "028", employee);
		WorkPlace workPlace = 
		new WorkPlace("UniShit", "Assistant, 20, employee);
		mySqlDataBase.insertAddressAndWorkPlace(address, 
											    workPlace);
		*/
		MySqlDatabase mySqlDataBase = 
				new MySqlDatabase(DB_CONN_STRING, 
								  DB_USERNAME, 
								  DB_PASSWORD);
		Scanner scanner = new Scanner(System.in);
		String command = " ";
		List<String> commands = new ArrayList<>();
		commands.add("END");
		commands.add("INSERT");
		commands.add("SELECT_FULL_INFO");
		commands.add("SELECT_SALARY");
		commands.add("DELETE_ADDRESS");
		commands.add("UPDATE_EMPLOYEE");
		Employee employee = new Employee();
		Address address = new Address();
		WorkPlace workPlace = new WorkPlace();
		boolean isValid = true;
		while(true) {
			System.out.println(
			"Enter INSERT, SELECT_FULL_INFO, "
			+"SELECT_SALARY, DELETE_ADDRESS, "
			+"UPDATE_EMPLOYEE or END");
			command = scanner.next();
			if(command.equalsIgnoreCase(commands.get(0))) {
				break;
			}else if(command.equalsIgnoreCase(commands.get(1))) {
				System.out.println(
				"Enter values for firstName, LastName, Salary");
				String firstName = scanner.next();
				String lastName = scanner.next();
				double salary = 0;
				do {
					if(scanner.hasNextDouble()) {
						salary = scanner.nextDouble();
						isValid = true;
					}else {
						System.out.println("Enter valid data");
						scanner.next();
						isValid = false;
					}
				}while(!isValid);
				employee = new Employee(firstName, 
									    lastName, 
										salary);
				System.out.println(
				"Enter city, neighborhood buildingNumber");
				String city = scanner.next();
				String neighborhood = scanner.next();
				String buildingNumber = scanner.next();
				address = new Address(city, 
									  neighborhood, 
									  buildingNumber, 
									  employee);
				System.out.println(
				"Enter placeOfWork, jobOccupation, hoursPerWeek");
				String placeOfWork = scanner.next();
				String jobOccupation = scanner.next();
				int hoursPerWeek = 0;
				do {
					if(scanner.hasNextInt()) {
						hoursPerWeek = scanner.nextInt();
						isValid = true;
					}else {
						System.out.println("Enter correct data");
						scanner.next();
						isValid = false;
					}
				}while(!isValid);
				workPlace = new WorkPlace(placeOfWork, 
										 jobOccupation, 
										 hoursPerWeek, 
										 employee);
				System.out.println(
				"Inserting new employee to database");
				mySqlDataBase.insertAddressAndWorkPlace(address, 
													    workPlace);
			}else if(command.equalsIgnoreCase(commands.get(2))) {
				System.out.println(
				mySqlDataBase.selectInformationAboutEmployess());
			}else if(command.equalsIgnoreCase(commands.get(3))) {
				System.out.println(
				mySqlDataBase.selectEmployeeSalary());
 			}else if(command.equalsIgnoreCase(commands.get(4))) {
 				int id = 0;
 				do {
 					System.out.println(
					"Enter key of record that will be deleted");
 					if(scanner.hasNextInt()) {
 						id = scanner.nextInt();
 						isValid = true;
 					}else {
 						System.out.println("Enter correct data");
 						scanner.next();
 						isValid = false;
 					}
 				}while(!isValid);
 				mySqlDataBase.deleteEmployeeAddress(id);
 				System.out.println("Record deleted!");
			}else if(command.equalsIgnoreCase(commands.get(5))) {
				System.out.println(
				"Enter values for firstName, LastName, Salary");
				String firstName = scanner.next();
				String lastName = scanner.next();
				double salary = 0;
				do {
					if(scanner.hasNextDouble()) {
						salary = scanner.nextDouble();
						isValid = true;
					}else {
						System.out.println("Enter valid data");
						scanner.next();
						isValid = false;
					}
				}while(!isValid);
				employee =
					new Employee(firstName, lastName, salary);
				
				int id = 0;
 				do {
 					System.out.println(
					"Enter key of record that will be updated");
 					if(scanner.hasNextInt()) {
 						id = scanner.nextInt();
 						isValid = true;
 					}else {
 						System.out.println("Enter correct data");
 						scanner.next();
 						isValid = false;
 					}
 				}while(!isValid);
 				mySqlDataBase.deleteEmployeeAddress(id);
				mySqlDataBase.updateEmployeeSalary(employee, id);
				System.out.println("Record updated!");
			}else {
				System.out.println("Enter corect command");
			}
		}
	}
}