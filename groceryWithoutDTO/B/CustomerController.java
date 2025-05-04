package com.example.B;

import java.util.ArrayList;
import java.util.List;
import com.example.B.repository.*;

import jakarta.transaction.Transactional;

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
public class CustomerController {
	
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
	
	@Transactional
	@DeleteMapping("/deleteByEmail")
	public ResponseEntity<String> deleteCustomerByEmail(String email){
//		customerdao.findByEmail(email);
//		Customer foundCustomer = customerdao.findByEmail(email);
		if(!customerdao.existsByEmail(email)) {
			return ResponseEntity.status(404).body("Customer with email "+ email+" not found." );
		}else {

			customerdao.deleteByEmail(email);
			return ResponseEntity.ok("Customer with email "+email+" deleted.");
		}
		
	}

	@DeleteMapping("/deleteALL")
	public ResponseEntity<String> deleteAllCUstomers(){
		customerdao.deleteAll();
		return ResponseEntity.ok("All customers delete successfully.");
	}
	//Getmapping is used to specify URL and fetch the data 
	//http://localhost/getstudent
//	@GetMapping("getcustomer")
//	public Customer getCustomer()
//	{
//		
//		Customer a= new Customer(1,"vishu", "abc@gmail.com", "1234567890", "asdaf");
//		return a;
//	}
	
	  // http://localhost:8080/studentlist
	//Getmapping is used to  fetch the data in the form of List of Students 
//    @GetMapping("customerlist")
//    public List<Customer> getCustomers(){
//        List<Customer> customers = new ArrayList<>();
//        customers.add(new Customer(1, "abc", "asd@tcs.com", "123456789", "sdfff"));
//        
//        return customers;
//    }
    
    // Spring BOOT REST API with Path Variable
    // {id} - URI template variable
    // http://localhost:8080/students/1/ramesh/fadatare
  
    /*
     * When the path variable is same as method arguments 
     * then no need to mention the name of the path variable name in the method signature 
     * but when it is different you have to mention it as a part of method signature
     */
//    @GetMapping("customers/{customerId}/{name}/{email}/{contactNumber}/{address}")
//    public Customer customerPathVariable(@PathVariable int customerId,
//                                       @PathVariable("name") String name,
//                                       @PathVariable("email") String email,
//                                       @PathVariable("contactNumber") String ContactNumber,
//                                       @PathVariable("address") String address){
//        Customer customer = new Customer(customerId, name, email, ContactNumber, address);
//        return customer;
//    }
//    
    
    // Spring boot REST API with Request Param
    //  http://localhost:8080/students/query?id=1&firstName=Ramesh&lastName=Fadatare
/*
 * 
 * 
 */
    
//    @GetMapping("customers/query")
//    public Customer customerRequestVariable(@RequestParam int id,
//                                          @RequestParam String name,
//                                          @RequestParam String email,
//                                          @RequestParam String contactNumber,
//                                          @RequestParam String address){
//        Customer customer = new Customer(id, name, email, contactNumber, address);
//        return customer;
//    }
//    @GetMapping("Stringaddquery")
//	public List<String> getCustomerRequestParam1(@RequestParam List<String> s ) {
//		
//		return s;
//	}

    // Spring boot REST API that handles HTTP POST Request - creating new resource
    // @PostMapping and @RequestBody


//    @PostMapping("customers/create")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Customer createCustomer(@RequestBody Customer customer){
//        System.out.println(customer.getId());
//        System.out.println(customer.getName());
//        System.out.println(customer.getEmail());
//        System.out.println(customer.getContactNumber());
//        System.out.println(customer.getAddress());
//        return  customer;
//    }
    

//    @PutMapping("customers/{id}/update")
//    public Customer updateCustomer(@RequestBody Customer customer, @PathVariable("id") int customerId){
//        System.out.println(customer.getName());
//        System.out.println(customer.getEmail());
//        System.out.println(customer.getId());
//        System.out.println(customer.getContactNumber());
//        System.out.println(customer.getAddress());
//        return customer;
//    }

//	@DeleteMapping("{id}/delete")
//	public String deleteCustomer(@PathVariable int id ) {
//		
//		return "added";
//	}
	
}
