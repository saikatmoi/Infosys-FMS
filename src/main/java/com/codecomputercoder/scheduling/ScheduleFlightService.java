package com.codecomputercoder.scheduling;

import com.codecomputercoder.dto.ModifyScheduledFlightDTO;
import com.codecomputercoder.entity.*;
import com.codecomputercoder.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.codecomputercoder.dto.ScheduleFlightRequest;
import com.codecomputercoder.email.EmailServiceImpl;
import com.codecomputercoder.repository.FlightRepository;
import com.codecomputercoder.repository.ScheduleRepository;
import com.codecomputercoder.repository.ScheduledFlightRepository;

@Service
public class ScheduleFlightService {

    @Autowired
    private ScheduledFlightRepository scheduleFlightRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private EmailServiceImpl emailServiceImpl;

    public void scheduleNewFlight(ScheduleFlightRequest scheduleFlightRequest) {

    Schedule schedule = scheduleFlightRequest.getSchedule();
    System.out.println(schedule);
    Flight flight = flightRepository.findById(scheduleFlightRequest.getFlightNumber()).get();

    ScheduledFlight scheduledFlight = new ScheduledFlight();
    scheduledFlight.setSchedule(schedule);
    scheduledFlight.setBookedSeats(0);
    scheduledFlight.setTicketPrice(scheduleFlightRequest.getTicketPrice());

    flight.addScheduledFlight(scheduledFlight);
    flightRepository.save(flight);



    }

    public List<ScheduledFlight> viewScheduledFlights(){
        EmailDetails emailDetails=new EmailDetails("saikatmoi2@gmail.com","Test Email","Testing","");
        emailServiceImpl.sendSimpleMail(emailDetails);
        return scheduleFlightRepository.findAll();
    }


    public List<ScheduledFlight> viewScheduledFlightsbyDate(int i, int j, String dateString) {
        List<ScheduledFlight> allFlights =scheduleFlightRepository.findBySchedule_sourceAirport_airportCodeAndSchedule_destinationAirport_airportCode(
            i, j);
        
            List<ScheduledFlight> flightsByDate=new ArrayList<ScheduledFlight>();
            for(ScheduledFlight it: allFlights){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate inputDate = LocalDate.parse(dateString, formatter);

                // Extract the date part of arrivalTime as LocalDate
                LocalDate arrivalDate = it.getSchedule().getArrivalTime().toLocalDate();;
                if(arrivalDate.equals(inputDate)){
                    flightsByDate.add(it);
                }
                
            }

        return flightsByDate;
    }


    public boolean deleteScheduledFlightbyId(Integer scheduledFlightNumber) {

        try{
            ScheduledFlight scheduledFlight= scheduleFlightRepository.findById(scheduledFlightNumber).get();
            for(Booking b: scheduledFlight.getBookings()){
              String emailId= b.getUser().getEmail();
                EmailDetails emailDetails=new EmailDetails(emailId,"","Scheduled Flight "+scheduledFlightNumber+" Cancelled","");
                emailDetails.setMsgBody("Your Booking with Booking Id " +b.getId()+" has been cancelled due to the cancellation of the scheduled Flight.");
                emailServiceImpl.sendSimpleMail(emailDetails);
            }
            scheduleFlightRepository.deleteById(scheduledFlightNumber);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean modifyScheduledFlightbyId(ModifyScheduledFlightDTO modifyScheduledFlightDTO) {
       try {
           ScheduledFlight scheduledFlight = scheduleFlightRepository.findById(modifyScheduledFlightDTO.getScheduledFlightId()).get();
           scheduledFlight.setTicketPrice(modifyScheduledFlightDTO.getTicketPrice());
           Schedule schedule = scheduledFlight.getSchedule();
           schedule.setArrivalTime(modifyScheduledFlightDTO.getArrivalTime());
           schedule.setDepartureTime(modifyScheduledFlightDTO.getDepartureTime());
           System.out.println(modifyScheduledFlightDTO.getArrivalTime());
           System.out.println(modifyScheduledFlightDTO.getDepartureTime());
           scheduledFlight.setSchedule(schedule);
           scheduleFlightRepository.save(scheduledFlight);
           for(Booking b: scheduledFlight.getBookings()){
               String emailId= b.getUser().getEmail();
               EmailDetails emailDetails=new EmailDetails(emailId,"","Scheduled Flight with ID "+modifyScheduledFlightDTO.getScheduledFlightId()+" is Modified","");
               emailDetails.setMsgBody("Your Booking with Booking Id " +b.getId()+" has been modified." + "New Arrival Time : " + modifyScheduledFlightDTO.getArrivalTime() +"and Departure Time : "+modifyScheduledFlightDTO.getDepartureTime());
               emailServiceImpl.sendSimpleMail(emailDetails);
           }

           return true;
       }catch (Exception e){
           return false;
       }
    }

    public ScheduledFlight getScheduledFlightbyId(Integer scheduledFlightNumber) {
        return scheduleFlightRepository.findById(scheduledFlightNumber).get();
    }
}
