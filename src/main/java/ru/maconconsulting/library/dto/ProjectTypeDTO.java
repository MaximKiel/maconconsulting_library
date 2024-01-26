package ru.maconconsulting.library.dto;

import jakarta.validation.constraints.NotBlank;

public class ProjectTypeDTO {

    @NotBlank
    private String name;

    public ProjectTypeDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProjectTypeDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}