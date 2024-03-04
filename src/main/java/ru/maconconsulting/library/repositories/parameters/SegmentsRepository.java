package ru.maconconsulting.library.repositories.parameters;

import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.parameters.Segment;

@Repository
public interface SegmentsRepository extends CommonParametersRepository<Segment> {
}
