package ru.maconconsulting.library.utils.validators.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maconconsulting.library.dto.parameters.FormatDTO;
import ru.maconconsulting.library.models.parameters.Format;
import ru.maconconsulting.library.services.parameters.FormatsService;

@Component
public class FormatValidator implements Validator {

    private final FormatsService formatsService;

    @Autowired
    public FormatValidator(FormatsService formatsService) {
        this.formatsService = formatsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Format.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FormatDTO formatDTO = (FormatDTO) target;
        if (formatsService.findByName(formatDTO.getName()).isPresent()) {
            errors.rejectValue("name", "422", "Такой формат отчета уже существует!");
        }
    }
}
