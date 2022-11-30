package com.hust.hustpital.web.rest;

import com.hust.hustpital.domain.Ethnics;
import com.hust.hustpital.repository.EthnicsRepository;
import com.hust.hustpital.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hust.hustpital.domain.Ethnics}.
 */
@RestController
@RequestMapping("/api")
public class EthnicsResource {

    private final Logger log = LoggerFactory.getLogger(EthnicsResource.class);

    private static final String ENTITY_NAME = "ethnics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EthnicsRepository ethnicsRepository;

    public EthnicsResource(EthnicsRepository ethnicsRepository) {
        this.ethnicsRepository = ethnicsRepository;
    }

    /**
     * {@code POST  /ethnics} : Create a new ethnics.
     *
     * @param ethnics the ethnics to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ethnics, or with status {@code 400 (Bad Request)} if the ethnics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ethnics")
    public ResponseEntity<Ethnics> createEthnics(@RequestBody Ethnics ethnics) throws URISyntaxException {
        log.debug("REST request to save Ethnics : {}", ethnics);
        if (ethnics.getId() != null) {
            throw new BadRequestAlertException("A new ethnics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ethnics result = ethnicsRepository.save(ethnics);
        return ResponseEntity
            .created(new URI("/api/ethnics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /ethnics/:id} : Updates an existing ethnics.
     *
     * @param id the id of the ethnics to save.
     * @param ethnics the ethnics to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ethnics,
     * or with status {@code 400 (Bad Request)} if the ethnics is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ethnics couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ethnics/{id}")
    public ResponseEntity<Ethnics> updateEthnics(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Ethnics ethnics
    ) throws URISyntaxException {
        log.debug("REST request to update Ethnics : {}, {}", id, ethnics);
        if (ethnics.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ethnics.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ethnicsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ethnics result = ethnicsRepository.save(ethnics);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ethnics.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /ethnics/:id} : Partial updates given fields of an existing ethnics, field will ignore if it is null
     *
     * @param id the id of the ethnics to save.
     * @param ethnics the ethnics to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ethnics,
     * or with status {@code 400 (Bad Request)} if the ethnics is not valid,
     * or with status {@code 404 (Not Found)} if the ethnics is not found,
     * or with status {@code 500 (Internal Server Error)} if the ethnics couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ethnics/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Ethnics> partialUpdateEthnics(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Ethnics ethnics
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ethnics partially : {}, {}", id, ethnics);
        if (ethnics.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ethnics.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ethnicsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ethnics> result = ethnicsRepository
            .findById(ethnics.getId())
            .map(existingEthnics -> {
                if (ethnics.getName() != null) {
                    existingEthnics.setName(ethnics.getName());
                }

                return existingEthnics;
            })
            .map(ethnicsRepository::save);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ethnics.getId()));
    }

    /**
     * {@code GET  /ethnics} : get all the ethnics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ethnics in body.
     */
    @GetMapping("/ethnics")
    public List<Ethnics> getAllEthnics() {
        log.debug("REST request to get all Ethnics");
        return ethnicsRepository.findAll();
    }

    /**
     * {@code GET  /ethnics/:id} : get the "id" ethnics.
     *
     * @param id the id of the ethnics to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ethnics, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ethnics/{id}")
    public ResponseEntity<Ethnics> getEthnics(@PathVariable String id) {
        log.debug("REST request to get Ethnics : {}", id);
        Optional<Ethnics> ethnics = ethnicsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ethnics);
    }

    /**
     * {@code DELETE  /ethnics/:id} : delete the "id" ethnics.
     *
     * @param id the id of the ethnics to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ethnics/{id}")
    public ResponseEntity<Void> deleteEthnics(@PathVariable String id) {
        log.debug("REST request to delete Ethnics : {}", id);
        ethnicsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
