package com.example.ipbprojekt.repository;

import com.example.ipbprojekt.model.Reservation;
import com.example.ipbprojekt.model.enums.EReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    boolean existsByStartDateBetweenOrEndDateBetweenOrStartDateLessThanEqualAndEndDateGreaterThanEqual
            (LocalDateTime startDate, LocalDateTime startDate2, LocalDateTime endDate, LocalDateTime endDate2, LocalDateTime startDate3, LocalDateTime endDate3);

    List<Reservation> findAllByStartDateBetweenOrEndDateBetweenOrStartDateLessThanEqualAndEndDateGreaterThanEqual
            (LocalDateTime startDate, LocalDateTime startDate2, LocalDateTime endDate, LocalDateTime endDate2, LocalDateTime startDate3, LocalDateTime endDate3);

    List<Reservation> findByStatus(EReservation status);

    Optional<Reservation> findByReservationIdAndStatus(Long reservationId, EReservation status);

    List<Reservation> findAllByOrderByStartDate();
}
