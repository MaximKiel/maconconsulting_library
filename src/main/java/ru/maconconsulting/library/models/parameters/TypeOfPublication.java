package ru.maconconsulting.library.models.parameters;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import ru.maconconsulting.library.models.content.Publication;

import java.util.Set;

@Entity
@Table(name = "type_of_publication")
public class TypeOfPublication extends AbstractParameterEntity {

    @ManyToMany (mappedBy = "typesOfPublication")
    private Set<Publication> publications;

    public TypeOfPublication() {
    }

    public TypeOfPublication(String name) {
        super(name);
    }

    public Set<Publication> getPublications() {
        return publications;
    }

    public void setPublications(Set<Publication> publications) {
        this.publications = publications;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
