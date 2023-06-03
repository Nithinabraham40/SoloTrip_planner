package com.tutorial.trip.model;



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
@Table(name="tbl_admin")
public class Admin {
	
	@Id
	@SequenceGenerator(name = "admin_sequence",sequenceName = "admin_sequence",allocationSize = 1,initialValue = 110000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "admin_sequence")
	private Long adminId;
	 @NotEmpty
	 private String adminName;
	 @NotEmpty
	 @Email(message = "Invalid email address")
	 private String adminEmail;
	
	

}
