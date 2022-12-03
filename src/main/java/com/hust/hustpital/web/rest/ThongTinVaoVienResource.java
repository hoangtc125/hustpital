package com.hust.hustpital.web.rest;

import com.hust.hustpital.domain.ThongTinVaoVien;
import com.hust.hustpital.repository.ThongTinVaoVienRepository;
import com.hust.hustpital.service.ThongTinVaoVienService;
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
 * REST controller for managing {@link com.hust.hustpital.domain.ThongTinVaoVien}.
 */
@RestController
@RequestMapping("/api")
public class ThongTinVaoVienResource {

    private final Logger log = LoggerFactory.getLogger(ThongTinVaoVienResource.class);

    private static final String ENTITY_NAME = "thongTinVaoVien";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThongTinVaoVienService thongTinVaoVienService;

    private final ThongTinVaoVienRepository thongTinVaoVienRepository;

    public ThongTinVaoVienResource(ThongTinVaoVienService thongTinVaoVienService, ThongTinVaoVienRepository thongTinVaoVienRepository) {
        this.thongTinVaoVienService = thongTinVaoVienService;
        this.thongTinVaoVienRepository = thongTinVaoVienRepository;
    }

    /**
     * {@code POST  /thong-tin-vao-viens} : Create a new thongTinVaoVien.
     *
     * @param thongTinVaoVien the thongTinVaoVien to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thongTinVaoVien, or with status {@code 400 (Bad Request)} if the thongTinVaoVien has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thong-tin-vao-viens")
    public ResponseEntity<ThongTinVaoVien> createThongTinVaoVien(@Valid @RequestBody ThongTinVaoVien thongTinVaoVien)
        throws URISyntaxException {
        log.debug("REST request to save ThongTinVaoVien : {}", thongTinVaoVien);
        if (thongTinVaoVien.getId() != null) {
            throw new BadRequestAlertException("A new thongTinVaoVien cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThongTinVaoVien result = thongTinVaoVienService.save(thongTinVaoVien);
        return ResponseEntity
            .created(new URI("/api/thong-tin-vao-viens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /thong-tin-vao-viens/:id} : Updates an existing thongTinVaoVien.
     *
     * @param id the id of the thongTinVaoVien to save.
     * @param thongTinVaoVien the thongTinVaoVien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thongTinVaoVien,
     * or with status {@code 400 (Bad Request)} if the thongTinVaoVien is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thongTinVaoVien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/thong-tin-vao-viens/{id}")
    public ResponseEntity<ThongTinVaoVien> updateThongTinVaoVien(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ThongTinVaoVien thongTinVaoVien
    ) throws URISyntaxException {
        log.debug("REST request to update ThongTinVaoVien : {}, {}", id, thongTinVaoVien);
        if (thongTinVaoVien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thongTinVaoVien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thongTinVaoVienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ThongTinVaoVien result = thongTinVaoVienService.update(thongTinVaoVien);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thongTinVaoVien.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /thong-tin-vao-viens/:id} : Partial updates given fields of an existing thongTinVaoVien, field will ignore if it is null
     *
     * @param id the id of the thongTinVaoVien to save.
     * @param thongTinVaoVien the thongTinVaoVien to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thongTinVaoVien,
     * or with status {@code 400 (Bad Request)} if the thongTinVaoVien is not valid,
     * or with status {@code 404 (Not Found)} if the thongTinVaoVien is not found,
     * or with status {@code 500 (Internal Server Error)} if the thongTinVaoVien couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/thong-tin-vao-viens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ThongTinVaoVien> partialUpdateThongTinVaoVien(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ThongTinVaoVien thongTinVaoVien
    ) throws URISyntaxException {
        log.debug("REST request to partial update ThongTinVaoVien partially : {}, {}", id, thongTinVaoVien);
        if (thongTinVaoVien.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thongTinVaoVien.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thongTinVaoVienRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ThongTinVaoVien> result = thongTinVaoVienService.partialUpdate(thongTinVaoVien);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thongTinVaoVien.getId())
        );
    }

    /**
     * {@code GET  /thong-tin-vao-viens} : get all the thongTinVaoViens.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thongTinVaoViens in body.
     */
    @GetMapping("/thong-tin-vao-viens")
    public ResponseEntity<List<ThongTinVaoVien>> getAllThongTinVaoViens(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of ThongTinVaoViens");
        Page<ThongTinVaoVien> page;
        if (eagerload) {
            page = thongTinVaoVienService.findAllWithEagerRelationships(pageable);
        } else {
            page = thongTinVaoVienService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /thong-tin-vao-viens/:id} : get the "id" thongTinVaoVien.
     *
     * @param id the id of the thongTinVaoVien to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thongTinVaoVien, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thong-tin-vao-viens/{id}")
    public ResponseEntity<ThongTinVaoVien> getThongTinVaoVien(@PathVariable String id) {
        log.debug("REST request to get ThongTinVaoVien : {}", id);
        Optional<ThongTinVaoVien> thongTinVaoVien = thongTinVaoVienService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thongTinVaoVien);
    }

    /**
     * {@code DELETE  /thong-tin-vao-viens/:id} : delete the "id" thongTinVaoVien.
     *
     * @param id the id of the thongTinVaoVien to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/thong-tin-vao-viens/{id}")
    public ResponseEntity<Void> deleteThongTinVaoVien(@PathVariable String id) {
        log.debug("REST request to delete ThongTinVaoVien : {}", id);
        thongTinVaoVienService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
