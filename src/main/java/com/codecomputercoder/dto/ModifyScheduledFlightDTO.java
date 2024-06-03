package com.codecomputercoder.dto;

import com.codecomputercoder.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyScheduledFlightDTO {
    private int scheduledFlightId;
    private Date arrivalTime;
    private Date departureTime;
    private int ticketPrice;
}
