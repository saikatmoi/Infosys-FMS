package com.codecomputercoder.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codecomputercoder.dto.ScheduleFlightRequest;
import com.codecomputercoder.email.EmailServiceImpl;
import com.codecomputercoder.entity.Airport;
import com.codecomputercoder.entity.EmailDetails;
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
    @Autowired
    private EmailServiceImpl emailServiceImpl;

    public void scheduleNewFlight(ScheduleFlightRequest scheduleFlightRequest) {

    Schedule schedule = scheduleFlightRequest.getSchedule();
    Flight flight = flightRepository.findById(scheduleFlightRequest.getFlightNumber()).get();

    ScheduledFlight scheduledFlight = new ScheduledFlight();
    scheduledFlight.setFlight(flight);
    scheduledFlight.setSchedule(schedule);
    scheduledFlight.setBookedSeats(0);
    scheduledFlight.setTicketPrice(scheduleFlightRequest.getTicketPrice());

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
        EmailDetails emailDetails=new EmailDetails("saikatmoi2@gmail.com","Test Email","Testing","");
        emailServiceImpl.sendSimpleMail(emailDetails);
        return scheduleFlightRepository.findAll();
    }


    public List<ScheduledFlight> viewScheduledFlightsbyDate(int i, int j, String date) {
        List<ScheduledFlight> allFlights =scheduleFlightRepository.findBySchedule_sourceAirport_airportCodeAndSchedule_destinationAirport_airportCode(
            i, j);
        
            List<ScheduledFlight> flightsByDate=new ArrayList<ScheduledFlight>();
            for(ScheduledFlight it: allFlights){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                
                String flightdate=sdf.format(it.getSchedule().getArrivalTime());
                System.out.println(flightdate+"  "+date);
                if(flightdate.equals(date)){
                    flightsByDate.add(it);
                }
                
            }

        return flightsByDate;
    }



}
