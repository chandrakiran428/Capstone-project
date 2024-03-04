package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "vendors")
public class Vendor {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendor_id")
    private int id;


	private String vendor_name;
	
	private String vendor_email;
	
	private String vendor_password;
	private String vendor_company;

	private String vendor_mobile;
	private String vendor_status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVendor_name() {
		return vendor_name;
	}
	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}
	public String getVendor_email() {
		return vendor_email;
	}
	public void setVendor_email(String vendor_email) {
		this.vendor_email = vendor_email;
	}
	public String getVendor_password() {
		return vendor_password;
	}
	public void setVendor_password(String vendor_password) {
		this.vendor_password = vendor_password;
	}
	public String getVendor_company() {
		return vendor_company;
	}
	public void setVendor_company(String vendor_company) {
		this.vendor_company = vendor_company;
	}
	public String getVendor_mobile() {
		return vendor_mobile;
	}
	public void setVendor_mobile(String vendor_mobile) {
		this.vendor_mobile = vendor_mobile;
	}
	public String getVendor_status() {
		return vendor_status;
	}
	public void setVendor_status(String vendor_status) {
		this.vendor_status = vendor_status;
	}
	
/*	@OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL) // mappedBy indicates the field in the Event entity that maps the relationship
    private List<Event> events;
	*/
	
	
	
	
	
	
	
}
