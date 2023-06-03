package com.tutorial.trip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tutorial.trip.model.MainAttractionsInYourDestination;

public interface MainAttractionRepo extends JpaRepository<MainAttractionsInYourDestination, Long>{

	
	
	@Query(value="select * from tbl_attractions where fk_destnation_id=:id",
			nativeQuery = true
			)
	
	List<MainAttractionsInYourDestination> getByDestID(Long id);

	

	
	

}
