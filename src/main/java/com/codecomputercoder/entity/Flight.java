package com.codecomputercoder.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int flightNumber;

    private int seatCapacity;
    private String carrierName;
    private String model;

    @OneToMany(mappedBy = "flight")
    private List<ScheduledFlight> scheduledFlights;


}
