package com.codecomputercoder.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import com.codecomputercoder.dto.ScheduleFlightRequest;
import com.codecomputercoder.entity.Airport;
import com.codecomputercoder.entity.Flight;
import com.codecomputercoder.entity.Schedule;
import com.codecomputercoder.entity.ScheduledFlight;
import com.codecomputercoder.repository.FlightRepository;
import com.codecomputercoder.repository.ScheduleRepository;
import com.codecomputercoder.repository.ScheduledFlightRepository;

@Service
public class ScheduleFlightService {

    @Autowired
    private ScheduledFlightRepository scheduleFlightRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private FlightRepository flightRepository;

    public void scheduleNewFlight(ScheduleFlightRequest scheduleFlightRequest) {

    Schedule schedule = scheduleFlightRequest.getSchedule();
    Flight flight = flightRepository.findById(scheduleFlightRequest.getFlightNumber()).get();

    ScheduledFlight scheduledFlight = new ScheduledFlight();
    scheduledFlight.setFlight(flight);
    scheduledFlight.setSchedule(schedule);
    flight.addScheduledFlight(scheduledFlight);

    if (scheduleRepository.existsBySourceAirportAndDestinationAirportAndArrivalTimeAndDepartureTime(
            schedule.getSourceAirport(), schedule.getDestinationAirport(), schedule.getArrivalTime(), schedule.getDepartureTime())) {
        schedule = scheduleRepository.findBySourceAirportAndDestinationAirportAndArrivalTimeAndDepartureTime(
                schedule.getSourceAirport(), schedule.getDestinationAirport(), schedule.getArrivalTime(), schedule.getDepartureTime());
    }

    schedule.addScheduledFlight(scheduledFlight);
    scheduleRepository.save(schedule);
    flightRepository.save(flight);


    }

    public List<ScheduledFlight> viewScheduledFlights(){
        return scheduleFlightRepository.findAll();
    }


    public List<ScheduledFlight> viewScheduledFlightsbyDate(Airport origin, Airport destination, LocalDate date) {
        return scheduleFlightRepository.findByScheduleSourceAirportAndScheduleDestinationAirportAndScheduleDepartureTimeBetween(
                origin, destination, date.atStartOfDay(), date.plusDays(1).atStartOfDay());
    }



}
