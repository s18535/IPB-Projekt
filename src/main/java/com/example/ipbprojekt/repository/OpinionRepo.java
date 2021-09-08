package com.example.ipbprojekt.repository;

import com.example.ipbprojekt.model.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpinionRepo extends JpaRepository<Opinion,Long> {

}
