package com.example.ipbprojekt.model;

import com.example.ipbprojekt.model.enums.EYacht;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "yacht")
public class Yacht {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long yachtId;
    private String name;
    private int maxPeople;
    @Enumerated(EnumType.STRING)
    private EYacht type;

    @OneToMany(mappedBy = "yacht", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
    private List<Reservation> reservations=new ArrayList<>();

    public Yacht() {
    }

    public Yacht(String name, int maxPeople, EYacht type) {
        this.name = name;
        this.maxPeople = maxPeople;
        this.type = type;
    }

    public Long getYachtId() {
        return yachtId;
    }

    public void setYachtId(Long yachtId) {
        this.yachtId = yachtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public EYacht getType() {
        return type;
    }

    public void setType(EYacht type) {
        this.type = type;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
