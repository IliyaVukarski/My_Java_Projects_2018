package student_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlDatabase {
	private final String DB_CONN_STRING;
	private final String DB_USERNAME;
	private final String DB_PASSWORD;
	
	public MySqlDatabase(String dB_CONN_STRING, 
						 String dB_USERNAME, 
						 String dB_PASSWORD) {
		DB_CONN_STRING = dB_CONN_STRING;
		DB_USERNAME = dB_USERNAME;
		DB_PASSWORD = dB_PASSWORD;
	}
	
	public List<String> selectInformationAboutEmployess() 
		throws SQLException {
		List<String> repository = new ArrayList<>();
		try(Connection connection = 
			DriverManager.getConnection(DB_CONN_STRING, 
										DB_USERNAME, 
										DB_PASSWORD)) {
			Statement statement = 
						connection.createStatement();
			ResultSet resultSet = 
					statement.executeQuery(
						"SELECT "
						+ "* "
						+ "FROM "
						+ "db_repository.address "
						+ "JOIN "
						+ "db_repository.employee "
						+ "ON "
						+ "address.employee_id = "
						+ "employee.employee_id "
						+ "JOIN "
						+ "db_repository.workplace "
						+ "ON "
						+ "address.employee_id = "
						+ "workplace.employee_info_id");

			while(resultSet.next()) {
				repository.add(
				resultSet.getInt("address_id") + " " +
				resultSet.getString("city") + " " +
				resultSet.getString("neighborhood") + " " +
				resultSet.getString("building_Number") + " " +
				resultSet.getString("first_name") + " " +
				resultSet.getString("last_name") + " " +
				resultSet.getDouble("salary") + " " +
				resultSet.getString("place_of_work") + " " +
				resultSet.getString("job_occupation") + " " +
				resultSet.getInt("hours_per_week"));			
			}		
		}
		return repository;
	}
	
	public List<String> selectEmployeeSalary() 
		throws SQLException {
		List<String> repository = new ArrayList<>();
		try(Connection connection = 
			DriverManager.getConnection(DB_CONN_STRING, 
										DB_USERNAME, 
										DB_PASSWORD)) {
			Statement statement = 
					connection.createStatement();
			ResultSet resultSet = 
					statement.executeQuery(
						"SELECT "
					  + "employee.first_name, "
					  + "employee.last_name, "
					  + "employee.salary "
					  + "FROM "
					  + "db_repository.employee "
			          + "WHERE "
					  + "employee.salary > 700");
			while(resultSet.next()) {
				repository.add(
				resultSet.getString("first_name") + " " +
				resultSet.getString("last_name") + " " +
				resultSet.getDouble("salary"));
			}
		}
		return repository;
	}
	
	public void deleteEmployeeAddress(int id) 
		throws SQLException {
		try(Connection connection = 
			DriverManager.getConnection(DB_CONN_STRING, 
										DB_USERNAME, 
										DB_PASSWORD)) {
			Statement statement = 
				connection.createStatement();
			statement.executeUpdate(
						"DELETE "
					  + "FROM "
					  + "db_repository.address "
				      + "WHERE "
					  + "address_id = " + id);
		}
	}
	
	public void updateEmployeeSalary(Employee employee, int id) 
		throws SQLException {
		try(Connection connection = 
			DriverManager.getConnection(DB_CONN_STRING, 
										DB_USERNAME, 
										DB_PASSWORD)) {
			PreparedStatement preparedStatement = 
					connection.prepareStatement(
						"UPDATE "
					  + "db_repository.employee "
					  + "SET "
				      + "employee.first_name = ?, "
					  + "employee.last_name = ?, "
					  + "employee.salary = ? "
					  + "WHERE "
					  + "employee.employee_id = ? ");
			preparedStatement.setString(1, 
								employee.getFirstName());
			preparedStatement.setString(2,	
								employee.getLastName());
			preparedStatement.setDouble(3, 
								employee.getSalary());
			preparedStatement.setInt(4, id);
			preparedStatement.execute();
		}
	}
	
	public void insertAddressAndWorkPlace(Address address, 
										  WorkPlace workPlace) 
		throws SQLException {
		int employeeId = 
			insertEmployee(address.getEmployee());
		try(Connection connection = 
		DriverManager.getConnection(DB_CONN_STRING, 
									DB_USERNAME, 
									DB_PASSWORD)) {
			PreparedStatement preparedStatement = 
					connection.prepareStatement(
						"INSERT INTO " 
					  + "db_repository.address("
					  + "city, "
					  + "neighborhood, " 
					  + "building_number, " 
					  + "employee_id) "
					  + "VALUES(?, ?, ?, ?)");
			preparedStatement.setString(1, 
							address.getCity());
			preparedStatement.setString(2, 
							address.getNeighborhood());
			preparedStatement.setString(3, 
							address.getBuildingNumber());
			preparedStatement.setInt(4, employeeId);
			preparedStatement.execute();
		}
		
		try(Connection connection = 
				DriverManager.getConnection(DB_CONN_STRING, 
											DB_USERNAME, 
											DB_PASSWORD)) {
			PreparedStatement preparedStatement = 
					connection.prepareStatement(
						"INSERT INTO "
					  + "db_repository.workplace("
					  + "place_of_work, "
					  + "job_occupation, "
					  + "hours_per_week, " 
					  + "employee_info_id) "
					  + "VALUES(?, ?, ?, ?)");
			preparedStatement.setString(1, 
								workPlace.getPlaceOfWork());
			preparedStatement.setString(2, 
								workPlace.getJobOccupation());
			preparedStatement.setInt(3, 
								workPlace.getHoursPerWeek());
			preparedStatement.setInt(4, employeeId);
			preparedStatement.execute();
		}
	}
	
	public int insertEmployee(Employee employee) 
		throws SQLException {
		try(Connection connection = 
				DriverManager.getConnection(DB_CONN_STRING, 
											DB_USERNAME, 
										    DB_PASSWORD)) {
			PreparedStatement preparedStatement = 
					connection.prepareStatement(
					"INSERT INTO "
				  + "db_repository.employee("
				  + "first_name, "
				  + "last_name, "
		          + "salary) "
				  + "VALUES(?, ?, ?)");
			preparedStatement.setString(1, 
								employee.getFirstName());
			preparedStatement.setString(2, 
								employee.getLastName());
			preparedStatement.setDouble(3, 
								employee.getSalary());
			preparedStatement.execute();
			
			try(ResultSet resultSet = 
				preparedStatement.executeQuery(
				"SELECT LAST_INSERT_ID();")) {
				resultSet.next();
				return resultSet.getInt(1);
			}
		}
	}
}