package ru.maconconsulting.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MaconUserDTO {

    @NotNull
    private String name;

    @NotBlank
    private String login;

    @NotNull
    @Email
    private String email;

    //    Only "ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN"
    @NotNull
    private String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
