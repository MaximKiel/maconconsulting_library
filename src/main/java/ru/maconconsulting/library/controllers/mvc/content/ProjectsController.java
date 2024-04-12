package ru.maconconsulting.library.controllers.mvc.content;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maconconsulting.library.dto.content.ProjectDTO;
import ru.maconconsulting.library.dto.parameters.*;
import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.models.parameters.*;
import ru.maconconsulting.library.services.parameters.*;
import ru.maconconsulting.library.services.content.ProjectsService;
import ru.maconconsulting.library.utils.validators.content.ProjectValidator;
import ru.maconconsulting.library.utils.search.SearchProject;
import ru.maconconsulting.library.utils.exceptions.content.ProjectNotFoundException;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    public static final Logger log = LoggerFactory.getLogger(ProjectsController.class);
    private final ProjectsService projectsService;
    private final ChaptersService chaptersService;
    private final SegmentsService segmentsService;
    private final FormatsService formatsService;
    private final ModelMapper modelMapper;
    private final ProjectValidator projectValidator;

    @Autowired
    public ProjectsController(ProjectsService projectsService, ChaptersService chaptersService, SegmentsService segmentsService, FormatsService formatsService, ModelMapper modelMapper, ProjectValidator projectValidator) {
        this.projectsService = projectsService;
        this.chaptersService = chaptersService;
        this.segmentsService = segmentsService;
        this.formatsService = formatsService;
        this.modelMapper = modelMapper;
        this.projectValidator = projectValidator;
    }

    @GetMapping
    public String getAllProjects() {
        log.info("Go to mvc/content/projects/manage");
        return "mvc/content/projects/manage";
    }

    @GetMapping("/{number}")
    public String show(@PathVariable("number") String number, Model model) {
        model.addAttribute("project", convertToProjectDTO(projectsService.findByNumber(number)
                .orElseThrow(() -> new ProjectNotFoundException("Проект с номером " + number + " не найден"))));
        log.info("Go to mvc/content/projects/show");
        return "mvc/content/projects/show";
    }

    @GetMapping("/new")
    public String newProject(@ModelAttribute("project") ProjectDTO projectDTO, Model model) {
        addParametersToModelAttribute(model);
        log.info("Go to mvc/content/projects/new");
        return "mvc/content/projects/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("project") @Valid ProjectDTO projectDTO, BindingResult bindingResult, Model model) {
        projectValidator.validate(projectDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            addParametersToModelAttribute(model);
            log.info("Go to mvc/content/projects/new");
            return "mvc/content/projects/new";
        }
        projectsService.save(convertToProject(projectDTO));
        log.info("Go to redirect:/projects/" + projectDTO.getNumber());
        return "redirect:/projects/" + projectDTO.getNumber();
    }

    @GetMapping("/{number}/edit")
    public String edit(Model model, @PathVariable("number") String number) {
        model.addAttribute("project", convertToProjectDTO(projectsService.findByNumber(number)
                .orElseThrow(() -> new ProjectNotFoundException("Проект с номером " + number + " не найден"))));
//        Use DTO parameters because they store into ProjectDTO for update
        addParametersDTOToModelAttribute(model);
        log.info("Go to mvc/content/projects/edit");
        return "mvc/content/projects/edit";
    }

    @PatchMapping("/{number}")
    public String update(@ModelAttribute("project") @Valid ProjectDTO projectDTO, BindingResult bindingResult,
                         @PathVariable("number") String number, Model model) {
        projectValidator.checkUniqueForUpdate(projectDTO, bindingResult);
        if (bindingResult.hasErrors()) {
//          Use DTO parameters because they store into ProjectDTO for update
            addParametersDTOToModelAttribute(model);
            log.info("Go to mvc/content/projects/edit");
            return "mvc/content/projects/edit";
        }

        projectsService.update(number, convertToProject(projectDTO));
        log.info("Go to redirect:/projects/" + number);
        return "redirect:/projects/" + number;
    }

    @DeleteMapping("/{number}")
    public String delete(@PathVariable("number") String number) {
        projectsService.delete(number);
        log.info("Go to redirect:/projects");
        return "redirect:/projects";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("searchProject") SearchProject searchProject, Model model) {
        addParametersToModelAttribute(model);
        log.info("Go to mvc/content/projects/search");
        return "mvc/content/projects/search";
    }

    @PostMapping("/search-result")
    public String showSearchResult(Model model, @ModelAttribute("searchProject") SearchProject searchProject,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/content/projects/search");
            return "mvc/content/projects/search";
        }
        model.addAttribute("result", projectsService.search(searchProject));
        log.info("Go to mvc/content/projects/result");
        return "mvc/content/projects/result";
    }

    @ExceptionHandler
    private String handleException(ProjectNotFoundException e) {
        return "mvc/content/projects/not_found";
    }

//    For new, create and search methods
    private void addParametersToModelAttribute(Model model) {
        model.addAttribute("chapters", chaptersService.findAll().stream().sorted(Comparator.comparing(Chapter::getName)).collect(Collectors.toList()));
        model.addAttribute("segments", segmentsService.findAll().stream().sorted(Comparator.comparing(Segment::getName)).collect(Collectors.toList()));
        model.addAttribute("formats", formatsService.findAll().stream().sorted(Comparator.comparing(Format::getName)).collect(Collectors.toList()));
    }

//    For edit and update methods
    private void addParametersDTOToModelAttribute(Model model) {
            model.addAttribute("chapters", chaptersService.findAll().stream().sorted(Comparator.comparing(Chapter::getName)).map(this::convertToChapterDTO).collect(Collectors.toList()));
            model.addAttribute("segments", segmentsService.findAll().stream().sorted(Comparator.comparing(Segment::getName)).map(this::convertToSegmentDTO).collect(Collectors.toList()));
            model.addAttribute("formats", formatsService.findAll().stream().sorted(Comparator.comparing(Format::getName)).map(this::convertToFormatDTO).collect(Collectors.toList()));
    }

    private Project convertToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

    private ProjectDTO convertToProjectDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }

    private ChapterDTO convertToChapterDTO(Chapter chapter) {
        return modelMapper.map(chapter, ChapterDTO.class);
    }

    private SegmentDTO convertToSegmentDTO(Segment segment) {
        return modelMapper.map(segment, SegmentDTO.class);
    }

    private FormatDTO convertToFormatDTO(Format format) {
        return modelMapper.map(format, FormatDTO.class);
    }
}
