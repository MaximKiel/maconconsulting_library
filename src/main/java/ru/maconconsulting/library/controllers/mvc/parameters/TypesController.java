package ru.maconconsulting.library.controllers.mvc.parameters;

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
import ru.maconconsulting.library.models.parameters.Type;
import ru.maconconsulting.library.services.parameters.TypesService;
import ru.maconconsulting.library.utils.validators.parameters.TypeValidator;
import ru.maconconsulting.library.utils.exceptions.parameters.TypeNotFoundException;

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
    public String getAllTypes(Model model) {
        model.addAttribute("types", typesService.findAll().stream().sorted(Comparator.comparing(Type::getName)).collect(Collectors.toList()));
        log.info("Go to mvc/projectfields/types/manage");
        return "mvc/projectfields/types/manage";
    }

    @GetMapping("/new")
    public String newType(@ModelAttribute("type") TypeDTO typeDTO) {
        log.info("Go to mvc/projectfields/types/new");
        return "mvc/projectfields/types/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("type") @Valid TypeDTO typeDTO, BindingResult bindingResult) {
        typeValidator.validate(typeDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/projectfields/types/new");
            return "mvc/projectfields/types/new";
        }
        typesService.save(convertToProjectType(typeDTO));
        log.info("Go to redirect:/types");
        return "redirect:/types";
    }

    @GetMapping("/{name}/edit")
    public String edit(Model model, @PathVariable("name") String name) {
        model.addAttribute("type", convertToProjectTypeDTO(typesService.findByName(name)
                .orElseThrow(() -> new TypeNotFoundException("Тип проекта " + name + " не найден"))));
        log.info("Go to mvc/projectfields/types/edit");
        return "mvc/projectfields/types/edit";
    }

    @PatchMapping("/{name}")
    public String update(@ModelAttribute("type") @Valid TypeDTO typeDTO, BindingResult bindingResult,
                         @PathVariable("name") String name) {
        if (bindingResult.hasErrors()) {
            log.info("Go to mvc/projectfields/types/edit");
            return "mvc/projectfields/types/edit";
        }

        typesService.update(name, convertToProjectType(typeDTO));
        log.info("Go to redirect:/types");
        return "redirect:/types";
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name) {
        typesService.delete(name);
        log.info("Go to redirect:/types");
        return "redirect:/types";
    }

    private Type convertToProjectType(TypeDTO typeDTO) {
        return modelMapper.map(typeDTO, Type.class);
    }

    private TypeDTO convertToProjectTypeDTO(Type type) {
        return modelMapper.map(type, TypeDTO.class);
    }
}
