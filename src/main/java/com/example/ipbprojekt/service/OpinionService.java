package com.example.ipbprojekt.service;

import com.example.ipbprojekt.model.Opinion;
import com.example.ipbprojekt.model.enums.EReservation;
import com.example.ipbprojekt.repository.OpinionRepo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Enumeration;

@Service
public class OpinionService {
    OpinionRepo opinionRepo;

    public OpinionService(OpinionRepo opinionRepo) {
        this.opinionRepo = opinionRepo;
    }

    public void save(Opinion opinion){
        opinion.getReservation().setStatus(EReservation.FINISHED);
        opinionRepo.save(opinion);
    }

    public Collection<Opinion> showAll(){
        return opinionRepo.findAll();
    }
}
