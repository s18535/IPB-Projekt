package com.example.ipbprojekt.DTO;


import com.example.ipbprojekt.model.enums.EReservation;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;

public class ReservationDTO {

    private Long reservationId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    private EReservation status;

    private boolean patent;
    private boolean paid;
    private YachtDTO yacht;

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public EReservation getStatus() {
        return status;
    }

    public void setStatus(EReservation status) {
        this.status = status;
    }

    public boolean isPatent() {
        return patent;
    }

    public void setPatent(boolean patent) {
        this.patent = patent;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public YachtDTO getYacht() {
        return yacht;
    }

    public void setYacht(YachtDTO yacht) {
        this.yacht = yacht;
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
                "reservationId=" + reservationId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", patent=" + patent +
                ", paid=" + paid +
                ", yacht=" + yacht +
                '}';
    }
}
