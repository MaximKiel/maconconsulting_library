package ru.maconconsulting.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.projectfieldetities.ProjectType;

import java.util.Optional;

@Repository
public interface ProjectTypesRepository extends JpaRepository<ProjectType, Integer> {

    Optional<ProjectType> findByName(String name);
    void deleteByName(String name);
}
