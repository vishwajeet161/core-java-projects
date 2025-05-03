package com.grocery_store.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.grocery_store.dto.customerDTO;
import com.grocery_store.entity.Customer;
import com.grocery_store.repository.customerRepository;

@Service
public class customerServiceImpl implements customerService {

	
	private customerRepository customerRepo;

    public customerServiceImpl(customerRepository custRepository) {
        this.customerRepo = custRepository;
    }
    
	@Override
	public customerDTO createPost(customerDTO customerDto) {
		 // convert DTO to entity
        Customer customer = mapToEntity(customerDto);
        Customer newCustomer = customerRepo.save(customer);

        // convert entity to DTO
        customerDTO custResponse = mapToDTO(newCustomer);
        return custResponse;
	}

	@Override
	public List<customerDTO> getAllCustomer() {
		 List<Customer> customers = customerRepository.findAll();
	     return customers.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
	}

	@Override
	public customerDTO getCustomerByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public customerDTO updateCustomerByEmail(customerDTO customerDto, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCustomerByEmail(String email) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	 // convert Entity into DTO
    private customerDTO mapToDTO(Customer customer){
    	customerDTO custDto = new customerDTO();
    	custDto.setId(customer.getId());
    	custDto.setName(customer.getName());
    	custDto.setEmail(customer.getEmail());
    	custDto.setContact(customer.getContactNumber());
        return custDto;
    }

    // convert DTO to entity
    private Customer mapToEntity(customerDTO custDto){
        Customer cust = new Customer();
        cust.setName(custDto.getName());
        cust.setEmail(custDto.getEmail());
        cust.setContactNumber(custDto.getContact());
        return cust;
    }

}
