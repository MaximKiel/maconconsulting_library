package ru.maconconsulting.library.utils.validators.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maconconsulting.library.dto.content.PublicationDTO;
import ru.maconconsulting.library.models.content.Publication;
import ru.maconconsulting.library.services.content.PublicationsService;

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
            errors.rejectValue("title", "422","Публикация с таким названием уже существует!");
        }
    }

    public void checkTitleForUpdate(Object target, Errors errors) {
        PublicationDTO publicationDTO = (PublicationDTO) target;
        if (publicationsService.findByTitle(publicationDTO.getTitle()).isPresent()) {
            boolean check = publicationsService.findAll().stream()
                    .filter(p -> !p.getId().equals(publicationDTO.getId()))
                    .anyMatch(p -> p.getTitle().equals(publicationDTO.getTitle()));
            if (check) {
                errors.rejectValue("title", "422","Публикация с таким названием уже существует!");
            }
        }
    }
}
