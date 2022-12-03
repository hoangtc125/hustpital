package com.hust.hustpital.service;

import com.hust.hustpital.domain.Cities;
import com.hust.hustpital.repository.CitiesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Cities}.
 */
@Service
public class CitiesService {

    private final Logger log = LoggerFactory.getLogger(CitiesService.class);

    private final CitiesRepository citiesRepository;

    public CitiesService(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    /**
     * Save a cities.
     *
     * @param cities the entity to save.
     * @return the persisted entity.
     */
    public Cities save(Cities cities) {
        log.debug("Request to save Cities : {}", cities);
        return citiesRepository.save(cities);
    }

    /**
     * Update a cities.
     *
     * @param cities the entity to save.
     * @return the persisted entity.
     */
    public Cities update(Cities cities) {
        log.debug("Request to update Cities : {}", cities);
        return citiesRepository.save(cities);
    }

    /**
     * Partially update a cities.
     *
     * @param cities the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Cities> partialUpdate(Cities cities) {
        log.debug("Request to partially update Cities : {}", cities);

        return citiesRepository
            .findById(cities.getId())
            .map(existingCities -> {
                if (cities.getName() != null) {
                    existingCities.setName(cities.getName());
                }

                return existingCities;
            })
            .map(citiesRepository::save);
    }

    /**
     * Get all the cities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Cities> findAll(Pageable pageable) {
        log.debug("Request to get all Cities");
        return citiesRepository.findAll(pageable);
    }

    /**
     * Get all the cities with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Cities> findAllWithEagerRelationships(Pageable pageable) {
        return citiesRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one cities by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Cities> findOne(String id) {
        log.debug("Request to get Cities : {}", id);
        return citiesRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the cities by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Cities : {}", id);
        citiesRepository.deleteById(id);
    }
}
