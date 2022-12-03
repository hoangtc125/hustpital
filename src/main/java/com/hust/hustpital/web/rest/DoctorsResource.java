package com.hust.hustpital.web.rest;

import com.hust.hustpital.domain.Doctors;
import com.hust.hustpital.repository.DoctorsRepository;
import com.hust.hustpital.service.DoctorsService;
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
 * REST controller for managing {@link com.hust.hustpital.domain.Doctors}.
 */
@RestController
@RequestMapping("/api")
public class DoctorsResource {

    private final Logger log = LoggerFactory.getLogger(DoctorsResource.class);

    private static final String ENTITY_NAME = "doctors";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoctorsService doctorsService;

    private final DoctorsRepository doctorsRepository;

    public DoctorsResource(DoctorsService doctorsService, DoctorsRepository doctorsRepository) {
        this.doctorsService = doctorsService;
        this.doctorsRepository = doctorsRepository;
    }

    /**
     * {@code POST  /doctors} : Create a new doctors.
     *
     * @param doctors the doctors to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doctors, or with status {@code 400 (Bad Request)} if the doctors has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doctors")
    public ResponseEntity<Doctors> createDoctors(@Valid @RequestBody Doctors doctors) throws URISyntaxException {
        log.debug("REST request to save Doctors : {}", doctors);
        if (doctors.getId() != null) {
            throw new BadRequestAlertException("A new doctors cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Doctors result = doctorsService.save(doctors);
        return ResponseEntity
            .created(new URI("/api/doctors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /doctors/:id} : Updates an existing doctors.
     *
     * @param id the id of the doctors to save.
     * @param doctors the doctors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctors,
     * or with status {@code 400 (Bad Request)} if the doctors is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doctors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doctors/{id}")
    public ResponseEntity<Doctors> updateDoctors(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Doctors doctors
    ) throws URISyntaxException {
        log.debug("REST request to update Doctors : {}, {}", id, doctors);
        if (doctors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doctors.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doctorsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Doctors result = doctorsService.update(doctors);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctors.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /doctors/:id} : Partial updates given fields of an existing doctors, field will ignore if it is null
     *
     * @param id the id of the doctors to save.
     * @param doctors the doctors to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctors,
     * or with status {@code 400 (Bad Request)} if the doctors is not valid,
     * or with status {@code 404 (Not Found)} if the doctors is not found,
     * or with status {@code 500 (Internal Server Error)} if the doctors couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/doctors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Doctors> partialUpdateDoctors(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Doctors doctors
    ) throws URISyntaxException {
        log.debug("REST request to partial update Doctors partially : {}, {}", id, doctors);
        if (doctors.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, doctors.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!doctorsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Doctors> result = doctorsService.partialUpdate(doctors);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, doctors.getId()));
    }

    /**
     * {@code GET  /doctors} : get all the doctors.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doctors in body.
     */
    @GetMapping("/doctors")
    public ResponseEntity<List<Doctors>> getAllDoctors(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Doctors");
        Page<Doctors> page;
        if (eagerload) {
            page = doctorsService.findAllWithEagerRelationships(pageable);
        } else {
            page = doctorsService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /doctors/:id} : get the "id" doctors.
     *
     * @param id the id of the doctors to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doctors, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doctors/{id}")
    public ResponseEntity<Doctors> getDoctors(@PathVariable String id) {
        log.debug("REST request to get Doctors : {}", id);
        Optional<Doctors> doctors = doctorsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doctors);
    }

    /**
     * {@code DELETE  /doctors/:id} : delete the "id" doctors.
     *
     * @param id the id of the doctors to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Void> deleteDoctors(@PathVariable String id) {
        log.debug("REST request to delete Doctors : {}", id);
        doctorsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
