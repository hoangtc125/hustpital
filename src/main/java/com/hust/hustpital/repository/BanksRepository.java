package com.hust.hustpital.repository;

import com.hust.hustpital.domain.Banks;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Banks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BanksRepository extends MongoRepository<Banks, String> {}
