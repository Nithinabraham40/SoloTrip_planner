package com.tutorial.trip.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.tutorial.trip.model.HisIntrest;
import com.tutorial.trip.model.Sex;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SignUpInput {
	
	@NotEmpty
	private String userName;
	@NotEmpty
	@Email
	private String userEmail;
	@NotEmpty
	private String userPassword;
	private Sex userSex;
	@NotEmpty
	private Integer userAge;
	@NotEmpty
	private String userJob;
	
	private String userStatus;
	@NotEmpty
	private String userState;
	@NotEmpty
	private String userCity;
	@NotEmpty
	private String preferredLanguage;
	
	private String regularWeekEndPlans;

	private List<HisIntrest>listOfuserIntrest;
	
	

}
