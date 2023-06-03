package com.tutorial.trip.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DestinationDto {
	
	
	@NotEmpty
	@Pattern(regexp = "[A-Z][a-zA-Z]*", message = "Destination name must start with an uppercase letter")
	private String destinationName;
	@NotEmpty
	private String famousFor;
	@NotEmpty
	private String timeToVisit;
	@NotEmpty
	private Long stateId;
	@NotEmpty
    private Double latitude;
	@NotEmpty
	private Double longitude;

}
