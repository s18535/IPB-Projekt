package com.example.ipbprojekt.controller;

import com.example.ipbprojekt.DTO.ObjectMapperUtils;
import com.example.ipbprojekt.DTO.Rest.ReservationRestDTO;
import com.example.ipbprojekt.model.Reservation;
import com.example.ipbprojekt.service.OpinionService;
import com.example.ipbprojekt.service.ReservationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/test")
public class TestController {

    private ReservationService reservationService;

    private OpinionService opinionService;

    public TestController(ReservationService reservationService, OpinionService opinionService) {
        this.reservationService = reservationService;
        this.opinionService = opinionService;
    }

    @GetMapping("/reservationts")
    Collection<ReservationRestDTO> ShowAllPlayers() {
        var r=reservationService.showAll();
        return ObjectMapperUtils.mapAll(r,ReservationRestDTO.class);
    }

    @GetMapping("/reservationts/{status}")
    Collection<ReservationRestDTO> ShowAllPlayersWithStatus(@PathVariable String status) {
        var r=reservationService.findByStatus(status);
        return ObjectMapperUtils.mapAll(r,ReservationRestDTO.class);
    }
    @GetMapping("/reservationts/sort")
    Collection<ReservationRestDTO> ShowAllPlayersSortByStartDate() {
        var r=reservationService.findAllOrderByStartDate();
        return ObjectMapperUtils.mapAll(r,ReservationRestDTO.class);
    }

    @GetMapping("/opinions")
    Collection<ReservationRestDTO> ShowAllOpinions() {
        var r=opinionService.showAll();
        return ObjectMapperUtils.mapAll(r,ReservationRestDTO.class);
    }


}
