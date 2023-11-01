package ru.maconconsulting.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.MaconUser;
import ru.maconconsulting.library.repositories.MaconUsersRepository;
import ru.maconconsulting.library.utils.exceptions.MaconUserNotFoundException;

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

    public MaconUser findById(int id) {
        return maconUsersRepository.findById(id)
                .orElseThrow(() -> new MaconUserNotFoundException("Пользователь с id " + id + " не найден"));
    }

    public Optional<MaconUser> findByEmail(String email) {
        return maconUsersRepository.findByEmail(email);
    }

    @Transactional
    public void save(MaconUser maconUser) {
        enrichMaconUser(maconUser);
        maconUsersRepository.save(maconUser);
    }

    @Transactional
    public void update(int id, MaconUser updatedMaconUser) {
        updatedMaconUser.setId(id);
        maconUsersRepository.save(updatedMaconUser);
    }

    @Transactional
    public void delete(int id) {
        maconUsersRepository.deleteById(id);
    }

    private void enrichMaconUser(MaconUser maconUser) {
        maconUser.setCreatedAt(LocalDateTime.now());
    }
}
