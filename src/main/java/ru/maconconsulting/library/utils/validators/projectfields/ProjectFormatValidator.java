package ru.maconconsulting.library.utils.validators.projectfields;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maconconsulting.library.dto.projectfields.ProjectSegmentDTO;
import ru.maconconsulting.library.services.projectfields.ProjectFormatsService;

@Component
public class ProjectFormatValidator implements Validator {

    private final ProjectFormatsService projectFormatsService;

    @Autowired
    public ProjectFormatValidator(ProjectFormatsService projectFormatsService) {
        this.projectFormatsService = projectFormatsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProjectSegmentDTO segmentDTO = (ProjectSegmentDTO) target;
        if (projectFormatsService.findByName(segmentDTO.getName()).isPresent()) {
            errors.rejectValue("type", "Такой формат отчета уже существует!");
        }
    }
}
