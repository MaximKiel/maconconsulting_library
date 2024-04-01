package ru.maconconsulting.library.utils.validators.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maconconsulting.library.dto.parameters.KeyWordDTO;
import ru.maconconsulting.library.models.parameters.KeyWord;
import ru.maconconsulting.library.services.parameters.KeyWordsService;

@Component
public class KeyWordValidator implements Validator {

    private final KeyWordsService keyWordsService;

    @Autowired
    public KeyWordValidator(KeyWordsService keyWordsService) {
        this.keyWordsService = keyWordsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return KeyWord.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        KeyWordDTO keyWordDTO = (KeyWordDTO) target;
        if (keyWordsService.findByName(keyWordDTO.getName()).isPresent()) {
            errors.rejectValue("name", "404", "Такое ключевое слово уже существует!");
        }
    }
}
