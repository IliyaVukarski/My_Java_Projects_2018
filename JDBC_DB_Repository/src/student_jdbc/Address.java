package student_jdbc;

public class Address {
	private String city;
	private String neighborhood;
	private String buildingNumber;
	private Employee employee;
	
	public Address() {
		
	}
	
	public Address(String city, 
				   String neighborhood, 
				   String buildingNumber, 
				   Employee employee) {
		this.city = city;
		this.neighborhood = neighborhood;
		this.buildingNumber = buildingNumber;
		this.employee = employee;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(
		String neighborhood) {
		this.neighborhood = neighborhood;
	}
	public String getBuildingNumber() {
		return buildingNumber;
	}
	public void setBuildingNumber(
		String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(
		Employee employee) {
		this.employee = employee;
	}
}