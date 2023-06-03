package com.tutorial.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NearByDestnitationDto {

	
    private String destinationName;
	
	private String famousFor;
	
	private String timeToVist;
}
