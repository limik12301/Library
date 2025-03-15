package org.example.services;

import org.example.models.Person;
import org.example.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findPersonById(Integer id) {
        return (Person) personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void deleteById(int id) {
        personRepository.deleteById(id);
    }

    public void update(int id, Person updatePerson) {
        updatePerson.setPerson_id(id);
        personRepository.save(updatePerson);
    }
}
