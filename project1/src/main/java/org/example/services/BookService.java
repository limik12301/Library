package org.example.services;

import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.xml.crypto.Data;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PersonService personService;

    @Autowired
    public BookService(BookRepository bookRepository, PersonService personService) {
        this.personService = personService;
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAllSortedByAge() {
        return bookRepository.findAll(Sort.by("age"));
    }

    public Page<Book> findAll(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page,size));
    }

    public Page<Book> findAllSortedByAgePage(int page, int size) {
        return bookRepository.findAll(PageRequest.of(page,size, Sort.by("age")));
    }

    public Book findBookById(int id) {
        return (Book) bookRepository.findById(id).orElse(null);
    }

    public List<Book> findByOwner(int id) {
        Person owner = new Person();
        owner.setPerson_id(id);
        return bookRepository.findAllByOwner(owner);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Book updateBook) {
        updateBook.setBook_id(id);
        bookRepository.save(updateBook);
    }

    @Transactional
    public void releaseBook(int book_id) {
        Book book = findBookById(book_id);
        book.setOwner(null);
    }

    @Transactional
    public void assignBook(int book_id, int person_id) {
        Book book = findBookById(book_id);
        Person owner = new Person();
        owner.setPerson_id(person_id);
        book.setOwner(owner);
        book.setGetTime(new Date());
    }


    public List<Book> findByTitleLike(String title) {
        return bookRepository.findAllByTitleStartingWith(title);
    }

    public boolean searchEmpty(String title) {
        return findByTitleLike(title).isEmpty();
    }

    public List<Person> isEmptyBook(int book_id) {
        if (findBookById(book_id).getOwner() == null) {
            return personService.findAll();
        }
        else {
            return Collections.singletonList(personService.findPersonById(findBookById(book_id).getOwner().getPerson_id()));
        }
    }
}
