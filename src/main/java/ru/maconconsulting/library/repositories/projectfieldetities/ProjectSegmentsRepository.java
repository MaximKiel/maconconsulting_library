package ru.maconconsulting.library.repositories.projectfieldetities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.projectfieldetities.ProjectSegment;

import java.util.Optional;

@Repository
public interface ProjectSegmentsRepository extends JpaRepository<ProjectSegment, Integer> {

    Optional<ProjectSegment> findByName(String name);

    void deleteByName(String name);
}
