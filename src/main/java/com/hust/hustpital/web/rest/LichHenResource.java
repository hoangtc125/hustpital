package com.hust.hustpital.web.rest;

import com.hust.hustpital.domain.LichHen;
import com.hust.hustpital.repository.LichHenRepository;
import com.hust.hustpital.service.LichHenService;
import com.hust.hustpital.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.hust.hustpital.domain.LichHen}.
 */
@RestController
@RequestMapping("/api")
public class LichHenResource {

    private final Logger log = LoggerFactory.getLogger(LichHenResource.class);

    private static final String ENTITY_NAME = "lichHen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LichHenService lichHenService;

    private final LichHenRepository lichHenRepository;

    public LichHenResource(LichHenService lichHenService, LichHenRepository lichHenRepository) {
        this.lichHenService = lichHenService;
        this.lichHenRepository = lichHenRepository;
    }

    /**
     * {@code POST  /lich-hens} : Create a new lichHen.
     *
     * @param lichHen the lichHen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lichHen, or with status {@code 400 (Bad Request)} if the lichHen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lich-hens")
    public ResponseEntity<LichHen> createLichHen(@RequestBody LichHen lichHen) throws URISyntaxException {
        log.debug("REST request to save LichHen : {}", lichHen);
        if (lichHen.getId() != null) {
            throw new BadRequestAlertException("A new lichHen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LichHen result = lichHenService.save(lichHen);
        return ResponseEntity
            .created(new URI("/api/lich-hens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /lich-hens/:id} : Updates an existing lichHen.
     *
     * @param id the id of the lichHen to save.
     * @param lichHen the lichHen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lichHen,
     * or with status {@code 400 (Bad Request)} if the lichHen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lichHen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lich-hens/{id}")
    public ResponseEntity<LichHen> updateLichHen(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody LichHen lichHen
    ) throws URISyntaxException {
        log.debug("REST request to update LichHen : {}, {}", id, lichHen);
        if (lichHen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lichHen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lichHenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LichHen result = lichHenService.update(lichHen);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lichHen.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /lich-hens/:id} : Partial updates given fields of an existing lichHen, field will ignore if it is null
     *
     * @param id the id of the lichHen to save.
     * @param lichHen the lichHen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lichHen,
     * or with status {@code 400 (Bad Request)} if the lichHen is not valid,
     * or with status {@code 404 (Not Found)} if the lichHen is not found,
     * or with status {@code 500 (Internal Server Error)} if the lichHen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/lich-hens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LichHen> partialUpdateLichHen(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody LichHen lichHen
    ) throws URISyntaxException {
        log.debug("REST request to partial update LichHen partially : {}, {}", id, lichHen);
        if (lichHen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, lichHen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!lichHenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LichHen> result = lichHenService.partialUpdate(lichHen);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lichHen.getId()));
    }

    /**
     * {@code GET  /lich-hens} : get all the lichHens.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lichHens in body.
     */
    @GetMapping("/lich-hens")
    public ResponseEntity<List<LichHen>> getAllLichHens(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of LichHens");
        Page<LichHen> page;
        if (eagerload) {
            page = lichHenService.findAllWithEagerRelationships(pageable);
        } else {
            page = lichHenService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lich-hens/:id} : get the "id" lichHen.
     *
     * @param id the id of the lichHen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lichHen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lich-hens/{id}")
    public ResponseEntity<LichHen> getLichHen(@PathVariable String id) {
        log.debug("REST request to get LichHen : {}", id);
        Optional<LichHen> lichHen = lichHenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lichHen);
    }

    /**
     * {@code DELETE  /lich-hens/:id} : delete the "id" lichHen.
     *
     * @param id the id of the lichHen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lich-hens/{id}")
    public ResponseEntity<Void> deleteLichHen(@PathVariable String id) {
        log.debug("REST request to delete LichHen : {}", id);
        lichHenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
