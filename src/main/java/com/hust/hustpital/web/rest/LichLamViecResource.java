package com.hust.hustpital.web.rest;

import com.hust.hustpital.domain.LichLamViec;
import com.hust.hustpital.repository.LichLamViecRepository;
import com.hust.hustpital.service.LichLamViecService;
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
 * REST controller for managing {@link com.hust.hustpital.domain.LichLamViec}.
 */
@RestController
@RequestMapping("/api")
public class LichLamViecResource {

    private final Logger log = LoggerFactory.getLogger(LichLamViecResource.class);

    private static final String ENTITY_NAME = "lichLamViec";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LichLamViecService lichLamViecService;

    private final LichLamViecRepository lichLamViecRepository;

    public LichLamViecResource(LichLamViecService lichLamViecService, LichLamViecRepository lichLamViecRepository) {
        this.lichLamViecService = lichLamViecService;
        this.lichLamViecRepository = lichLamViecRepository;
    }

    /**
     * {@code POST  /lich-lam-viecs} : Create a new lichLamViec.
     *
     * @param lichLamViec the lichLamViec to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lichLamViec, or with status {@code 400 (Bad Request)} if the lichLamViec has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lich-lam-viecs")
    public ResponseEntity<LichLamViec> createLichLamViec(@Valid @RequestBody LichLamViec lichLamViec) throws URISyntaxException {
        log.debug("REST request to save LichLamViec : {}", lichLamViec);
        if (lichLamViec.getId() != null) {
            throw new BadRequestAlertException("A new lichLamViec cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LichLamViec result = lichLamViecService.save(lichLamViec);
        return ResponseEntity
            .created(new URI("/api/lich-lam-viecs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /lich-lam-viecs/:id} : Updates an existing lichLamViec.
     *
     * @param id the id of the lichLamViec to save.
     * @param lichLamViec the lichLamViec to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lichLamViec,
     * or with status {@code 400 (Bad Request)} if the lichLamViec is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lichLamViec couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lich-lam-viecs/{id}")
    public ResponseEntity<LichLamViec> updateLichLamViec(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody LichLamViec lichLamViec
    ) throws URISyntaxException {
        log.debug("REST request to update LichLamViec : {}, {}", id, lichLamViec);
        if (lichLamViec.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lichLamViec.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lichLamViecRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LichLamViec result = lichLamViecService.update(lichLamViec);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lichLamViec.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /lich-lam-viecs/:id} : Partial updates given fields of an existing lichLamViec, field will ignore if it is null
     *
     * @param id the id of the lichLamViec to save.
     * @param lichLamViec the lichLamViec to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lichLamViec,
     * or with status {@code 400 (Bad Request)} if the lichLamViec is not valid,
     * or with status {@code 404 (Not Found)} if the lichLamViec is not found,
     * or with status {@code 500 (Internal Server Error)} if the lichLamViec couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lich-lam-viecs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LichLamViec> partialUpdateLichLamViec(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody LichLamViec lichLamViec
    ) throws URISyntaxException {
        log.debug("REST request to partial update LichLamViec partially : {}, {}", id, lichLamViec);
        if (lichLamViec.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lichLamViec.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lichLamViecRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LichLamViec> result = lichLamViecService.partialUpdate(lichLamViec);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lichLamViec.getId())
        );
    }

    /**
     * {@code GET  /lich-lam-viecs} : get all the lichLamViecs.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lichLamViecs in body.
     */
    @GetMapping("/lich-lam-viecs")
    public ResponseEntity<List<LichLamViec>> getAllLichLamViecs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of LichLamViecs");
        Page<LichLamViec> page;
        if (eagerload) {
            page = lichLamViecService.findAllWithEagerRelationships(pageable);
        } else {
            page = lichLamViecService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lich-lam-viecs/:id} : get the "id" lichLamViec.
     *
     * @param id the id of the lichLamViec to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lichLamViec, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lich-lam-viecs/{id}")
    public ResponseEntity<LichLamViec> getLichLamViec(@PathVariable String id) {
        log.debug("REST request to get LichLamViec : {}", id);
        Optional<LichLamViec> lichLamViec = lichLamViecService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lichLamViec);
    }

    /**
     * {@code DELETE  /lich-lam-viecs/:id} : delete the "id" lichLamViec.
     *
     * @param id the id of the lichLamViec to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lich-lam-viecs/{id}")
    public ResponseEntity<Void> deleteLichLamViec(@PathVariable String id) {
        log.debug("REST request to delete LichLamViec : {}", id);
        lichLamViecService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
