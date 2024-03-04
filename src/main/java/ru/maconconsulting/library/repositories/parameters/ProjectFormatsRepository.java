package ru.maconconsulting.library.repositories.parameters;

import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.parameters.ProjectFormat;

@Repository
public interface ProjectFormatsRepository extends CommonProjectFieldsRepository<ProjectFormat> {
}
