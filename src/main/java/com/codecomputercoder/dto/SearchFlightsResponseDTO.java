package com.codecomputercoder.dto;

import com.codecomputercoder.entity.Flight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFlightsResponseDTO {
    Long id;
    Flight flight;
    Date arrivalTime;
    Date departureTime;
    int availableSeats;
    int ticketPrice;

}
