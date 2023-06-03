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
import javax.validation.constraints.NotEmpty;

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
@Table(name="tbl_attractions")
public class MainAttractionsInYourDestination {

   @Id
   @SequenceGenerator(name = "attraction_sequence",sequenceName = "attraction_sequence",allocationSize = 1,initialValue = 47895)
   @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "attraction_sequence")
   
	private Long attractionId;
	 
    @NotEmpty
    private String attractionName;
    @NotEmpty
    private String description;
    @NotEmpty
    private String timeToVisit;
    @NotEmpty
    private String diatanceFromYou;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_destnationId")
    @JsonIgnore
    private TravelDestinations travelDestination;
}
