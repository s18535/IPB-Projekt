package com.example.ipbprojekt.schedule;

import com.example.ipbprojekt.service.ReservationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    private ReservationService reservationService;

    public ScheduleTask(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Scheduled(fixedDelay = 60000)
    public void checkStatusTournament(){
        reservationService.checkOpinion();
    }
}
