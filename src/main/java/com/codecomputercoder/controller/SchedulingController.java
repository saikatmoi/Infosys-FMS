package com.codecomputercoder.controller;
import java.util.ArrayList;
import java.util.List;

import com.codecomputercoder.dto.ModifyScheduledFlightDTO;
import com.codecomputercoder.dto.SearchFlightsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.codecomputercoder.dto.ScheduleFlightRequest;
import com.codecomputercoder.dto.ScheduledFlightbyDateRequest;
import com.codecomputercoder.entity.Flight;
import com.codecomputercoder.entity.ScheduledFlight;
import com.codecomputercoder.flight.FlightService;
import com.codecomputercoder.scheduling.ScheduleFlightService;


@RestController
@CrossOrigin
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

    @GetMapping("/getscheduledflight/{scheduledFlightNumber}")
    public ResponseEntity<?> viewScheduledFlight(@PathVariable Integer scheduledFlightNumber){
        ScheduledFlight scheduledFlight=scheduleFlightService.getScheduledFlightbyId(scheduledFlightNumber);

        return new ResponseEntity<>(scheduledFlight,HttpStatus.OK);
        //return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deletescheduledflight/{scheduledFlightNumber}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteScheduledFlights(@PathVariable Integer scheduledFlightNumber){


        if(scheduleFlightService.deleteScheduledFlightbyId(scheduledFlightNumber)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/modifyscheduledflight")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateScheduledFlights(@RequestBody ModifyScheduledFlightDTO modifyScheduledFlightDTO){


        if(scheduleFlightService.modifyScheduledFlightbyId(modifyScheduledFlightDTO)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getflightschedule")
    public ResponseEntity<?> viewAllScheduledFlights(){
        List<ScheduledFlight> scheduledFlights=scheduleFlightService.viewScheduledFlights();
        //System.out.println(flight);
         return new ResponseEntity<>(scheduledFlights,HttpStatus.OK);
        //return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/getflightsbydate")
    public ResponseEntity<?> viewAllScheduledFlightsbyDate(@RequestBody ScheduledFlightbyDateRequest scheduledFlightbyDateRequest){
        List<ScheduledFlight> scheduledFlights=scheduleFlightService.viewScheduledFlightsbyDate(scheduledFlightbyDateRequest.getSrcAirportId(),
        scheduledFlightbyDateRequest.getDesAirportId(),scheduledFlightbyDateRequest.getDate());
        //System.out.println(flight);
        List<SearchFlightsResponseDTO> searchFlightsResponseDTOS=new ArrayList<>();
        for(ScheduledFlight it :scheduledFlights){SearchFlightsResponseDTO temp=new SearchFlightsResponseDTO(it.getId(),it.getFlight(),it.getSchedule().getArrivalTime(),it.getSchedule().getDepartureTime(),it.getFlight().getSeatCapacity()-it.getBookedSeats(),it.getTicketPrice());
        searchFlightsResponseDTOS.add(temp);
        }
         
         return new ResponseEntity<>(searchFlightsResponseDTOS,HttpStatus.OK);
        //return new ResponseEntity<>(HttpStatus.OK);
    }



}
