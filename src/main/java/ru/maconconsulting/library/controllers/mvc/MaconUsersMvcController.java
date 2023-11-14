package ru.maconconsulting.library.controllers.mvc;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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

import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
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
        model.addAttribute("maconUsers",
                maconUsersService.findAll().stream().map(this::convertToMaconUserDTO).collect(Collectors.toList()));
        return "mvc/users/manage";
    }

    @GetMapping("/{login}")
    public String show(@PathVariable("login") String login, Model model) {
        model.addAttribute("maconUser", convertToMaconUserDTO(maconUsersService.findByLogin(login)
                .orElseThrow(() -> new MaconUserNotFoundException("Пользователь с логином " + login + " не найден"))));
        return "mvc/users/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("maconUser") MaconUser maconUser) {
        return "mvc/users/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("maconUser") MaconUser maconUser, BindingResult bindingResult) {
        maconUserValidator.validate(convertToMaconUserDTO(maconUser), bindingResult);
        if (bindingResult.hasErrors()) {
            return "mvc/users/new";
        }
        maconUsersService.save(maconUser);
        return "redirect:/users";
    }

    @GetMapping("/{login}/edit")
    public String edit(Model model, @PathVariable("login") String login) {
        model.addAttribute("maconUser", maconUsersService.findByLogin(login)
                .orElseThrow(() -> new MaconUserNotFoundException("Пользователь с логином " + login + " не найден")));
        return "mvc/users/edit";
    }

    @PatchMapping("/{login}")
    public String update(@ModelAttribute("maconUser") @Valid MaconUserDTO maconUserDTO, BindingResult bindingResult,
                         @PathVariable("login") String login) {
        if (bindingResult.hasErrors()) {
            return "mvc/users/edit";
        }

        maconUsersService.update(login, convertToMaconUser(maconUserDTO));
        return "redirect:/users";
    }

    @DeleteMapping("/{login}")
    public String delete(@PathVariable("login") String login) {
        maconUsersService.delete(login);
        return "redirect:/users";
    }

    private MaconUser convertToMaconUser(MaconUserDTO maconUserDTO) {
        return modelMapper.map(maconUserDTO, MaconUser.class);
    }

    private MaconUserDTO convertToMaconUserDTO(MaconUser maconUser) {
        return modelMapper.map(maconUser, MaconUserDTO.class);
    }
}
