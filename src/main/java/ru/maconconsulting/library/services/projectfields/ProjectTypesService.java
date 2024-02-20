package ru.maconconsulting.library.services.projectfields;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.projectfields.ProjectType;
import ru.maconconsulting.library.repositories.projectfields.ProjectTypesRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectTypesService implements CommonProjectFieldsService<ProjectType> {

    private final ProjectTypesRepository projectTypesRepository;

    @Autowired
    public ProjectTypesService(ProjectTypesRepository projectTypesRepository) {
        this.projectTypesRepository = projectTypesRepository;
    }

    @Override
    public List<ProjectType> findAllSorted() {
        List<ProjectType> types = projectTypesRepository.findAll();
        types.sort(Comparator.comparing(ProjectType::getName));
        return types;
    }

    @Override
    public Optional<ProjectType> findByName(String name) {
        return projectTypesRepository.findByName(name);
    }

    @Override
    @Transactional
    public void save(ProjectType entity) {
        enrichProjectFieldEntity(entity);
        projectTypesRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(String name, ProjectType updatedEntity) {
        Optional<ProjectType> currentType = findByName(name);
        if (currentType.isPresent()) {
            updatedEntity.setId(currentType.get().getId());
            updatedEntity.setCreatedAt(currentType.get().getCreatedAt());
            updatedEntity.setProjects(currentType.get().getProjects());
        }
        projectTypesRepository.save(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(String name) {
        projectTypesRepository.deleteByName(name);
    }
}
