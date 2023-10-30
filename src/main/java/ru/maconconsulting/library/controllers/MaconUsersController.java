package ru.maconconsulting.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.maconconsulting.library.services.MaconUsersService;

@RestController
public class MaconUsersController {

    private final MaconUsersService maconUsersService;

    @Autowired
    public MaconUsersController(MaconUsersService maconUsersService) {
        this.maconUsersService = maconUsersService;
    }
}
