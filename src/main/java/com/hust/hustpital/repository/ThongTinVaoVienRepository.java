package com.hust.hustpital.repository;

import com.hust.hustpital.domain.ThongTinVaoVien;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ThongTinVaoVien entity.
 */
@Repository
public interface ThongTinVaoVienRepository extends MongoRepository<ThongTinVaoVien, String> {
    @Query("{}")
    Page<ThongTinVaoVien> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<ThongTinVaoVien> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<ThongTinVaoVien> findOneWithEagerRelationships(String id);
}
