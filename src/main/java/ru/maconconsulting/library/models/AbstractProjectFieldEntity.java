package ru.maconconsulting.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@MappedSuperclass
public abstract class AbstractProjectFieldEntity extends AbstractBasedEntityWithId {

    @Column(name = "name")
    @NotBlank
    private String name;

    public AbstractProjectFieldEntity() {
    }

    public AbstractProjectFieldEntity(String name) {
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
        return "AbstractProjectFieldEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
