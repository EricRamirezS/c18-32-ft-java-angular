package tech.nocountry.c1832ftjavaangular.repository;

import org.springframework.data.repository.NoRepositoryBean;

@SuppressWarnings("unused")
@NoRepositoryBean
public interface CrudRepository<T, ID> extends
                                       CreateUpdateRepository<T, ID>,
                                       ReadRepository<T, ID>,
                                       DeleteRepository<T, ID> {
}