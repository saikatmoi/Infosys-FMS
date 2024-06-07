package com.codecomputercoder.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

    @JsonIgnore
    @OneToMany(mappedBy = "flight",cascade=CascadeType.ALL)
    private List<ScheduledFlight> scheduledFlights=new ArrayList<>();


    public void addScheduledFlight(ScheduledFlight scheduledFlight) {
        scheduledFlight.setFlight(this);
        scheduledFlights.add(scheduledFlight);

    }

    // public void removeScheduledFlight(ScheduledFlight scheduledFlight) {
    //     scheduledFlights.remove(scheduledFlight);
    //     scheduledFlight.setFlight(null);
    // }

}
