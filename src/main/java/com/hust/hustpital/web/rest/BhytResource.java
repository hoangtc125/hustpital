package com.hust.hustpital.web.rest;

import com.hust.hustpital.domain.Bhyt;
import com.hust.hustpital.repository.BhytRepository;
import com.hust.hustpital.service.BhytService;
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
 * REST controller for managing {@link com.hust.hustpital.domain.Bhyt}.
 */
@RestController
@RequestMapping("/api")
public class BhytResource {

    private final Logger log = LoggerFactory.getLogger(BhytResource.class);

    private static final String ENTITY_NAME = "bhyt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BhytService bhytService;

    private final BhytRepository bhytRepository;

    public BhytResource(BhytService bhytService, BhytRepository bhytRepository) {
        this.bhytService = bhytService;
        this.bhytRepository = bhytRepository;
    }

    /**
     * {@code POST  /bhyts} : Create a new bhyt.
     *
     * @param bhyt the bhyt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bhyt, or with status {@code 400 (Bad Request)} if the bhyt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bhyts")
    public ResponseEntity<Bhyt> createBhyt(@RequestBody Bhyt bhyt) throws URISyntaxException {
        log.debug("REST request to save Bhyt : {}", bhyt);
        if (bhyt.getId() != null) {
            throw new BadRequestAlertException("A new bhyt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bhyt result = bhytService.save(bhyt);
        return ResponseEntity
            .created(new URI("/api/bhyts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /bhyts/:id} : Updates an existing bhyt.
     *
     * @param id the id of the bhyt to save.
     * @param bhyt the bhyt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bhyt,
     * or with status {@code 400 (Bad Request)} if the bhyt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bhyt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bhyts/{id}")
    public ResponseEntity<Bhyt> updateBhyt(@PathVariable(value = "id", required = false) final String id, @RequestBody Bhyt bhyt)
        throws URISyntaxException {
        log.debug("REST request to update Bhyt : {}, {}", id, bhyt);
        if (bhyt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bhyt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bhytRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bhyt result = bhytService.update(bhyt);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bhyt.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /bhyts/:id} : Partial updates given fields of an existing bhyt, field will ignore if it is null
     *
     * @param id the id of the bhyt to save.
     * @param bhyt the bhyt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bhyt,
     * or with status {@code 400 (Bad Request)} if the bhyt is not valid,
     * or with status {@code 404 (Not Found)} if the bhyt is not found,
     * or with status {@code 500 (Internal Server Error)} if the bhyt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bhyts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bhyt> partialUpdateBhyt(@PathVariable(value = "id", required = false) final String id, @RequestBody Bhyt bhyt)
        throws URISyntaxException {
        log.debug("REST request to partial update Bhyt partially : {}, {}", id, bhyt);
        if (bhyt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bhyt.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bhytRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bhyt> result = bhytService.partialUpdate(bhyt);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bhyt.getId()));
    }

    /**
     * {@code GET  /bhyts} : get all the bhyts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bhyts in body.
     */
    @GetMapping("/bhyts")
    public ResponseEntity<List<Bhyt>> getAllBhyts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Bhyts");
        Page<Bhyt> page = bhytService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bhyts/:id} : get the "id" bhyt.
     *
     * @param id the id of the bhyt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bhyt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bhyts/{id}")
    public ResponseEntity<Bhyt> getBhyt(@PathVariable String id) {
        log.debug("REST request to get Bhyt : {}", id);
        Optional<Bhyt> bhyt = bhytService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bhyt);
    }

    /**
     * {@code DELETE  /bhyts/:id} : delete the "id" bhyt.
     *
     * @param id the id of the bhyt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bhyts/{id}")
    public ResponseEntity<Void> deleteBhyt(@PathVariable String id) {
        log.debug("REST request to delete Bhyt : {}", id);
        bhytService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
