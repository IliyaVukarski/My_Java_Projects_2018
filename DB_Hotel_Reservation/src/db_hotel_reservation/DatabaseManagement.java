package db_hotel_reservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManagement {
	private final String DB_Connection;
	private final String DB_Username;
	private final String DB_Password;
	
	public DatabaseManagement(String dB_Connection, String dB_Username,
			String dB_Password) {
		DB_Connection = dB_Connection;
		DB_Username = dB_Username;
		DB_Password = dB_Password;
	}
	
	public void insertAccount(Customer customer) throws SQLException {
		try(Connection connection = DriverManager.getConnection(DB_Connection, DB_Username, DB_Password)) {
			List<String> allUsers = new ArrayList<>();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT "
													   + "username "
													   + "FROM "
													   + "db_reservation.credentials ");
			while(resultSet.next()) {
				allUsers.add(resultSet.getString("username"));
			}
			
			if(!allUsers.contains(customer.getCredentials().getUsername())) {
				insertCustomer(customer);
			}else {
				System.out.println("Username already exists in the system." 
								 + "Please choose another one!");
			}
		}
	}
	
	public void insertCustomer(Customer customer) throws SQLException {
		int credentials_id = addNewUser(customer.getCredentials());
		try(Connection connection = DriverManager.getConnection(DB_Connection, DB_Username, DB_Password)) {
			PreparedStatement preparedStatement = 
					connection.prepareStatement("INSERT INTO "
											  + "db_reservation.customer(first_name, last_name, balance, credentials_id) "
											  + "VALUES "
											  + "(?, ?, ?, ?) ");
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setDouble(3, customer.getBalance());
			preparedStatement.setInt(4, credentials_id);
			preparedStatement.execute();
			System.out.printf("Successfully added new customer %s %s to database, register on %s. ",
								customer.getFirstName(), customer.getLastName(), customer.getCredentials().getTimeOfRegister());
		}
	}
	
	public int addNewUser(Credentials credentials) throws SQLException {
		try(Connection connection = DriverManager.getConnection(DB_Connection, DB_Username, DB_Password)) {
			LocalDateTime timeOfRegister = LocalDateTime.now();
			credentials.setTimeOfRegister(timeOfRegister);
			String date = timeOfRegister.toString();
			PreparedStatement preparedStatement = 
					connection.prepareStatement("INSERT INTO "
											  + "db_reservation.credentials(username, password, time_of_register) "
											  + "VALUES "
											  + "(?, ?, ?) ");
			preparedStatement.setString(1, credentials.getUsername());
			preparedStatement.setString(2, credentials.getPassword());
			preparedStatement.setString(3, date);
			preparedStatement.execute();
			System.out.printf("Successfully added new user %s to database, register on %s ", 
								credentials.getUsername(), date);
			try(ResultSet resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()")) {
				resultSet.next();
				return resultSet.getInt(1);
			}
		}
	}
	
	public boolean hasAccess(Customer customer) throws SQLException {
		boolean authorize = true;
		List<Credentials> usersInfo = new ArrayList<Credentials>();
		try(Connection connection = DriverManager.getConnection(DB_Connection, DB_Username, DB_Password)) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT "
													   + "username, "
													   + "password "
													   + "FROM "
													   + "db_reservation.credentials ");
			while(resultSet.next()) {
				usersInfo.add(new Credentials(resultSet.getString("username"),
							resultSet.getString("password")));
			}
			for(int index = 0; index < usersInfo.size(); index++) {
				if(customer.getCredentials().getUsername().equalsIgnoreCase(usersInfo.get(index).getUsername()) &&
						customer.getCredentials().getPassword().equalsIgnoreCase(usersInfo.get(index).getPassword())) {
					authorize = true;
					System.out.println("Access granted! ");
				}else if(customer.getCredentials().getUsername() != (usersInfo.get(index).getUsername()) ||
						customer.getCredentials().getPassword() != (usersInfo.get(index).getPassword())) {
					authorize = false;
					System.out.println("Access denied! ");
				}
			}
		}
		return authorize;
	}
	
	public double calculateReservation(Reservation reservation, Customer customer) {
		double priceToPay = 0;
		double periodOfStay = 0;
		
		LocalDateTime dateOfArrival = reservation.getDateOfArrival();
		LocalDateTime dateOfDeparture = reservation.getDateOfDeparture();
		periodOfStay = ChronoUnit.DAYS.between(dateOfArrival, dateOfDeparture);
		
		double roomPricePerNight = 100;
		double appartmentPricePerNight = 400;
		
		Month monthOfArrival = dateOfArrival.getMonth();
		switch(monthOfArrival) {
		case NOVEMBER:
		case DECEMBER:
		case JANUARY:
		case FEBRUARY:
		case MARCH:
			roomPricePerNight = roomPricePerNight + roomPricePerNight * 0.2;
			break;
		default:
			roomPricePerNight = roomPricePerNight - roomPricePerNight * 0.2;
			break;
		}
			
		if(periodOfStay >= 7) {
			roomPricePerNight = roomPricePerNight - roomPricePerNight * 0.1;
			appartmentPricePerNight = appartmentPricePerNight - appartmentPricePerNight * 0.15;
		}
		
		switch(reservation.getTypeOfRoom()) {
		case "ROOM":
			priceToPay = periodOfStay * roomPricePerNight;
			break;
			
		case "APPARTMENT":
			priceToPay = periodOfStay * appartmentPricePerNight;
			break;
		default:
			System.out.println("Wrong type of room. ");
			break;
		}
		System.out.println("The price of this period is " + priceToPay);
		return priceToPay;
	}
	
	public void makeReservation(Customer customer, Reservation reservation) throws SQLException {
		try(Connection connection = DriverManager.getConnection(DB_Connection, DB_Username, DB_Password)) {
			List<Customer> customerInfo = new ArrayList<Customer>();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT "
													   + "first_name,"
													   + "last_name, "
													   + "balance "
													   + "FROM "
													   + "db_reservation.customer ");
			while(resultSet.next()) {
				customerInfo.add(new Customer(resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getDouble("balance")));
			}
			
			if(hasAccess(customer)) {
				for(int index = 0; index < customerInfo.size(); index++) {
					if(customer.getFirstName().equalsIgnoreCase(customerInfo.get(index).getFirstName()) 
					&& customer.getLastName().equalsIgnoreCase(customerInfo.get(index).getLastName())) {
						if(customerInfo.get(index).getBalance() >= calculateReservation(reservation, customer)) {
							PreparedStatement preparedStatement = 
									connection.prepareStatement("UPDATE "
															  + "db_reservation.customer "
															  + "SET "
															  + "balance = balance - ? "
															  + "WHERE "
															  + "first_name = ? "
															  + "AND "
															  + "last_name = ? ");
							preparedStatement.setDouble(1, calculateReservation(reservation, customer));
							preparedStatement.setString(2, customer.getFirstName());
							preparedStatement.setString(3, customer.getLastName());
							preparedStatement.execute();
							
							LocalDateTime dateOfArrival = reservation.getDateOfArrival();
							LocalDateTime dateOfDeparture = reservation.getDateOfDeparture();
							
							String arrivalDate = dateOfArrival.toString();
							String departureDate = dateOfDeparture.toString();
							PreparedStatement preparedStatement2 = 
									connection.prepareStatement("INSERT INTO "
															  + "db_reservation.reservation(type_of_room, price, date_of_arrival, date_of_departure, name_of_customer, period_of_stay) "
															  + "VALUES "
															  + "(?, ?, ?, ?, ?, ?)");
							preparedStatement2.setString(1, reservation.getTypeOfRoom());
							preparedStatement2.setDouble(2, calculateReservation(reservation, customer));
							preparedStatement2.setString(3, arrivalDate);
							preparedStatement2.setString(4, departureDate);
							preparedStatement2.setString(5, reservation.getNameOfCustomer());
							preparedStatement2.setInt(6, reservation.getPeriodOfStay());
							preparedStatement2.execute();
							System.out.println("Reservation successfully made!");
						}
					}
				}
			}else {
				System.out.println("Access denied! ");
			}
			
		}
	}
}