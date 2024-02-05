package ru.maconconsulting.library.controllers.mvc.projectfields;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.maconconsulting.library.dto.ProjectTypeDTO;
import ru.maconconsulting.library.models.projectfieldetities.ProjectType;
import ru.maconconsulting.library.services.projectfields.ProjectTypesService;
import ru.maconconsulting.library.utils.ProjectTypeValidator;
import ru.maconconsulting.library.utils.exceptions.ProjectTypeNotFoundException;

@Controller
@RequestMapping("/types")
public class ProjectTypesController {

    public static final Logger log = LoggerFactory.getLogger(ProjectTypesController.class);
    private final ProjectTypesService projectTypesService;
    private final ModelMapper modelMapper;
    private final ProjectTypeValidator projectTypeValidator;

    @Autowired
    public ProjectTypesController(ProjectTypesService projectTypesService, ModelMapper modelMapper, ProjectTypeValidator projectTypeValidator) {
        this.projectTypesService = projectTypesService;
        this.modelMapper = modelMapper;
        this.projectTypeValidator = projectTypeValidator;
    }

    @GetMapping
    public String getAllTypes(Model model) {
        model.addAttribute("types", projectTypesService.findAll());
        log.info("Go to mvc/types/manage");
        return "mvc/types/manage";
    }

    @GetMapping("/new")
    public String newType(@ModelAttribute ProjectTypeDTO typeDTO) {
        log.info("Go to mvc/types/new");
        return "mvc/types/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("type") @Valid ProjectTypeDTO typeDTO, BindingResult bindingResult) {
        projectTypeValidator.validate(typeDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/types/new");
            return "mvc/types/new";
        }
        projectTypesService.save(convertToProjectType(typeDTO));
        log.info("Go to redirect:/types");
        return "redirect:/types";
    }

    @GetMapping("/{name}/edit")
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("type", convertToProjectTypeDTO(projectTypesService.findByName(name)
                .orElseThrow(() -> new ProjectTypeNotFoundException("Тип проекта " + name + " не найден"))));
        log.info("Go to mvc/types/edit");
        return "mvc/types/edit";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("type") @Valid ProjectTypeDTO typeDTO, BindingResult bindingResult,
                         @PathVariable("name") String name) {
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/types/edit");
            return "mvc/types/edit";
        }

        projectTypesService.update(name, convertToProjectType(typeDTO));
        log.info("Go to redirect:/types");
        return "redirect:/types";
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name) {
        projectTypesService.delete(name);
        log.info("Go to redirect:/types");
        return "redirect:/types";
    }

    private ProjectType convertToProjectType(ProjectTypeDTO typeDTO) {
        return modelMapper.map(typeDTO, ProjectType.class);
    }

    private ProjectTypeDTO convertToProjectTypeDTO(ProjectType type) {
        return modelMapper.map(type, ProjectTypeDTO.class);
    }
}