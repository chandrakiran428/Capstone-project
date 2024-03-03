package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "venuelist")
public class VenueList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name= "Venue Name", nullable=false)
	private String venueName;
	
	@Column(name= "Capacity")
	private String capacity;
	
	@Column(name= "Address")
	private String address;
	
	@Column(name= "Phone Number")
	private String phoneNumber;
	
	@Column(name= "Price")
	private int cost;
	
	
	public VenueList() {
		
	}
	
	public VenueList(String venueName, String capacity, String address, String phoneNumber, int cost) {
		super();
		this.venueName = venueName;
		this.capacity = capacity;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.cost = cost;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	

}
