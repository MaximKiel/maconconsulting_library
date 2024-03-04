package ru.maconconsulting.library.dto.parameters;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractProjectFieldDTO that = (AbstractProjectFieldDTO) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
