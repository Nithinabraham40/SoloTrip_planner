package com.tutorial.trip.dto;


import java.util.List;

import com.tutorial.trip.model.HisIntrest;
import com.tutorial.trip.model.Sex;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnersRequestedDto {
	
	
	
    private String userName;
	private Sex userSex;
	private Integer userAge;
	private String userJob;
	private String userStatus;
	private String userState;
	private String userCity;
	private String userEmail;
	private String preferredLanguage;	
	private List<HisIntrest>listOfIntrest;
	
	private String regularWeekEndPlans;
	
}