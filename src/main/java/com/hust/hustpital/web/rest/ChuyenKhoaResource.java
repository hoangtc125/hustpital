package com.hust.hustpital.web.rest;

import com.hust.hustpital.domain.ChuyenKhoa;
import com.hust.hustpital.repository.ChuyenKhoaRepository;
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
 * REST controller for managing {@link com.hust.hustpital.domain.ChuyenKhoa}.
 */
@RestController
@RequestMapping("/api")
public class ChuyenKhoaResource {

    private final Logger log = LoggerFactory.getLogger(ChuyenKhoaResource.class);

    private static final String ENTITY_NAME = "chuyenKhoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChuyenKhoaRepository chuyenKhoaRepository;

    public ChuyenKhoaResource(ChuyenKhoaRepository chuyenKhoaRepository) {
        this.chuyenKhoaRepository = chuyenKhoaRepository;
    }

    /**
     * {@code POST  /chuyen-khoas} : Create a new chuyenKhoa.
     *
     * @param chuyenKhoa the chuyenKhoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chuyenKhoa, or with status {@code 400 (Bad Request)} if the chuyenKhoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chuyen-khoas")
    public ResponseEntity<ChuyenKhoa> createChuyenKhoa(@RequestBody ChuyenKhoa chuyenKhoa) throws URISyntaxException {
        log.debug("REST request to save ChuyenKhoa : {}", chuyenKhoa);
        if (chuyenKhoa.getId() != null) {
            throw new BadRequestAlertException("A new chuyenKhoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChuyenKhoa result = chuyenKhoaRepository.save(chuyenKhoa);
        return ResponseEntity
            .created(new URI("/api/chuyen-khoas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /chuyen-khoas/:id} : Updates an existing chuyenKhoa.
     *
     * @param id the id of the chuyenKhoa to save.
     * @param chuyenKhoa the chuyenKhoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chuyenKhoa,
     * or with status {@code 400 (Bad Request)} if the chuyenKhoa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chuyenKhoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chuyen-khoas/{id}")
    public ResponseEntity<ChuyenKhoa> updateChuyenKhoa(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ChuyenKhoa chuyenKhoa
    ) throws URISyntaxException {
        log.debug("REST request to update ChuyenKhoa : {}, {}", id, chuyenKhoa);
        if (chuyenKhoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chuyenKhoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chuyenKhoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChuyenKhoa result = chuyenKhoaRepository.save(chuyenKhoa);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chuyenKhoa.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /chuyen-khoas/:id} : Partial updates given fields of an existing chuyenKhoa, field will ignore if it is null
     *
     * @param id the id of the chuyenKhoa to save.
     * @param chuyenKhoa the chuyenKhoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chuyenKhoa,
     * or with status {@code 400 (Bad Request)} if the chuyenKhoa is not valid,
     * or with status {@code 404 (Not Found)} if the chuyenKhoa is not found,
     * or with status {@code 500 (Internal Server Error)} if the chuyenKhoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chuyen-khoas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChuyenKhoa> partialUpdateChuyenKhoa(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody ChuyenKhoa chuyenKhoa
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChuyenKhoa partially : {}, {}", id, chuyenKhoa);
        if (chuyenKhoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chuyenKhoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chuyenKhoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChuyenKhoa> result = chuyenKhoaRepository
            .findById(chuyenKhoa.getId())
            .map(existingChuyenKhoa -> {
                if (chuyenKhoa.getCode() != null) {
                    existingChuyenKhoa.setCode(chuyenKhoa.getCode());
                }
                if (chuyenKhoa.getName() != null) {
                    existingChuyenKhoa.setName(chuyenKhoa.getName());
                }

                return existingChuyenKhoa;
            })
            .map(chuyenKhoaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chuyenKhoa.getId())
        );
    }

    /**
     * {@code GET  /chuyen-khoas} : get all the chuyenKhoas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chuyenKhoas in body.
     */
    @GetMapping("/chuyen-khoas")
    public List<ChuyenKhoa> getAllChuyenKhoas() {
        log.debug("REST request to get all ChuyenKhoas");
        return chuyenKhoaRepository.findAll();
    }

    /**
     * {@code GET  /chuyen-khoas/:id} : get the "id" chuyenKhoa.
     *
     * @param id the id of the chuyenKhoa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chuyenKhoa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chuyen-khoas/{id}")
    public ResponseEntity<ChuyenKhoa> getChuyenKhoa(@PathVariable String id) {
        log.debug("REST request to get ChuyenKhoa : {}", id);
        Optional<ChuyenKhoa> chuyenKhoa = chuyenKhoaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(chuyenKhoa);
    }

    /**
     * {@code DELETE  /chuyen-khoas/:id} : delete the "id" chuyenKhoa.
     *
     * @param id the id of the chuyenKhoa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chuyen-khoas/{id}")
    public ResponseEntity<Void> deleteChuyenKhoa(@PathVariable String id) {
        log.debug("REST request to delete ChuyenKhoa : {}", id);
        chuyenKhoaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
