package com.codecomputercoder.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
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
    @OneToMany(mappedBy = "sourceAirport")
    private List<Schedule> sourceOfSchedule;
    
    @OneToMany(mappedBy = "destinationAirport")
    private List<Schedule> destOfSchedule;



}
