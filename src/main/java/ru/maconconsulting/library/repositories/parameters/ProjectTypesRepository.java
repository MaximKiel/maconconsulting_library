package ru.maconconsulting.library.repositories.parameters;

import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.parameters.ProjectType;

@Repository
public interface ProjectTypesRepository extends CommonProjectFieldsRepository<ProjectType> {
}
