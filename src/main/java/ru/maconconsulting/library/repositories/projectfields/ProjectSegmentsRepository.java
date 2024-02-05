package ru.maconconsulting.library.repositories.projectfields;

import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.projectfieldetities.ProjectSegment;

import java.util.Optional;

@Repository
public interface ProjectSegmentsRepository extends AbstractProjectFieldsRepository<ProjectSegment> {

    Optional<ProjectSegment> findByName(String name);

    void deleteByName(String name);
}
