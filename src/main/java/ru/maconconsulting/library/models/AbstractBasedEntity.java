package ru.maconconsulting.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractBasedEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created_at")
    @NotNull
    @Temporal(value = TemporalType.TIMESTAMP)
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
