package ru.maconconsulting.library.services.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.parameters.Chapter;
import ru.maconconsulting.library.repositories.parameters.ChaptersRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ChaptersService implements CommonParametersService<Chapter> {

    private final ChaptersRepository chaptersRepository;

    @Autowired
    public ChaptersService(ChaptersRepository chaptersRepository) {
        this.chaptersRepository = chaptersRepository;
    }


    @Override
    public List<Chapter> findAll() {
        return chaptersRepository.findAll();
    }

    @Override
    public Optional<Chapter> findByName(String name) {
        return chaptersRepository.findByName(name);
    }

    @Override
    @Transactional
    public void save(Chapter entity) {
        enrichProjectFieldEntity(entity);
        chaptersRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(String name, Chapter updatedEntity) {
        Optional<Chapter> currentChapter = findByName(name);
        if (currentChapter.isPresent()) {
            updatedEntity.setId(currentChapter.get().getId());
            updatedEntity.setCreatedAt(currentChapter.get().getCreatedAt());
            updatedEntity.setProjects(currentChapter.get().getProjects());
        }
        chaptersRepository.save(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(String name) {
        chaptersRepository.deleteByName(name);
    }
}
