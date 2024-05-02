package com.codecomputercoder.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codecomputercoder.entity.ScheduledFlight;
import com.codecomputercoder.scheduling.ScheduleFlightService;


@RestController
public class SchedulingController {
    @Autowired
    private ScheduleFlightService scheduleFlightService;

    @PostMapping("/scheduleflight")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> scheduleFlight(@RequestBody ScheduledFlight scheduledFlight) {
    //scheduleFlightService.scheduleNewFlight(scheduledFlight);
    

    return new ResponseEntity<>(HttpStatus.CREATED);
       
    }



}
