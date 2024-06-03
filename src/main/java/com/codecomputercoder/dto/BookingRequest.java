package com.codecomputercoder.dto;

import java.util.List;

import com.codecomputercoder.entity.Passenger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

private int scheduledFlightId;
private List<Passenger> passengerList;


}
