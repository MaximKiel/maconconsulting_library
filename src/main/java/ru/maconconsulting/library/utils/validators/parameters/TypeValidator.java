package ru.maconconsulting.library.utils.validators.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maconconsulting.library.dto.parameters.TypeDTO;
import ru.maconconsulting.library.services.parameters.TypesService;

@Component
public class TypeValidator implements Validator {

    private final TypesService typesService;

    @Autowired
    public TypeValidator(TypesService typesService) {
        this.typesService = typesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        TypeDTO typeDTO = (TypeDTO) target;
        if (typesService.findByName(typeDTO.getName()).isPresent()) {
            errors.rejectValue("type", "Такой тип уже существует!");
        }
    }
}