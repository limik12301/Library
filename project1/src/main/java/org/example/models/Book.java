package org.example.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int Book_id;
    private int Person_id;
    @NotEmpty(message = "title should not be empty")
    @Size(min=2,max=30,message = "title should be 2 between 30")
    private String title;
    @NotEmpty(message = "author should not be empty")
    private String author;
    @Min(value = 0, message = "age should be more than zero")
    private int age;

    public int getPerson_id() {
        return Person_id;
    }

    public void setPerson_id(int person_id) {
        Person_id = person_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBook_id() {
        return Book_id;
    }

    public void setBook_id(int Book_id) {
        this.Book_id = Book_id;
    }
}
