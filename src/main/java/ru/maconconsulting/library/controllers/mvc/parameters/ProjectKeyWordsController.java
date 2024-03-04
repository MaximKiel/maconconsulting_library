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
import ru.maconconsulting.library.dto.parameters.ProjectKeyWordDTO;
import ru.maconconsulting.library.models.parameters.ProjectKeyWord;
import ru.maconconsulting.library.services.parameters.ProjectKeyWordsService;
import ru.maconconsulting.library.utils.exceptions.parameters.ProjectKeyWordNotFoundException;
import ru.maconconsulting.library.utils.validators.parameters.ProjectKeyWordValidator;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/key_words")
public class ProjectKeyWordsController {

    public static final Logger log = LoggerFactory.getLogger(ProjectTypesController.class);
    private final ModelMapper modelMapper;
    private final ProjectKeyWordsService projectKeyWordsService;
    private final ProjectKeyWordValidator projectKeyWordValidator;

    @Autowired
    public ProjectKeyWordsController(ModelMapper modelMapper, ProjectKeyWordsService projectKeyWordsService, ProjectKeyWordValidator projectKeyWordValidator) {
        this.modelMapper = modelMapper;
        this.projectKeyWordsService = projectKeyWordsService;
        this.projectKeyWordValidator = projectKeyWordValidator;
    }

    @GetMapping
    public String getAllKeyWords(Model model) {
        model.addAttribute("key_words", projectKeyWordsService.findAll().stream().sorted(Comparator.comparing(ProjectKeyWord::getName)).collect(Collectors.toList()));
        log.info("Go to mvc/projectfields/key_words/manage");
        return "mvc/projectfields/key_words/manage";
    }

    @GetMapping("/new")
    public String newKeyWord(@ModelAttribute("key_word")ProjectKeyWordDTO keyWordDTO) {
        log.info("Go to mvc/projectfields/key_words/new");
        return "mvc/projectfields/key_words/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("key_word") @Valid ProjectKeyWordDTO keyWordDTO, BindingResult bindingResult) {
        projectKeyWordValidator.validate(keyWordDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/projectfields/key_words/new");
            return "mvc/projectfields/key_words/new";
        }
        projectKeyWordsService.save(convertToProjectKeyWord(keyWordDTO));
        log.info("Go to redirect:/key_words");
        return "redirect:/key_words";
    }

    @GetMapping("/{name}/edit")
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("key_word", convertToProjectKeyWordDTO(projectKeyWordsService.findByName(name)
                .orElseThrow(() -> new ProjectKeyWordNotFoundException("Ключевое слово " + name + "не найдено"))));
        log.info("Go to mvc/projectfields/key_words/edit");
        return "mvc/projectfields/key_words/edit";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("key_word") @Valid ProjectKeyWordDTO keyWordDTO, BindingResult bindingResult,
                         @PathVariable("name") String name) {
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/projectfields/key_words/edit");
            return "mvc/projectfields/key_words/edit";
        }

        projectKeyWordsService.update(name, convertToProjectKeyWord(keyWordDTO));
        log.info("Go to redirect:/key_words");
        return "redirect:/key_words";
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name) {
        projectKeyWordsService.delete(name);
        log.info("Go to redirect:/key_words");
        return "redirect:/key_words";
    }

    private ProjectKeyWord convertToProjectKeyWord(ProjectKeyWordDTO keyWordDTO) {
        return modelMapper.map(keyWordDTO, ProjectKeyWord.class);
    }

    private ProjectKeyWordDTO convertToProjectKeyWordDTO(ProjectKeyWord keyWord) {
        return modelMapper.map(keyWord, ProjectKeyWordDTO.class);
    }
}
