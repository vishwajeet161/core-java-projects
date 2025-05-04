package com.example.B;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//@jakarta.persistence.Entity
//@jakarta.persistence.Table(name = "customer")


@Component
@Entity
public class Customer {
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	
//	@Column (name = "email")
//	@Column(unique = true, name = "email")
	private String email;
//	@Column (name = "contactNumber")
	private String contactNumber;
//	@Column (name = "address")
	private String address;
	
	public Customer() {
		 
	}
	
	public Customer(int id, String name, String email, String contactNumber, String address) {
		super();
		this.id = id;
		this.email = email;
		this.contactNumber = contactNumber;
		this.address = address;
	}
	
	//setters
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	//getters
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public String getAddress() {
		return address;
	}
	public String getEmail() {
		return email; 
	}
}
