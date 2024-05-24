package tech.nocountry.c1832ftjavaangular.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@SuppressWarnings("unused")
@NoRepositoryBean
public interface CreateUpdateRepository<T, ID> extends Repository<T, ID> {

    <S extends T> S save(S entity);

    <S extends T> Iterable<S> saveAll(Iterable<S> entities);

}
