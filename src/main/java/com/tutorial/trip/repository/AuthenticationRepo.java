package com.tutorial.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tutorial.trip.model.Authentication;

public interface AuthenticationRepo extends JpaRepository<Authentication, Long>{


	
	@Query(value="select * from tbl_token where token=:token",nativeQuery = true)

	Authentication findFirstbyToken(String token);
	
	
	
	

}
