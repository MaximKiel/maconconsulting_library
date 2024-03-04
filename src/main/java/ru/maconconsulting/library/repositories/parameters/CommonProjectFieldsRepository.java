package ru.maconconsulting.library.repositories.parameters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.maconconsulting.library.models.parameters.AbstractProjectFieldEntity;

import java.util.Optional;

@NoRepositoryBean
public interface CommonProjectFieldsRepository<E extends AbstractProjectFieldEntity> extends JpaRepository<E, Integer> {

    Optional<E> findByName(String name);

    void deleteByName(String name);
}
