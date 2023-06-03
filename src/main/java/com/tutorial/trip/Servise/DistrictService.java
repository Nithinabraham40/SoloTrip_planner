package com.tutorial.trip.Servise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.trip.model.District;
import com.tutorial.trip.repository.DistrictRepository;

@Service
public class DistrictService {
	
	
	@Autowired
	private DistrictRepository districtRepo;
	

	 public void saveAllDistricts(List<District> districts) {
		 districtRepo.saveAll(districts);
	    }


	 public List<District> getAllDistricts() {
	        return districtRepo.findAll();
	    }
	
	

}
