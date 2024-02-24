package ru.maconconsulting.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.maconconsulting.library.models.Role;

public class MaconUserDTO {

    @NotBlank(message = "Имя не должно быть пустым!")
    private String name;

    private String login;

    @NotBlank(message = "Email не должен быть пустым!")
    @Email(message = "Email должен иметь вид ***.mail.ru")
    private String email;

    @NotNull
    private Role role;

    @NotBlank(message = "Пароль не должен быть пустым!")
    private String password;

    public MaconUserDTO() {
    }

    public MaconUserDTO(String name, String login, String email, Role role, String password) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.role = role;
        this.password = password;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
