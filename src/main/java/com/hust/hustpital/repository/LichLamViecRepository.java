package com.hust.hustpital.repository;

import com.hust.hustpital.domain.LichLamViec;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the LichLamViec entity.
 */
@Repository
public interface LichLamViecRepository extends MongoRepository<LichLamViec, String> {
    @Query("{}")
    Page<LichLamViec> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<LichLamViec> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<LichLamViec> findOneWithEagerRelationships(String id);
}
