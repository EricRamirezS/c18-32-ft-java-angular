package tech.nocountry.c1832ftjavaangular.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@SuppressWarnings("unused")
@NoRepositoryBean
public interface ReadRepository<T, ID> extends Repository<T, ID> {
    Optional<T> findById(ID id);

    boolean existsById(ID id);

    Iterable<T> findAll();

    Iterable<T> findAllById(Iterable<ID> ids);

    long count();
}
