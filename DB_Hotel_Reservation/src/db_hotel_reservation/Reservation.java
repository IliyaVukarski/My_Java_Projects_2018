package db_hotel_reservation;

import java.time.LocalDateTime;

public class Reservation {
	private String typeOfRoom;
	private LocalDateTime dateOfArrival;
	private LocalDateTime dateOfDeparture;
	private String nameOfCustomer;
	private int periodOfStay;
	
	public Reservation() {
		
	}
	
	public Reservation(String typeOfRoom,
			LocalDateTime dateOfArrival, LocalDateTime dateOfDeparture,
			String nameOfCustomer, int periodOfStay) {
		this.typeOfRoom = typeOfRoom;
		this.dateOfArrival = dateOfArrival;
		this.dateOfDeparture = dateOfDeparture;
		this.nameOfCustomer = nameOfCustomer;
		this.periodOfStay = periodOfStay;
	}
	
	public String getTypeOfRoom() {
		return typeOfRoom;
	}

	public void setTypeOfRoom(String typeOfRoom) {
		this.typeOfRoom = typeOfRoom;
	}
	
	public LocalDateTime getDateOfArrival() {
		return dateOfArrival;
	}

	public void setDateOfArrival(LocalDateTime localDate) {
		this.dateOfArrival = localDate;
	}

	public LocalDateTime getDateOfDeparture() {
		return dateOfDeparture;
	}

	public void setDateOfDeparture(LocalDateTime dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture;
	}

	public String getNameOfCustomer() {
		return nameOfCustomer;
	}

	public void setNameOfCustomer(String nameOfCustomer) {
		this.nameOfCustomer = nameOfCustomer;
	}

	public int getPeriodOfStay() {
		return periodOfStay;
	}

	public void setPeriodOfStay(int periodOfStay) {
		this.periodOfStay = periodOfStay;
	}
}
