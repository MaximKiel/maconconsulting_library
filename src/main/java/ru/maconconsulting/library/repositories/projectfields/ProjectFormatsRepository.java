package ru.maconconsulting.library.repositories.projectfields;

import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.projectfields.ProjectFormat;

import java.util.Optional;

@Repository
public interface ProjectFormatsRepository extends AbstractProjectFieldsRepository<ProjectFormat> {

    Optional<ProjectFormat> findByName(String name);

    void deleteByName(String name);
}
