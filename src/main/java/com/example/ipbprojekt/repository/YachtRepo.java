package com.example.ipbprojekt.repository;

import com.example.ipbprojekt.model.Yacht;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YachtRepo extends JpaRepository<Yacht,Long> {
}
