package com.tutorial.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.trip.model.User;

public interface UserRepository extends JpaRepository<User, Long>{



	User findFirstByUserEmail(String userEmail);
	
	

}
