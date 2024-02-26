package ru.maconconsulting.library.utils.validators.projectfields;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maconconsulting.library.dto.projectfields.ProjectKeyWordDTO;
import ru.maconconsulting.library.services.projectfields.ProjectKeyWordsService;

@Component
public class ProjectKeyWordValidator implements Validator {

    private final ProjectKeyWordsService projectKeyWordsService;

    @Autowired
    public ProjectKeyWordValidator(ProjectKeyWordsService projectKeyWordsService) {
        this.projectKeyWordsService = projectKeyWordsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProjectKeyWordDTO keyWordDTO = (ProjectKeyWordDTO) target;
        if (projectKeyWordsService.findByName(keyWordDTO.getName()).isPresent()) {
            errors.rejectValue("keyWord", "Такое ключевое слово уже существует!");
        }
    }
}
