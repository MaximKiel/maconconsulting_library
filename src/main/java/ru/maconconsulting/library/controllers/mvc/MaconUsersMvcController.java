package ru.maconconsulting.library.controllers.mvc;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.maconconsulting.library.services.MaconUsersService;
import ru.maconconsulting.library.utils.MaconUserValidator;

@Controller
@RequestMapping("/mvc/users")
public class MaconUsersMvcController {

    private final MaconUsersService maconUsersService;
    private final ModelMapper modelMapper;
    private final MaconUserValidator maconUserValidator;

    @Autowired
    public MaconUsersMvcController(MaconUsersService maconUsersService, ModelMapper modelMapper, MaconUserValidator maconUserValidator) {
        this.maconUsersService = maconUsersService;
        this.modelMapper = modelMapper;
        this.maconUserValidator = maconUserValidator;
    }

    @GetMapping
    public String getAllMaconUsers(Model model) {
        model.addAttribute("maconUsers", maconUsersService.findAll());
        return "mvc/users/allUsers";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("maconUser", maconUsersService.findById(id));
        return "mvc/users/show";
    }
}
