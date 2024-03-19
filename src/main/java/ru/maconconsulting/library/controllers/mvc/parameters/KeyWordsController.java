package ru.maconconsulting.library.controllers.mvc.parameters;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maconconsulting.library.dto.parameters.KeyWordDTO;
import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.models.content.Publication;
import ru.maconconsulting.library.models.parameters.KeyWord;
import ru.maconconsulting.library.services.parameters.KeyWordsService;
import ru.maconconsulting.library.utils.exceptions.parameters.KeyWordNotFoundException;
import ru.maconconsulting.library.utils.validators.parameters.KeyWordValidator;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/key_words")
public class KeyWordsController {

    public static final Logger log = LoggerFactory.getLogger(TypesController.class);
    private final ModelMapper modelMapper;
    private final KeyWordsService keyWordsService;
    private final KeyWordValidator keyWordValidator;

    @Autowired
    public KeyWordsController(ModelMapper modelMapper, KeyWordsService keyWordsService, KeyWordValidator keyWordValidator) {
        this.modelMapper = modelMapper;
        this.keyWordsService = keyWordsService;
        this.keyWordValidator = keyWordValidator;
    }

    @GetMapping
    public String getAllKeyWords(Model model) {
        model.addAttribute("key_words", keyWordsService.findAll().stream().sorted(Comparator.comparing(KeyWord::getName)).collect(Collectors.toList()));
        log.info("Go to mvc/parameters/key_words/manage");
        return "mvc/parameters/key_words/manage";
    }

    @GetMapping("/new")
    public String newKeyWord(@ModelAttribute("key_word") KeyWordDTO keyWordDTO) {
        log.info("Go to mvc/parameters/key_words/new");
        return "mvc/parameters/key_words/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("key_word") @Valid KeyWordDTO keyWordDTO, BindingResult bindingResult) {
        keyWordValidator.validate(keyWordDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/parameters/key_words/new");
            return "mvc/parameters/key_words/new";
        }
        keyWordsService.save(convertToProjectKeyWord(keyWordDTO));
        log.info("Go to redirect:/key_words");
        return "redirect:/key_words";
    }

    @GetMapping("/{name}/edit")
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("key_word", convertToProjectKeyWordDTO(keyWordsService.findByName(name)
                .orElseThrow(() -> new KeyWordNotFoundException("Ключевое слово " + name + "не найдено"))));
        log.info("Go to mvc/parameters/key_words/edit");
        return "mvc/parameters/key_words/edit";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("key_word") @Valid KeyWordDTO keyWordDTO, BindingResult bindingResult,
                         @PathVariable("name") String name) {
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/parameters/key_words/edit");
            return "mvc/parameters/key_words/edit";
        }

        keyWordsService.update(name, convertToProjectKeyWord(keyWordDTO));
        log.info("Go to redirect:/key_words");
        return "redirect:/key_words";
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name, Model model) {
        if (keyWordsService.findByName(name).isPresent() && !keyWordsService.findByName(name).get().getProjects().isEmpty()) {
            model.addAttribute("projects", keyWordsService.findByName(name).get().getProjects().stream().sorted(Comparator.comparing(Project::getNumber)).collect(Collectors.toList()));
            model.addAttribute("publications", keyWordsService.findByName(name).get().getPublications().stream().sorted(Comparator.comparing(Publication::getTitle)).collect(Collectors.toList()));
            log.info("mvc/parameters/delete_error");
            return "mvc/parameters/delete_error";
        }

        keyWordsService.delete(name);
        log.info("Go to redirect:/key_words");
        return "redirect:/key_words";
    }

    private KeyWord convertToProjectKeyWord(KeyWordDTO keyWordDTO) {
        return modelMapper.map(keyWordDTO, KeyWord.class);
    }

    private KeyWordDTO convertToProjectKeyWordDTO(KeyWord keyWord) {
        return modelMapper.map(keyWord, KeyWordDTO.class);
    }
}
