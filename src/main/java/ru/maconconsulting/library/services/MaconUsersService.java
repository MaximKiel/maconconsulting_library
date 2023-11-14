package ru.maconconsulting.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.MaconUser;
import ru.maconconsulting.library.repositories.MaconUsersRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MaconUsersService {

    private final MaconUsersRepository maconUsersRepository;

    @Autowired
    public MaconUsersService(MaconUsersRepository maconUsersRepository) {
        this.maconUsersRepository = maconUsersRepository;
    }

    public List<MaconUser> findAll() {
        return maconUsersRepository.findAll();
    }

    public Optional<MaconUser> findByEmail(String email) {
        return maconUsersRepository.findByEmail(email);
    }

    public Optional<MaconUser> findByLogin(String login) {
        return maconUsersRepository.findByLogin(login);
    }

    @Transactional
    public void save(MaconUser maconUser) {
        enrichMaconUser(maconUser);
        maconUsersRepository.save(maconUser);
    }

    @Transactional
    public void update(String login, MaconUser updatedMaconUser) {
        updatedMaconUser.setId(findByLogin(login).get().getId());
        updatedMaconUser.setCreatedAt(findByLogin(login).get().getCreatedAt());
        updatedMaconUser.setPassword(findByLogin(login).get().getPassword());
        maconUsersRepository.save(updatedMaconUser);
    }

    @Transactional
    public void delete(String login) {
        maconUsersRepository.deleteByLogin(login);
    }

    private void enrichMaconUser(MaconUser maconUser) {
        maconUser.setCreatedAt(LocalDateTime.now());
    }
}
