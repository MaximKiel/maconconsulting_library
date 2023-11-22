package ru.maconconsulting.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.maconconsulting.library.models.MaconUser;
import ru.maconconsulting.library.repositories.MaconUsersRepository;
import ru.maconconsulting.library.security.MaconUserDetails;

import java.util.Optional;

@Service
public class MaconUserDetailsService implements UserDetailsService {

    private final MaconUsersRepository usersRepository;

    @Autowired
    public MaconUserDetailsService(MaconUsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MaconUser> maconUser = usersRepository.findByLogin(username);

        if (maconUser.isEmpty()) {
            throw new UsernameNotFoundException("MaconUser not found");
        }

        return new MaconUserDetails(maconUser.get());
    }
}
