package com.tutorial.trip.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


   public class RestrauntDto {
	
	  @NotEmpty
	  private String hotelName;
	  @NotEmpty
	  private String description;
	  @NotEmpty
	  private Integer totalRooms;

}
