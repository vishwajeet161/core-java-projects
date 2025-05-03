package com.grocery_store.controller;

import java.util.ArrayList;
import java.util.List;
import com.grocery_store.repository.*;
import com.grocery_store.entity.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
public class customerController {
	
		@Autowired
		customerRepository customerdao;
	
	@PostMapping("/addCustomer")
	public Customer addCustomer(Customer cust) {
		customerdao.save(cust);
		return cust;
		
	}
	
	@GetMapping("/fetchCustomerByEmail")
	public Customer CustomerByEmail(String email) {
		return customerdao.findByEmail(email);
	}
	
	@PutMapping("/updateCustomerByEmail") 
	public ResponseEntity<String> UpdateCustomerByEmail(@RequestBody Customer updatedcustomer, String email) {
		Customer foundCustomer = customerdao.findByEmail(email);
		
		if(foundCustomer==null) {
			return ResponseEntity.status(404).body("Customer with email"+ email+" not found." );
//			return;
		}
		
		  if (updatedcustomer.getName() != null) {
	            foundCustomer.setName(updatedcustomer.getName());
	        }
	        if (updatedcustomer.getContactNumber() != null) {
	            foundCustomer.setContactNumber(updatedcustomer.getContactNumber());
	        }
	        if (updatedcustomer.getAddress() != null) {
	            foundCustomer.setAddress(updatedcustomer.getAddress());
	        }

	        // Save the updated customer
	        customerdao.save(foundCustomer);

	        return ResponseEntity.ok("Customer with email " + email + " updated successfully.");

	}
	
	@DeleteMapping("/deleteByEmail")
	public ResponseEntity<String> deleteCustomerByEmail(String email){
		customerdao.findByEmail(email);
		Customer foundCustomer = customerdao.findByEmail(email);
		if(foundCustomer!=null) {
			customerdao.deleteByEmail(email);
			return ResponseEntity.ok("Customer with email "+email+" deleted.");
		}else {
			return ResponseEntity.status(404).body("Customer with email "+ email+" not found." );
		}
		
	}

	@DeleteMapping("/deleteALL")
	public ResponseEntity<String> deleteAllCUstomers(){
		customerdao.deleteAll();
		return ResponseEntity.ok("All customers delete successfully.");
	}

}
