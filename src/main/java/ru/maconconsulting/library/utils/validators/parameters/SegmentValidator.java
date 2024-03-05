package ru.maconconsulting.library.utils.validators.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maconconsulting.library.dto.parameters.SegmentDTO;
import ru.maconconsulting.library.models.parameters.Segment;
import ru.maconconsulting.library.services.parameters.SegmentsService;

@Component
public class SegmentValidator implements Validator {

    private final SegmentsService segmentsService;

    @Autowired
    public SegmentValidator(SegmentsService segmentsService) {
        this.segmentsService = segmentsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Segment.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SegmentDTO segmentDTO = (SegmentDTO) target;
        if (segmentsService.findByName(segmentDTO.getName()).isPresent()) {
            errors.rejectValue("segment", "Такой сегмент рынка уже существует!");
        }
    }
}
