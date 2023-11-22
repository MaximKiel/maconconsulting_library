package ru.maconconsulting.library.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.maconconsulting.library.models.MaconUser;

import java.util.Collection;
import java.util.Collections;

public class MaconUserDetails implements UserDetails {

    private final MaconUser maconUser;

    public MaconUserDetails(MaconUser maconUser) {
        this.maconUser = maconUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(maconUser.getRole().name()));
    }

    @Override
    public String getPassword() {
        return maconUser.getPassword();
    }

    @Override
    public String getUsername() {
        return maconUser.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
