package ru.maconconsulting.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.Project;

@Repository
public interface ProjectsRepository extends JpaRepository<Integer, Project> {
}
