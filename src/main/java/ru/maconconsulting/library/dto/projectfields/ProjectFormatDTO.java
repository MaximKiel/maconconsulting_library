package ru.maconconsulting.library.dto.projectfields;

import jakarta.validation.constraints.NotBlank;

public class ProjectFormatDTO {

    @NotBlank
    private String name;

    public ProjectFormatDTO() {
    }

    public ProjectFormatDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
