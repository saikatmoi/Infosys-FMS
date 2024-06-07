package com.codecomputercoder.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.internal.Cascade;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@JsonIgnore
    @ManyToOne
    private Flight flight;
    //@JsonIgnore
    @OneToOne(cascade= CascadeType.ALL)
    private Schedule schedule;
    @Version
    private Long version;


    private int ticketPrice;
    private int bookedSeats;

    @JsonIgnore
    @OneToMany(mappedBy = "scheduledFlight",orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();


    public void addBooking(Booking b){
        bookings.add(b);
    }

}
