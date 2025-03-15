package org.example.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int Person_id;

    @Column(name = "name")
    @NotEmpty(message = "name should not be empty")
    @Size(min=2,max=30,message = "name should be 2 between 30")
    private String name;

    @Column(name = "age")
    @Min(value = 0, message = "age should be more than zero")
    private int age;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public int getPerson_id() {
        return Person_id;
    }

    public void setPerson_id(int Person_id) {
        this.Person_id = Person_id;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
