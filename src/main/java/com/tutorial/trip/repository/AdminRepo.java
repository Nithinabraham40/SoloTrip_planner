package com.tutorial.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.trip.model.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long> {

	Admin findFirstByAdminEmail(String adminEmail);
	
	

}
