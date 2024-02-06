package ru.maconconsulting.library.dto.projectfields;

import jakarta.validation.constraints.NotBlank;

public class ProjectSegmentDTO {

    @NotBlank
    private String name;

    public ProjectSegmentDTO() {
    }

    public ProjectSegmentDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

