package com.codecomputercoder.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codecomputercoder.entity.ScheduledFlight;
import com.codecomputercoder.repository.ScheduledFlightRepository;

@Service
public class ScheduleFlightService {

    @Autowired
    private ScheduledFlightRepository scheduleFlightRepository;

    public void scheduleNewFlight(ScheduledFlight scheduledFlight) {
        scheduleFlightRepository.save(scheduledFlight);

    }

}
