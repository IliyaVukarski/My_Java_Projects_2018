package employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeRepo {						 
	private static final String DB_CONNECTION = 
				"jdbc:mysql://localhost:3306/db_employees";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD =
				"sofiyabulgaria1989";
	
	public static void main(String[] args) 
		throws SQLException {
		EmployeeDatabase employeeDatabase = 
				new EmployeeDatabase(DB_CONNECTION, 
									 DB_USERNAME, 
									 DB_PASSWORD);
		Scanner scanner = new Scanner(System.in);
		List<String> commands = new ArrayList<>();
		commands.add("END");
		commands.add("ADD");
		commands.add("DEL");
		commands.add("ASCENDING");
		commands.add("DESCENDING");
		commands.add("RAISE");
		commands.add("SWAP");
		String[] userInputCommand = null;
		boolean isValid = true;
		while(true) {
			System.out.println("Enter one of these commands: ");
			System.out.println("END, ADD, DEL, ASCENDING, "
							  +"DESCENDING, RAISE, SWAP");
			userInputCommand = scanner.next().split(" ");
			if(userInputCommand[0].equalsIgnoreCase(commands.get(0))) {
				break;
			}
			for(String com : userInputCommand) {
				if(com.equalsIgnoreCase(commands.get(1))) {
					System.out.println(
					"For how many people are you typing an information?");
					int numberOfStaff = 0;
					do {
						if(scanner.hasNextInt()) {
							numberOfStaff = scanner.nextInt();
							isValid = true;
						}else {
							System.out.println("Enter correct data!");
							scanner.next();
							isValid = false;
						}
					}while(!isValid);
					Employee employee = null;
					do {
						System.out.println("Employee");
						System.out.println("Enter name");
						String name = scanner.next();
						System.out.println(
							"Enter Employee Social Security Number");
						String empSSN = scanner.next();
						System.out.println("Enter salary");
						double salary = 0;
						do {
							if(scanner.hasNextDouble()) {
								salary = scanner.nextDouble();
								isValid = true;
							}else {
								System.out.println(
									"Please enter correct info!");
								scanner.next();
								isValid = false;
							}
						}while(!isValid);
						System.out.println("Enter position");
						String position = scanner.next();
						System.out.println("Enter department");
						String department = scanner.next();
						System.out.println("Enter email");
						String email = scanner.next();
						System.out.println("Enter age");
						int age = 0;
						do {
							if(scanner.hasNextInt()) {
								age = scanner.nextInt();
								isValid = true;
							}else {
								System.out.println(
									"Please enter correct info!");
								scanner.next();
								isValid = false;
							}
						}while(!isValid);
						employee = new Employee(name, 
												empSSN, 
												salary, 
												position, 
												department, 
												age, 
												email);
						employeeDatabase.insertEmployee(employee);
						numberOfStaff --;
					}while(numberOfStaff != 0);
				}else if(com.equalsIgnoreCase(commands.get(2))) {
					Employee firedEmployee = new Employee();
					System.out.println("Enter name of fired employee");
					String name = scanner.next();
					System.out.println("Enter ssn of fired employee");
					String emp_ssn = scanner.next();
					firedEmployee.setName(name);
					firedEmployee.setEmpSSN(emp_ssn);
					employeeDatabase.removeEmployee(firedEmployee);
				}else if(com.equalsIgnoreCase(commands.get(3))) {
					System.out.println("Ascending order of employeeSalary");
					employeeDatabase.employeeSalaryAscendingSort();
					System.out.println();
				}else if(com.equalsIgnoreCase(commands.get(4))) {
					System.out.println("Descending order of employee");
					employeeDatabase.employeeSalaryDescendingSort();
					System.out.println();
				}else if(com.equalsIgnoreCase(commands.get(5))) {
					System.out.println("Enter percentage of salary raise");
					double raisePercentange = 0;
					do { 
						if(scanner.hasNextInt()) {
							raisePercentange = scanner.nextDouble();
							isValid = true;
						}else {
							System.out.println("Enter correct data!");
							isValid = false;
							scanner.next();
						}
					}while(!isValid);
					System.out.println("Enter ssn");
					String ssn = scanner.next();
					employeeDatabase.raiseSalary(ssn, raisePercentange);
				}else if(com.equalsIgnoreCase(commands.get(6))) {
					System.out.println("Enter first Employee name");
					String firstEmpName = scanner.next();
					System.out.println("Enter second Employee name");
					String secondEmpName = scanner.next();
					employeeDatabase.swapSalary(firstEmpName, secondEmpName);
				}else {
					System.out.println("Wrong operation!");
				}
			}
		}
	}
}