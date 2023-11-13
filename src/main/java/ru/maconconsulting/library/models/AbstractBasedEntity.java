package ru.maconconsulting.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractBasedEntity {

    @Column(name = "created_at")
    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    public AbstractBasedEntity() {
    }

    public AbstractBasedEntity(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
                ", createdAt=" + createdAt +
                '}';
    }
}
