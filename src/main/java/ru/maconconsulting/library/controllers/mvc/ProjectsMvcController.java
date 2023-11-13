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
    public String getAllProjects(Model model) {
        model.addAttribute("projects", projectsService.findAll().stream().map(this::convertToProjectDTO));
        return "mvc/projects/manage";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("project", convertToProjectDTO(projectsService.findById(id)
                .orElseThrow(() -> new MaconUserNotFoundException("Проект с id " + id + " не найден"))));
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

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("project", projectsService.findById(id));
        return "mvc/projects/edit";
    }

    @PatchMapping
    public String update(@ModelAttribute("project") @Valid ProjectDTO projectDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "mvc/projects/edit";
        }

        int id = projectsService.findByNumber(projectDTO.getNumber()).get().getId();
        projectsService.update(id, convertToProject(projectDTO));
        return "redirect:/projects";
    }

//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("project") @Valid ProjectDTO projectDTO, BindingResult bindingResult,
//                         @PathVariable("id") int id) {
//        if (bindingResult.hasErrors()) {
//            return "mvc/projects/edit";
//        }
//
//        projectsService.update(id, convertToProject(projectDTO));
//        return "redirect:/projects";
//    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        projectsService.delete(id);
        return "redirect:/projects";
    }

    private Project convertToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

    private ProjectDTO convertToProjectDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }
}
