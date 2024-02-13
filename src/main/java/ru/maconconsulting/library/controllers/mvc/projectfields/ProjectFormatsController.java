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
import ru.maconconsulting.library.dto.projectfields.ProjectFormatDTO;
import ru.maconconsulting.library.models.projectfields.ProjectFormat;
import ru.maconconsulting.library.services.projectfields.ProjectFormatsService;
import ru.maconconsulting.library.utils.exceptions.projectfields.ProjectFormatNotFoundException;
import ru.maconconsulting.library.utils.validators.projectfields.ProjectFormatValidator;

@Controller
@RequestMapping("/formats")
public class ProjectFormatsController {

    public static final Logger log = LoggerFactory.getLogger(ProjectTypesController.class);
    private final ProjectFormatsService projectFormatsService;
    private final ModelMapper modelMapper;
    private final ProjectFormatValidator projectFormatValidator;

    @Autowired
    public ProjectFormatsController(ProjectFormatsService projectFormatsService, ModelMapper modelMapper, ProjectFormatValidator projectFormatValidator) {
        this.projectFormatsService = projectFormatsService;
        this.modelMapper = modelMapper;
        this.projectFormatValidator = projectFormatValidator;
    }

    @GetMapping
    public String getAllFormats(Model model) {
        model.addAttribute("formats", projectFormatsService.findAll());
        log.info("Go to mvc/projectfields/formats/manage");
        return "mvc/projectfields/formats/manage";
    }

    @GetMapping("/new")
    public String newFormat(@ModelAttribute("format") ProjectFormatDTO formatDTO) {
        log.info("Go to mvc/projectfields/formats/new");
        return "mvc/projectfields/formats/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("format") @Valid ProjectFormatDTO formatDTO, BindingResult bindingResult) {
        projectFormatValidator.validate(formatDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/projectfields/formats/new");
            return "mvc/projectfields/formats/new";
        }

        projectFormatsService.save(convertToProjectFormat(formatDTO));
        log.info("Go to redirect:/formats");
        return "redirect:/formats";
    }

    @GetMapping("/{name}/edit")
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("format", convertToProjectFormatDTO(projectFormatsService.findByName(name)
                .orElseThrow(() -> new ProjectFormatNotFoundException("Формат отчета " + name + " не найден"))));
        log.info("Go to mvc/projectfields/formats/edit");
        return "mvc/projectfields/formats/edit";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("format") @Valid ProjectFormatDTO formatDTO, BindingResult bindingResult,
                         @PathVariable("name") String name) {
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/projectfields/formats/edit");
            return "mvc/projectfields/formats/edit";
        }

        projectFormatsService.update(name, convertToProjectFormat(formatDTO));
        log.info("Go to redirect:/formats");
        return "redirect:/formats";
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name) {
        projectFormatsService.delete(name);
        log.info("Go to redirect:/formats");
        return "redirect:/formats";
    }

    private ProjectFormat convertToProjectFormat(ProjectFormatDTO formatDTO) {
        return modelMapper.map(formatDTO, ProjectFormat.class);
    }

    private ProjectFormatDTO convertToProjectFormatDTO(ProjectFormat format) {
        return modelMapper.map(format, ProjectFormatDTO.class);
    }
}
