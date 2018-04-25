package student_jdbc;

public class WorkPlace {
	private String placeOfWork;
	private String jobOccupation;
	private int hoursPerWeek;
	private Employee employee;
	
	public WorkPlace() {
		
	}
	
	public WorkPlace(String placeOfWork, 
					String jobOccupation, 
					int hoursPerWeek, 
					Employee employee) {
		this.placeOfWork = placeOfWork;
		this.jobOccupation = jobOccupation;
		this.hoursPerWeek = hoursPerWeek;
		this.employee = employee;
	}
	
	public String getPlaceOfWork() {
		return placeOfWork;
	}
	public void setPlaceOfWork(
		String placeOfWork) {
		this.placeOfWork = placeOfWork;
	}
	public String getJobOccupation() {
		return jobOccupation;
	}
	public void setJobOccupation(
		String jobOccupation) {
		this.jobOccupation = jobOccupation;
	}
	public int getHoursPerWeek() {
		return hoursPerWeek;
	}
	public void setHoursPerWeek(
		int hoursPerWeek) {
		this.hoursPerWeek = hoursPerWeek;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(
		Employee employee) {
		this.employee = employee;
	}
}