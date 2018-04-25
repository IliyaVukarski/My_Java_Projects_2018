package vehicle_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySqlDatabase {	
	private final String db_CONNECTION;
	private final String db_USERNAME;
	private final String db_PASSWORD;
	
	public MySqlDatabase(String db_CONNECTION, 
						 String db_USERNAME, 
						 String db_PASSWORD) {
		this.db_CONNECTION = db_CONNECTION;
		this.db_USERNAME = db_USERNAME;
		this.db_PASSWORD = db_PASSWORD;
	}
	
	public void insertVehicle(Vehicle vehicle, 
							  double fuelPrice) 
					throws SQLException {
		try(Connection connection = 
			DriverManager.getConnection(db_CONNECTION, 
										db_USERNAME, 
										db_PASSWORD)) {
			PreparedStatement preparedStatement = 
			connection.prepareStatement(
					"INSERT INTO "
				  + "db_repo_vehicles."
				  + "vehicles("
				  + "type, "
			      + "model, "
				  + "power, "
				  + "fuel_consumption, "
				  + "distance_travelled, "
				  + "year_produced, "
				  + "licence_plate, "
				  + "weight, "
				  + "color, "
				  + "insurance_price, "
			      + "travel_cost) "
				  + "VALUES"
				  + "(?,?,?,?,?,?,?,?,?,?,?)");
					preparedStatement.setString(1, 
						vehicle.getType());
					preparedStatement.setString(2, 
						vehicle.getModel());
					preparedStatement.setDouble(3, 
						vehicle.getPower());
					preparedStatement.setDouble(4, 
						vehicle.getFuelConsumption());
					preparedStatement.setDouble(5, 
						vehicle.getDistanceTravelled());
					preparedStatement.setInt(6,
						vehicle.getYearProduced());
					preparedStatement.setString(7, 
						vehicle.getLicencePlate());
					preparedStatement.setInt(8, 
						vehicle.getWeight());
					preparedStatement.setString(9, 
						vehicle.getColor());
					preparedStatement.setDouble(10, 
						vehicle.getInsurancePrice());
					preparedStatement.setDouble(11, 
					vehicle.calculatePriceTravell(fuelPrice));
					preparedStatement.execute();
		}
	}
}