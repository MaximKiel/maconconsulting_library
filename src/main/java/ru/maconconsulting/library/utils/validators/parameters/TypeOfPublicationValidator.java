package ru.maconconsulting.library.utils.validators.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maconconsulting.library.dto.parameters.TypeOfPublicationDTO;
import ru.maconconsulting.library.models.parameters.TypeOfPublication;
import ru.maconconsulting.library.services.parameters.TypesOfPublicationService;

@Component
public class TypeOfPublicationValidator implements Validator {

    private final TypesOfPublicationService service;

    @Autowired
    public TypeOfPublicationValidator(TypesOfPublicationService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return TypeOfPublication.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TypeOfPublicationDTO typeDTO = (TypeOfPublicationDTO) target;
        if (service.findByName(typeDTO.getName()).isPresent()) {
            errors.rejectValue("name", "404", "Такой тип уже существует!");
        }
    }
}
