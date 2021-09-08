package com.example.ipbprojekt.service;

import com.example.ipbprojekt.model.Client;
import com.example.ipbprojekt.model.Person;
import com.example.ipbprojekt.repository.PersonRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    PersonRepo repo;

    public PersonService(PersonRepo repo) {
        this.repo = repo;
    }


    public boolean isOnBlackList(long id){
        return ((Client)repo.findByPersonId(id)).isInBlackList();
    }

    public List<Person> showAllHelmsman(){
        return repo.findAllByType("Helmsman");
    }
    public Person findPerson(Long id){
        return repo.findById(id).get();
    }

    public void savePerson(Person person){
        repo.save(person);
    }
}
