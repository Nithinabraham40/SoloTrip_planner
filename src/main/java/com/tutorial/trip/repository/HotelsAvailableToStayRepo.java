package com.tutorial.trip.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tutorial.trip.model.HotelsAvailableToStay;

public interface HotelsAvailableToStayRepo extends JpaRepository<HotelsAvailableToStay, Long>{

	
	
	@Query(
			value="select * from tbl_hotel where fk_traveldest_id=:destId",
			nativeQuery = true
			)
	List<HotelsAvailableToStay> getHotels(Long destId);


	@Query(
			value="SELECT * FROM tbl_hotel where hotel_name=:hotelName and fk_traveldest_id=:destID",
			nativeQuery = true
			)

	HotelsAvailableToStay findByHotelNameAndDestinationId(String hotelName, Long destID);

	
	
	@Modifying
	@Transactional
	@Query(
			
			value="update tbl_hotel set total_rooms=:totalRooms where hotel_id=:hotelId",
			
			nativeQuery=true
			
			)
	

	void updateThenumberOfRoomsById(Integer totalRooms, Long hotelId);

	
	
	

}
