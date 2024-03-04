package ru.maconconsulting.library.services.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.parameters.ProjectKeyWord;
import ru.maconconsulting.library.repositories.parameters.ProjectKeyWordsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProjectKeyWordsService implements CommonProjectFieldsService<ProjectKeyWord> {

    private final ProjectKeyWordsRepository projectKeyWordsRepository;

    @Autowired
    public ProjectKeyWordsService(ProjectKeyWordsRepository projectKeyWordsRepository) {
        this.projectKeyWordsRepository = projectKeyWordsRepository;
    }

    @Override
    public List<ProjectKeyWord> findAll() {
        return projectKeyWordsRepository.findAll();
    }

    @Override
    public Optional<ProjectKeyWord> findByName(String name) {
        return projectKeyWordsRepository.findByName(name);
    }

    @Override
    @Transactional
    public void save(ProjectKeyWord entity) {
        enrichProjectFieldEntity(entity);
        projectKeyWordsRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(String name, ProjectKeyWord updatedEntity) {
        Optional<ProjectKeyWord> currentKeyWord = findByName(name);
        if (currentKeyWord.isPresent()) {
            updatedEntity.setId(currentKeyWord.get().getId());
            updatedEntity.setCreatedAt(currentKeyWord.get().getCreatedAt());
            updatedEntity.setProjects(currentKeyWord.get().getProjects());
        }
        projectKeyWordsRepository.save(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(String name) {
        projectKeyWordsRepository.deleteByName(name);
    }
}
