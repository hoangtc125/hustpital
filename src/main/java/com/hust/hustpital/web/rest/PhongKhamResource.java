package com.hust.hustpital.web.rest;

import com.hust.hustpital.domain.PhongKham;
import com.hust.hustpital.repository.PhongKhamRepository;
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
 * REST controller for managing {@link com.hust.hustpital.domain.PhongKham}.
 */
@RestController
@RequestMapping("/api")
public class PhongKhamResource {

    private final Logger log = LoggerFactory.getLogger(PhongKhamResource.class);

    private static final String ENTITY_NAME = "phongKham";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhongKhamRepository phongKhamRepository;

    public PhongKhamResource(PhongKhamRepository phongKhamRepository) {
        this.phongKhamRepository = phongKhamRepository;
    }

    /**
     * {@code POST  /phong-khams} : Create a new phongKham.
     *
     * @param phongKham the phongKham to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phongKham, or with status {@code 400 (Bad Request)} if the phongKham has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phong-khams")
    public ResponseEntity<PhongKham> createPhongKham(@Valid @RequestBody PhongKham phongKham) throws URISyntaxException {
        log.debug("REST request to save PhongKham : {}", phongKham);
        if (phongKham.getId() != null) {
            throw new BadRequestAlertException("A new phongKham cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PhongKham result = phongKhamRepository.save(phongKham);
        return ResponseEntity
            .created(new URI("/api/phong-khams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /phong-khams/:id} : Updates an existing phongKham.
     *
     * @param id the id of the phongKham to save.
     * @param phongKham the phongKham to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phongKham,
     * or with status {@code 400 (Bad Request)} if the phongKham is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phongKham couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phong-khams/{id}")
    public ResponseEntity<PhongKham> updatePhongKham(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody PhongKham phongKham
    ) throws URISyntaxException {
        log.debug("REST request to update PhongKham : {}, {}", id, phongKham);
        if (phongKham.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phongKham.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phongKhamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PhongKham result = phongKhamRepository.save(phongKham);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phongKham.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /phong-khams/:id} : Partial updates given fields of an existing phongKham, field will ignore if it is null
     *
     * @param id the id of the phongKham to save.
     * @param phongKham the phongKham to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phongKham,
     * or with status {@code 400 (Bad Request)} if the phongKham is not valid,
     * or with status {@code 404 (Not Found)} if the phongKham is not found,
     * or with status {@code 500 (Internal Server Error)} if the phongKham couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/phong-khams/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PhongKham> partialUpdatePhongKham(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody PhongKham phongKham
    ) throws URISyntaxException {
        log.debug("REST request to partial update PhongKham partially : {}, {}", id, phongKham);
        if (phongKham.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phongKham.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phongKhamRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PhongKham> result = phongKhamRepository
            .findById(phongKham.getId())
            .map(existingPhongKham -> {
                if (phongKham.getCode() != null) {
                    existingPhongKham.setCode(phongKham.getCode());
                }
                if (phongKham.getName() != null) {
                    existingPhongKham.setName(phongKham.getName());
                }

                return existingPhongKham;
            })
            .map(phongKhamRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phongKham.getId())
        );
    }

    /**
     * {@code GET  /phong-khams} : get all the phongKhams.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phongKhams in body.
     */
    @GetMapping("/phong-khams")
    public List<PhongKham> getAllPhongKhams(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all PhongKhams");
        if (eagerload) {
            return phongKhamRepository.findAllWithEagerRelationships();
        } else {
            return phongKhamRepository.findAll();
        }
    }

    /**
     * {@code GET  /phong-khams/:id} : get the "id" phongKham.
     *
     * @param id the id of the phongKham to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phongKham, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phong-khams/{id}")
    public ResponseEntity<PhongKham> getPhongKham(@PathVariable String id) {
        log.debug("REST request to get PhongKham : {}", id);
        Optional<PhongKham> phongKham = phongKhamRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(phongKham);
    }

    /**
     * {@code DELETE  /phong-khams/:id} : delete the "id" phongKham.
     *
     * @param id the id of the phongKham to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phong-khams/{id}")
    public ResponseEntity<Void> deletePhongKham(@PathVariable String id) {
        log.debug("REST request to delete PhongKham : {}", id);
        phongKhamRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
