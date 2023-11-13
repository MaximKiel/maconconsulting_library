package ru.maconconsulting.library.controllers.rest;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.maconconsulting.library.dto.ProjectDTO;
import ru.maconconsulting.library.dto.ProjectsResponse;
import ru.maconconsulting.library.models.Project;
import ru.maconconsulting.library.utils.exceptions.ProjectNotCreateException;
import ru.maconconsulting.library.services.ProjectsService;
import ru.maconconsulting.library.utils.ProjectValidator;
import ru.maconconsulting.library.utils.errors.ProjectErrorResponse;
import ru.maconconsulting.library.utils.exceptions.ProjectNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/rest/projects", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectsRestController {

    private final ProjectsService projectsService;
    private final ModelMapper modelMapper;
    private final ProjectValidator projectValidator;

    @Autowired
    public ProjectsRestController(ProjectsService projectsService, ModelMapper modelMapper, ProjectValidator projectValidator) {
        this.projectsService = projectsService;
        this.modelMapper = modelMapper;
        this.projectValidator = projectValidator;
    }

    @GetMapping
    public ProjectsResponse getAllProjects() {
        return new ProjectsResponse(projectsService.findAll().stream().map(this::convertToProjectDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{number}")
    public ProjectDTO getProject(@PathVariable("number") int number) {
        return convertToProjectDTO(projectsService.findByNumber(number)
                .orElseThrow(() -> new ProjectNotFoundException("Проект с номером " + number + " не найден")));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid ProjectDTO projectDTO, BindingResult bindingResult) {
        projectValidator.validate(projectDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for (FieldError error : errorList) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new ProjectNotCreateException(errorMessage.toString());
        }

        projectsService.save(convertToProject(projectDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{number}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("number") int number) {
        projectsService.delete(number);
    }

    @PatchMapping(value = "/edit/{number}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("number") int number, @RequestBody @Valid ProjectDTO projectDTO) {
        projectsService.update(number, convertToProject(projectDTO));
    }

    @ExceptionHandler
    private ResponseEntity<ProjectErrorResponse> handleException(ProjectNotFoundException e) {
        ProjectErrorResponse response = new ProjectErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ProjectErrorResponse> handleException(ProjectNotCreateException e) {
        ProjectErrorResponse response = new ProjectErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Project convertToProject(ProjectDTO projectDTO) {
        return modelMapper.map(projectDTO, Project.class);
    }

    private ProjectDTO convertToProjectDTO(Project project) {
        return modelMapper.map(project, ProjectDTO.class);
    }
}
