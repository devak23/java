package com.ak.rnd.mongoplayground.repository;

import com.ak.rnd.mongoplayground.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PersonRepository {
    private final MongoTemplate mongoTemplate;


    public Person findById(String id) {
        return mongoTemplate.findById(id, Person.class);
    }


    public List<Person> findByUid(String uid) {
        Query query = new Query(Criteria.where("uid").is(uid));
        return mongoTemplate.find(query, Person.class);
    }
}
