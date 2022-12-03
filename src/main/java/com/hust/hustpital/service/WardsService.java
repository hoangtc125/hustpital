package com.hust.hustpital.service;

import com.hust.hustpital.domain.Wards;
import com.hust.hustpital.repository.WardsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Wards}.
 */
@Service
public class WardsService {

    private final Logger log = LoggerFactory.getLogger(WardsService.class);

    private final WardsRepository wardsRepository;

    public WardsService(WardsRepository wardsRepository) {
        this.wardsRepository = wardsRepository;
    }

    /**
     * Save a wards.
     *
     * @param wards the entity to save.
     * @return the persisted entity.
     */
    public Wards save(Wards wards) {
        log.debug("Request to save Wards : {}", wards);
        return wardsRepository.save(wards);
    }

    /**
     * Update a wards.
     *
     * @param wards the entity to save.
     * @return the persisted entity.
     */
    public Wards update(Wards wards) {
        log.debug("Request to update Wards : {}", wards);
        return wardsRepository.save(wards);
    }

    /**
     * Partially update a wards.
     *
     * @param wards the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Wards> partialUpdate(Wards wards) {
        log.debug("Request to partially update Wards : {}", wards);

        return wardsRepository
            .findById(wards.getId())
            .map(existingWards -> {
                if (wards.getName() != null) {
                    existingWards.setName(wards.getName());
                }

                return existingWards;
            })
            .map(wardsRepository::save);
    }

    /**
     * Get all the wards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Wards> findAll(Pageable pageable) {
        log.debug("Request to get all Wards");
        return wardsRepository.findAll(pageable);
    }

    /**
     * Get all the wards with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Wards> findAllWithEagerRelationships(Pageable pageable) {
        return wardsRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one wards by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Wards> findOne(String id) {
        log.debug("Request to get Wards : {}", id);
        return wardsRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the wards by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Wards : {}", id);
        wardsRepository.deleteById(id);
    }
}
