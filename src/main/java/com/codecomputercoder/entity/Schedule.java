package com.codecomputercoder.entity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Airport sourceAirport;
    @ManyToOne
    private Airport destinationAirport;
    private Date arrivalTime;
    private Date departureTime;

    @JsonIgnore
    @OneToMany(mappedBy = "schedule",fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private List<ScheduledFlight> scheduledFlights=new ArrayList<>();

    public void addScheduledFlight(ScheduledFlight scheduledFlight) {
        scheduledFlights.add(scheduledFlight);
        //scheduledFlight.setFlight(this);
    }


}
