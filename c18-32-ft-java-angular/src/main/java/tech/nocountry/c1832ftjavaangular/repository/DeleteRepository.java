package tech.nocountry.c1832ftjavaangular.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@SuppressWarnings("unused")
@NoRepositoryBean
public interface DeleteRepository<T, ID> extends Repository<T, ID> {
    void deleteById(ID id);

    void delete(T entity);

    void deleteAllById(Iterable<? extends ID> ids);

    void deleteAll(Iterable<? extends T> entities);

    void deleteAll();
}
