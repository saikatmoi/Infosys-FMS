package com.codecomputercoder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codecomputercoder.airport.AirportService;
import com.codecomputercoder.entity.Airport;
import com.codecomputercoder.entity.Flight;
import com.codecomputercoder.flight.FlightService;

@RestController
public class AirportController {

    @Autowired
    private AirportService airportService;
    @Autowired
    private FlightService flightService;
    
    @GetMapping("/airports")
    public ResponseEntity<?> getAirportList() {

    List<Airport> airports= airportService.getAirportList();
    return new ResponseEntity<>(airports,HttpStatus.OK);
       
    }

    @PostMapping("/addflight")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> addNewFlight(@RequestBody Flight flight) {

    flightService.addNewFlight((flight));;
    return new ResponseEntity<>(HttpStatus.CREATED);
       
    }



}
