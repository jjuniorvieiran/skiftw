package nl.rabobank.service;


import nl.rabobank.model.Person;
import nl.rabobank.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class PersonService {

    private final static String PATH = "/person";

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(PATH)
    @RolesAllowed("READ")
    public ResponseEntity<List<Person>> findAllPerson() {

        List<Person> allPerson = new ArrayList<>();

        personRepository.findAll().iterator().forEachRemaining(allPerson::add);

        return ResponseEntity.ok(allPerson);

    }

    @PostMapping(PATH)
    @RolesAllowed("WRITE")
    public ResponseEntity createPerson(@Valid @RequestBody Person person) {
        System.out.println("Create person");
        return ResponseEntity.ok(personRepository.save(person));
    }

    @PutMapping(PATH+"/{id}")
    @RolesAllowed("WRITE")
    public ResponseEntity<Person> update(@PathVariable Long id, @Valid @RequestBody Person product) {
        if (!personRepository.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(personRepository.save(product));
    }

    @GetMapping(PATH+"/{id}")
    @RolesAllowed("READ")
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (!person.isPresent()) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(person.get());
    }

}