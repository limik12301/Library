package org.example.models;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int Person_id;
    @NotEmpty(message = "name should not be empty")
    @Size(min=2,max=30,message = "name should be 2 between 30")
    private String name;
    @Min(value = 0, message = "age should be more than zero")
    private int age;

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
