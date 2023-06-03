package com.tutorial.trip.Servise;

import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tutorial.trip.dto.DestinationDto;
import com.tutorial.trip.dto.RestrauntDto;
import com.tutorial.trip.model.Admin;
import com.tutorial.trip.model.HotelsAvailableToStay;
import com.tutorial.trip.model.MainAttractionsInYourDestination;
import com.tutorial.trip.model.StatesWeHaveServise;
import com.tutorial.trip.model.TravelDestinations;
import com.tutorial.trip.repository.AdminRepo;
import com.tutorial.trip.repository.DestinationRepo;
import com.tutorial.trip.repository.HotelsAvailableToStayRepo;
import com.tutorial.trip.repository.MainAttractionRepo;
import com.tutorial.trip.repository.StateRepo;



@Service
public class AdminService {
	
	
	@Autowired
	private AdminRepo adminRepo;
	
	
	@Autowired
	private StateRepo stateRepo;
	
	
	@Autowired
	private DestinationRepo destinationRepo;
	
	
	@Autowired
	private HotelsAvailableToStayRepo hotelsAvailableToStayRepo;
	
	@Autowired
	private MainAttractionRepo mainAttractionRepo;
	
	

	public ResponseEntity<String> addAdmin(Admin admin) {
		
		Admin adMin=adminRepo.findFirstByAdminEmail(admin.getAdminEmail());
		
		if(adMin!=null) {return new ResponseEntity<String>("Admin already exist with the give mail",HttpStatus.BAD_REQUEST);}
		
		adminRepo.save(admin);
		
		
		return new ResponseEntity<String>("Admin added sucessfully",HttpStatus.OK);
	}

	public ResponseEntity<String> addState(StatesWeHaveServise state) {
		
		StatesWeHaveServise stateInDataBase=stateRepo.findFirstByStateName(state.getStateName());
		
		if(stateInDataBase!=null) {return new ResponseEntity<String>("State already in database add different state",HttpStatus.BAD_REQUEST);}
		
		
		
		stateRepo.save(state);
		
		return new ResponseEntity<String>("State added",HttpStatus.OK);
	}

	public ResponseEntity<String> addDestination(DestinationDto destination) {
		
		TravelDestinations destinationInDataBase=destinationRepo.findFirstByDestinationName(destination.getDestinationName());
		
		if(destinationInDataBase!=null) {return new ResponseEntity<String>("Destination with these name already exist",HttpStatus.BAD_REQUEST);}
		
		//find state using id
		
		Long stateId=destination.getStateId();
		
		StatesWeHaveServise state=stateRepo.findById(stateId).get();
		//add input to traveldestination
		
		
		TravelDestinations travelDestination=TravelDestinations.builder().destinationName(destination.getDestinationName())
				.famousFor(destination.getFamousFor()).stateWeHaveServise(state).timeToVist(destination.getTimeToVisit()).latitude(destination.getLatitude()).longitude(destination.getLongitude())
				.build();
		
		//save destination
		
		
		destinationRepo.save(travelDestination);
		
		
		return new ResponseEntity<String>("Destination added sucessfully",HttpStatus.OK);
	}

	public List<TravelDestinations> getDestinationsByState(Long id) {
		
		List<TravelDestinations>getDestinationByStateID=destinationRepo.getAllDestinationByStateId(id);
		
		return getDestinationByStateID ;
	}

	public ResponseEntity<String> addRestraunt(Long id, RestrauntDto restrauntDto) {
		
		
		TravelDestinations travelDestination=destinationRepo.findFirstByDestID(id);
		
		if(travelDestination==null) {return new ResponseEntity<String>("Destination not found give valid id",HttpStatus.BAD_GATEWAY);}
		
		
		
		HotelsAvailableToStay hotelToStay=HotelsAvailableToStay.builder().description(restrauntDto.getDescription())
				
				.totalRooms(restrauntDto.getTotalRooms()).hotelName(restrauntDto.getHotelName()).travelDestination(travelDestination)
				.build();
		
		hotelsAvailableToStayRepo.save(hotelToStay);
		
		
		return new ResponseEntity<String>("Hotel added",HttpStatus.ACCEPTED);
	}

	
	
	public List<HotelsAvailableToStay>getHotels(String name) {
		
		List<HotelsAvailableToStay>allHotelsAvailable=new ArrayList<>();
		
		TravelDestinations travelDestination=destinationRepo.findFirstByDestinationName(name);
	
		
		
		if(travelDestination==null) {return allHotelsAvailable;}
		
		Long destId=travelDestination.getDestID();
	
	    allHotelsAvailable=hotelsAvailableToStayRepo.getHotels(destId);
		
		return allHotelsAvailable;
	}

	public ResponseEntity<String> addAttractions(String destination, MainAttractionsInYourDestination attractions) {
	
		TravelDestinations travelDestination=destinationRepo.findFirstByDestinationName(destination);
		
		if(travelDestination==null) {
			
			return new ResponseEntity<String>("destination not found",HttpStatus.BAD_REQUEST);
		}
		
		attractions.setTravelDestination(travelDestination);
		
		 mainAttractionRepo.save(attractions);
		
		
		return new ResponseEntity<String>("Attractions added",HttpStatus.OK);
	}

	public List<MainAttractionsInYourDestination> getAttratactionByDestnationId(Long id) {
		
		
		List<MainAttractionsInYourDestination>getAllByDestId=mainAttractionRepo.getByDestID(id);
		
		return getAllByDestId;
	}

	public ResponseEntity<String> deleteAllDestination() {
	
		destinationRepo.deleteAll();
		
		return new ResponseEntity<String>("Deleted",HttpStatus.OK);
	}

	
	
	
	
	
	
	

}
