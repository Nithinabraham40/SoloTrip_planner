package com.tutorial.trip.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="tbl_booking")
public class Booking {
	
	@Id
	@SequenceGenerator(name = "booking_sequence",sequenceName = "booking_sequence",initialValue = 35698,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "booking_sequence")
	private Long bookingId;
	@NotEmpty
	private String userName;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	private String destinationName;
	@NotEmpty
	private String hotelName;
	
	private String token;
	
	private LocalDate dateOfVisit;
	
	private LocalDate checkOutDay;
	

}
