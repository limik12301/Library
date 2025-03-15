package org.example.Controllers;


import org.example.models.Person;
import org.example.services.BookService;
import org.example.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;
    private final BookService bookService;

    @Autowired
    public PersonController(PersonService personService, BookService bookService) {
        this.personService = personService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String getPeople(Model model) {
        model.addAttribute("people", personService.findAll());
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping("/new")
    public String createPerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{Person_id}")
    public String showPerson(Model model, @PathVariable("Person_id") int Person_id) {
        model.addAttribute("person", personService.findPersonById(Person_id));
        model.addAttribute("books", bookService.findByOwner(Person_id));
        boolean IsNotNull = !bookService.findByOwner(Person_id).isEmpty();
        model.addAttribute("IsNotNull", IsNotNull);
        return "people/show";
    }

    @GetMapping("/{Person_id}/edit")
    public String editPerson(Model model, @PathVariable("Person_id") int Person_id) {
        model.addAttribute("person", personService.findPersonById(Person_id));
        return "people/edit";
    }

    @PatchMapping("/{Person_id}/edit")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               @PathVariable("Person_id") int Person_id,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/people/edit";
        }
        personService.update(Person_id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{Person_id}/delete")
    public String deletePerson(@PathVariable("Person_id") int Person_id) {
        personService.deleteById(Person_id);
        return "redirect:/people";
    }


}

