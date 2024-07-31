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
import ru.maconconsulting.library.dto.parameters.TypeOfPublicationDTO;
import ru.maconconsulting.library.models.content.Publication;
import ru.maconconsulting.library.models.parameters.TypeOfPublication;
import ru.maconconsulting.library.services.parameters.TypesOfPublicationService;
import ru.maconconsulting.library.utils.exceptions.parameters.TypeOfPublicationNotFoundException;
import ru.maconconsulting.library.utils.validators.parameters.TypeOfPublicationValidator;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/types_of_publication")
public class TypesOfPublicationController {

    public static final Logger log = LoggerFactory.getLogger(TypesOfPublicationController.class);
    private final TypesOfPublicationService service;
    private final ModelMapper modelMapper;
    private final TypeOfPublicationValidator validator;

    @Autowired
    public TypesOfPublicationController(TypesOfPublicationService service, ModelMapper modelMapper, TypeOfPublicationValidator validator) {
        this.service = service;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("types_of_publication", service.findAll().stream().sorted(Comparator.comparing(TypeOfPublication::getName)).collect(Collectors.toList()));
        log.info("Show manage types_of_publication view - call TypesOfPublicationController method getAll()");
        return "parameters/types_of_publication/manage";
    }

    @GetMapping("/new")
    public String newTypeOfPublication(@ModelAttribute("type_of_publication") TypeOfPublicationDTO typeOfPublicationDTO) {
        log.info("Show view for create new type_of_publication - call TypesOfPublicationController method newTypeOfPublication()");
        return "parameters/types_of_publication/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("type_of_publication") @Valid TypeOfPublicationDTO typeOfPublicationDTO,
                         BindingResult bindingResult) {
        validator.validate(typeOfPublicationDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Catch error for create new type_of_publication - recall TypesOfPublicationController method newTypeOfPublication()");
            return "parameters/types_of_publication/new";
        }

        service.save(convertToTypeOfPublication(typeOfPublicationDTO));
        TypeOfPublication type = service.findByName(typeOfPublicationDTO.getName())
                .orElseThrow(() -> new TypeOfPublicationNotFoundException("Тип " + typeOfPublicationDTO.getName() + " не найден"));
        log.info("Create new type_of_publication with ID=" + type.getId() + " - redirect to TypesOfPublicationController method show()");
        return "redirect:/types_of_publication";
    }

    @GetMapping("/{name}/edit")
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("type_of_publication", convertToTypeOfPublicationDTO(service.findByName(name)
                .orElseThrow(() -> new TypeOfPublicationNotFoundException("Тип " + name + " не найден"))));
        log.info("Show view for edit type_of_publication with name=" + name + " - call TypesOfPublicationController method edit()");
        return "parameters/types_of_publication/edit";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("type_of_publication") @Valid TypeOfPublicationDTO typeOfPublicationDTO,
                         BindingResult bindingResult, @PathVariable("name") String name) {
        validator.validate(typeOfPublicationDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Catch error for update type_of_publication with name=" + name + " - recall TypesOfPublicationController method edit()");
            return "parameters/types_of_publication/edit";
        }

        service.update(name, convertToTypeOfPublication(typeOfPublicationDTO));
        log.info("Update type_of_publication with name=" + name + " - redirect to TypesOfPublicationController method show()");
        return "redirect:/types_of_publication";
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name, Model model) {
        if (service.findByName(name).isPresent() && !service.findByName(name).get().getPublications().isEmpty()) {
            model.addAttribute("publications", service.findByName(name).get().getPublications().stream().sorted(Comparator.comparing(Publication::getTitle)).collect(Collectors.toList()));
            log.info("Catch error for delete type_of_publication with name=" + name + " - show delete parameter page");
            return "parameters/delete_error";
        }

        service.delete(name);
        log.info("Delete type_of_publication with name=" + name + " - redirect to TypesOfPublicationController method getAll()");
        return "redirect:/types_of_publication";
    }

    @ExceptionHandler
    private String handleException(TypeOfPublicationNotFoundException e) {
        log.info("Catch TypeOfPublicationNotFoundException: " + e.getMessage());
        return "parameters/types_of_publication/not_found";
    }

    private TypeOfPublication convertToTypeOfPublication(TypeOfPublicationDTO typeOfPublicationDTO) {
        return modelMapper.map(typeOfPublicationDTO, TypeOfPublication.class);
    }

    private TypeOfPublicationDTO convertToTypeOfPublicationDTO(TypeOfPublication typeOfPublication) {
        return modelMapper.map(typeOfPublication, TypeOfPublicationDTO.class);
    }
}
