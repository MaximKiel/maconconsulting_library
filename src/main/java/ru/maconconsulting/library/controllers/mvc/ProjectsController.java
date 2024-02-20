package ru.maconconsulting.library.controllers.mvc;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maconconsulting.library.dto.ProjectDTO;
import ru.maconconsulting.library.dto.projectfields.ProjectFormatDTO;
import ru.maconconsulting.library.dto.projectfields.ProjectSegmentDTO;
import ru.maconconsulting.library.dto.projectfields.ProjectTypeDTO;
import ru.maconconsulting.library.models.Project;
import ru.maconconsulting.library.models.projectfields.ProjectFormat;
import ru.maconconsulting.library.models.projectfields.ProjectSegment;
import ru.maconconsulting.library.models.projectfields.ProjectType;
import ru.maconconsulting.library.services.projectfields.ProjectFormatsService;
import ru.maconconsulting.library.services.projectfields.ProjectSegmentsService;
import ru.maconconsulting.library.services.projectfields.ProjectTypesService;
import ru.maconconsulting.library.services.ProjectsService;
import ru.maconconsulting.library.utils.validators.ProjectValidator;
import ru.maconconsulting.library.utils.SearchProject;
import ru.maconconsulting.library.utils.exceptions.MaconUserNotFoundException;
import ru.maconconsulting.library.utils.exceptions.ProjectNotFoundException;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/projects")
public class ProjectsController {

    public static final Logger log = LoggerFactory.getLogger(ProjectsController.class);
    private final ProjectsService projectsService;
    private final ProjectTypesService projectTypesService;
    private final ProjectSegmentsService projectSegmentsService;
    private final ProjectFormatsService projectFormatsService;
    private final ModelMapper modelMapper;
    private final ProjectValidator projectValidator;

    @Autowired
    public ProjectsController(ProjectsService projectsService, ProjectTypesService projectTypesService, ProjectSegmentsService projectSegmentsService, ProjectFormatsService projectFormatsService, ModelMapper modelMapper, ProjectValidator projectValidator) {
        this.projectsService = projectsService;
        this.projectTypesService = projectTypesService;
        this.projectSegmentsService = projectSegmentsService;
        this.projectFormatsService = projectFormatsService;
        this.modelMapper = modelMapper;
        this.projectValidator = projectValidator;
    }

    @GetMapping
    public String getAllProjects() {
        log.info("Go to mvc/projects/manage");
        return "mvc/projects/manage";
    }

    @GetMapping("/{number}")
    public String show(@PathVariable("number") String number, Model model) {
        log.info("Go to mvc/projects/show");
        model.addAttribute("project", convertToProjectDTO(projectsService.findByNumber(number)
                .orElseThrow(() -> new MaconUserNotFoundException("Проект с номером " + number + " не найден"))));
        return "mvc/projects/show";
    }

    @GetMapping("/new")
    public String newProject(@ModelAttribute("project") ProjectDTO projectDTO, Model model) {
        model.addAttribute("types", projectTypesService.findAll());
        model.addAttribute("segments", projectSegmentsService.findAll());
        model.addAttribute("formats", projectFormatsService.findAll());
        log.info("Go to mvc/projects/new");
        return "mvc/projects/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("project") @Valid ProjectDTO projectDTO, BindingResult bindingResult) {
        projectValidator.validate(projectDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/projects/new");
            return "mvc/projects/new";
        }
        projectsService.save(convertToProject(projectDTO));
        log.info("Go to redirect:/projects/" + projectDTO.getNumber());
        return "redirect:/projects/" + projectDTO.getNumber();
    }

    @GetMapping("/{number}/edit")
    public String edit(Model model, @PathVariable("number") String number) {
        model.addAttribute("project", convertToProjectDTO(projectsService.findByNumber(number)
                .orElseThrow(() -> new ProjectNotFoundException("Проект с номером " + number + " не найден"))));
        model.addAttribute("types", projectTypesService.findAll().stream().map(this::convertToProjectTypeDTO).collect(Collectors.toList()));
        model.addAttribute("segments", projectSegmentsService.findAll().stream().map(this::convertToProjectSegmentDTO).collect(Collectors.toList()));
        model.addAttribute("formats", projectFormatsService.findAll().stream().map(this::convertToProjectFormatDTO).collect(Collectors.toList()));
        log.info("Go to mvc/projects/edit");
        return "mvc/projects/edit";
    }

    @PatchMapping("/{number}")
    public String update(@ModelAttribute("project") @Valid ProjectDTO projectDTO, BindingResult bindingResult,
                         @PathVariable("number") String number) {
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/projects/edit");
            return "mvc/projects/edit";
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
        model.addAttribute("types", projectTypesService.findAll());
        model.addAttribute("segments", projectSegmentsService.findAll());
        model.addAttribute("formats", projectFormatsService.findAll());
        log.info("Go to mvc/projects/search");
        return "mvc/projects/search";
    }

    @PostMapping("/search-result")
    public String showSearchResult(Model model, @ModelAttribute("searchProject") SearchProject searchProject,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/projects/search");
            return "mvc/projects/search";
        }
        model.addAttribute("result", projectsService.search(searchProject));
        log.info("Go to mvc/projects/result");
        return "mvc/projects/result";
    }

    private Project convertToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

    private ProjectDTO convertToProjectDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }

    private ProjectTypeDTO convertToProjectTypeDTO(ProjectType type) {
        return modelMapper.map(type, ProjectTypeDTO.class);
    }

    private ProjectSegmentDTO convertToProjectSegmentDTO(ProjectSegment segment) {
        return modelMapper.map(segment, ProjectSegmentDTO.class);
    }

    private ProjectFormatDTO convertToProjectFormatDTO(ProjectFormat format) {
        return modelMapper.map(format, ProjectFormatDTO.class);
    }
}
