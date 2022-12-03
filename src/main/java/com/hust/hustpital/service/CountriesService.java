package com.hust.hustpital.service;

import com.hust.hustpital.domain.Countries;
import com.hust.hustpital.repository.CountriesRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Countries}.
 */
@Service
public class CountriesService {

    private final Logger log = LoggerFactory.getLogger(CountriesService.class);

    private final CountriesRepository countriesRepository;

    public CountriesService(CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    /**
     * Save a countries.
     *
     * @param countries the entity to save.
     * @return the persisted entity.
     */
    public Countries save(Countries countries) {
        log.debug("Request to save Countries : {}", countries);
        return countriesRepository.save(countries);
    }

    /**
     * Update a countries.
     *
     * @param countries the entity to save.
     * @return the persisted entity.
     */
    public Countries update(Countries countries) {
        log.debug("Request to update Countries : {}", countries);
        return countriesRepository.save(countries);
    }

    /**
     * Partially update a countries.
     *
     * @param countries the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Countries> partialUpdate(Countries countries) {
        log.debug("Request to partially update Countries : {}", countries);

        return countriesRepository
            .findById(countries.getId())
            .map(existingCountries -> {
                if (countries.getName() != null) {
                    existingCountries.setName(countries.getName());
                }

                return existingCountries;
            })
            .map(countriesRepository::save);
    }

    /**
     * Get all the countries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Countries> findAll(Pageable pageable) {
        log.debug("Request to get all Countries");
        return countriesRepository.findAll(pageable);
    }

    /**
     * Get one countries by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Countries> findOne(String id) {
        log.debug("Request to get Countries : {}", id);
        return countriesRepository.findById(id);
    }

    /**
     * Delete the countries by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Countries : {}", id);
        countriesRepository.deleteById(id);
    }
}
