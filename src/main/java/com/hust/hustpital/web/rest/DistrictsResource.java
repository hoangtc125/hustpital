package com.hust.hustpital.web.rest;

import com.hust.hustpital.domain.Districts;
import com.hust.hustpital.repository.DistrictsRepository;
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
 * REST controller for managing {@link com.hust.hustpital.domain.Districts}.
 */
@RestController
@RequestMapping("/api")
public class DistrictsResource {

    private final Logger log = LoggerFactory.getLogger(DistrictsResource.class);

    private static final String ENTITY_NAME = "districts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistrictsRepository districtsRepository;

    public DistrictsResource(DistrictsRepository districtsRepository) {
        this.districtsRepository = districtsRepository;
    }

    /**
     * {@code POST  /districts} : Create a new districts.
     *
     * @param districts the districts to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new districts, or with status {@code 400 (Bad Request)} if the districts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/districts")
    public ResponseEntity<Districts> createDistricts(@Valid @RequestBody Districts districts) throws URISyntaxException {
        log.debug("REST request to save Districts : {}", districts);
        if (districts.getId() != null) {
            throw new BadRequestAlertException("A new districts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Districts result = districtsRepository.save(districts);
        return ResponseEntity
            .created(new URI("/api/districts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /districts/:id} : Updates an existing districts.
     *
     * @param id the id of the districts to save.
     * @param districts the districts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districts,
     * or with status {@code 400 (Bad Request)} if the districts is not valid,
     * or with status {@code 500 (Internal Server Error)} if the districts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/districts/{id}")
    public ResponseEntity<Districts> updateDistricts(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Districts districts
    ) throws URISyntaxException {
        log.debug("REST request to update Districts : {}, {}", id, districts);
        if (districts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, districts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!districtsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Districts result = districtsRepository.save(districts);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, districts.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /districts/:id} : Partial updates given fields of an existing districts, field will ignore if it is null
     *
     * @param id the id of the districts to save.
     * @param districts the districts to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districts,
     * or with status {@code 400 (Bad Request)} if the districts is not valid,
     * or with status {@code 404 (Not Found)} if the districts is not found,
     * or with status {@code 500 (Internal Server Error)} if the districts couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/districts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Districts> partialUpdateDistricts(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Districts districts
    ) throws URISyntaxException {
        log.debug("REST request to partial update Districts partially : {}, {}", id, districts);
        if (districts.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, districts.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!districtsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Districts> result = districtsRepository
            .findById(districts.getId())
            .map(existingDistricts -> {
                if (districts.getName() != null) {
                    existingDistricts.setName(districts.getName());
                }

                return existingDistricts;
            })
            .map(districtsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, districts.getId())
        );
    }

    /**
     * {@code GET  /districts} : get all the districts.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of districts in body.
     */
    @GetMapping("/districts")
    public List<Districts> getAllDistricts(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Districts");
        if (eagerload) {
            return districtsRepository.findAllWithEagerRelationships();
        } else {
            return districtsRepository.findAll();
        }
    }

    /**
     * {@code GET  /districts/:id} : get the "id" districts.
     *
     * @param id the id of the districts to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the districts, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/districts/{id}")
    public ResponseEntity<Districts> getDistricts(@PathVariable String id) {
        log.debug("REST request to get Districts : {}", id);
        Optional<Districts> districts = districtsRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(districts);
    }

    /**
     * {@code DELETE  /districts/:id} : delete the "id" districts.
     *
     * @param id the id of the districts to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/districts/{id}")
    public ResponseEntity<Void> deleteDistricts(@PathVariable String id) {
        log.debug("REST request to delete Districts : {}", id);
        districtsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
