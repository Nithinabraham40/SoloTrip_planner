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
@Table(name="tbl_intrest")
public class HisIntrest {
	
	
	@Id
	@SequenceGenerator(name = "intrest_sequence",sequenceName = "intrest_sequence",allocationSize = 1,initialValue = 1000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "intrest_sequence")
	@JsonIgnore
	private Long IntrestId;
	
	private  String intrest;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_profileId")
	@JsonIgnore
	private UserPortfolio userProfile;
	

	
	

}
