package com.tutorial.trip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tutorial.trip.Servise.AdminService;
import com.tutorial.trip.dto.DestinationDto;
import com.tutorial.trip.dto.RestrauntDto;
import com.tutorial.trip.model.Admin;
import com.tutorial.trip.model.HotelsAvailableToStay;
import com.tutorial.trip.model.MainAttractionsInYourDestination;
import com.tutorial.trip.model.StatesWeHaveServise;
import com.tutorial.trip.model.TravelDestinations;



@RestController
@RequestMapping("/admin")
public class AdminController {

	
	@Autowired
	private AdminService adminService;
	
	
	
	@PostMapping("/add")
	public ResponseEntity<String>addAdmin(@RequestBody Admin admin){
		
		
		return adminService.addAdmin(admin);
		
	}
	
	@PostMapping("/add/states")
	
	public ResponseEntity<String>addState(@RequestBody StatesWeHaveServise state){
		
		return adminService.addState(state);
	}
	
	
	
	@PostMapping("/add/destnation")
	
	public ResponseEntity<String>addDestination(@RequestBody DestinationDto destination){
		
		return adminService.addDestination(destination);
	}
	
	@DeleteMapping("/delete/destination")
	
	
	public ResponseEntity<String>deleteDestinations(){
		
		
		return adminService.deleteAllDestination();
	}
	
	@GetMapping("/get/destinations/stateid/{id}")
	
	public List<TravelDestinations> getDestinationsByState(@PathVariable Long id){
		
		return adminService.getDestinationsByState(id);
	}
	
	@PostMapping("/add/hotel/destid/{id}")
	
	public ResponseEntity<String>addHotel(@PathVariable("id") Long id,@RequestBody RestrauntDto restrauntDto){
		
		
		return adminService.addRestraunt(id,restrauntDto);
		
	}
	
	@GetMapping("get/hotels/destnationname/{name}")
	
	public List<HotelsAvailableToStay>getHotels(@PathVariable ("name") String name){
		
		
		return adminService.getHotels(name);
		
	}
	@PostMapping("/add/attraction/{destination}")
	
	public ResponseEntity<String>addAttractions(@PathVariable ("destination")String destination,@RequestBody MainAttractionsInYourDestination attractions){
		
		return adminService.addAttractions(destination,attractions);
		
	}
	
	@GetMapping("get/attractions/{destId}")
	public List<MainAttractionsInYourDestination>getAttratactionByDestnationId(@PathVariable("destId") Long id){
		
		return adminService.getAttratactionByDestnationId(id);
		
	}
	
	
	

	
	
	
	
	
}
