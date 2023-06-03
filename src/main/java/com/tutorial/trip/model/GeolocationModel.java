package com.tutorial.trip.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeolocationModel {

    private String city;
    private String latitude;
    private String longitude;

}
