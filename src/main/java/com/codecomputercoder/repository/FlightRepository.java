package com.codecomputercoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codecomputercoder.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Integer> {

    
    
} 
