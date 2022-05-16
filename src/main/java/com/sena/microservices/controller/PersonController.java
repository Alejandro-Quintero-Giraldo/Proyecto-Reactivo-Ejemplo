
package com.sena.microservices.controller;

import com.sena.microservices.person.Person;
import com.sena.microservices.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin("*")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/save")
    public Mono<Person> savePerson(@RequestBody Person person){
        return personService.savePerson(person);
    }

    @GetMapping("/find/{id}")
    public Mono<Person> findById(@PathVariable("id") String id){
        return personService.findById(id);
    }

    @GetMapping("/find")
    public Flux<Person> findAll(){
        return personService.findAll();
    }

    @PutMapping("/update")
    public Mono<Person> updatePerson(@RequestBody Person person){
        return personService.updatePerson(person);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Person>> deletePerson(@PathVariable("id") String id){
        return personService.deletePerson(id)
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
    }
    @GetMapping("/consultar/{id}")
    public Mono<String> consultarPerson(@PathVariable("id") String id){
        return personService.consultarNombrePerson(id);
    }

    @GetMapping("/consultarEdad/{age}")
    public  Flux<Person> findByAge(@PathVariable("age") Integer age){
        return personService.findByAge(age);
    }
}

