package ru.maconconsulting.library.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maconconsulting.library.dto.MaconUserDTO;
import ru.maconconsulting.library.models.MaconUser;
import ru.maconconsulting.library.services.MaconUsersService;

@Component
public class MaconUserValidator implements Validator {

    private final MaconUsersService maconUsersService;

    @Autowired
    public MaconUserValidator(MaconUsersService maconUsersService) {
        this.maconUsersService = maconUsersService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MaconUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MaconUserDTO maconUserDTO = (MaconUserDTO) target;
        if (maconUsersService.findByEmail(maconUserDTO.getEmail()).isPresent() ||
                maconUsersService.findByLogin(maconUserDTO.getLogin()).isPresent() ) {
            errors.rejectValue("maconUser", "Пользователь с таким email и/или логином уже существует!");
        }
    }
}
