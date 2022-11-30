package com.hust.hustpital.repository;

import com.hust.hustpital.domain.Districts;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Districts entity.
 */
@Repository
public interface DistrictsRepository extends MongoRepository<Districts, String> {
    @Query("{}")
    Page<Districts> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Districts> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Districts> findOneWithEagerRelationships(String id);
}
