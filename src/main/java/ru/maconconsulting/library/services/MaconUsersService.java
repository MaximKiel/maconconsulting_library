package ru.maconconsulting.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MaconUsersService(MaconUsersRepository maconUsersRepository, PasswordEncoder passwordEncoder) {
        this.maconUsersRepository = maconUsersRepository;
        this.passwordEncoder = passwordEncoder;
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
        maconUser.setPassword(passwordEncoder.encode(maconUser.getPassword()));
        enrichMaconUser(maconUser);
        maconUsersRepository.save(maconUser);
    }

    @Transactional
    public void update(String login, MaconUser updatedMaconUser) {
        updatedMaconUser.setId(findByLogin(login).get().getId());
        updatedMaconUser.setCreatedAt(findByLogin(login).get().getCreatedAt());
        updatedMaconUser.setPassword(passwordEncoder.encode(findByLogin(login).get().getPassword()));
        maconUsersRepository.save(updatedMaconUser);
    }

    @Transactional
    public void delete(String login) {
        maconUsersRepository.deleteByLogin(login);
    }

    private void enrichMaconUser(MaconUser maconUser) {
        maconUser.setLogin(defineLogin(maconUser.getEmail()));
        maconUser.setCreatedAt(LocalDateTime.now());
    }

    private String defineLogin(String email) {
        String[] splitStrings = email.split("@");
        return splitStrings[0];
    }
}
