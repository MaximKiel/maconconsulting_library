package ru.maconconsulting.library.services.projectfields;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.projectfieldetities.ProjectType;
import ru.maconconsulting.library.repositories.projectfieldetities.ProjectTypesRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectTypesService {

    private final ProjectTypesRepository projectTypesRepository;

    @Autowired
    public ProjectTypesService(ProjectTypesRepository projectTypesRepository) {
        this.projectTypesRepository = projectTypesRepository;
    }

    public List<ProjectType> findAll() {
        return projectTypesRepository.findAll();
    }

    public Optional<ProjectType> findByName(String name) {
        return projectTypesRepository.findByName(name);
    }

    @Transactional
    public void save(ProjectType type) {
        enrichProjectType(type);
        projectTypesRepository.save(type);
    }

    @Transactional
    public void update(String name, ProjectType updatedProjectType) {
        Optional<ProjectType> currentType = findByName(name);
        if (currentType.isPresent()) {
            updatedProjectType.setId(currentType.get().getId());
            updatedProjectType.setCreatedAt(currentType.get().getCreatedAt());
            updatedProjectType.setProjects(currentType.get().getProjects());
        }
        projectTypesRepository.save(updatedProjectType);
    }

    @Transactional
    public void delete(String name) {
        projectTypesRepository.deleteByName(name);
    }

    private void enrichProjectType(ProjectType type) {
        type.setCreatedAt(LocalDateTime.now());
    }
}
