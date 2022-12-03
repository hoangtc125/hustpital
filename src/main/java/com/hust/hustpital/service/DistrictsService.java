package com.hust.hustpital.service;

import com.hust.hustpital.domain.Districts;
import com.hust.hustpital.repository.DistrictsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Districts}.
 */
@Service
public class DistrictsService {

    private final Logger log = LoggerFactory.getLogger(DistrictsService.class);

    private final DistrictsRepository districtsRepository;

    public DistrictsService(DistrictsRepository districtsRepository) {
        this.districtsRepository = districtsRepository;
    }

    /**
     * Save a districts.
     *
     * @param districts the entity to save.
     * @return the persisted entity.
     */
    public Districts save(Districts districts) {
        log.debug("Request to save Districts : {}", districts);
        return districtsRepository.save(districts);
    }

    /**
     * Update a districts.
     *
     * @param districts the entity to save.
     * @return the persisted entity.
     */
    public Districts update(Districts districts) {
        log.debug("Request to update Districts : {}", districts);
        return districtsRepository.save(districts);
    }

    /**
     * Partially update a districts.
     *
     * @param districts the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Districts> partialUpdate(Districts districts) {
        log.debug("Request to partially update Districts : {}", districts);

        return districtsRepository
            .findById(districts.getId())
            .map(existingDistricts -> {
                if (districts.getName() != null) {
                    existingDistricts.setName(districts.getName());
                }

                return existingDistricts;
            })
            .map(districtsRepository::save);
    }

    /**
     * Get all the districts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Districts> findAll(Pageable pageable) {
        log.debug("Request to get all Districts");
        return districtsRepository.findAll(pageable);
    }

    /**
     * Get all the districts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Districts> findAllWithEagerRelationships(Pageable pageable) {
        return districtsRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one districts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Districts> findOne(String id) {
        log.debug("Request to get Districts : {}", id);
        return districtsRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the districts by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Districts : {}", id);
        districtsRepository.deleteById(id);
    }
}
