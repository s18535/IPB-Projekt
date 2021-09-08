package com.example.ipbprojekt.DTO.Rest;

import com.example.ipbprojekt.model.enums.EReservation;

import java.time.LocalDateTime;

public class ReservationRestDTO {

    private Long reservationId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private EReservation status;

    private boolean patent;

    private boolean paid;

    private YachtRestDTO yacht;

    private BailRestDTO bail;

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

    public YachtRestDTO getYacht() {
        return yacht;
    }

    public void setYacht(YachtRestDTO yacht) {
        this.yacht = yacht;
    }

    public BailRestDTO getBail() {
        return bail;
    }

    public void setBail(BailRestDTO bail) {
        this.bail = bail;
    }
}
