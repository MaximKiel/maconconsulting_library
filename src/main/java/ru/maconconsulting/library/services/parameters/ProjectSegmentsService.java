package ru.maconconsulting.library.services.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.parameters.ProjectSegment;
import ru.maconconsulting.library.repositories.parameters.ProjectSegmentsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectSegmentsService implements CommonProjectFieldsService<ProjectSegment>{

    private final ProjectSegmentsRepository projectSegmentsRepository;

    @Autowired
    public ProjectSegmentsService(ProjectSegmentsRepository projectSegmentsRepository) {
        this.projectSegmentsRepository = projectSegmentsRepository;
    }

    @Override
    public List<ProjectSegment> findAll() {
        return projectSegmentsRepository.findAll();
    }

    @Override
    public Optional<ProjectSegment> findByName(String name) {
        return projectSegmentsRepository.findByName(name);
    }

    @Override
    @Transactional
    public void save(ProjectSegment entity) {
        enrichProjectFieldEntity(entity);
        projectSegmentsRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(String name, ProjectSegment updatedEntity) {
        Optional<ProjectSegment> currentSegment = findByName(name);
        if (currentSegment.isPresent()) {
            updatedEntity.setId(currentSegment.get().getId());
            updatedEntity.setCreatedAt(currentSegment.get().getCreatedAt());
            updatedEntity.setProjects(currentSegment.get().getProjects());
        }
        projectSegmentsRepository.save(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(String name) {
        projectSegmentsRepository.deleteByName(name);
    }
}
