package com.tutorial.trip.model;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name="tbl_token")
public class Authentication {

	
	
	@Id
	@SequenceGenerator(name = "token_sequence",sequenceName = "token_sequence0",allocationSize = 1,initialValue = 6000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "token_sequence")
	private Long authId;
	
	private String token;
	private LocalDate creationDate;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_userId")

	private User user;
	
	
	public Authentication(User user) {
		
		this.user=user;
		this.creationDate=LocalDate.now();
		this.token=UUID.randomUUID().toString();
		
	}
	
	
	
}
