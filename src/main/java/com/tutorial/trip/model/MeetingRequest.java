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
import javax.validation.constraints.Email;

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
@Table(name="tbl_meeting")
public class MeetingRequest {

	@Id
	@SequenceGenerator(name = "meeting_sequence",sequenceName = "meeting_sequence",allocationSize = 1,initialValue = 879356)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "meeting_sequence")
	private Long meetingId;

	@Email
	private String friendEmail;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="fk_userId")
	@JsonIgnore
	private User user;
}
