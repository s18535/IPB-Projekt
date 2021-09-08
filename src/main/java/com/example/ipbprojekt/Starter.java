package com.example.ipbprojekt;

import com.example.ipbprojekt.DTO.ObjectMapperUtils;
import com.example.ipbprojekt.DTO.ReservationDTO;
import com.example.ipbprojekt.model.*;
import com.example.ipbprojekt.model.enums.EBail;
import com.example.ipbprojekt.model.enums.EReservation;
import com.example.ipbprojekt.model.enums.EYacht;
import com.example.ipbprojekt.repository.PersonRepo;
import com.example.ipbprojekt.repository.ReservationRepo;
import com.example.ipbprojekt.service.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class Starter implements CommandLineRunner {

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private PersonService personService;

    private ModelMapper mapper;

    @Override
    public void run(String... args) throws Exception {

        Client client = new Client("Andrzej", "Nowak", "652333214", false);
        Bail bail = new Bail(2000, EBail.NOTPAID);
        Yacht yacht = new Yacht("Yaaa102", 5, EYacht.MOTOR);

        Reservation reservation1 = new Reservation();
        reservation1.setBail(bail);
        reservation1.setPatent(true);
        reservation1.setStartDate(LocalDateTime.now().minusDays(2));
        reservation1.setEndDate(LocalDateTime.now().minusDays(1));
        reservation1.setYacht(yacht);
        reservation1.setClient(client);
        reservation1.setStatus(EReservation.ACCEPTED);
        reservation1.calculateCost();

        reservationRepo.save(reservation1);

        Reservation reservation2 = new Reservation(LocalDateTime.now(), LocalDateTime.now().plusDays(2), false);
        Client client3 = new Client("Adam", "Kaut", "524111236", false);
        reservation2.setClient(client3);
        Yacht yacht3 = new Yacht("Yaaa105", 1, EYacht.SAILING);
        reservation2.setYacht(yacht3);
        Helmsman helmsman = new Helmsman("Dawid", "Dąb");
        reservation2.setHelmsman(helmsman);
        reservation2.calculateCost();
        Bail b3 = new Bail(2000, EBail.NOTPAID);
        reservation2.setBail(b3);

        reservationRepo.save(reservation2);


        Reservation reservation3 = new Reservation(LocalDateTime.now(), LocalDateTime.now().plusDays(2), true);
        Yacht yacht2 = new Yacht("Yaaa103", 5, EYacht.SAILMOTOR);
        reservation3.setYacht(yacht2);
        Bail b2 = new Bail(2000, EBail.PAID);
        reservation3.setBail(b2);
        Client client2 = new Client("Maciej", "Modły", "524111236", true);
        reservation3.setClient(client2);
        reservation3.calculateCost();
        reservation3.setStatus(EReservation.CHECKED);

        reservationRepo.save(reservation3);

        Reservation reservation4 = new Reservation(LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(3), true);
        Yacht yacht4 = new Yacht("Yaaa104", 5, EYacht.MOTOR);
        reservation4.setYacht(yacht4);
        Bail b4 = new Bail(2000, EBail.PAID);
        reservation4.setBail(b4);
        Client client4 = new Client("Tomasz", "Jak", "524441236", false);
        reservation4.setClient(client4);
        reservation4.calculateCost();
        reservation4.setStatus(EReservation.FINISHED);

        Opinion opinion4=new Opinion(5,"Polecam");
        reservation4.setOpinion(opinion4);

        reservationRepo.save(reservation4);

        Helmsman helmsman2 = new Helmsman("Szczepan", "Szok");

        personService.savePerson(helmsman2);

    }
}
