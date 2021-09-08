package com.example.ipbprojekt.service;

import com.example.ipbprojekt.exception.NotExistDateAvaibleException;
import com.example.ipbprojekt.exception.NotFoundReservationException;
import com.example.ipbprojekt.model.Bail;
import com.example.ipbprojekt.model.Client;
import com.example.ipbprojekt.model.Reservation;
import com.example.ipbprojekt.model.enums.EBail;
import com.example.ipbprojekt.model.enums.EReservation;
import com.example.ipbprojekt.repository.PersonRepo;
import com.example.ipbprojekt.repository.ReservationRepo;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private ReservationRepo reservationRepo;
    private PersonRepo personRepo;

    public ReservationService(ReservationRepo reservationRepo, PersonRepo personRepo) {
        this.reservationRepo = reservationRepo;
        this.personRepo = personRepo;
    }

    public boolean existTermIsAvailable(LocalDateTime startDate, LocalDateTime endDate) throws NotExistDateAvaibleException {
        if (!reservationRepo.existsByStartDateBetweenOrEndDateBetweenOrStartDateLessThanEqualAndEndDateGreaterThanEqual(
                startDate, endDate, startDate, endDate, startDate, endDate)) {
            return true;
        }
        throw new NotExistDateAvaibleException("Ten termin jest zajęty");
    }

    public List<Reservation> showAll() {
        return reservationRepo.findAll();
    }

    public List<Reservation> showAllWithStatusNew() {
        return reservationRepo.findByStatus(EReservation.NEW).stream().filter(e -> !e.getClient().isInBlackList()).collect(Collectors.toList());
    }

    public List<Reservation> findByStatus(String status) {
        return reservationRepo.findByStatus(EReservation.valueOf(status));
    }

    public void saveReservation(Reservation reservation) {
        Client client = (Client) personRepo.findByPersonId(1);
        reservation.setClient(client);
        if (reservation.getClient().isInBlackList()) {
            reservation.setStatus(EReservation.REJECTED);
        }

        reservationRepo.save(reservation);
    }

    public List<Reservation> showAllWithStatusChecked() {
        return reservationRepo.findByStatus(EReservation.CHECKED);
    }

    public Reservation findById(long id) throws NotFoundException {
        return reservationRepo.findById(id).orElseThrow(() -> new NotFoundException("not found"));
    }

    public void updateReservation(long id, Reservation updateFrom) {

        reservationRepo.findById(id).ifPresent(r -> {
            r.updateFrom(updateFrom);
            reservationRepo.save(r);
        });
    }

    public void finishAndReturnBail(long id) {
        reservationRepo.findById(id).ifPresent(r -> {
            r.setStatus(EReservation.OPINION);
            r.getBail().setStatus(EBail.RETURNED);
            reservationRepo.save(r);
        });
    }

    public void finishAndNoReturnBail(long id, double cost) {
        reservationRepo.findById(id).ifPresent(r -> {

            r.getBail().setStatus(EBail.NOTRETURNED);
            if (cost > r.getBail().getAmount()) {
                r.getClient().setInBlackList(true);
                r.setStatus(EReservation.FINISHED);
            } else
                r.setStatus(EReservation.OPINION);

            reservationRepo.save(r);
        });
    }

    public Reservation findByIdAndStatus(Long id, EReservation status) throws NotFoundReservationException {
        return reservationRepo.findByReservationIdAndStatus(id, status).
                orElseThrow(() -> new NotFoundReservationException("Not exist reservation by id: " + id));
    }

    public List<Reservation> showAllWithStatusAccepted() {
        return reservationRepo.findByStatus(EReservation.ACCEPTED);
    }

    public void payBail(long id) {
        reservationRepo.findById(id).ifPresent(r -> {
            r.getBail().setStatus(EBail.PAID);
            r.setStatus(EReservation.IN_PROGRESS);

            reservationRepo.save(r);

        });

    }

    public List<Reservation> showAllWithStatusFinished() {
        return reservationRepo.findByStatus(EReservation.FINISHED);
    }

    public List<Reservation> showAllWithStatusOpinion() {
        return reservationRepo.findByStatus(EReservation.OPINION);
    }

    public void rejectedReservation(long id) throws NotFoundReservationException {
        var reservation = reservationRepo.findById(id).
                orElseThrow(() -> new NotFoundReservationException("Not exist reservation id: " + id));
        reservation.setStatus(EReservation.REJECTED);


        reservationRepo.save(reservation);
    }

    public void finish(long id, double extraCost) {
        reservationRepo.findById(id).ifPresent(r -> {
            r.setExtraCost(extraCost);

            if (extraCost > r.getBail().getAmount()) {
                r.getClient().setInBlackList(true);
                r.setStatus(EReservation.FINISHED);
            } else
                r.setStatus(EReservation.OPINION);

            if (extraCost == 0) {
                r.getBail().setStatus(EBail.RETURNED);
            } else {
                r.getBail().setStatus(EBail.NOTRETURNED);
            }

            reservationRepo.save(r);
        });
    }

    public Collection<Reservation> findAllOrderByStartDate() {
        return reservationRepo.findAllByOrderByStartDate();
    }

    public void checkOpinion(){
        showAll().stream().filter(e->e.getStatus()==EReservation.OPINION).forEach(e->{
            if (LocalDateTime.now().isAfter(e.getEndDate().plusDays(3))){
                e.setStatus(EReservation.FINISHED);
            }
        });
    }
}
