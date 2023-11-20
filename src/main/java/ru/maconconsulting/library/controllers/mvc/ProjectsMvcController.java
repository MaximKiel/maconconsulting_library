package ru.maconconsulting.library.controllers.mvc;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maconconsulting.library.dto.ProjectDTO;
import ru.maconconsulting.library.models.Project;
import ru.maconconsulting.library.services.ProjectsService;
import ru.maconconsulting.library.utils.ProjectValidator;
import ru.maconconsulting.library.utils.SearchProject;
import ru.maconconsulting.library.utils.exceptions.MaconUserNotFoundException;

@Controller
@RequestMapping("/projects")
public class ProjectsMvcController {

    private final ProjectsService projectsService;
    private final ModelMapper modelMapper;
    private final ProjectValidator projectValidator;

    @Autowired
    public ProjectsMvcController(ProjectsService projectsService, ModelMapper modelMapper, ProjectValidator projectValidator) {
        this.projectsService = projectsService;
        this.modelMapper = modelMapper;
        this.projectValidator = projectValidator;
    }

    @GetMapping
    public String getAllProjects() {
        return "mvc/projects/manage";
    }

    @GetMapping("/{number}")
    public String show(@PathVariable("number") int number, Model model) {
        model.addAttribute("project", convertToProjectDTO(projectsService.findByNumber(number)
                .orElseThrow(() -> new MaconUserNotFoundException("Проект с номером " + number + " не найден"))));
        return "mvc/projects/show";
    }

    @GetMapping("/new")
    public String newProject(@ModelAttribute("project") ProjectDTO projectDTO) {
        return "mvc/projects/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("project") @Valid ProjectDTO projectDTO, BindingResult bindingResult) {
        projectValidator.validate(projectDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "mvc/projects/new";
        }
        projectsService.save(convertToProject(projectDTO));
        return "redirect:/projects";
    }

    @GetMapping("/{number}/edit")
    public String edit(Model model, @PathVariable("number") int number) {
        model.addAttribute("project", projectsService.findByNumber(number)
                .orElseThrow(() -> new MaconUserNotFoundException("Проект с номером " + number + " не найден")));
        return "mvc/projects/edit";
    }

    @PatchMapping("/{number}")
    public String update(@ModelAttribute("project") @Valid ProjectDTO projectDTO, BindingResult bindingResult,
                         @PathVariable("number") int number) {
        if (bindingResult.hasErrors()) {
            return "mvc/projects/edit";
        }

        projectsService.update(number, convertToProject(projectDTO));
        return "redirect:/projects";
    }

    @DeleteMapping("/{number}")
    public String delete(@PathVariable("number") int number) {
        projectsService.delete(number);
        return "redirect:/projects";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("searchProject") SearchProject searchProject) {
        return "mvc/projects/search";
    }

    @PostMapping("/search-result")
    public String showSearchResult(Model model, @ModelAttribute("searchProject") SearchProject searchProject,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "mvc/projects/search";
        }
        model.addAttribute("result", projectsService.search(searchProject));
        return "mvc/projects/result";
    }

    private Project convertToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

    private ProjectDTO convertToProjectDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }
}
