package com.tutorial.trip.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tutorial.trip.model.Booking;

public interface BookingRepo extends JpaRepository<Booking, Long> {

	Booking findFirstByToken(String token);

	@Modifying
	@Transactional
	@Query(
	    value = "update tbl_booking set date_of_visit=:visitdate, check_out_day=:checkoutdate where token=:token",
	    nativeQuery = true
	)
	void updateDatesByToken(@Param("visitdate") LocalDate visitdate, @Param("checkoutdate") LocalDate checkoutdate, @Param("token") String token);

	
	@Query(
		    value = "SELECT * FROM tbl_booking WHERE destination_name = :destinationName AND booking_id != :bookingId",
		    nativeQuery = true
		)
		List<Booking> findBookingsOfAllInSameLocation(@Param("bookingId") Long bookingId, @Param("destinationName") String destinationName);

	
	@Query(
			value="select * from tbl_booking where destination_name = :destinationName And hotel_name=:hotelName ",
			nativeQuery = true
			
			)
	
	
	
	List<Booking> findByHotelNameAndDestinationName(@Param("hotelName") String hotelName,@Param("destinationName")  String destinationName);
}
