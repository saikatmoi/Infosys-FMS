package com.codecomputercoder.airport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.codecomputercoder.entity.Airport;

import com.codecomputercoder.repository.AirportRepository;

@Service
public class AirportService {

@Autowired
private AirportRepository airportRepo;


public List<Airport> getAirportList(){


    return airportRepo.findAll();

}

}
