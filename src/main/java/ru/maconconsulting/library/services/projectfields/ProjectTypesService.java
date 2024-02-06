package ru.maconconsulting.library.services.projectfields;

import org.springframework.stereotype.Service;
import ru.maconconsulting.library.models.projectfields.ProjectType;
import ru.maconconsulting.library.repositories.projectfields.ProjectTypesRepository;

@Service
public class ProjectTypesService extends AbstractProjectFieldsService<ProjectType, ProjectTypesRepository> {

    public ProjectTypesService(ProjectTypesRepository repository) {
        super(repository);
    }

    @Override
    protected void setProjectsToUpdatedEntity(ProjectType updatedEntity, ProjectType currentEntity) {
        updatedEntity.setProjects(currentEntity.getProjects());
    }
}
