package com.hust.hustpital.repository;

import com.hust.hustpital.domain.Ethnics;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Ethnics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EthnicsRepository extends MongoRepository<Ethnics, String> {}
