package com.tutorial.trip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tutorial.trip.model.HisIntrest;


public interface HisIntrestRepo extends JpaRepository<HisIntrest, Long>{

	
	
	@Query(
			value="select * from tbl_intrest where fk_profile_id=:profileId",
			nativeQuery = true
			
			)
	List<HisIntrest> findByprofileID(Long profileId);

	

	
	
	

}
