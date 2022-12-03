package com.hust.hustpital.web.rest;

import com.hust.hustpital.domain.Wards;
import com.hust.hustpital.repository.WardsRepository;
import com.hust.hustpital.service.WardsService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.hust.hustpital.domain.Wards}.
 */
@RestController
@RequestMapping("/api")
public class WardsResource {

    private final Logger log = LoggerFactory.getLogger(WardsResource.class);

    private static final String ENTITY_NAME = "wards";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WardsService wardsService;

    private final WardsRepository wardsRepository;

    public WardsResource(WardsService wardsService, WardsRepository wardsRepository) {
        this.wardsService = wardsService;
        this.wardsRepository = wardsRepository;
    }

    /**
     * {@code POST  /wards} : Create a new wards.
     *
     * @param wards the wards to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wards, or with status {@code 400 (Bad Request)} if the wards has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wards")
    public ResponseEntity<Wards> createWards(@Valid @RequestBody Wards wards) throws URISyntaxException {
        log.debug("REST request to save Wards : {}", wards);
        if (wards.getId() != null) {
            throw new BadRequestAlertException("A new wards cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Wards result = wardsService.save(wards);
        return ResponseEntity
            .created(new URI("/api/wards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /wards/:id} : Updates an existing wards.
     *
     * @param id the id of the wards to save.
     * @param wards the wards to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wards,
     * or with status {@code 400 (Bad Request)} if the wards is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wards couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wards/{id}")
    public ResponseEntity<Wards> updateWards(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Wards wards
    ) throws URISyntaxException {
        log.debug("REST request to update Wards : {}, {}", id, wards);
        if (wards.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wards.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wardsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Wards result = wardsService.update(wards);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wards.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /wards/:id} : Partial updates given fields of an existing wards, field will ignore if it is null
     *
     * @param id the id of the wards to save.
     * @param wards the wards to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wards,
     * or with status {@code 400 (Bad Request)} if the wards is not valid,
     * or with status {@code 404 (Not Found)} if the wards is not found,
     * or with status {@code 500 (Internal Server Error)} if the wards couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/wards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Wards> partialUpdateWards(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Wards wards
    ) throws URISyntaxException {
        log.debug("REST request to partial update Wards partially : {}, {}", id, wards);
        if (wards.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, wards.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!wardsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Wards> result = wardsService.partialUpdate(wards);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wards.getId()));
    }

    /**
     * {@code GET  /wards} : get all the wards.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wards in body.
     */
    @GetMapping("/wards")
    public ResponseEntity<List<Wards>> getAllWards(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Wards");
        Page<Wards> page;
        if (eagerload) {
            page = wardsService.findAllWithEagerRelationships(pageable);
        } else {
            page = wardsService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /wards/:id} : get the "id" wards.
     *
     * @param id the id of the wards to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wards, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/wards/{id}")
    public ResponseEntity<Wards> getWards(@PathVariable String id) {
        log.debug("REST request to get Wards : {}", id);
        Optional<Wards> wards = wardsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wards);
    }

    /**
     * {@code DELETE  /wards/:id} : delete the "id" wards.
     *
     * @param id the id of the wards to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wards/{id}")
    public ResponseEntity<Void> deleteWards(@PathVariable String id) {
        log.debug("REST request to delete Wards : {}", id);
        wardsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
