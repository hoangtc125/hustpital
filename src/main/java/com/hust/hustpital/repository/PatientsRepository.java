package com.hust.hustpital.repository;

import com.hust.hustpital.domain.Patients;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Patients entity.
 */
@Repository
public interface PatientsRepository extends MongoRepository<Patients, String> {
    @Query("{}")
    Page<Patients> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Patients> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Patients> findOneWithEagerRelationships(String id);
}
