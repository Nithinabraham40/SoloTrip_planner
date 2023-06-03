package com.tutorial.trip.model;



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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="tbl_profile")
public class UserPortfolio {
	
	@Id
	@SequenceGenerator(name = "profile_sequence",sequenceName = "profile_sequence",allocationSize = 1,initialValue = 80000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="profile_sequence")
	private Long profileId;
	private Sex userSex;
	private Integer userAge;
	private String userJob;
	private String userStatus;
	private String userState;
	private String userCity;
	private String preferredLanguage;
	
	private String regularWeekEndPlans;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_userid")
	private User user;
	
	
	

}
