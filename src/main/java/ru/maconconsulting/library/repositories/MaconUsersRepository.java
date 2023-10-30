package ru.maconconsulting.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.MaconUser;

@Repository
public interface MaconUsersRepository extends JpaRepository<Integer, MaconUser> {
}
