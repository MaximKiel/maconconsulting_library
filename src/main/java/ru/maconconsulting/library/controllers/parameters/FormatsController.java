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
import ru.maconconsulting.library.dto.parameters.FormatDTO;
import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.models.content.Publication;
import ru.maconconsulting.library.models.parameters.Format;
import ru.maconconsulting.library.services.parameters.FormatsService;
import ru.maconconsulting.library.utils.exceptions.parameters.FormatNotFoundException;
import ru.maconconsulting.library.utils.validators.parameters.FormatValidator;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/formats")
public class FormatsController {

    public static final Logger log = LoggerFactory.getLogger(FormatsController.class);
    private final FormatsService formatsService;
    private final ModelMapper modelMapper;
    private final FormatValidator formatValidator;

    @Autowired
    public FormatsController(FormatsService formatsService, ModelMapper modelMapper, FormatValidator formatValidator) {
        this.formatsService = formatsService;
        this.modelMapper = modelMapper;
        this.formatValidator = formatValidator;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("formats", formatsService.findAll().stream().sorted(Comparator.comparing(Format::getName)).collect(Collectors.toList()));
        log.info("Show manage formats view - call FormatsController method getAll()");
        return "parameters/formats/manage";
    }

    @GetMapping("/new")
    public String newFormat(@ModelAttribute("format") FormatDTO formatDTO) {
        log.info("Show view for create new format - call FormatsController method newFormat()");
        return "parameters/formats/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("format") @Valid FormatDTO formatDTO, BindingResult bindingResult) {
        formatValidator.validate(formatDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Catch error for create new format - recall FormatsController method newFormat()");
            return "parameters/formats/new";
        }

        formatsService.save(convertToFormat(formatDTO));
        Format format = formatsService.findByName(formatDTO.getName())
                .orElseThrow(() -> new FormatNotFoundException("Формат " + formatDTO.getName() + "не найден"));
        log.info("Create new format with ID=" + format.getId() + " - redirect to FormatsController method show()");
        return "redirect:/formats";
    }

    @GetMapping("/{name}/edit")
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("format", convertToFormatDTO(formatsService.findByName(name)
                .orElseThrow(() -> new FormatNotFoundException("Формат " + name + " не найден"))));
        log.info("Show view for edit format with name=" + name + " - call FormatsController method edit()");
        return "parameters/formats/edit";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("format") @Valid FormatDTO formatDTO, BindingResult bindingResult,
                         @PathVariable("name") String name) {
        formatValidator.validate(formatDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Catch error for update format with name=" + name + " - recall FormatsController method edit()");
            return "parameters/formats/edit";
        }

        formatsService.update(name, convertToFormat(formatDTO));
        log.info("Update format with name=" + name + " - redirect to FormatsController method show()");
        return "redirect:/formats";
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name, Model model) {
        if (formatsService.findByName(name).isPresent() && (!formatsService.findByName(name).get().getProjects().isEmpty() || !formatsService.findByName(name).get().getPublications().isEmpty())) {
            model.addAttribute("projects", formatsService.findByName(name).get().getProjects().stream().sorted(Comparator.comparing(Project::getTitle)).collect(Collectors.toList()));
            model.addAttribute("publications", formatsService.findByName(name).get().getPublications().stream().sorted(Comparator.comparing(Publication::getTitle)).collect(Collectors.toList()));
            log.info("Catch error for delete format with name=" + name + " - show delete parameter page");
            return "parameters/delete_error";
        }

        formatsService.delete(name);
        log.info("Delete format with name=" + name + " - redirect to FormatsController method getAll()");
        return "redirect:/formats";
    }

    @ExceptionHandler
    private String handleException(FormatNotFoundException e) {
        log.info("Catch FormatNotFoundException: " + e.getMessage());
        return "parameters/formats/not_found";
    }

    private Format convertToFormat(FormatDTO formatDTO) {
        return modelMapper.map(formatDTO, Format.class);
    }

    private FormatDTO convertToFormatDTO(Format format) {
        return modelMapper.map(format, FormatDTO.class);
    }
}
