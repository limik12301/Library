package org.example.Controllers;

import org.example.DAO.BookDAO;
import org.example.DAO.PersonDAO;
import org.example.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    @Autowired
    public PersonController(PersonDAO personDAO, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String getPeople(Model model) {
        model.addAttribute("people", personDAO.getPeople());
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
        personDAO.createPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{Person_id}")
    public String showPerson(Model model, @PathVariable("Person_id") int Person_id) {
        model.addAttribute("person", personDAO.showPerson(Person_id));
        model.addAttribute("books", bookDAO.getListBooks(Person_id));
        boolean IsNotNull = !bookDAO.getListBooks(Person_id).isEmpty();
        model.addAttribute("IsNotNull", IsNotNull);
        return "people/show";
    }

    @GetMapping("/{Person_id}/edit")
    public String editPerson(Model model, @PathVariable("Person_id") int Person_id) {
        model.addAttribute("person", personDAO.showPerson(Person_id));
        return "people/edit";
    }

    @PatchMapping("/{Person_id}/edit")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               @PathVariable("Person_id") int Person_id,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/people/edit";
        }
        personDAO.updatePerson(Person_id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{Person_id}/delete")
    public String deletePerson(@PathVariable("Person_id") int Person_id) {
        personDAO.deletePerson(Person_id);
        return "redirect:/people";
    }


}

