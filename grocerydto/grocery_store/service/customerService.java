package com.grocery_store.service;
import com.grocery_store.dto.*;
import java.util.List;
public interface customerService {
	 customerDTO createPost(customerDTO customerDto);

	 List<customerDTO> getAllCustomer();

	 customerDTO getCustomerByEmail(String email);

	 customerDTO updateCustomerByEmail(customerDTO customerDto, String email);

	 void deleteCustomerByEmail(String email);
}
