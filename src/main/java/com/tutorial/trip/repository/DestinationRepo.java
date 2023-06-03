package com.tutorial.trip.repository;



import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tutorial.trip.model.TravelDestinations;

public interface DestinationRepo extends JpaRepository<TravelDestinations, Long> {

	TravelDestinations findFirstByDestinationName(String destinationName);

	
	@Query(value="select * from tbl_destination where fk_state_id=:id",nativeQuery = true)
	List<TravelDestinations> getAllDestinationByStateId(Long id);


	TravelDestinations findFirstByDestID(Long id);

	
	




	



	

}
