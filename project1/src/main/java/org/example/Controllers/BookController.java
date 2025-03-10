package org.example.Controllers;

import org.example.DAO.BookDAO;
import org.example.DAO.PersonDAO;
import org.example.models.Book;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getBooks(Model model) {
        model.addAttribute("books", bookDAO.getBooks());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping("/new")
    public String createBook(@ModelAttribute("book") @Valid Book book,
                               BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "/books/new";
            }
        bookDAO.createBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}")
    public String showBook(Model model, @PathVariable("book_id") int book_id,
                           @ModelAttribute("person") Person person) {
        boolean isEmpty = bookDAO.showBook(book_id).getPerson_id() == 0;
        if (isEmpty) {
            model.addAttribute("people", personDAO.getPeople());
        }
        else {
            model.addAttribute("person", personDAO.showPerson(bookDAO.showBook(book_id).getPerson_id()));
        }
        model.addAttribute("isEmpty", isEmpty);
        model.addAttribute("book", bookDAO.showBook(book_id));
        return "books/show";
    }

    @GetMapping("/{book_id}/edit")
    public String editBook(Model model, @PathVariable("book_id") int book_id) {
        model.addAttribute("book", bookDAO.showBook(book_id));
        return "books/edit";
    }

    @PatchMapping("/{book_id}/edit")
    public String updateBook(@ModelAttribute("book") @Valid Book book, @PathVariable("book_id") int book_id,
                           BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "/books/edit";
            }
        bookDAO.updateBook(book_id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{book_id}/delete")
    public String deleteBook(@PathVariable("book_id") int book_id) {
        bookDAO.deleteBook(book_id);
        return "redirect:/books";
    }

    @PatchMapping("/{book_id}/release")
    public String releaseBook(@PathVariable("book_id") int book_id) {
        bookDAO.releaseBook(book_id);
        return "redirect:/books";
    }

    @PatchMapping("/{book_id}/assign")
    public String assignBook(@PathVariable("book_id") int book_id,@ModelAttribute("person") Person person) {
        bookDAO.assignBook(book_id, person.getPerson_id());
        return "redirect:/books";
    }

}
