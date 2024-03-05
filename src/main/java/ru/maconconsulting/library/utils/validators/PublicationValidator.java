package ru.maconconsulting.library.utils.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maconconsulting.library.dto.PublicationDTO;
import ru.maconconsulting.library.models.Publication;
import ru.maconconsulting.library.services.PublicationsService;

@Component
public class PublicationValidator implements Validator {

    private final PublicationsService publicationsService;

    @Autowired
    public PublicationValidator(PublicationsService publicationsService) {
        this.publicationsService = publicationsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Publication.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PublicationDTO publicationDTO = (PublicationDTO) target;
        if (publicationsService.findByTitle(publicationDTO.getTitle()).isPresent()) {
            errors.rejectValue("publication", "Публикация с таким названием уже существует!");
        }
    }
}
