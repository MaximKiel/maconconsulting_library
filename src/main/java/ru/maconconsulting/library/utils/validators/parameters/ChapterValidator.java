package ru.maconconsulting.library.utils.validators.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maconconsulting.library.dto.parameters.ChapterDTO;
import ru.maconconsulting.library.models.parameters.Chapter;
import ru.maconconsulting.library.services.parameters.ChaptersService;

@Component
public class ChapterValidator implements Validator {

    private final ChaptersService chaptersService;

    @Autowired
    public ChapterValidator(ChaptersService chaptersService) {
        this.chaptersService = chaptersService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Chapter.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChapterDTO chapterDTO = (ChapterDTO) target;
        if (chaptersService.findByName(chapterDTO.getName()).isPresent()) {
            errors.rejectValue("name", "404", "Такой раздел уже существует!");
        }
    }
}
