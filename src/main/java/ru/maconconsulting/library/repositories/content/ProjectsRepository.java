package ru.maconconsulting.library.repositories.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.content.Project;

import java.util.Optional;

@Repository
public interface ProjectsRepository extends JpaRepository<Project, Integer> {

    Optional<Project> findByNumber(String number);

    Optional<Project> findByTitle(String title);

    void deleteByNumber(String number);
}
