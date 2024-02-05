package ru.maconconsulting.library.repositories.projectfields;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ru.maconconsulting.library.models.projectfields.AbstractProjectFieldEntity;

@NoRepositoryBean
public interface AbstractProjectFieldsRepository<T extends AbstractProjectFieldEntity> extends JpaRepository<T, Integer> {
}
