package ru.maconconsulting.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.maconconsulting.library.models.Publication;

import java.util.Optional;

public interface PublicationsRepository extends JpaRepository<Publication, Integer> {

    Optional<Publication> findByTitle(String title);
}
