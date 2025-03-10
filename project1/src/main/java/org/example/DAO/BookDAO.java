package org.example.DAO;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooks() {
        return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
    }

    public void createBook(Book book) {
        jdbcTemplate.update("INSERT INTO book(title,author,age) VALUES(?,?,?)",
                           book.getTitle(), book.getAuthor(), book.getAge());
    }

    public Book showBook(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id=?", new Object[]{id},
                                   new BookMapper())
                .stream().findAny().orElse(null);
    }

    public void updateBook(int book_id, Book book) {
        jdbcTemplate.update("UPDATE book SET title=?,author=?,age=? WHERE book_id=?", book.getTitle(),
                             book.getAuthor(), book.getAge(), book_id);
    }

    public void deleteBook(int book_id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id=?", book_id);
    }

    public List<Book> getListBooks(int person_id) {
        return jdbcTemplate.query("SELECT * FROM book JOIN person ON person.person_id = book.person_id WHERE book.person_id=?",
                new Object[]{person_id}, new BookMapper());
    }

    public void releaseBook(int book_id) {
        jdbcTemplate.update("UPDATE book SET person_id=NULL WHERE book_id=?", book_id);
    }

    public void assignBook(int book_id, int person_id) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book_id=?", person_id, book_id);
    }
}
