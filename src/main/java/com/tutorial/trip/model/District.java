package com.tutorial.trip.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="tbl_district")
public class District {
	@Id
	@SequenceGenerator(name = "district_state",sequenceName = "district_state",allocationSize = 1,initialValue = 33697)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "district_state")
	    private Long id;
	    private String name;
	    private double latitude;
	    private double longitude;
	    private String state;

}
