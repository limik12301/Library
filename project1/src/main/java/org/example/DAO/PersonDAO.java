package org.example.DAO;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getPeople() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<Person>(Person.class));
    }

    public void createPerson(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name,age) VALUES(?,?)", person.getName(), person.getAge());
    }

    public Person showPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id=?", new Object[]{id},
                                   new BeanPropertyRowMapper<Person>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void updatePerson(int Person_id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name=?, age=? WHERE Person_id=?",
                                 person.getName(), person.getAge(), Person_id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE Person_id=?", id);
    }

}
