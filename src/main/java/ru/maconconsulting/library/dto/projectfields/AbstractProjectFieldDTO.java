package ru.maconconsulting.library.dto.projectfields;

import jakarta.validation.constraints.NotBlank;

public abstract class AbstractProjectFieldDTO {

    @NotBlank
    private String name;

    public AbstractProjectFieldDTO() {
    }

    public AbstractProjectFieldDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
