package com.codecomputercoder.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledFlightbyDateRequest {
private int srcAirportId;
private int desAirportId;
private String date;
}
