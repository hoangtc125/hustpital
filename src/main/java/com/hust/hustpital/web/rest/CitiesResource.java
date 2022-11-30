package com.hust.hustpital.web.rest;

import com.hust.hustpital.domain.Cities;
import com.hust.hustpital.repository.CitiesRepository;
import com.hust.hustpital.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hust.hustpital.domain.Cities}.
 */
@RestController
@RequestMapping("/api")
public class CitiesResource {

    private final Logger log = LoggerFactory.getLogger(CitiesResource.class);

    private static final String ENTITY_NAME = "cities";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitiesRepository citiesRepository;

    public CitiesResource(CitiesRepository citiesRepository) {
        this.citiesRepository = citiesRepository;
    }

    /**
     * {@code POST  /cities} : Create a new cities.
     *
     * @param cities the cities to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cities, or with status {@code 400 (Bad Request)} if the cities has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cities")
    public ResponseEntity<Cities> createCities(@Valid @RequestBody Cities cities) throws URISyntaxException {
        log.debug("REST request to save Cities : {}", cities);
        if (cities.getId() != null) {
            throw new BadRequestAlertException("A new cities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cities result = citiesRepository.save(cities);
        return ResponseEntity
            .created(new URI("/api/cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /cities/:id} : Updates an existing cities.
     *
     * @param id the id of the cities to save.
     * @param cities the cities to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cities,
     * or with status {@code 400 (Bad Request)} if the cities is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cities couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cities/{id}")
    public ResponseEntity<Cities> updateCities(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Cities cities
    ) throws URISyntaxException {
        log.debug("REST request to update Cities : {}, {}", id, cities);
        if (cities.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cities.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Cities result = citiesRepository.save(cities);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cities.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /cities/:id} : Partial updates given fields of an existing cities, field will ignore if it is null
     *
     * @param id the id of the cities to save.
     * @param cities the cities to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cities,
     * or with status {@code 400 (Bad Request)} if the cities is not valid,
     * or with status {@code 404 (Not Found)} if the cities is not found,
     * or with status {@code 500 (Internal Server Error)} if the cities couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Cities> partialUpdateCities(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Cities cities
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cities partially : {}, {}", id, cities);
        if (cities.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cities.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cities> result = citiesRepository
            .findById(cities.getId())
            .map(existingCities -> {
                if (cities.getName() != null) {
                    existingCities.setName(cities.getName());
                }

                return existingCities;
            })
            .map(citiesRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cities.getId()));
    }

    /**
     * {@code GET  /cities} : get all the cities.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cities in body.
     */
    @GetMapping("/cities")
    public List<Cities> getAllCities(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Cities");
        if (eagerload) {
            return citiesRepository.findAllWithEagerRelationships();
        } else {
            return citiesRepository.findAll();
        }
    }

    /**
     * {@code GET  /cities/:id} : get the "id" cities.
     *
     * @param id the id of the cities to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cities, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cities/{id}")
    public ResponseEntity<Cities> getCities(@PathVariable String id) {
        log.debug("REST request to get Cities : {}", id);
        Optional<Cities> cities = citiesRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(cities);
    }

    /**
     * {@code DELETE  /cities/:id} : delete the "id" cities.
     *
     * @param id the id of the cities to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cities/{id}")
    public ResponseEntity<Void> deleteCities(@PathVariable String id) {
        log.debug("REST request to delete Cities : {}", id);
        citiesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
