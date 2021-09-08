package com.example.ipbprojekt.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends Person {
    private String phoneNumber;

    private boolean inBlackList;

    @OneToMany(mappedBy = "client",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH})
    private List<Reservation> reservations = new ArrayList<>();

    public Client(String firstName, String lastName, String phoneNumber, boolean inBlackList) {
        super(firstName, lastName);
        this.phoneNumber = phoneNumber;
        this.inBlackList = inBlackList;
    }

    public Client() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isInBlackList() {
        return inBlackList;
    }

    public void setInBlackList(boolean inBlackList) {
        this.inBlackList = inBlackList;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
