package com.hust.hustpital.repository;

import com.hust.hustpital.domain.Wards;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Wards entity.
 */
@Repository
public interface WardsRepository extends MongoRepository<Wards, String> {
    @Query("{}")
    Page<Wards> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Wards> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Wards> findOneWithEagerRelationships(String id);
}
