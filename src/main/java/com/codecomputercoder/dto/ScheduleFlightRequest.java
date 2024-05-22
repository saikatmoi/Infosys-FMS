package com.codecomputercoder.dto;

import com.codecomputercoder.entity.Schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleFlightRequest {

    private int flightNumber;
    private Schedule schedule;
    private int ticketPrice;

    
    
}
