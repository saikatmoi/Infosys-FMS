package com.codecomputercoder.flight;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codecomputercoder.entity.Airport;
import com.codecomputercoder.entity.Flight;
import com.codecomputercoder.repository.FlightRepository;

@Service
public class FlightService {


    @Autowired
    private FlightRepository flightRepository;

    public void addNewFlight(Flight flight) {
        flightRepository.save(flight);

    }

    public List<Flight> getFlightList() {
        System.out.println("Received Request");
        return flightRepository.findAll();
    }

    public boolean updateFlight(Flight flight) {
        
        try {
            Flight oldFlight=flightRepository.findById(flight.getFlightNumber()).get();
             flightRepository.save(flight);
             return true;
        } catch (Exception e) {
            return false;
        }
       
    }

    public boolean deleteFlight(Flight flight) {
        try {
            Flight oldFlight=flightRepository.findById(flight.getFlightNumber()).get();
             flightRepository.deleteById(flight.getFlightNumber());
             return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Flight getFlightbyId(Integer id) {
        try {
        Optional<Flight> optionalFlight = flightRepository.findById(id);
        return optionalFlight.orElse(null); 
        } catch (Exception e) {
            return null;
        }
        
    }


}
