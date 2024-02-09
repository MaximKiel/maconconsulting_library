package ru.maconconsulting.library.utils.validators.projectfields;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maconconsulting.library.dto.projectfields.ProjectSegmentDTO;
import ru.maconconsulting.library.services.projectfields.ProjectSegmentsService;

@Component
public class ProjectSegmentValidator implements Validator {

    private final ProjectSegmentsService projectSegmentsService;

    @Autowired
    public ProjectSegmentValidator(ProjectSegmentsService projectSegmentsService) {
        this.projectSegmentsService = projectSegmentsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProjectSegmentDTO segmentDTO = (ProjectSegmentDTO) target;
        if (projectSegmentsService.findByName(segmentDTO.getName()).isPresent()) {
            errors.rejectValue("type", "Такой сегмент рынка уже существует!");
        }
    }
}
