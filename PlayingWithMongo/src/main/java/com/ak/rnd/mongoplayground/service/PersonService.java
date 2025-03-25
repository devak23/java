package com.ak.rnd.mongoplayground.service;

import com.ak.rnd.mongoplayground.model.Person;
import com.ak.rnd.mongoplayground.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;

    public Person getPersonById(String id) {
        return personRepository.findById(id);
    }


    public List<Person> getPeopleByUid(String uid) {
        return personRepository.findByUid(uid);
    }
}
