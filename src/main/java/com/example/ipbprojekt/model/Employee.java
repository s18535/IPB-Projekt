package com.example.ipbprojekt.model;

import javax.persistence.Entity;

@Entity
public class Employee extends Person{

    private Long nrId;

    public Employee() {
    }

    public Employee(String firstName, String lastName, Long nrId) {
        super(firstName, lastName);
        this.nrId = nrId;
    }

    public Long getNrId() {
        return nrId;
    }

    public void setNrId(Long nrId) {
        this.nrId = nrId;
    }
}
