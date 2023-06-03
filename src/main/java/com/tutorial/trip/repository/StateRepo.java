package com.tutorial.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.trip.model.StatesWeHaveServise;

public interface StateRepo extends JpaRepository< StatesWeHaveServise, Long>{

	StatesWeHaveServise findFirstByStateName(String stateName);
	
	
	

}
