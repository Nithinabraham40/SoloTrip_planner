package com.tutorial.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tutorial.trip.model.UserPortfolio;

public interface UserProfileRepo extends JpaRepository<UserPortfolio, Long> {

	
	@Query(value = "select * from tbl_profile where fk_userid=:userId",nativeQuery = true)
	
	UserPortfolio findByUserId(Long userId);
	
	

}
