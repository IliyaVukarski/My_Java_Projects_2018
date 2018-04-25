package db_hotel_reservation;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HotelManagement {
	private final static String DB_CONNECTION = "jdbc:mysql://localhost:3306/db_reservation";
	private final static String DB_USERNAME = "root";
	private final static String DB_PASSWORD = "sofiyabulgaria1989";
	public static void main(String[] args) throws SQLException {
		DatabaseManagement databaseManagement = new DatabaseManagement(DB_CONNECTION, DB_USERNAME, DB_PASSWORD);
		Scanner scanner = new Scanner(System.in);
		List<String> userCommands = new ArrayList<String>();
		userCommands.add("END");
		userCommands.add("ADD_USER");
		userCommands.add("MAKE_RESERVATION");
		String[] userInput = null;
		boolean isValid = true;
		while(true) {
			System.out.println("Please choose a command between: ");
			System.out.println("END, ADD_USER or MAKE_RESERVATION");
			userInput = scanner.next().split(" ");
			
			if(userInput[0].equalsIgnoreCase(userCommands.get(0))) {
				break;
			}
			
			for(String com : userInput) {
				if(com.equalsIgnoreCase(userCommands.get(1))) {
					System.out.println("Enter username and password");
					Credentials credentials = new Credentials(scanner.next(), scanner.next());
					Customer customer = new Customer();
					System.out.println("Enter first name");
					String firstName = scanner.next();
					customer.setFirstName(firstName);
					System.out.println("Enter last name");
					String lastName = scanner.next();
					customer.setLastName(lastName);
					System.out.println("Enter balance");
					double balance = 0;
					do {
						if(scanner.hasNextDouble()) {
							balance = scanner.nextDouble();
							isValid = true;
						}else {
							System.out.println("Enter valid value");
							scanner.next();
							isValid = false;
						}
					}while(!isValid);
						customer.setBalance(balance);
					customer.setCredentials(credentials);
					databaseManagement.insertAccount(customer);
				}else if(com.equalsIgnoreCase(userCommands.get(2))) {
					Reservation reservation = new Reservation();
					System.out.println("Enter type Of room ROOM or APPARTMENT");
					String typeOfRoom = scanner.next();
					reservation.setTypeOfRoom(typeOfRoom);
					System.out.println("Enter date of arival in format year/mm/day");
					String[] strArrDate = scanner.next().split("/");
					int[] arrivalDate = new int[3];
					for(int index = 0; index < strArrDate.length; index++) {
						int date = Integer.parseInt(strArrDate[index]);
						arrivalDate[index] = date;
					}
					LocalTime time = LocalTime.now();
					LocalDateTime dateOfArrDateTime = 
					LocalDateTime.of(arrivalDate[0], arrivalDate[1], arrivalDate[2], 
													time.getHour(), time.getMinute());
					reservation.setDateOfArrival(dateOfArrDateTime);
					System.out.println("Enter name of customer");
					String nameOfCustomer = scanner.next();
					reservation.setNameOfCustomer(nameOfCustomer);
					System.out.println("Enter period of stay");
					int periodOfStay = 0;
					do {
						if(scanner.hasNextInt()) {
							periodOfStay = scanner.nextInt();
							isValid = true;
						}else {
							System.out.println("Enter valid values");
							scanner.next();
							isValid = false;
						}
					}while(!isValid);
					reservation.setPeriodOfStay(periodOfStay);
					System.out.println("Enter date of departure in format year/mm/day");
					String[] strDepDate = scanner.next().split("/");
					int[] departureDate = new int[3];
					for(int index = 0; index < strDepDate.length; index++) {
						int date = Integer.parseInt(strDepDate[index]);
						departureDate[index] = date;
					}
					LocalDateTime dateOfDeparture = LocalDateTime.of(departureDate[0], departureDate[1], departureDate[2], time.getHour(), time.getMinute());
					reservation.setDateOfDeparture(dateOfDeparture);
					System.out.println("Enter customer first name and last name");
					Customer customer = new Customer(scanner.next(), scanner.next());
					System.out.println("Enter customer username and password");
					Credentials credentials = new Credentials(scanner.next(), scanner.next());
					customer.setCredentials(credentials);
					databaseManagement.makeReservation(customer, reservation);
				}else {
					System.out.println("Wrong command");
				}
			}
		}
	}
}