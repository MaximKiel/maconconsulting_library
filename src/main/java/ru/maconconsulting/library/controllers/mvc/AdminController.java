package ru.maconconsulting.library.controllers.mvc;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maconconsulting.library.dto.MaconUserDTO;
import ru.maconconsulting.library.models.MaconUser;
import ru.maconconsulting.library.services.MaconUsersService;
import ru.maconconsulting.library.utils.MaconUserValidator;
import ru.maconconsulting.library.utils.exceptions.MaconUserNotFoundException;

@Controller
@RequestMapping("/users")
public class AdminController {

    public static final Logger log = LoggerFactory.getLogger(AdminController.class);
    private final MaconUsersService maconUsersService;
    private final ModelMapper modelMapper;
    private final MaconUserValidator maconUserValidator;

    @Autowired
    public AdminController(MaconUsersService maconUsersService, ModelMapper modelMapper, MaconUserValidator maconUserValidator) {
        this.maconUsersService = maconUsersService;
        this.modelMapper = modelMapper;
        this.maconUserValidator = maconUserValidator;
    }

    @GetMapping
    public String getAllMaconUsers(Model model) {
        model.addAttribute("maconUsers", maconUsersService.findAll());
        log.info("Go to mvc/users/manage");
        return "mvc/users/manage";
    }

    @GetMapping("/{login}")
    public String show(@PathVariable("login") String login, Model model) {
        model.addAttribute("maconUser", maconUsersService.findByLogin(login)
                .orElseThrow(() -> new MaconUserNotFoundException("Пользователь с логином " + login + " не найден")));
        log.info("Go to mvc/users/show");
        return "mvc/users/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("maconUser") MaconUser maconUser) {
        log.info("Go to mvc/users/new");
        return "mvc/users/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("maconUser") MaconUser maconUser, BindingResult bindingResult) {
        maconUserValidator.validate(convertToMaconUserDTO(maconUser), bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/users/new");
            return "mvc/users/new";
        }
        maconUsersService.save(maconUser);
        log.info("Go to redirect:/users");
        return "redirect:/users";
    }

    @GetMapping("/{login}/edit")
    public String edit(Model model, @PathVariable("login") String login) {
        model.addAttribute("maconUser", maconUsersService.findByLogin(login)
                .orElseThrow(() -> new MaconUserNotFoundException("Пользователь с логином " + login + " не найден")));
        log.info("Go to mvc/users/edit");
        return "mvc/users/edit";
    }

    @PatchMapping("/{login}")
    public String update(@ModelAttribute("maconUser") MaconUser maconUser, BindingResult bindingResult,
                         @PathVariable("login") String login) {
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/users/edit");
            return "mvc/users/edit";
        }

        maconUsersService.update(login, maconUser);
        log.info("Go to redirect:/users");
        return "redirect:/users";
    }

    @DeleteMapping("/{login}")
    public String delete(@PathVariable("login") String login) {
        maconUsersService.delete(login);
        log.info("Go to redirect:/users");
        return "redirect:/users";
    }

    private MaconUserDTO convertToMaconUserDTO(MaconUser maconUser) {
        return modelMapper.map(maconUser, MaconUserDTO.class);
    }
}
