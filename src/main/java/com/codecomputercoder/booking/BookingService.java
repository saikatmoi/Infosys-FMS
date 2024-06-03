package com.codecomputercoder.booking;

import java.util.Date;
import java.util.List;

import com.codecomputercoder.entity.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import com.codecomputercoder.dto.BookingRequest;
import com.codecomputercoder.email.EmailServiceImpl;
import com.codecomputercoder.entity.Booking;
import com.codecomputercoder.entity.ScheduledFlight;
import com.codecomputercoder.entity.UserInfo;
import com.codecomputercoder.repository.BookingRepository;
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

    @Transactional
    public Boolean bookTickets (String username, BookingRequest bookingRequest) throws Exception{
        System.out.println(bookingRequest);
        Booking booking = new Booking();
        ScheduledFlight scheduledFlight = scheduleFlightRepository.findById(bookingRequest.getScheduledFlightId()).get();
        UserInfo user = userRepository.findByUserName(username).get();
        int availableSeats = scheduledFlight.getFlight().getSeatCapacity() - scheduledFlight.getBookedSeats();
        if (availableSeats < bookingRequest.getPassengerList().size()) {
            return false;
        }
        booking.setPassengerList(bookingRequest.getPassengerList());
        booking.setTicketCost(bookingRequest.getPassengerList().size() * scheduledFlight.getTicketPrice());
        booking.setUser(user);
        booking.setBookingDate(new Date());
        List<Booking> bookings = user.getBookings();
        bookings.add(booking);
        user.setBookings(bookings);
        bookings = scheduledFlight.getBookings();
        bookings.add(booking);
        booking.setScheduledFlight(scheduledFlight);
        scheduledFlight.setBookedSeats(scheduledFlight.getBookedSeats() + bookingRequest.getPassengerList().size());
        scheduledFlight.setBookings(bookings);

            userRepository.save(user);
            scheduleFlightRepository.save(scheduledFlight);
            booking = bookingRepository.save(booking);
            sendTicket(booking);
            return true;



    }

    public List<Booking> viewBookings(String username) {


        return bookingRepository.findByUser_userName(username);

    }


    public Booking viewBookingById(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId).get();
        return booking;

    }

    public Boolean deleteBookingById(Long id) {
        try {
            Booking booking = bookingRepository.findById(id).get();
            int noPassengers = booking.getPassengerList().size();
            ScheduledFlight scheduledFlight = booking.getScheduledFlight();
            scheduledFlight.setBookedSeats(scheduledFlight.getBookedSeats() - noPassengers);
            scheduleFlightRepository.save(scheduledFlight);
            bookingRepository.deleteById(id);
            EmailDetails emailDetails=new EmailDetails(booking.getUser().getEmail(),"","Booking Cancelled","");
            emailDetails.setMsgBody("Your Booking with Booking Id " +id+" has been cancelled successfully.");
            emailService.sendSimpleMail(emailDetails);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void sendTicket(Booking booking) {
        Context context = new Context();
        // Set variables for the template from the POST request data
        context.setVariable("booking", booking);
        try {
            emailService.sendEmail(booking.getUser().getEmail(), "Booking Confirmation Ticket", "bookingConfirmationTemplate", context);
            //return "Email sent successfully!";
        } catch (Exception e) {
            //return "Error sending email: " + e.getMessage();
        }
    }

}
