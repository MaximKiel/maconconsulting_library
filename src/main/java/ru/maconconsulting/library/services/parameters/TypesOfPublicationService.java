package ru.maconconsulting.library.services.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.parameters.TypeOfPublication;
import ru.maconconsulting.library.repositories.parameters.TypesOfPublicationRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TypesOfPublicationService implements CommonParametersService<TypeOfPublication> {

    private final TypesOfPublicationRepository repository;

    @Autowired
    public TypesOfPublicationService(TypesOfPublicationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TypeOfPublication> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<TypeOfPublication> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    @Transactional
    public void save(TypeOfPublication entity) {
        enrichParameterFieldEntity(entity);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void update(String name, TypeOfPublication updatedEntity) {
        Optional<TypeOfPublication> currentType = findByName(name);
        if (currentType.isPresent()) {
            updatedEntity.setId(currentType.get().getId());
            updatedEntity.setCreatedAt(currentType.get().getCreatedAt());
            updatedEntity.setPublications(currentType.get().getPublications());
        }
        repository.save(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(String name) {
        repository.deleteByName(name);
    }
}
