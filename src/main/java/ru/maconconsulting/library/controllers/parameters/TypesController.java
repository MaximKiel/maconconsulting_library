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
import ru.maconconsulting.library.dto.parameters.TypeDTO;
import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.models.parameters.Type;
import ru.maconconsulting.library.services.parameters.TypesService;
import ru.maconconsulting.library.utils.exceptions.parameters.TypeNotFoundException;
import ru.maconconsulting.library.utils.validators.parameters.TypeValidator;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/types")
public class TypesController {

    public static final Logger log = LoggerFactory.getLogger(TypesController.class);
    private final TypesService typesService;
    private final ModelMapper modelMapper;
    private final TypeValidator typeValidator;

    @Autowired
    public TypesController(TypesService typesService, ModelMapper modelMapper, TypeValidator typeValidator) {
        this.typesService = typesService;
        this.modelMapper = modelMapper;
        this.typeValidator = typeValidator;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("types", typesService.findAll().stream().sorted(Comparator.comparing(Type::getName)).collect(Collectors.toList()));
        log.info("Show manage types view - call TypesController method getAll()");
        return "parameters/types/manage";
    }

    @GetMapping("/new")
    public String newType(@ModelAttribute("type") TypeDTO typeDTO) {
        log.info("Show view for create new type - call TypesController method newType()");
        return "parameters/types/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("type") @Valid TypeDTO typeDTO, BindingResult bindingResult) {
        typeValidator.validate(typeDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Catch error for create new type - recall TypesController method newType()");
            return "parameters/types/new";
        }

        typesService.save(convertToType(typeDTO));
        Type type = typesService.findByName(typeDTO.getName())
                .orElseThrow(() -> new TypeNotFoundException("Тип " + typeDTO.getName() + " не найден"));
        log.info("Create new type with ID=" + type.getId() + " - redirect to TypesController method show()");
        return "redirect:/types";
    }

    @GetMapping("/{name}/edit")
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("type", convertToTypeDTO(typesService.findByName(name)
                .orElseThrow(() -> new TypeNotFoundException("Тип " + name + " не найден"))));
        log.info("Show view for edit type with name=" + name + " - call TypesController method edit()");
        return "parameters/types/edit";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("type") @Valid TypeDTO typeDTO, BindingResult bindingResult,
                         @PathVariable("name") String name) {
        typeValidator.validate(typeDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Catch error for update type with name=" + name + " - recall TypesController method edit()");
            return "parameters/types/edit";
        }

        typesService.update(name, convertToType(typeDTO));
        log.info("Update type with name=" + name + " - redirect to TypesController method show()");
        return "redirect:/types";
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name, Model model) {
        if (typesService.findByName(name).isPresent() && !typesService.findByName(name).get().getProjects().isEmpty()) {
            model.addAttribute("projects", typesService.findByName(name).get().getProjects().stream().sorted(Comparator.comparing(Project::getTitle)).collect(Collectors.toList()));
            log.info("Catch error for delete type with name=" + name + " - show delete parameter page");
            return "parameters/delete_error";
        }

        typesService.delete(name);
        log.info("Delete type with name=" + name + " - redirect to TypesController method getAll()");
        return "redirect:/types";
    }

    @ExceptionHandler
    private String handleException(TypeNotFoundException e) {
        log.info("Catch TypeNotFoundException: " + e.getMessage());
        return "parameters/types/not_found";
    }

    private Type convertToType(TypeDTO typeDTO) {
        return modelMapper.map(typeDTO, Type.class);
    }

    private TypeDTO convertToTypeDTO(Type type) {
        return modelMapper.map(type, TypeDTO.class);
    }
}
