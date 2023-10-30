package ru.maconconsulting.library.models;

import java.time.LocalDateTime;

public abstract class AbstractBasedEntity {

    private Integer id;

    private LocalDateTime createdAt;

    public AbstractBasedEntity() {
    }

    public AbstractBasedEntity(Integer id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "AbstractBasedEntity{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                '}';
    }
}
