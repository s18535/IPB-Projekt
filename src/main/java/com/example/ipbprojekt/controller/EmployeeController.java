package com.example.ipbprojekt.controller;

import com.example.ipbprojekt.DTO.HelmsmanRequest;
import com.example.ipbprojekt.DTO.ObjectMapperUtils;
import com.example.ipbprojekt.DTO.ReservationDTO;
import com.example.ipbprojekt.exception.NotFoundReservationException;
import com.example.ipbprojekt.model.Helmsman;
import com.example.ipbprojekt.model.Reservation;
import com.example.ipbprojekt.model.enums.EReservation;
import com.example.ipbprojekt.service.PersonService;
import com.example.ipbprojekt.service.ReservationService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("employee")
public class EmployeeController {

    private ReservationService reservationService;

    private PersonService personService;

    private ModelMapper mapper;

    public EmployeeController(ReservationService reservationService, ModelMapper mapper,PersonService personService) {
        this.reservationService = reservationService;
        this.personService=personService;
        this.mapper = mapper;
    }

    @GetMapping("choose")
    public String chooseAction() {
        return "chooseEmployee";
    }

    @GetMapping("/reservations")
    public String allReservation(Model model) {
        var r = reservationService.showAllWithStatusNew();
        var reservation = ObjectMapperUtils.mapAll(r, ReservationDTO.class);
        model.addAttribute("reservations", reservation);
        return "reservation-list";
    }

    @GetMapping("/reservations/checked")
    public String allCheckedReservation(Model model) {
        model.addAttribute("reservations", reservationService.showAllWithStatusChecked());
        model.addAttribute("cost", 0);

        return "checkedReservations";
    }

    @GetMapping("/reservations/details")
    public String getDetails(@RequestParam("id") long id, Model model) {

        try {
            Double cost = 0.0;
            Reservation reservation = reservationService.findById(id);
            model.addAttribute("reservation", reservation);
            model.addAttribute("cost", cost);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return "reservation-details";
    }

    @PostMapping("/reservations/finish")
    public String finishAndReturnBail(@RequestParam("id") long id, Reservation reservation, Model model) {
        reservationService.finish(id, reservation.getExtraCost());
        return "checkedReservations";
    }


//    @GetMapping("/reservations/finish_return")
//    public String finishAndReturnBail(@RequestParam("id") long id, Model model) {
//        reservationService.finishAndReturnBail(id);
//        return "checkedReservations";
//    }
//
//    @GetMapping("/reservations/finish_notreturn")
//    public String finishAndNotReturnBail(@RequestParam("id") long id, @RequestParam("cost") double cost, Model model) {
//        reservationService.finishAndNoReturnBail(id, cost);
//        return "checkedReservations";
//    }

    @GetMapping("/reservation/new/{id}")
    public String getReservationStatusNew(@PathVariable Long id, Model model) throws NotFoundReservationException {
        var reservation = reservationService.findByIdAndStatus(id, EReservation.NEW);
        model.addAttribute("reservation", reservation);

        return "veryfication-details";
    }

    @GetMapping("reservation/rejected")
    public String rejetedReservation(@RequestParam("id") long id) throws NotFoundReservationException {
        reservationService.rejectedReservation(id);

        return "redirect:/employee/reservations";
    }

  @GetMapping("reservation/accepted")
    public String acceptedReservation(@RequestParam("id") long id, Model model) throws NotFoundException {
        var reservation=reservationService.findById(id);

        if (!reservation.isPatent()){
            model.addAttribute("helmsmans",personService.showAllHelmsman());
            model.addAttribute("helm",new HelmsmanRequest());
            model.addAttribute("reservation",reservationService.findById(id));
            model.addAttribute("default", " ");
            return "acceptedReservation";
        }else {
            reservation.setStatus(EReservation.ACCEPTED);
            reservation.calculateCost();
            reservationService.saveReservation(reservation);
            return "redirect:/employee/reservations";
        }
    }

    @PostMapping("accept/{id}")
    public String acceptedReservationForm(@PathVariable("id") long id, HelmsmanRequest helmsmanRequest, Model model) throws NotFoundException{
       // model.addAttribute("helmsmans",personService.showAllHelmsman());
        var r=reservationService.findById(id);
        model.addAttribute("helm",helmsmanRequest);
        model.addAttribute("default", " ");

        var h=personService.findPerson(helmsmanRequest.getHelmsman());


        r.setHelmsman((Helmsman) h);
        r.setStatus(EReservation.ACCEPTED);
        r.calculateCost();
        reservationService.saveReservation(r);

        return "redirect:/employee/reservations";
    }

}
