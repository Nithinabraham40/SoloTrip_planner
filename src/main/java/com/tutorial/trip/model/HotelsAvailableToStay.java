package com.tutorial.trip.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="tbl_hotel")
public class HotelsAvailableToStay {
	@Id
	@SequenceGenerator(name = "hotel_sequence",sequenceName = "hotel_sequence",allocationSize = 1,initialValue = 54689)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "hotel_sequence")
	private Long hotelId;
	
	private String hotelName;
	
	private String description;
	
	private Integer totalRooms;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fk_traveldestId")
	@JsonIgnore
	private TravelDestinations travelDestination;
	
	

}
