package ru.maconconsulting.library.repositories.projectfields;

import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.projectfieldetities.ProjectType;

import java.util.Optional;

@Repository
public interface ProjectTypesRepository extends AbstractProjectFieldsRepository<ProjectType> {

    Optional<ProjectType> findByName(String name);

    void deleteByName(String name);
}
