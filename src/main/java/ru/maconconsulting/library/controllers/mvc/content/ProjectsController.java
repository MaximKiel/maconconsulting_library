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
import ru.maconconsulting.library.dto.parameters.FormatDTO;
import ru.maconconsulting.library.dto.parameters.KeyWordDTO;
import ru.maconconsulting.library.dto.parameters.SegmentDTO;
import ru.maconconsulting.library.dto.parameters.TypeDTO;
import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.models.parameters.Format;
import ru.maconconsulting.library.models.parameters.KeyWord;
import ru.maconconsulting.library.models.parameters.Segment;
import ru.maconconsulting.library.models.parameters.Type;
import ru.maconconsulting.library.services.parameters.FormatsService;
import ru.maconconsulting.library.services.parameters.KeyWordsService;
import ru.maconconsulting.library.services.parameters.SegmentsService;
import ru.maconconsulting.library.services.parameters.TypesService;
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
    private final TypesService typesService;
    private final SegmentsService segmentsService;
    private final FormatsService formatsService;
    private final KeyWordsService keyWordsService;
    private final ModelMapper modelMapper;
    private final ProjectValidator projectValidator;

    @Autowired
    public ProjectsController(ProjectsService projectsService, TypesService typesService, SegmentsService segmentsService, FormatsService formatsService, KeyWordsService keyWordsService, ModelMapper modelMapper, ProjectValidator projectValidator) {
        this.projectsService = projectsService;
        this.typesService = typesService;
        this.segmentsService = segmentsService;
        this.formatsService = formatsService;
        this.keyWordsService = keyWordsService;
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
        model.addAttribute("types", typesService.findAll().stream().sorted(Comparator.comparing(Type::getName)).collect(Collectors.toList()));
        model.addAttribute("segments", segmentsService.findAll().stream().sorted(Comparator.comparing(Segment::getName)).collect(Collectors.toList()));
        model.addAttribute("formats", formatsService.findAll().stream().sorted(Comparator.comparing(Format::getName)).collect(Collectors.toList()));
        model.addAttribute("key_words", keyWordsService.findAll().stream().sorted(Comparator.comparing(KeyWord::getName)).collect(Collectors.toList()));
        log.info("Go to mvc/content/projects/new");
        return "mvc/content/projects/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("project") @Valid ProjectDTO projectDTO, BindingResult bindingResult) {
        projectValidator.validate(projectDTO, bindingResult);
        if (bindingResult.hasErrors()) {
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
        model.addAttribute("types", typesService.findAll().stream().sorted(Comparator.comparing(Type::getName)).map(this::convertToProjectTypeDTO).collect(Collectors.toList()));
        model.addAttribute("segments", segmentsService.findAll().stream().sorted(Comparator.comparing(Segment::getName)).map(this::convertToProjectSegmentDTO).collect(Collectors.toList()));
        model.addAttribute("formats", formatsService.findAll().stream().sorted(Comparator.comparing(Format::getName)).map(this::convertToProjectFormatDTO).collect(Collectors.toList()));
        model.addAttribute("key_words", keyWordsService.findAll().stream().sorted(Comparator.comparing(KeyWord::getName)).map(this::convertToProjectKeyWordDTO).collect(Collectors.toList()));
        log.info("Go to mvc/content/projects/edit");
        return "mvc/content/projects/edit";
    }

    @PatchMapping("/{number}")
    public String update(@ModelAttribute("project") @Valid ProjectDTO projectDTO, BindingResult bindingResult,
                         @PathVariable("number") String number) {
        if (bindingResult.hasErrors()) {
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
        model.addAttribute("types", typesService.findAll().stream().sorted(Comparator.comparing(Type::getName)).collect(Collectors.toList()));
        model.addAttribute("segments", segmentsService.findAll().stream().sorted(Comparator.comparing(Segment::getName)).collect(Collectors.toList()));
        model.addAttribute("formats", formatsService.findAll().stream().sorted(Comparator.comparing(Format::getName)).collect(Collectors.toList()));
        model.addAttribute("key_words", keyWordsService.findAll().stream().sorted(Comparator.comparing(KeyWord::getName)).collect(Collectors.toList()));
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

    private Project convertToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

    private ProjectDTO convertToProjectDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }

    private TypeDTO convertToProjectTypeDTO(Type type) {
        return modelMapper.map(type, TypeDTO.class);
    }

    private SegmentDTO convertToProjectSegmentDTO(Segment segment) {
        return modelMapper.map(segment, SegmentDTO.class);
    }

    private FormatDTO convertToProjectFormatDTO(Format format) {
        return modelMapper.map(format, FormatDTO.class);
    }

    private KeyWordDTO convertToProjectKeyWordDTO(KeyWord keyWord) {
        return modelMapper.map(keyWord, KeyWordDTO.class);
    }
}
