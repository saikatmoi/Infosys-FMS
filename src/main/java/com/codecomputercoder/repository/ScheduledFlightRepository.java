package com.codecomputercoder.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codecomputercoder.entity.Airport;
import com.codecomputercoder.entity.ScheduledFlight;

@Repository
public interface ScheduledFlightRepository extends JpaRepository<ScheduledFlight,Integer> {


    List<ScheduledFlight> findByScheduleSourceAirportAndScheduleDestinationAirport(
            Airport sourceAirport, Airport destinationAirport);

    List<ScheduledFlight> findBySchedule_sourceAirport_airportCodeAndSchedule_destinationAirport_airportCode(int i,
            int j);

}
