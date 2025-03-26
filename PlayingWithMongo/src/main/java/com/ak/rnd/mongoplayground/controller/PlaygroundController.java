package com.ak.rnd.mongoplayground.controller;

import com.ak.rnd.mongoplayground.model.Person;
import com.ak.rnd.mongoplayground.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

@RestController
@RequestMapping("/playground")
@Slf4j
@RequiredArgsConstructor
public class PlaygroundController {

    private final PersonService personService;
    private final ObjectMapper objectMapper;

    @GetMapping("/person/{id}")
    public Person getPersonById(@PathVariable("id") String id) {
        log.info("Received request for person with id {}", id);
        Person person = personService.getPersonById(id);
        log.info("Returning person {}", person);
        return person;
    }

    @GetMapping("/person/{uid}")
    public List<Person> getByUid(@PathVariable("uid") String uid) {
        return personService.getPeopleByUid(uid);
    }

    @GetMapping(value = "/person/stream/{uid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StreamingResponseBody streamJsonAsBytes(@PathVariable("uid") String uid) {
        log.info("Received request for stream of people with uid {}", uid);
        return outputStream -> {
            List<Person> people = personService.getPeopleByUid(uid);
            for (Person person : people) {
                byte[] bytes = objectMapper.writeValueAsBytes(person);
                outputStream.write(bytes);
                outputStream.write("\n".getBytes());
                outputStream.flush();
            }
        };
    }
}
