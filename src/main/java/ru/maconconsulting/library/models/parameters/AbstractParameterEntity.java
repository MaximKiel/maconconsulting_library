package ru.maconconsulting.library.models.parameters;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import ru.maconconsulting.library.models.AbstractBasedEntity;

@MappedSuperclass
public abstract class AbstractParameterEntity extends AbstractBasedEntity {

    @Column(name = "name")
    @NotBlank
    private String name;

    public AbstractParameterEntity() {
    }

    public AbstractParameterEntity(String name) {
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
        return "AbstractParameterEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
