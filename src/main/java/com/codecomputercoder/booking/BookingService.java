package com.codecomputercoder.booking;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.codecomputercoder.dto.BookingRequest;
import com.codecomputercoder.email.EmailServiceImpl;
import com.codecomputercoder.entity.Booking;
import com.codecomputercoder.entity.ScheduledFlight;
import com.codecomputercoder.entity.UserInfo;
import com.codecomputercoder.repository.BookingRepository;
import com.codecomputercoder.repository.FlightRepository;
import com.codecomputercoder.repository.ScheduleRepository;
import com.codecomputercoder.repository.ScheduledFlightRepository;
import com.codecomputercoder.repository.UserInfoRepository;

@Service
public class BookingService {

    @Autowired
    private ScheduledFlightRepository scheduleFlightRepository;
    @Autowired
    private UserInfoRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private EmailServiceImpl emailService;

    public void booktickets(BookingRequest bookingRequest) {
        Booking booking =new Booking();
        ScheduledFlight scheduledFlight=scheduleFlightRepository.findById(bookingRequest.getScheduledFlightId()).get();
        UserInfo user=userRepository.findById(bookingRequest.getUserId()).get();
        booking.setPassengerList(bookingRequest.getPassengerList());
        booking.setTicketCost(bookingRequest.getPassengerList().size()*scheduledFlight.getTicketPrice());
        booking.setUser(user);
        booking.setBookingDate(new Date());
        List<Booking>bookings=user.getBookings();
        bookings.add(booking);
        user.setBookings(bookings);
        bookings=scheduledFlight.getBookings();
        bookings.add(booking);
        booking.setScheduledFlight(scheduledFlight);
        scheduledFlight.setBookedSeats(scheduledFlight.getBookedSeats()+bookingRequest.getPassengerList().size());
        scheduledFlight.setBookings(bookings);

        userRepository.save(user);
        scheduleFlightRepository.save(scheduledFlight);
        bookingRepository.save(booking);
        
    }

    public List<Booking> viewBookings(String username) {
        // TODO Auto-generated method stub

        return bookingRepository.findByUser_userName(username);
        
    }

    public Booking viewBookingById(Long bookingId) {
        // TODO Auto-generated method stub
        Booking booking=bookingRepository.findById(bookingId).get();


        Context context = new Context();
        // Set variables for the template from the POST request data
        context.setVariable("booking", booking);
        try {
            emailService.sendEmail("saikatmoi2@gmail.com", "Booking Ticket", "bookingConfirmationTemplate", context);
            //return "Email sent successfully!";
        } catch (Exception e) {
            //return "Error sending email: " + e.getMessage();
        }
        return booking;
        
    }

}
