package com.codecomputercoder.dto;

import java.time.LocalDate;

import com.codecomputercoder.entity.Airport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledFlightbyDateRequest {
private Airport srcAirport;
private Airport desAirport;
private LocalDate date;
}
