package vehicle_jdbc;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class VehicleRepo {
	private static final String DB_CONNECTION = 
			"jdbc:mysql://localhost:3306/db_repo_vehicles";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = 
								"sofiyabulgaria1989";
	
	public static void main(String[] args) 
		throws SQLException {
		MySqlDatabase mySqlDatabase = 
				new MySqlDatabase(DB_CONNECTION, 
								  DB_USERNAME, 
								  DB_PASSWORD);
		Scanner scanner = new Scanner(System.in);
		boolean isValid = true;
		Vehicle vehicle = new Vehicle();
		
		System.out.println("Enter fuel price");
		double fuelPrice = 0;
		do {
			if(scanner.hasNextDouble()) {
				fuelPrice = scanner.nextDouble();
				isValid = true;
			}else {
				System.out.println("Enter valid value");
				isValid = false;
				scanner.next();
			}
		}while(!isValid);
		System.out.println(
		  "For how many vehicles are "
		+ "you typing an information?");
		int numberOfVehicles = 0;
		do {
			if(scanner.hasNextInt()) {
				numberOfVehicles = scanner.nextInt();
				isValid = true;
			}else {
				System.out.println("Enter valid value");
				isValid = false;
				scanner.next();
			}
		}while(!isValid);
		
		int carNumber = 1;
		do {
			System.out.println();
			System.out.println("Enter type");
			String type = scanner.next();
			
			System.out.println("Enter model");
			String model = scanner.next();
			
			System.out.println("Enter power");
			double power = 0;
			do {
				if(scanner.hasNextDouble()) {
					power = scanner.nextDouble();
					isValid = true;
				}else {
					System.out.println("Enter valid value");
					isValid = false;
					scanner.next();
				}
			}while(!isValid);
			
			System.out.println("Enter fuel consumption");
			double fuelConsumption = 0;
			do {
				if(scanner.hasNextDouble()) {
					fuelConsumption = scanner.nextDouble();
					isValid = true;
				}else {
					System.out.println("Enter valid value");
					isValid = false;
					scanner.next();
				}
			}while(!isValid);
			
			System.out.println("Enter distance travelled");
			double distanceTravelled = 0;
			do {
				if(scanner.hasNextDouble()) {
					distanceTravelled = scanner.nextDouble();
					isValid = true;
				}else {
					System.out.println("Enter valid value");
					isValid = false;
					scanner.next();
				}
			}while(!isValid);
			
			System.out.println("Enter year produced");
			int yearProduced = 0;
			do {
				if(scanner.hasNextInt()) {
					yearProduced = scanner.nextInt();
					isValid = true;
				}else {
					System.out.println("Enter valid value");
					isValid = false;
					scanner.next();
				}
			}while(!isValid);
			
			DecimalFormat decimalFormat = 
							new DecimalFormat("0000");
			String licenceNo = 
					decimalFormat.format(carNumber++);
			
			System.out.println("Enter weight");
			int weight = 0;
			do {
				if(scanner.hasNextInt()) {
					weight = scanner.nextInt();
					isValid = true;
				}else {
					System.out.println("Enter valid value");
					isValid = false;
					scanner.next();
				}
			}while(!isValid);
			
			System.out.println("Enter color");
			String color = scanner.next();
			
			vehicle = new Vehicle(type, 
								  model, 
								  power, 
								  fuelConsumption,
								  distanceTravelled, 
								  yearProduced,
								  licenceNo, 
								  weight, 
								  color);
			mySqlDatabase.insertVehicle(vehicle, fuelPrice);
			numberOfVehicles--;
		}while(numberOfVehicles != 0);
	}
}