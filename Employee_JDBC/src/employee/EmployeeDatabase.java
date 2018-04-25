package employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeDatabase {

	private final String db_Connection;
	private final String db_Username;
	private final String db_Password;
	
	public EmployeeDatabase(String db_Connection, 
							String db_Username, 
							String db_Password) {
		this.db_Connection = db_Connection;
		this.db_Username = db_Username;
		this.db_Password = db_Password;
	}
	
	public void insertEmployee(Employee employee) 
		throws SQLException {
		LocalDate localDate = LocalDate.now();
		String date = localDate.toString();
		employee.setHireDate(date);
		try(Connection connection = 
		DriverManager.getConnection(db_Connection, 
								    db_Username, 
									db_Password)) {
			PreparedStatement preparedStatement = 
			connection.prepareStatement("INSERT INTO "
									  + "db_employees."
									  + "employees("
									  + "name, "
									  + "emp_ssn, "
									  + "salary, "    
									  + "position, "
									  + "department, "
									  + "age, "
									  + "email, "
									  + "hire_date) "
									  + "VALUES "
									  + "(?, ?, ?, ?, "
									  + "?, ?, ?, ?)");
			preparedStatement.setString(1, 
								employee.getName());
			preparedStatement.setString(2, 
								employee.getEmpSSN());
			preparedStatement.setDouble(3, 
								employee.getSalary());
			preparedStatement.setString(4, 
								employee.getPosition());
			preparedStatement.setString(5, 
								employee.getDepartment());
			preparedStatement.setInt(6, 
								employee.getAge());
			preparedStatement.setString(7, 
								employee.getEmail());
			preparedStatement.setString(8, 
								date);
			preparedStatement.execute();
		}
		System.out.printf("Successfully added "
						+ "new user %s to database", 
						employee.getName());
	}
	
	public void removeEmployee(Employee employee) 
		throws SQLException {
		String deletedEmp = employee.getName();
		try(Connection connection = 
			DriverManager.getConnection(db_Connection, 
										db_Username, 
										db_Password)) {
			PreparedStatement preparedStatement = 
			connection.prepareStatement("DELETE FROM "
									  + "db_employees."
									  + "employees "
									  + "WHERE "
							          + "employees."
									  + "emp_ssn = ? ");
			preparedStatement.setString(1, 
									employee.getEmpSSN());
			preparedStatement.execute();
			System.out.printf("Employee %s has "
							+ "been successfully "
							+ "removed. ", deletedEmp);
		}
	}
	
	public void raiseSalary(String empSSN,
							double raiseSalary) 
		throws SQLException {
		double raiseSalaryPercentage = 
									raiseSalary / 100;
		try(Connection connection = 
			DriverManager.getConnection(db_Connection, 
										db_Username, 
										db_Password)) {
			PreparedStatement preparedStatement = 
			connection.prepareStatement("UPDATE "
									  + "db_employees."
									  + "employees "
									  + "SET "
									  + "salary = salary "
									  + "+ (salary * ?) "
									  + "WHERE employees."
									  + "emp_ssn = ?");
			preparedStatement.setDouble(1, 
							raiseSalaryPercentage);
			preparedStatement.setString(2, 
							empSSN);
			preparedStatement.execute();
		}
	}
	
	public void employeeSalaryAscendingSort() 
		throws SQLException {
		List<Employee> employees = new ArrayList<>();
		try(Connection connection = 
			DriverManager.getConnection(db_Connection, 
										db_Username, 
										db_Password)) {
			Statement statement = 
				      connection.createStatement();
			ResultSet resultSet = 
				statement.executeQuery("SELECT "
									 + "name, "
									 + "emp_ssn, "
									 + "salary, "
									 + "position, "
									 + "department, "
									 + "age, "
									 + "email, "
									 + "hire_date "
									 + "FROM "
									 + "db_employees."
									 + "employees ");
			while(resultSet.next()) {
				employees.add(
					new Employee(
						resultSet.getString("name"), 
						resultSet.getString("emp_ssn"),
						resultSet.getDouble("salary"), 
						resultSet.getString("position"),
						resultSet.getString("department"),
						resultSet.getInt("age"), 
						resultSet.getString("email"), 
						resultSet.getString("hire_date")));
			}
		}
		
		Collections.sort(employees, new Comparator<Employee>() {
			public int compare(Employee emp1, Employee emp2) {
				return (int) (emp1.getSalary() - emp2.getSalary());
			}
		});
		
		for(Employee emp : employees) {
			System.out.println(emp.getName() + " " +
							   emp.getEmpSSN() + " " +
							   emp.getSalary() + " " + 
							   emp.getPosition() + " " +
 							   emp.getDepartment() + " " +
							   emp.getAge() + " " +
							   emp.getEmail() + " " +
							   emp.getHireDate());
		}
	}
	
	public void employeeSalaryDescendingSort() 
		throws SQLException {
		List<Employee> employees = new ArrayList<>();
		try(Connection connection = 
			DriverManager.getConnection(db_Connection, 
										db_Username, 
										db_Password)) {
			Statement statement = 
							connection.createStatement();
			ResultSet resultSet = 
				statement.executeQuery("SELECT "
									 + "name, "
									 + "emp_ssn, "
									 + "salary, "
									 + "position, "
									 + "department, "
									 + "age, "
									 + "email, "
									 + "hire_date "
									 + "FROM "
									 + "db_employees."
									 + "employees ");
			while(resultSet.next()) {
				employees.add(
					new Employee(
							resultSet.getString("name"), 
							resultSet.getString("emp_ssn"),
							resultSet.getDouble("salary"), 
							resultSet.getString("position"),
							resultSet.getString("department"),
							resultSet.getInt("age"), 
							resultSet.getString("email"), 
							resultSet.getString("hire_date")));
			}
		}
		
		Collections.sort(employees, new Comparator<Employee>() {
			public int compare(Employee emp1, Employee emp2) {
				return (int) (emp2.getSalary() - emp1.getSalary());
			}
		});
		
		for(Employee emp : employees) {
			System.out.println(emp.getName() + " " +
							   emp.getEmpSSN() + " " +
							   emp.getSalary() + " " + 
							   emp.getPosition() + " " +
 							   emp.getDepartment() + " " +
							   emp.getAge() + " " +
							   emp.getEmail() + " " +
							   emp.getHireDate());
		}
	}
	
	public void swapSalary(String nameEmp1, String nameEmp2) 
		throws SQLException {
		List<Employee> employees = new ArrayList<>();
		Employee emp1 = null;
		Employee emp2 = null;
		try(Connection connection =
				DriverManager.getConnection(db_Connection, 
											db_Username, 
											db_Password)) {
			Statement statement = 
						connection.createStatement();
			ResultSet resultSet = 
				statement.executeQuery("SELECT "
									  + "name, "
									  + "emp_ssn, "
									  + "salary, "
									  + "position, "
									  + "department, "
									  + "age, "
								      + "email, "
								      + "hire_date "
								      + "FROM "
									  + "db_employees."
									  + "employees ");
			while(resultSet.next()) {
				employees.add(
					new Employee(
						resultSet.getString("name"), 
						resultSet.getString("emp_ssn"),
						resultSet.getDouble("salary"), 
						resultSet.getString("position"),
						resultSet.getString("department"),
						resultSet.getInt("age"), 
						resultSet.getString("email"), 
						resultSet.getString("hire_date")));
			}
		}
		
		double firstSalary = 0;
		double secondSalary = 0;
		
		for(int index = 0; index < employees.size(); index++) {
			if(employees.get(index).getName().
				equalsIgnoreCase(nameEmp1)) {
				System.out.println("Emp 1 exists in database!");
				emp1 = employees.get(index);
				firstSalary = emp1.getSalary();
				System.out.println(firstSalary);
				break;
			}else {
				continue;
			}
		}
		
		for(int index = 0; index < employees.size(); index++) {
			if(employees.get(index).getName().
				equalsIgnoreCase(nameEmp2)) {
				System.out.println("Emp 2 exists in database!");
				emp2 = employees.get(index);
				secondSalary = emp2.getSalary();
				System.out.println(secondSalary);
				break;
			}else {
				continue;
			}
		}
		
		for(int index = 0; index < employees.size(); index++) {
			if(employees.get(index).getName().
				contains(nameEmp1) &&
			   employees.get(index).getName().
				contains(nameEmp2)) {
				System.out.println("Salary before swapping");
				System.out.println(emp1.getName() + " " 
								 + emp1.getSalary());
				System.out.println(emp2.getName() + " " 
								 + emp2.getSalary());
				
				emp1.setSalary(secondSalary);
				emp2.setSalary(firstSalary);
				
				System.out.println("Salary after swapping");
				System.out.println(emp1.getName() + " " 
								 + emp1.getSalary());
				System.out.println(emp2.getName() + " " 
								 + emp2.getSalary());
			}
		}
	}
}