package ru.maconconsulting.library.dto.projectfields;

import jakarta.validation.constraints.NotBlank;

public class ProjectTypeDTO {

    @NotBlank
    private String name;

    public ProjectTypeDTO() {
    }

    public ProjectTypeDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
