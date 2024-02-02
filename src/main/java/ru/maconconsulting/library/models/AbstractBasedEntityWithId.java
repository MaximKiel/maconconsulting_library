package ru.maconconsulting.library.models;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class AbstractBasedEntityWithId extends AbstractBasedEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public AbstractBasedEntityWithId() {
    }

    public AbstractBasedEntityWithId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AbstractBasedEntityWithId{" +
                "id=" + id +
                '}';
    }
}
