package ru.maconconsulting.library.utils.validators.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maconconsulting.library.dto.parameters.ProjectTypeDTO;
import ru.maconconsulting.library.services.parameters.ProjectTypesService;

@Component
public class ProjectTypeValidator implements Validator {

    private final ProjectTypesService projectTypesService;

    @Autowired
    public ProjectTypeValidator(ProjectTypesService projectTypesService) {
        this.projectTypesService = projectTypesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProjectTypeDTO typeDTO = (ProjectTypeDTO) target;
        if (projectTypesService.findByName(typeDTO.getName()).isPresent()) {
            errors.rejectValue("type", "Такой тип уже существует!");
        }
    }
}
