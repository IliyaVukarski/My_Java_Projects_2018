package vehicle_jdbc;

import java.time.Year;

public class Vehicle {
	private String type;
	private String model;
	private double power;
	private double fuelConsumption;
	private double distanceTravelled;
	private int yearProduced;
	private String licencePlate;
	private int weight;
	private String color;
	
	public Vehicle() {
		
	}
	
	public Vehicle(String type, 
				   String model, 
				   double power, 
				   double fuelConsumption, 
				   double distanceTravelled,
				   int yearProduced, 
				   String licencePlate, 
				   int weight, String color) {
		this.type = type;
		this.model = model;
		this.power = power;
		this.fuelConsumption = fuelConsumption;
		this.distanceTravelled = distanceTravelled;
		this.yearProduced = yearProduced;
		this.licencePlate = licencePlate;
		this.weight = weight;
		this.color = color;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public double getFuelConsumption() {
		return fuelConsumption;
	}

	public void setFuelConsumption(
				double fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}

	public double getDistanceTravelled() {
		return distanceTravelled;
	}

	public void setDistanceTravelled(
				double distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	public int getYearProduced() {
		return yearProduced;
	}

	public void setYearProduced(int yearProduced) {
		this.yearProduced = yearProduced;
	}

	public String getLicencePlate() {
		return licencePlate;
	}

	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public double getInsurancePrice() {
		int currentYear = 
					Year.now().getValue();
		int carAge = currentYear - 
					this.getYearProduced();
		System.out.printf(
				"This car is %s years old.", 
						  carAge);
		double insurancePrice = 0;
		double typeCoefficient = 0;
		switch(type) {
		case "car":
			typeCoefficient = 1.00;
			break;
		case "suv":
			typeCoefficient = 1.12;
			break;
		case "truck":
			typeCoefficient = 1.20;
			break;
		case "motorcycle":
			typeCoefficient = 1.50;
			break;
		default:
			System.out.println(
			"No data available");
			break;
		}
		insurancePrice = 
				(((power * 0.16) + 
				  (carAge * 1.25) + 
				  (fuelConsumption * 0.05)) * 
				   typeCoefficient);
		return insurancePrice;
	}
	
	public double calculatePriceTravell(
						double fuelPrice) {
		double travelCost = 0;
		travelCost = ((fuelPrice * 
					   fuelConsumption) * 
					  (distanceTravelled / 100));
		return travelCost;
	}
}