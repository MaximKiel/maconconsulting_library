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
        model.addAttribute("maconUsers", maconUsersService.findAll().stream().map(this::convertToMaconUserDTO));
        return "mvc/users/manage";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("maconUser", convertToMaconUserDTO(maconUsersService.findById(id)
                .orElseThrow(() -> new MaconUserNotFoundException("Пользователь с id " + id + " не найден"))));
        return "mvc/users/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("maconUser") MaconUserDTO maconUserDTO) {
        return "mvc/users/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("maconUser") @Valid MaconUserDTO maconUserDTO, BindingResult bindingResult) {
        maconUserValidator.validate(maconUserDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "mvc/users/new";
        }
        maconUsersService.save(convertToMaconUser(maconUserDTO));
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("maconUser", maconUsersService.findById(id));
        return "mvc/users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("maconUser") @Valid MaconUserDTO maconUserDTO, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "mvc/users/edit";
        }

        maconUsersService.update(id, convertToMaconUser(maconUserDTO));
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        maconUsersService.delete(id);
        return "redirect:/users";
    }

    private MaconUser convertToMaconUser(MaconUserDTO maconUserDTO) {
        return modelMapper.map(maconUserDTO, MaconUser.class);
    }

    private MaconUserDTO convertToMaconUserDTO(MaconUser maconUser) {
        return modelMapper.map(maconUser, MaconUserDTO.class);
    }
}
