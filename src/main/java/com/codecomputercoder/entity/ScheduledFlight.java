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
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //@JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private Flight flight;
    //@JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    private Schedule schedule;


    private int ticketPrice;
    private int bookedSeats;
    
    // @JsonIgnore
    @OneToMany(mappedBy = "scheduledFlight")
    private List<Booking> bookings=new ArrayList<>();




}
