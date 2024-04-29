package ru.maconconsulting.library.controllers;

import jakarta.validation.Valid;
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
import ru.maconconsulting.library.utils.validators.MaconUserValidator;
import ru.maconconsulting.library.utils.exceptions.MaconUserNotFoundException;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class MaconUsersController {

    public static final Logger log = LoggerFactory.getLogger(MaconUsersController.class);
    private final MaconUsersService maconUsersService;
    private final ModelMapper modelMapper;
    private final MaconUserValidator maconUserValidator;

    @Autowired
    public MaconUsersController(MaconUsersService maconUsersService, ModelMapper modelMapper, MaconUserValidator maconUserValidator) {
        this.maconUsersService = maconUsersService;
        this.modelMapper = modelMapper;
        this.maconUserValidator = maconUserValidator;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("maconUsers", maconUsersService.findAll().stream().sorted(Comparator.comparing(MaconUser::getName)).collect(Collectors.toList()));
        log.info("Show manage maconUsers view - call MaconUsersController method getAll()");
        return "users/manage";
    }

    @GetMapping("/{login}")
    public String show(@PathVariable("login") String login, Model model) {
        model.addAttribute("maconUser", maconUsersService.findByLogin(login)
                .orElseThrow(() -> new MaconUserNotFoundException("Пользователь с логином=" + login + " не найден")));
        log.info("Show maconUser with login=" + login + " page view - call MaconUsersController method show()");
        return "users/show";
    }

    @GetMapping("/new")
    public String newMaconUser(@ModelAttribute("maconUser") MaconUser maconUser) {
        log.info("Show view for create new maconUser - call MaconUsersController method newMaconUser()");
        return "users/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("maconUser") @Valid MaconUserDTO maconUserDTO, BindingResult bindingResult) {
        maconUserValidator.validate(maconUserDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Catch error for create new maconUser - recall MaconUsersController method newMaconUser()");
            return "users/new";
        }
        maconUsersService.save(convertToMaconUser(maconUserDTO));
        MaconUser maconUser = maconUsersService.findByLogin(maconUserDTO.getLogin())
                .orElseThrow(() -> new MaconUserNotFoundException("Пользователь с логином=" + maconUserDTO.getLogin() + " не найден"));
        log.info("Create new maconUser with login=" + maconUser.getLogin() + " - redirect to MaconUsersController method show()");
        return "redirect:/users";
    }

    @GetMapping("/{login}/edit")
    public String edit(Model model, @PathVariable("login") String login) {
        model.addAttribute("maconUser", maconUsersService.findByLogin(login)
                .orElseThrow(() -> new MaconUserNotFoundException("Пользователь с логином=" + login + " не найден")));
        log.info("Show view for edit maconUser with login=" + login + " - call MaconUsersController method edit()");
        return "users/edit";
    }

    @PatchMapping("/{login}")
    public String update(@ModelAttribute("maconUser") MaconUser maconUser, BindingResult bindingResult,
                         @PathVariable("login") String login) {
        if (bindingResult.hasErrors()) {
            log.info("Catch error for update maconUser with login=" + login + " - recall MaconUsersController method edit()");
            return "users/edit";
        }

        maconUsersService.update(login, maconUser);
        log.info("Update maconUser with login=" + login + " - redirect to MaconUsersController method show()");
        return "redirect:/users";
    }

    @DeleteMapping("/{login}")
    public String delete(@PathVariable("login") String login) {
        maconUsersService.delete(login);
        log.info("Delete maconUser with login=" + login + " - redirect to MaconUsersController method getAll()");
        return "redirect:/users";
    }

    @ExceptionHandler
    private String handleException(MaconUserNotFoundException e) {
        log.info("Catch MaconUserNotFoundException: " + e.getMessage());
        return "users/not_found";
    }

    private MaconUser convertToMaconUser(MaconUserDTO maconUserDTO) {
        return modelMapper.map(maconUserDTO, MaconUser.class);
    }
}
