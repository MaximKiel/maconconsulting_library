package ru.maconconsulting.library.services.projectfields;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.projectfields.AbstractProjectFieldEntity;
import ru.maconconsulting.library.repositories.projectfields.AbstractProjectFieldsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public abstract class AbstractProjectFieldsService <E extends AbstractProjectFieldEntity,
        R extends AbstractProjectFieldsRepository<E>> {

    private final R repository;

//    TODO: Fix the problem - Could not autowire. No beans of 'R' type found.
    @Autowired
    public AbstractProjectFieldsService(R repository) {
        this.repository = repository;
    }

    public List<E> findAll() {
        return repository.findAll();
    }

    public Optional<E> findByName(String name) {
        return repository.findByName(name);
    }

    @Transactional
    public void save(E entity) {
        enrichEntity(entity);
        repository.save(entity);
    }

    @Transactional
    public void update(String name, E updatedEntity) {
        Optional<E> currentEntity = findByName(name);
        if (currentEntity.isPresent()) {
            updatedEntity.setId(currentEntity.get().getId());
            updatedEntity.setCreatedAt(currentEntity.get().getCreatedAt());
            setProjectsToUpdatedEntity(updatedEntity, currentEntity.get());
        }
        repository.save(updatedEntity);
    }

    @Transactional
    public void delete(String name) {
        repository.deleteByName(name);
    }

    protected abstract void setProjectsToUpdatedEntity(E updatedEntity, E currentEntity);

    private void enrichEntity(E entity) {
        entity.setCreatedAt(LocalDateTime.now());
    }
}
