package com.hust.hustpital.repository;

import com.hust.hustpital.domain.Doctors;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Doctors entity.
 */
@Repository
public interface DoctorsRepository extends MongoRepository<Doctors, String> {
    @Query("{}")
    Page<Doctors> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Doctors> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Doctors> findOneWithEagerRelationships(String id);
}
