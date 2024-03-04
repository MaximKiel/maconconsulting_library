package ru.maconconsulting.library.repositories.parameters;

import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.parameters.ProjectSegment;

@Repository
public interface ProjectSegmentsRepository extends CommonProjectFieldsRepository<ProjectSegment> {
}
