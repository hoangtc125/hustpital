package com.hust.hustpital.repository;

import com.hust.hustpital.domain.LichHen;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the LichHen entity.
 */
@Repository
public interface LichHenRepository extends MongoRepository<LichHen, String> {
    @Query("{}")
    Page<LichHen> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<LichHen> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<LichHen> findOneWithEagerRelationships(String id);
}
