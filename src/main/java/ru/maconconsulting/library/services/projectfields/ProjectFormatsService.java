package ru.maconconsulting.library.services.projectfields;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.projectfields.ProjectFormat;
import ru.maconconsulting.library.repositories.projectfields.ProjectFormatsRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectFormatsService implements CommonProjectFieldsService<ProjectFormat> {

    private final ProjectFormatsRepository projectFormatsRepository;

    @Autowired
    public ProjectFormatsService(ProjectFormatsRepository projectFormatsRepository) {
        this.projectFormatsRepository = projectFormatsRepository;
    }

    @Override
    public List<ProjectFormat> findAll() {
        List<ProjectFormat> formats = projectFormatsRepository.findAll();
        formats.sort(Comparator.comparing(ProjectFormat::getName));
        return formats;
    }

    @Override
    public Optional<ProjectFormat> findByName(String name) {
        return projectFormatsRepository.findByName(name);
    }

    @Override
    @Transactional
    public void save(ProjectFormat entity) {
        enrichProjectFieldEntity(entity);
        projectFormatsRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(String name, ProjectFormat updatedEntity) {
        Optional<ProjectFormat> currentFormat = findByName(name);
        if (currentFormat.isPresent()) {
            updatedEntity.setId(currentFormat.get().getId());
            updatedEntity.setCreatedAt(currentFormat.get().getCreatedAt());
            updatedEntity.setProjects(currentFormat.get().getProjects());
        }
        projectFormatsRepository.save(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(String name) {
        projectFormatsRepository.deleteByName(name);
    }
}
