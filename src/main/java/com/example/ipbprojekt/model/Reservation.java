package com.example.ipbprojekt.model;

import com.example.ipbprojekt.model.enums.EReservation;
import com.example.ipbprojekt.model.enums.EYacht;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Period;

@Entity(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private EReservation status;

    private boolean patent;
    private int amount;

    private double extraCost;

    private static final int priceRentByOneDay=200;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "person_id")
    private Client client;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "opinion_id")
    private Opinion opinion;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "yacht_id")
    private Yacht yacht;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "bail_id")
    private Bail bail;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "helmsman_id")
    private Helmsman helmsman;

    public Reservation() {
        this.status = EReservation.NEW;
       /* Client client = new Client("Tomek", "Nowak", "652333214", false);
        this.setClient(client);*/
        this.setBail(new Bail());
    }

    public Reservation(LocalDateTime startDate, LocalDateTime endDate, EReservation status, boolean patent) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.patent = patent;
        extraCost = 0;
    }

    public Reservation(LocalDateTime startDate, LocalDateTime endDate, boolean patent) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.patent = patent;
        this.status = EReservation.NEW;
        extraCost = 0;
    }

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Opinion getOpinion() {
        return opinion;
    }

    public void setOpinion(Opinion opinion) {
        this.opinion = opinion;
    }

    public Yacht getYacht() {
        return yacht;
    }

    public void setYacht(Yacht yacht) {
        this.yacht = yacht;
    }

    public Bail getBail() {
        return bail;
    }

    public void setBail(Bail bail) {
        this.bail = bail;
    }

    public Helmsman getHelmsman() {
        return helmsman;
    }

    public void setHelmsman(Helmsman helmsman) {
        this.helmsman = helmsman;
    }

    public void updateFrom(Reservation updateFrom) {
        setStatus(updateFrom.getStatus());
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getExtraCost() {
        return extraCost;
    }

    public void setExtraCost(double extraCost) {
        this.extraCost = extraCost;
    }

    public void calculateCost() {
        int day= Period.between(startDate.toLocalDate(),endDate.toLocalDate()).getDays();
        int sum=day*priceRentByOneDay;
        if (getHelmsman()!=null){
            sum+=300;
        }
        else if (getYacht().getType()== EYacht.SAILMOTOR){
            sum+=100;
        }
        this.amount=sum;
    }
}
