package com.example.ipbprojekt.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Helmsman extends Person{

    @OneToMany(mappedBy = "helmsman")
    private List<Reservation> reservations = new ArrayList();

    public Helmsman() {
    }

    public Helmsman(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
