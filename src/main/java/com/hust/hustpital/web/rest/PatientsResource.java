package com.hust.hustpital.web.rest;

import com.hust.hustpital.domain.Patients;
import com.hust.hustpital.repository.PatientsRepository;
import com.hust.hustpital.service.PatientsService;
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
 * REST controller for managing {@link com.hust.hustpital.domain.Patients}.
 */
@RestController
@RequestMapping("/api")
public class PatientsResource {

    private final Logger log = LoggerFactory.getLogger(PatientsResource.class);

    private static final String ENTITY_NAME = "patients";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientsService patientsService;

    private final PatientsRepository patientsRepository;

    public PatientsResource(PatientsService patientsService, PatientsRepository patientsRepository) {
        this.patientsService = patientsService;
        this.patientsRepository = patientsRepository;
    }

    /**
     * {@code POST  /patients} : Create a new patients.
     *
     * @param patients the patients to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patients, or with status {@code 400 (Bad Request)} if the patients has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patients")
    public ResponseEntity<Patients> createPatients(@RequestBody Patients patients) throws URISyntaxException {
        log.debug("REST request to save Patients : {}", patients);
        if (patients.getId() != null) {
            throw new BadRequestAlertException("A new patients cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Patients result = patientsService.save(patients);
        return ResponseEntity
            .created(new URI("/api/patients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /patients/:id} : Updates an existing patients.
     *
     * @param id the id of the patients to save.
     * @param patients the patients to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patients,
     * or with status {@code 400 (Bad Request)} if the patients is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patients couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patients/{id}")
    public ResponseEntity<Patients> updatePatients(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Patients patients
    ) throws URISyntaxException {
        log.debug("REST request to update Patients : {}, {}", id, patients);
        if (patients.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patients.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Patients result = patientsService.update(patients);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patients.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /patients/:id} : Partial updates given fields of an existing patients, field will ignore if it is null
     *
     * @param id the id of the patients to save.
     * @param patients the patients to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patients,
     * or with status {@code 400 (Bad Request)} if the patients is not valid,
     * or with status {@code 404 (Not Found)} if the patients is not found,
     * or with status {@code 500 (Internal Server Error)} if the patients couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/patients/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Patients> partialUpdatePatients(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Patients patients
    ) throws URISyntaxException {
        log.debug("REST request to partial update Patients partially : {}, {}", id, patients);
        if (patients.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patients.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Patients> result = patientsService.partialUpdate(patients);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patients.getId())
        );
    }

    /**
     * {@code GET  /patients} : get all the patients.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patients in body.
     */
    @GetMapping("/patients")
    public ResponseEntity<List<Patients>> getAllPatients(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Patients");
        Page<Patients> page;
        if (eagerload) {
            page = patientsService.findAllWithEagerRelationships(pageable);
        } else {
            page = patientsService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /patients/:id} : get the "id" patients.
     *
     * @param id the id of the patients to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patients, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patients/{id}")
    public ResponseEntity<Patients> getPatients(@PathVariable String id) {
        log.debug("REST request to get Patients : {}", id);
        Optional<Patients> patients = patientsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patients);
    }

    /**
     * {@code DELETE  /patients/:id} : delete the "id" patients.
     *
     * @param id the id of the patients to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatients(@PathVariable String id) {
        log.debug("REST request to delete Patients : {}", id);
        patientsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
