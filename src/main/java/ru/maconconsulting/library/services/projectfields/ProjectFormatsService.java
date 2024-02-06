package ru.maconconsulting.library.services.projectfields;

import org.springframework.stereotype.Service;
import ru.maconconsulting.library.models.projectfields.ProjectFormat;
import ru.maconconsulting.library.repositories.projectfields.ProjectFormatsRepository;

@Service
public class ProjectFormatsService extends AbstractProjectFieldsService<ProjectFormat, ProjectFormatsRepository> {

    public ProjectFormatsService(ProjectFormatsRepository repository) {
        super(repository);
    }

    @Override
    protected void setProjectsToUpdatedEntity(ProjectFormat updatedEntity, ProjectFormat currentEntity) {
        updatedEntity.setProjects(currentEntity.getProjects());
    }
}
