package com.hust.hustpital.repository;

import com.hust.hustpital.domain.PhongKham;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PhongKham entity.
 */
@Repository
public interface PhongKhamRepository extends MongoRepository<PhongKham, String> {
    @Query("{}")
    Page<PhongKham> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<PhongKham> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<PhongKham> findOneWithEagerRelationships(String id);
}
