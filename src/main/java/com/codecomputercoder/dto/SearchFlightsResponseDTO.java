package com.codecomputercoder.dto;

import com.codecomputercoder.entity.Flight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFlightsResponseDTO {
    Long id;
    Flight flight;
    LocalDateTime arrivalTime;
    LocalDateTime departureTime;
    int availableSeats;
    int ticketPrice;

}
