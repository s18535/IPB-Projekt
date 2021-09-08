package com.example.ipbprojekt.model;

import com.example.ipbprojekt.model.enums.EBail;

import javax.persistence.*;

@Entity
public class Bail {

    private static final int priceBail=2000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bailId;

    private Integer amount;

    @Enumerated(EnumType.STRING)
    private EBail status;

    @OneToOne(mappedBy = "bail",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
    private Reservation reservation;

    public Bail() {
        this.amount=priceBail;
        this.status=EBail.NOTPAID;
    }


    public Bail(Integer amount, EBail status) {
        this.amount = amount;
        this.status = status;
    }

    public Long getBailId() {
        return bailId;
    }

    public void setBailId(Long bailId) {
        this.bailId = bailId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public EBail getStatus() {
        return status;
    }

    public void setStatus(EBail status) {
        this.status = status;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
