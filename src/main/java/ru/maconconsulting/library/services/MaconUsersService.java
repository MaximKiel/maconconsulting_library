package ru.maconconsulting.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maconconsulting.library.repositories.MaconUsersRepository;

@Service
public class MaconUsersService {

    private final MaconUsersRepository maconUsersRepository;

    @Autowired
    public MaconUsersService(MaconUsersRepository maconUsersRepository) {
        this.maconUsersRepository = maconUsersRepository;
    }
}
