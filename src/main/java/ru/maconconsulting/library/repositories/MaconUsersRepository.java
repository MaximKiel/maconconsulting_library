package ru.maconconsulting.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.MaconUser;

import java.util.Optional;

@Repository
public interface MaconUsersRepository extends JpaRepository<MaconUser, Integer> {
    Optional<MaconUser> findByEmail(String email);
    Optional<MaconUser> findByLogin(String login);
}
