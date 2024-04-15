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
    public String getAllFormats(Model model) {
        model.addAttribute("formats", formatsService.findAll().stream().sorted(Comparator.comparing(Format::getName)).collect(Collectors.toList()));
        log.info("Go to parameters/formats/manage");
        return "parameters/formats/manage";
    }

    @GetMapping("/new")
    public String newFormat(@ModelAttribute("format") FormatDTO formatDTO) {
        log.info("Go to parameters/formats/new");
        return "parameters/formats/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("format") @Valid FormatDTO formatDTO, BindingResult bindingResult) {
        formatValidator.validate(formatDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to parameters/formats/new");
            return "parameters/formats/new";
        }

        formatsService.save(convertToFormat(formatDTO));
        log.info("Go to redirect:/formats");
        return "redirect:/formats";
    }

    @GetMapping("/{name}/edit")
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("format", convertToFormatDTO(formatsService.findByName(name)
                .orElseThrow(() -> new FormatNotFoundException("Формат отчета " + name + " не найден"))));
        log.info("Go to parameters/formats/edit");
        return "parameters/formats/edit";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("format") @Valid FormatDTO formatDTO, BindingResult bindingResult,
                         @PathVariable("name") String name) {
        formatValidator.validate(formatDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to parameters/formats/edit");
            return "parameters/formats/edit";
        }

        formatsService.update(name, convertToFormat(formatDTO));
        log.info("Go to redirect:/formats");
        return "redirect:/formats";
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name, Model model) {
        if (formatsService.findByName(name).isPresent() && (!formatsService.findByName(name).get().getProjects().isEmpty() || !formatsService.findByName(name).get().getPublications().isEmpty())) {
            model.addAttribute("projects", formatsService.findByName(name).get().getProjects().stream().sorted(Comparator.comparing(Project::getTitle)).collect(Collectors.toList()));
            model.addAttribute("publications", formatsService.findByName(name).get().getPublications().stream().sorted(Comparator.comparing(Publication::getTitle)).collect(Collectors.toList()));
            log.info("Go to parameters/delete_error");
            return "parameters/delete_error";
        }

        formatsService.delete(name);
        log.info("Go to redirect:/formats");
        return "redirect:/formats";
    }

    @ExceptionHandler
    private String handleException(FormatNotFoundException e) {
        return "parameters/formats/not_found";
    }

    private Format convertToFormat(FormatDTO formatDTO) {
        return modelMapper.map(formatDTO, Format.class);
    }

    private FormatDTO convertToFormatDTO(Format format) {
        return modelMapper.map(format, FormatDTO.class);
    }
}
