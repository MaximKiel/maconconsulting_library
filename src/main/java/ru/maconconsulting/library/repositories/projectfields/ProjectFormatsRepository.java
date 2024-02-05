package ru.maconconsulting.library.repositories.projectfields;

import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.projectfieldetities.ProjectFormat;

import java.util.Optional;

@Repository
public interface ProjectFormatsRepository extends AbstractProjectFieldsRepository<ProjectFormat> {

    Optional<ProjectFormat> findByName(String name);

    void deleteByName(String name);
}
