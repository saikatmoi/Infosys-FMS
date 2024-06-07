package com.codecomputercoder.dto;

import com.codecomputercoder.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModifyScheduledFlightDTO {
    private int scheduledFlightId;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private int ticketPrice;
}
