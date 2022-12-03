package com.hust.hustpital.service;

import com.hust.hustpital.domain.PhongKham;
import com.hust.hustpital.repository.PhongKhamRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link PhongKham}.
 */
@Service
public class PhongKhamService {

    private final Logger log = LoggerFactory.getLogger(PhongKhamService.class);

    private final PhongKhamRepository phongKhamRepository;

    public PhongKhamService(PhongKhamRepository phongKhamRepository) {
        this.phongKhamRepository = phongKhamRepository;
    }

    /**
     * Save a phongKham.
     *
     * @param phongKham the entity to save.
     * @return the persisted entity.
     */
    public PhongKham save(PhongKham phongKham) {
        log.debug("Request to save PhongKham : {}", phongKham);
        return phongKhamRepository.save(phongKham);
    }

    /**
     * Update a phongKham.
     *
     * @param phongKham the entity to save.
     * @return the persisted entity.
     */
    public PhongKham update(PhongKham phongKham) {
        log.debug("Request to update PhongKham : {}", phongKham);
        return phongKhamRepository.save(phongKham);
    }

    /**
     * Partially update a phongKham.
     *
     * @param phongKham the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PhongKham> partialUpdate(PhongKham phongKham) {
        log.debug("Request to partially update PhongKham : {}", phongKham);

        return phongKhamRepository
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
    }

    /**
     * Get all the phongKhams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<PhongKham> findAll(Pageable pageable) {
        log.debug("Request to get all PhongKhams");
        return phongKhamRepository.findAll(pageable);
    }

    /**
     * Get all the phongKhams with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PhongKham> findAllWithEagerRelationships(Pageable pageable) {
        return phongKhamRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one phongKham by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PhongKham> findOne(String id) {
        log.debug("Request to get PhongKham : {}", id);
        return phongKhamRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the phongKham by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete PhongKham : {}", id);
        phongKhamRepository.deleteById(id);
    }
}
