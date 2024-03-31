package ru.maconconsulting.library.utils.validators.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maconconsulting.library.dto.content.ProjectDTO;
import ru.maconconsulting.library.models.content.Project;
import ru.maconconsulting.library.services.content.ProjectsService;

@Component
public class ProjectValidator implements Validator {

    private final ProjectsService projectsService;

    @Autowired
    public ProjectValidator(ProjectsService projectsService) {
        this.projectsService = projectsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Project.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProjectDTO projectDTO = (ProjectDTO) target;
        if (projectsService.findByNumber(projectDTO.getNumber()).isPresent()) {
            errors.rejectValue("number", "422", "Проект с таким номером уже существует!");
        }
        if (projectsService.findByTitle(projectDTO.getTitle()).isPresent()) {
            errors.rejectValue("title", "422", "Проект с таким названием уже существует!");
        }
    }

    public void checkUniqueForUpdate(Object target, Errors errors) {
        ProjectDTO projectDTO = (ProjectDTO) target;
        if (projectsService.findByTitle(projectDTO.getTitle()).isPresent()) {
            boolean check = projectsService.findAll().stream()
                    .filter(p -> !p.getNumber().equals(projectDTO.getNumber()))
                    .anyMatch(p -> p.getTitle().equals(projectDTO.getTitle()));
            if (check) {
                errors.rejectValue("title", "422", "Проект с таким названием уже существует!");
            }
        }
    }
}
