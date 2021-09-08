package com.example.ipbprojekt.controller;

import com.example.ipbprojekt.DTO.TermDTO;
import com.example.ipbprojekt.exception.NotExistDateAvaibleException;
import com.example.ipbprojekt.model.Opinion;
import com.example.ipbprojekt.model.Reservation;
import com.example.ipbprojekt.model.enums.EReservation;
import com.example.ipbprojekt.service.OpinionService;
import com.example.ipbprojekt.service.PersonService;
import com.example.ipbprojekt.service.ReservationService;
import com.example.ipbprojekt.service.YachtService;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/client")
public class ClientController {

    private YachtService yachtService;

    private ReservationService reservationService;

    private ModelMapper mapper;

    private PersonService personService;

    private OpinionService opinionService;

    static boolean checkDate = false;
    TermDTO termDTO;

    public ClientController(YachtService yachtService, ReservationService reservationService, ModelMapper mapper, PersonService personService,OpinionService opinionService) {
        this.yachtService = yachtService;
        this.reservationService = reservationService;
        this.mapper = mapper;
        this.personService = personService;
        this.opinionService = opinionService;
    }

    @GetMapping("choose")
    public String chooseAction(){
        return "chooseClient";
    }



    @GetMapping("/term")
    public String chooseTerme(Model model) {
        checkDate = false;
        model.addAttribute("checkDate", checkDate);
        model.addAttribute("term", new TermDTO());
        model.addAttribute("default", " ");

        return "reservationTerm";
    }

    @PostMapping("checkTerm")
    public String checkTermOnline(@Valid @ModelAttribute("term") TermDTO term, BindingResult bindingResult, Model model) throws NotExistDateAvaibleException {
        Reservation reservation = new Reservation();
        if (bindingResult.hasErrors()) {

            model.addAttribute("term",term);
            model.addAttribute("reservation", reservation);
            model.addAttribute("checkDate", checkDate);
            model.addAttribute("yachts", yachtService.showFreeYacht(term.getStartDate(),term.getEndDate()));
            model.addAttribute("default", " ");

            return "reservationTerm";
        } else {
            try {
                model.addAttribute("term",term);
                model.addAttribute("reservation", reservation);
                model.addAttribute("yachts", yachtService.showFreeYacht(term.getStartDate(),term.getEndDate()));
                model.addAttribute("default", " ");
                //reservationService.existTermIsAvailable(term.getStartDate(), term.getEndDate());
                termDTO = new TermDTO();
                termDTO.setStartDate(term.getStartDate());
                termDTO.setEndDate(term.getEndDate());
                reservation.setStartDate(term.getStartDate());
                reservation.setEndDate(term.getEndDate());
                checkDate = true;
                model.addAttribute("checkDate", checkDate);
            } catch (NotExistDateAvaibleException e) {
                model.addAttribute("message", "W tym terminie odbywa się juz rejs. Zmien termin");
                return "reservationTerm";
            }
            return "reservationTerm";
        }
    }

    @PostMapping("/reservation")
    public String saveTournamentOnline(@Valid @ModelAttribute("reservation") Reservation reservation, BindingResult bindingResult, Model model) throws NotExistDateAvaibleException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("reservation", reservation);
            model.addAttribute("term", termDTO);
            model.addAttribute("checkDate", checkDate);
            model.addAttribute("yachts", yachtService.showFreeYacht(termDTO.getStartDate(),termDTO.getEndDate()));
            model.addAttribute("default", " ");

            return "reservationTerm";
        } else {
                model.addAttribute("reservation", reservation);
                model.addAttribute("term", termDTO);
                model.addAttribute("checkDate", checkDate);
                model.addAttribute("yachts", yachtService.showFreeYacht(termDTO.getStartDate(),termDTO.getEndDate()));
                model.addAttribute("default", " ");
                checkDate = true;
                reservationService.saveReservation(reservation);

            return "redirect:/";
        }
    }

    @GetMapping("/reservations")
    public String allReservations(Model model){
        model.addAttribute("reservations",reservationService.showAllWithStatusAccepted());
        return "reservations-bails";
    }

    @GetMapping("/reservations/bail")
    public String payBail(@RequestParam("id") long id, Model model){
        reservationService.payBail(id);
        return "redirect:/client/reservations";
    }

    @GetMapping("/reservations/sentopinion")
    public String allReservationsToOpinion(Model model){
        model.addAttribute("reservations",reservationService.showAllWithStatusOpinion());
        return "reservations-opinion";
    }

    @GetMapping("/reservations/opinion")
    public String addOpinion(@RequestParam("id") long id, Model model){
        Opinion opinion = new Opinion();
        try {
            opinion.setReservation(reservationService.findById(id));
            model.addAttribute("opinion",opinion);
            model.addAttribute("id",id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }

        return "reservation-opinion";
    }


    @PostMapping("/reservations/opinion")
    public String saveOpinion(@Valid Opinion opinion, @RequestParam("id") long id, Model model){

        try {
            //opinion.setReservation(reservationService.findById(id));
            Reservation r = reservationService.findById(id);
            r.setOpinion(opinion);
            r.setStatus(EReservation.FINISHED);
            reservationService.saveReservation(r);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
       // opinionService.save(opinion);
        return "reservations-opinion";
    }
}
