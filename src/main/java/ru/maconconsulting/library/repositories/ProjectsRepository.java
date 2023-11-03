package ru.maconconsulting.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.Project;

import java.util.Optional;

@Repository
public interface ProjectsRepository extends JpaRepository<Project, Integer> {

    Optional<Project> findByNumber(int number);
    Optional<Project> findByTitle(String title);
}
