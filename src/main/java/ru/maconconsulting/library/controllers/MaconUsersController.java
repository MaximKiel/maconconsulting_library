package ru.maconconsulting.library.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.maconconsulting.library.dto.MaconUserDTO;
import ru.maconconsulting.library.dto.MaconUsersResponse;
import ru.maconconsulting.library.models.MaconUser;
import ru.maconconsulting.library.services.MaconUsersService;
import ru.maconconsulting.library.utils.MaconUserValidator;
import ru.maconconsulting.library.utils.errors.MaconUserErrorResponse;
import ru.maconconsulting.library.utils.exceptions.MaconUserNotCreateException;
import ru.maconconsulting.library.utils.exceptions.MaconUserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class MaconUsersController {

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
    public MaconUsersResponse getMaconUsers() {
        return new MaconUsersResponse(maconUsersService.findAll().stream().map(this::convertToMaconUserDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public MaconUserDTO getMaconUser(@PathVariable("id") int id) {
        return convertToMaconUserDTO(maconUsersService.findById(id));
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MaconUserDTO maconUserDTO, BindingResult bindingResult) {
        maconUserValidator.validate(maconUserDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for (FieldError error : errorList) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new MaconUserNotCreateException(errorMessage.toString());
        }

        maconUsersService.save(convertToMaconUser(maconUserDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MaconUserErrorResponse> handleException(MaconUserNotCreateException e) {
        MaconUserErrorResponse response = new MaconUserErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        maconUsersService.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") int id, @RequestBody @Valid MaconUserDTO maconUserDTO) {
        maconUsersService.update(id, convertToMaconUser(maconUserDTO));
    }

    @ExceptionHandler
    private ResponseEntity<MaconUserErrorResponse> handleException(MaconUserNotFoundException e) {
        MaconUserErrorResponse response = new MaconUserErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private MaconUser convertToMaconUser(MaconUserDTO maconUserDTO) {
        return modelMapper.map(maconUserDTO, MaconUser.class);
    }

    private MaconUserDTO convertToMaconUserDTO(MaconUser maconUser) {
        return modelMapper.map(maconUser, MaconUserDTO.class);
    }
}
