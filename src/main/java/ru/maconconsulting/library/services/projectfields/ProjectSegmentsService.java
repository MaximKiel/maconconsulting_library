package ru.maconconsulting.library.services.projectfields;

import org.springframework.stereotype.Service;
import ru.maconconsulting.library.models.projectfields.ProjectSegment;
import ru.maconconsulting.library.repositories.projectfields.ProjectSegmentsRepository;

@Service
public class ProjectSegmentsService extends AbstractProjectFieldsService<ProjectSegment, ProjectSegmentsRepository> {

    public ProjectSegmentsService(ProjectSegmentsRepository repository) {
        super(repository);
    }

    @Override
    protected void setProjectsToUpdatedEntity(ProjectSegment updatedEntity, ProjectSegment currentEntity) {
        updatedEntity.setProjects(currentEntity.getProjects());
    }
}
