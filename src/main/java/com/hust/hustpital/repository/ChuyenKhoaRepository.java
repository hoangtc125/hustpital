package com.hust.hustpital.repository;

import com.hust.hustpital.domain.ChuyenKhoa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ChuyenKhoa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChuyenKhoaRepository extends MongoRepository<ChuyenKhoa, String> {}
