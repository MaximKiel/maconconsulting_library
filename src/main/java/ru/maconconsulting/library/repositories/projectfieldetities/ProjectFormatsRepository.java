package ru.maconconsulting.library.repositories.projectfieldetities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.projectfieldetities.ProjectFormat;

import java.util.Optional;

@Repository
public interface ProjectFormatsRepository extends JpaRepository<ProjectFormat, Integer> {

    Optional<ProjectFormat> findByName(String name);

    void deleteByName(String name);
}
