package ru.maconconsulting.library.services.projectfields;

import ru.maconconsulting.library.models.projectfields.AbstractProjectFieldEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommonProjectFieldsService <E extends AbstractProjectFieldEntity> {

    List<E> findAll();

    Optional<E> findByName(String name);

    void save(E entity);

    void update(String name, E updatedEntity);

    void delete(String name);

    default void enrichProjectFieldEntity(AbstractProjectFieldEntity entity) {
        entity.setCreatedAt(LocalDateTime.now());
    }
}
