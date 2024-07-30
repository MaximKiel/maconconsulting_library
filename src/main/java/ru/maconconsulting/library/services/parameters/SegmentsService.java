package ru.maconconsulting.library.services.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maconconsulting.library.models.parameters.Segment;
import ru.maconconsulting.library.repositories.parameters.SegmentsRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SegmentsService implements CommonParametersService<Segment> {

    private final SegmentsRepository segmentsRepository;

    @Autowired
    public SegmentsService(SegmentsRepository segmentsRepository) {
        this.segmentsRepository = segmentsRepository;
    }

    @Override
    public List<Segment> findAll() {
        return segmentsRepository.findAll();
    }

    @Override
    public Optional<Segment> findByName(String name) {
        return segmentsRepository.findByName(name);
    }

    @Override
    @Transactional
    public void save(Segment entity) {
        enrichParameterFieldEntity(entity);
        segmentsRepository.save(entity);
    }

    @Override
    @Transactional
    public void update(String name, Segment updatedEntity) {
        Optional<Segment> currentSegment = findByName(name);
        if (currentSegment.isPresent()) {
            updatedEntity.setId(currentSegment.get().getId());
            updatedEntity.setCreatedAt(currentSegment.get().getCreatedAt());
            updatedEntity.setProjects(currentSegment.get().getProjects());
        }
        segmentsRepository.save(updatedEntity);
    }

    @Override
    @Transactional
    public void delete(String name) {
        segmentsRepository.deleteByName(name);
    }
}
