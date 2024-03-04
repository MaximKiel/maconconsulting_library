package ru.maconconsulting.library.services.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.parameters.KeyWord;
import ru.maconconsulting.library.repositories.parameters.KeyWordsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class KeyWordsService implements CommonParametersService<KeyWord> {

    private final KeyWordsRepository keyWordsRepository;

    @Autowired
    public KeyWordsService(KeyWordsRepository keyWordsRepository) {
        this.keyWordsRepository = keyWordsRepository;
    }

    @Override
    public List<KeyWord> findAll() {
        return keyWordsRepository.findAll();
    }

    @Override
    public Optional<KeyWord> findByName(String name) {
        return keyWordsRepository.findByName(name);
    }

    @Override
    @Transactional
    public void save(KeyWord entity) {
        enrichProjectFieldEntity(entity);
        keyWordsRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(String name, KeyWord updatedEntity) {
        Optional<KeyWord> currentKeyWord = findByName(name);
        if (currentKeyWord.isPresent()) {
            updatedEntity.setId(currentKeyWord.get().getId());
            updatedEntity.setCreatedAt(currentKeyWord.get().getCreatedAt());
            updatedEntity.setProjects(currentKeyWord.get().getProjects());
        }
        keyWordsRepository.save(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(String name) {
        keyWordsRepository.deleteByName(name);
    }
}
