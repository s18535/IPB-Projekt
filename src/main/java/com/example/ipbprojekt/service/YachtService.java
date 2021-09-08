package com.example.ipbprojekt.service;

import com.example.ipbprojekt.exception.NotExistDateAvaibleException;
import com.example.ipbprojekt.model.Yacht;
import com.example.ipbprojekt.repository.ReservationRepo;
import com.example.ipbprojekt.repository.YachtRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class YachtService {

    private YachtRepo yachtRepo;

    private ReservationRepo reservationRepo;

    public YachtService(YachtRepo yachtRepo, ReservationRepo reservationRepo) {
        this.yachtRepo = yachtRepo;
        this.reservationRepo = reservationRepo;
    }

    public List<Yacht> showAll() {
        return yachtRepo.findAll();
    }

    public void saveYacht(Yacht yacht) {
        yachtRepo.save(yacht);
    }

    public List<Yacht> showFreeYacht(LocalDateTime startDate, LocalDateTime endDate) throws NotExistDateAvaibleException {
        var reservation = reservationRepo.findAllByStartDateBetweenOrEndDateBetweenOrStartDateLessThanEqualAndEndDateGreaterThanEqual
                (startDate, endDate, startDate, endDate, startDate, endDate);
        var emptyYacht = showAll();
        if (reservation.isEmpty()) {
            return showAll();
        } else if (reservation.size()==emptyYacht.size()) {
            throw new NotExistDateAvaibleException("Ten termin jest zajęty");
        } else {
            for (int i = 0; i < reservation.size(); i++) {
                emptyYacht.remove(reservation.get(i).getYacht());
            }
            return emptyYacht;
        }
    }
}
