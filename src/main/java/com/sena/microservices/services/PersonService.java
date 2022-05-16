package com.sena.microservices.services;

import com.sena.microservices.person.Person;
import com.sena.microservices.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonService  {

    @Autowired
    private PersonRepository personRepository;

    public Mono<Person> savePerson(Person person){
        return personRepository.existsById(person.getId())//Mono<Boolean>
                .flatMap(result -> Boolean.FALSE.equals(result)
                        ? personRepository.save(person)
                        : Mono.error(new Exception("La persona con id "+person.getId()+" ya existe")));
    }

    public Mono<Person> findById(String id){
        return personRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("La persona con id "+id+" no existe")));
    }

    public Flux<Person> findAll(){
        return  personRepository.findAll()
                .switchIfEmpty(Flux.error(new Exception("No hay personas en la base de datos")));
    }

    public Mono<Person> updatePerson(Person person){
        return personRepository.existsById(person.getId())
                .flatMap(result -> Boolean.TRUE.equals(result)
                        ? personRepository.save(person)
                        : Mono.error(new Exception("La persona con id "+person.getId()+" no existe")));
    }

    public Mono<Void> deletePerson(String id){
        return personRepository.existsById(id)
                .flatMap(result -> Boolean.TRUE.equals(result)
                        ? personRepository.deleteById(id)
                        : Mono.error(new Exception("La persona con id "+id+" no existe")));
    }

    public Mono<String> consultarNombrePerson(String id){
        return personRepository.existsById(id)
                .flatMap(result -> Boolean.TRUE.equals(result)
                        ? personRepository.findById(id)
                        : Mono.error(new Exception("La persona con id "+id+" no existe")))
                .map(person -> person.getName());
    }

    public Flux<Person> findByAge(Integer age){
        return personRepository.findByAge(age);
    }
}
