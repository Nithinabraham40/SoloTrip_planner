package com.tutorial.trip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tutorial.trip.model.District;

public interface DistrictRepository extends JpaRepository<District, Long> {

	
	
	District findByName(String userCity);

	List<District> findByState(String userState);
	
	

}
