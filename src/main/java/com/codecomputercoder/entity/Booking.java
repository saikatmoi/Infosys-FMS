package com.codecomputercoder.entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JsonIgnore
    private ScheduledFlight scheduledFlight;
    
    @ManyToOne
    private UserInfo user;

    //@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL)
    private List<Passenger> passengerList;

    private Date bookingDate;

    private int ticketCost;


}
