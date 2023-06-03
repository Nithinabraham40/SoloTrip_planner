package com.tutorial.trip.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_states")
public class StatesWeHaveServise {
	
	@Id
	@SequenceGenerator(name = "state_sequence",sequenceName = "state_sequence",allocationSize = 1,initialValue = 4000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "state_sequence")
	private Long stateId;
	@NotEmpty
	@Pattern(regexp = "[A-Z][a-zA-Z]*", message = "State name must start with an uppercase letter")
	private String stateName;
	

}
