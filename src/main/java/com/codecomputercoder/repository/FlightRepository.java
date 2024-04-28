package com.codecomputercoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codecomputercoder.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight,Integer> {

    
    
} 
