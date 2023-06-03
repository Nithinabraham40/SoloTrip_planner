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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="tbl_Destination")
public class TravelDestinations {

	@Id
	@SequenceGenerator(name = "destination_sequence",sequenceName = "destination_sequence",allocationSize = 1,initialValue = 17000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "destination_sequence")
	 private Long destID;
	
	 private String destinationName;
	
	 private String famousFor;
	
	 private String timeToVist;
	
	 private Double latitude;
	 private Double longitude;
	
	
	@ManyToOne(fetch  = FetchType.EAGER)
	@JoinColumn(name = "fk_stateId")
	private StatesWeHaveServise stateWeHaveServise;
	
	
	
}
