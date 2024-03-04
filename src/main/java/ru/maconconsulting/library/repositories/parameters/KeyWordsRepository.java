package ru.maconconsulting.library.repositories.parameters;

import org.springframework.stereotype.Repository;
import ru.maconconsulting.library.models.parameters.KeyWord;

@Repository
public interface KeyWordsRepository extends CommonParametersRepository<KeyWord> {
}
