package com.hust.hustpital.web.rest;

import com.hust.hustpital.domain.LichLamViec;
import com.hust.hustpital.repository.LichLamViecRepository;
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
 * REST controller for managing {@link com.hust.hustpital.domain.LichLamViec}.
 */
@RestController
@RequestMapping("/api")
public class LichLamViecResource {

    private final Logger log = LoggerFactory.getLogger(LichLamViecResource.class);

    private static final String ENTITY_NAME = "lichLamViec";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LichLamViecRepository lichLamViecRepository;

    public LichLamViecResource(LichLamViecRepository lichLamViecRepository) {
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
        LichLamViec result = lichLamViecRepository.save(lichLamViec);
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

        LichLamViec result = lichLamViecRepository.save(lichLamViec);
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

        Optional<LichLamViec> result = lichLamViecRepository
            .findById(lichLamViec.getId())
            .map(existingLichLamViec -> {
                if (lichLamViec.getThu() != null) {
                    existingLichLamViec.setThu(lichLamViec.getThu());
                }
                if (lichLamViec.getThoiGian() != null) {
                    existingLichLamViec.setThoiGian(lichLamViec.getThoiGian());
                }

                return existingLichLamViec;
            })
            .map(lichLamViecRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lichLamViec.getId())
        );
    }

    /**
     * {@code GET  /lich-lam-viecs} : get all the lichLamViecs.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lichLamViecs in body.
     */
    @GetMapping("/lich-lam-viecs")
    public List<LichLamViec> getAllLichLamViecs(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all LichLamViecs");
        if (eagerload) {
            return lichLamViecRepository.findAllWithEagerRelationships();
        } else {
            return lichLamViecRepository.findAll();
        }
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
        Optional<LichLamViec> lichLamViec = lichLamViecRepository.findOneWithEagerRelationships(id);
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
        lichLamViecRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
