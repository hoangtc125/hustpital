package com.hust.hustpital.service;

import com.hust.hustpital.domain.Ethnics;
import com.hust.hustpital.repository.EthnicsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Ethnics}.
 */
@Service
public class EthnicsService {

    private final Logger log = LoggerFactory.getLogger(EthnicsService.class);

    private final EthnicsRepository ethnicsRepository;

    public EthnicsService(EthnicsRepository ethnicsRepository) {
        this.ethnicsRepository = ethnicsRepository;
    }

    /**
     * Save a ethnics.
     *
     * @param ethnics the entity to save.
     * @return the persisted entity.
     */
    public Ethnics save(Ethnics ethnics) {
        log.debug("Request to save Ethnics : {}", ethnics);
        return ethnicsRepository.save(ethnics);
    }

    /**
     * Update a ethnics.
     *
     * @param ethnics the entity to save.
     * @return the persisted entity.
     */
    public Ethnics update(Ethnics ethnics) {
        log.debug("Request to update Ethnics : {}", ethnics);
        return ethnicsRepository.save(ethnics);
    }

    /**
     * Partially update a ethnics.
     *
     * @param ethnics the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Ethnics> partialUpdate(Ethnics ethnics) {
        log.debug("Request to partially update Ethnics : {}", ethnics);

        return ethnicsRepository
            .findById(ethnics.getId())
            .map(existingEthnics -> {
                if (ethnics.getName() != null) {
                    existingEthnics.setName(ethnics.getName());
                }

                return existingEthnics;
            })
            .map(ethnicsRepository::save);
    }

    /**
     * Get all the ethnics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Ethnics> findAll(Pageable pageable) {
        log.debug("Request to get all Ethnics");
        return ethnicsRepository.findAll(pageable);
    }

    /**
     * Get one ethnics by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Ethnics> findOne(String id) {
        log.debug("Request to get Ethnics : {}", id);
        return ethnicsRepository.findById(id);
    }

    /**
     * Delete the ethnics by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Ethnics : {}", id);
        ethnicsRepository.deleteById(id);
    }
}
