package ru.maconconsulting.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "macon_user")
public class MaconUser extends AbstractBasedEntity {

    @Column(name = "name")
    @NotBlank(message = "Имя не должно быть пустым!")
    private String name;

    @Column(name = "login")
    @NotBlank
    private String login;

    @Column(name = "email")
    @NotBlank(message = "Email не должен быть пустым!")
    @Email(message = "Email должен иметь вид ***.mail.ru")
    private String email;

    @Column(name = "role")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "password")
    @NotBlank(message = "Пароль не должен быть пустым!")
    private String password;

    public MaconUser() {
    }

    public MaconUser(String name, String login, String email, String password, Role role) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "MaconUser{" +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
