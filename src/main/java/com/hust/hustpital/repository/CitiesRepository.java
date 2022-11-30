package com.hust.hustpital.repository;

import com.hust.hustpital.domain.Cities;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Cities entity.
 */
@Repository
public interface CitiesRepository extends MongoRepository<Cities, String> {
    @Query("{}")
    Page<Cities> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Cities> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Cities> findOneWithEagerRelationships(String id);
}
