package com.codecomputercoder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.codecomputercoder.booking.BookingService;
import com.codecomputercoder.dto.BookingRequest;
import com.codecomputercoder.entity.Booking;

@RestController
@CrossOrigin
public class BookingController {

    @Autowired
    private BookingService bookingService;



    @PostMapping("/booktickets")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> scheduleFlight(@RequestBody BookingRequest bookingRequest) {
     try{
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
         String username = userDetails.getUsername();
         bookingService.bookTickets(username,bookingRequest);
      return new ResponseEntity<>(HttpStatus.CREATED);
     }
     catch (Exception e){
         e.printStackTrace(); // Log the exception
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     }
    


       
    }

    @GetMapping("/viewbookings")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> viewAllBookings() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		  String username = userDetails.getUsername();
      List<Booking> bookings=bookingService.viewBookings(username);
    

    return new ResponseEntity<>(bookings,HttpStatus.CREATED);
       
    }

    @GetMapping("/viewbooking/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> viewBooking(@PathVariable Long id) {
  
    Booking bookings=bookingService.viewBookingById(id);
    

    return new ResponseEntity<>(bookings,HttpStatus.OK);
       
    }

    @DeleteMapping("/deletebooking/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<?> deleteBookings(@PathVariable Long id) {
  
    Boolean value =bookingService.deleteBookingById(id);
    

    return new ResponseEntity<>(value,HttpStatus.OK);
       
    }




}
