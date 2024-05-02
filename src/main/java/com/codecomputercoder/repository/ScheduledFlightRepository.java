package com.codecomputercoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codecomputercoder.entity.ScheduledFlight;

@Repository
public interface ScheduledFlightRepository extends JpaRepository<ScheduledFlight,Integer> {

}
