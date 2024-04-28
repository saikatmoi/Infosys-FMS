package com.codecomputercoder.flight;

import java.util.List;

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

}
