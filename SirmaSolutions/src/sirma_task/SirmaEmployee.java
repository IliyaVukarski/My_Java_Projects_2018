package sirma_task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class SirmaEmployee {
	
	private int empID;
	private int projectID;
	private LocalDate dateFrom;
	private LocalDate dateTo;
	
	public SirmaEmployee() {
		
	}
	
	public SirmaEmployee(int empID, int projectID, LocalDate dateFrom,
			LocalDate dateTo) {
		this.empID = empID;
		this.projectID = projectID;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}
	
	public int getEmpID() {
		return empID;
	}

	public void setEmpID(int empID) {
		this.empID = empID;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}
	
	@Override
	public String toString() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
		String dateFrom = dateTimeFormatter.format(this.dateFrom);
		String dateTo = dateTimeFormatter.format(this.dateTo);
		return String.format("empID: %d projectID %d Date from: %s Date to: %s", 
						this.empID, this.projectID, dateFrom, dateTo);
	}
	
}
