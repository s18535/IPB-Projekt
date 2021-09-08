package com.example.ipbprojekt.repository;

import com.example.ipbprojekt.model.Client;
import com.example.ipbprojekt.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepo extends JpaRepository<Person,Long> {
    public Person findByPersonId(long id);

    List<Person> findAllByType(String type);
}
