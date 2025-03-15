package org.example.models;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int book_id;

    @Column(name = "title")
    @NotEmpty(message = "title should not be empty")
    @Size(min=2,max=30,message = "title should be 2 between 30")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "author should not be empty")
    private String author;

    @Column(name = "age")
    @Min(value = 0, message = "age should be more than zero")
    private int age;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    @Column(name = "get_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date getTime;

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
        return book_id;
    }

    public void setBook_id(int Book_id) {
        this.book_id = Book_id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public String checkOverdue(){
        Date now = new Date();
        long timeNow = now.getTime();
        long diff = timeNow - this.getGetTime().getTime();
        if(diff >= 864000000) {
            return "color:red";
        }
        else {
            return "color:green";
        }
    }

    public String getOwnerNameForSearch() {
        if (owner != null) {
            return "Owner: " + owner.getName();
        }
        else {
            return "";
        }
    }
}
