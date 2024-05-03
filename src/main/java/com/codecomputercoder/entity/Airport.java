package com.codecomputercoder.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int airportCode;
    
    private String airportLocation;
    private String airportName;

    // @JsonIgnore
    // @OneToMany(mappedBy = "sourceAirport",fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    // private List<Schedule> sourceOfSchedule;
    // @JsonIgnore
    // @OneToMany(mappedBy = "destinationAirport",fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    // private List<Schedule> destOfSchedule;



}
