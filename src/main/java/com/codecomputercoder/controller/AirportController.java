package com.codecomputercoder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.codecomputercoder.airport.AirportService;
import com.codecomputercoder.entity.Airport;
import com.codecomputercoder.entity.Flight;
import com.codecomputercoder.flight.FlightService;

@RestController
@CrossOrigin
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

    @PutMapping("/updateflight")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateFlight(@RequestBody Flight flight) {


    if(flightService.updateFlight((flight))){
        return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    
    
       
    }

    @DeleteMapping("/deleteflight")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteFlight(@RequestBody Flight flight) {


    if(flightService.deleteFlight((flight))){
        return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    
    
       
    }

    @GetMapping("/flights")
    public ResponseEntity<?> getFlightList() {

    List<Flight> flights= flightService.getFlightList();
    return new ResponseEntity<>(flights,HttpStatus.OK);
       
    }

    @GetMapping("/flight/{id}")
    public ResponseEntity<?> getFlightbyId(@PathVariable Integer id) {

    Flight flight= flightService.getFlightbyId(id);
    if(flight!=null){
        return new ResponseEntity<>(flight,HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    
       
    }



}
