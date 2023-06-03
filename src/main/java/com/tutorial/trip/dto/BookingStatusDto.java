package com.tutorial.trip.dto;

import java.util.UUID;



import lombok.Data;


@Data
public class BookingStatusDto {
	
	
	
	private String bookingToken=UUID.randomUUID().toString();

}
