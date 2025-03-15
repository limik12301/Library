package org.example.Controllers;

import org.example.DAO.BookDAO;
import org.example.DAO.PersonDAO;
import org.example.models.Book;
import org.example.models.Person;
import org.example.repositories.BookRepository;
import org.example.services.BookService;
import org.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping()
    public String getBook(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/index";
    }

    @GetMapping(params = {"sorted"})
    public String getBook(Model model, @RequestParam("sorted") boolean sorted) {
        model.addAttribute("books", bookService.findAllSortedByAge());
        return "books/index";
    }

    @GetMapping(params = {"page", "size"})
    public String getBook(Model model, @RequestParam("page") int page,
                          @RequestParam("size") int size) {
        model.addAttribute("books", bookService.findAll(page, size));
        return "books/index";
    }

    @GetMapping(params = {"page", "size", "sorted"})
    public String getBook(Model model, @RequestParam("page") int page,
                          @RequestParam("size") int size,
                          @RequestParam("sorted") boolean sorted) {
        model.addAttribute("books", bookService.findAllSortedByAgePage(page, size));
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
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{book_id}")
    public String showBook(Model model, @PathVariable("book_id") int book_id,
                           @ModelAttribute("person") Person person) {
        model.addAttribute("people", bookService.isEmptyBook(book_id));
        model.addAttribute("isEmpty", bookService.findBookById(book_id).getOwner() == null);
        model.addAttribute("book", bookService.findBookById(book_id));
        return "books/show";
    }

    @GetMapping("/{book_id}/edit")
    public String editBook(Model model, @PathVariable("book_id") int book_id) {
        model.addAttribute("book", bookService.findBookById(book_id));
        return "books/edit";
    }

    @PatchMapping("/{book_id}/edit")
    public String updateBook(@ModelAttribute("book") @Valid Book book, @PathVariable("book_id") int book_id,
                           BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "/books/edit";
            }
        bookService.update(book_id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{book_id}/delete")
    public String deleteBook(@PathVariable("book_id") int book_id) {
        bookService.deleteById(book_id);
        return "redirect:/books";
    }

    @PatchMapping("/{book_id}/release")
    public String releaseBook(@PathVariable("book_id") int book_id) {
        bookService.releaseBook(book_id);
        return "redirect:/books";
    }

    @PatchMapping("/{book_id}/assign")
    public String assignBook(@PathVariable("book_id") int book_id,@ModelAttribute("person") Person person) {
        bookService.assignBook(book_id, person.getPerson_id());
        return "redirect:/books";
    }

    @GetMapping(value = "/search")
    public String search() {
        return "books/search";
    }

    @GetMapping(value = "/search", params = {"query"})
    public String search(Model model, @RequestParam("query") String query) {
        model.addAttribute("searchEmpty", bookService.searchEmpty(query));
        model.addAttribute("books", bookService.findByTitleLike(query));
        return "books/search";
    }

}
