package org.example.DAO;

import org.example.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        Book book = new Book();

        book.setBook_id(resultSet.getInt("Book_id"));
        book.setPerson_id(resultSet.getInt("Person_id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setAge(resultSet.getInt("age"));
        return book;
    }
}
