package com.example.B.repository;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.example.B.*;
import org.springframework.stereotype.Repository;

@Repository
public interface customerRepository extends JpaRepository<Customer, Integer>{

	@Query("from Customer where email=?1")
	Customer findByEmail(String email);
	
	void deleteByEmail(String email);

	boolean existsByEmail(String email);

}
