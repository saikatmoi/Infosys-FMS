package com.codecomputercoder.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codecomputercoder.dto.ScheduleFlightRequest;
import com.codecomputercoder.dto.ScheduledFlightbyDateRequest;
import com.codecomputercoder.entity.Flight;
import com.codecomputercoder.entity.ScheduledFlight;
import com.codecomputercoder.flight.FlightService;
import com.codecomputercoder.scheduling.ScheduleFlightService;


@RestController
public class SchedulingController {
    @Autowired
    private ScheduleFlightService scheduleFlightService;
    @Autowired
    private FlightService flightService;

    @PostMapping("/scheduleflight")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> scheduleFlight(@RequestBody ScheduleFlightRequest scheduleFlightRequest) {
    scheduleFlightService.scheduleNewFlight(scheduleFlightRequest);
    

    return new ResponseEntity<>(HttpStatus.CREATED);
       
    }

    @GetMapping("/getflightschedule/{flightNumber}")
    public ResponseEntity<?> viewScheduledFlights(@PathVariable Integer flightNumber){
        Flight flight=flightService.getFlightbyId(flightNumber);
        //System.out.println(flight);
         List<ScheduledFlight> scheduledFlights=flight.getScheduledFlights();
         return new ResponseEntity<>(scheduledFlights,HttpStatus.OK);
        //return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/getflightschedule")
    public ResponseEntity<?> viewAllScheduledFlights(){
        List<ScheduledFlight> scheduledFlights=scheduleFlightService.viewScheduledFlights();
        //System.out.println(flight);
         return new ResponseEntity<>(scheduledFlights,HttpStatus.OK);
        //return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getflightsbydate")
    public ResponseEntity<?> viewAllScheduledFlightsbyDate(@RequestBody ScheduledFlightbyDateRequest scheduledFlightbyDateRequest){
        List<ScheduledFlight> scheduledFlights=scheduleFlightService.viewScheduledFlightsbyDate(scheduledFlightbyDateRequest.getSrcAirport(),
        scheduledFlightbyDateRequest.getDesAirport(),scheduledFlightbyDateRequest.getDate());
        //System.out.println(flight);
         
         return new ResponseEntity<>(scheduledFlights,HttpStatus.OK);
        //return new ResponseEntity<>(HttpStatus.OK);
    }



}
