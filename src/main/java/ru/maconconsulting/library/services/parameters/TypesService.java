package ru.maconconsulting.library.services.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.parameters.Type;
import ru.maconconsulting.library.repositories.parameters.TypesRepository;

import java.util.Optional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class TypesService implements CommonParametersService<Type> {

    private final TypesRepository typesRepository;

    @Autowired
    public TypesService(TypesRepository typesRepository) {
        this.typesRepository = typesRepository;
    }


    @Override
    public List<Type> findAll() {
        return typesRepository.findAll();
    }

    @Override
    public Optional<Type> findByName(String name) {
        return typesRepository.findByName(name);
    }

    @Override
    @Transactional
    public void save(Type entity) {
        enrichProjectFieldEntity(entity);
        typesRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(String name, Type updatedEntity) {
        Optional<Type> currentChapter = findByName(name);
        if (currentChapter.isPresent()) {
            updatedEntity.setId(currentChapter.get().getId());
            updatedEntity.setCreatedAt(currentChapter.get().getCreatedAt());
            updatedEntity.setProjects(currentChapter.get().getProjects());
        }
        typesRepository.save(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(String name) {
        typesRepository.deleteByName(name);
    }
}
