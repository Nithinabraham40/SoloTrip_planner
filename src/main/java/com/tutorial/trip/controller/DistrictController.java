package com.tutorial.trip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.tutorial.trip.Servise.DistrictService;
import com.tutorial.trip.model.District;





	
	@RestController
	
	public class DistrictController {
	    

	    @Autowired
	    
       private  DistrictService districtService;

	  
	    
	    @PostMapping("districts/add")
	    public ResponseEntity<String> createDistricts(@RequestBody List<District> districts) {
	        districtService.saveAllDistricts(districts);
	        return ResponseEntity.status(HttpStatus.CREATED).body("Districts created successfully.");
	    }

	    @GetMapping("districts/get")
	    public List<District> getAllDistricts() {
	        return districtService.getAllDistricts();
	    }
	}

