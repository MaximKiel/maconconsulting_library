package ru.maconconsulting.library.controllers.parameters;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maconconsulting.library.dto.parameters.ChapterDTO;
import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.models.parameters.Chapter;
import ru.maconconsulting.library.services.parameters.ChaptersService;
import ru.maconconsulting.library.utils.exceptions.parameters.ChapterNotFoundException;
import ru.maconconsulting.library.utils.validators.parameters.ChapterValidator;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/chapters")
public class ChaptersController {

    public static final Logger log = LoggerFactory.getLogger(ChaptersController.class);
    private final ChaptersService chaptersService;
    private final ModelMapper modelMapper;
    private final ChapterValidator chapterValidator;

    @Autowired
    public ChaptersController(ChaptersService chaptersService, ModelMapper modelMapper, ChapterValidator chapterValidator) {
        this.chaptersService = chaptersService;
        this.modelMapper = modelMapper;
        this.chapterValidator = chapterValidator;
    }

    @GetMapping
    public String getAllChapters(Model model) {
        model.addAttribute("chapters", chaptersService.findAll().stream().sorted(Comparator.comparing(Chapter::getName)).collect(Collectors.toList()));
        log.info("Go to parameters/chapters/manage");
        return "parameters/chapters/manage";
    }

    @GetMapping("/new")
    public String newChapter(@ModelAttribute("chapter") ChapterDTO chapterDTO) {
        log.info("Go to parameters/chapters/new");
        return "parameters/chapters/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("chapter") @Valid ChapterDTO chapterDTO, BindingResult bindingResult) {
        chapterValidator.validate(chapterDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to parameters/chapters/new");
            return "parameters/chapters/new";
        }

        chaptersService.save(convertToChapter(chapterDTO));
        log.info("Go to redirect:/chapters");
        return "redirect:/chapters";
    }

    @GetMapping("/{name}/edit")
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("chapter", convertToChapterDTO(chaptersService.findByName(name)
                .orElseThrow(() -> new ChapterNotFoundException("Раздел " + name + " не найден"))));
        log.info("Go to parameters/chapters/edit");
        return "parameters/chapters/edit";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("chapter") @Valid ChapterDTO chapterDTO, BindingResult bindingResult,
                         @PathVariable("name") String name) {
        chapterValidator.validate(chapterDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to parameters/chapters/edit");
            return "parameters/chapters/edit";
        }

        chaptersService.update(name, convertToChapter(chapterDTO));
        log.info("Go to redirect:/chapters");
        return "redirect:/chapters";
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name, Model model) {
        if (chaptersService.findByName(name).isPresent() && !chaptersService.findByName(name).get().getProjects().isEmpty()) {
            model.addAttribute("projects", chaptersService.findByName(name).get().getProjects().stream().sorted(Comparator.comparing(Project::getTitle)).collect(Collectors.toList()));
            log.info("Go to parameters/chapters/delete_error");
            return "parameters/delete_error";
        }

        chaptersService.delete(name);
        log.info("Go to redirect:/chapters");
        return "redirect:/chapters";
    }

    @ExceptionHandler
    private String handleException(ChapterNotFoundException e) {
        return "parameters/chapters/not_found";
    }

    private Chapter convertToChapter(ChapterDTO chapterDTO) {
        return modelMapper.map(chapterDTO, Chapter.class);
    }

    private ChapterDTO convertToChapterDTO(Chapter chapter) {
        return modelMapper.map(chapter, ChapterDTO.class);
    }
}
