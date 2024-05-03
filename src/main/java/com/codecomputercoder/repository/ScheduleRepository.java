package com.codecomputercoder.repository;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codecomputercoder.entity.Airport;
import com.codecomputercoder.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

boolean existsBySourceAirportAndDestinationAirportAndArrivalTimeAndDepartureTime(
            Airport sourceAirport, Airport destinationAirport, Date arrivalTime, Date departureTime);

Schedule findBySourceAirportAndDestinationAirportAndArrivalTimeAndDepartureTime(Airport sourceAirport,
        Airport destinationAirport, Date arrivalTime, Date departureTime);

}
