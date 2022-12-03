package com.hust.hustpital.service;

import com.hust.hustpital.domain.LichLamViec;
import com.hust.hustpital.repository.LichLamViecRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link LichLamViec}.
 */
@Service
public class LichLamViecService {

    private final Logger log = LoggerFactory.getLogger(LichLamViecService.class);

    private final LichLamViecRepository lichLamViecRepository;

    public LichLamViecService(LichLamViecRepository lichLamViecRepository) {
        this.lichLamViecRepository = lichLamViecRepository;
    }

    /**
     * Save a lichLamViec.
     *
     * @param lichLamViec the entity to save.
     * @return the persisted entity.
     */
    public LichLamViec save(LichLamViec lichLamViec) {
        log.debug("Request to save LichLamViec : {}", lichLamViec);
        return lichLamViecRepository.save(lichLamViec);
    }

    /**
     * Update a lichLamViec.
     *
     * @param lichLamViec the entity to save.
     * @return the persisted entity.
     */
    public LichLamViec update(LichLamViec lichLamViec) {
        log.debug("Request to update LichLamViec : {}", lichLamViec);
        return lichLamViecRepository.save(lichLamViec);
    }

    /**
     * Partially update a lichLamViec.
     *
     * @param lichLamViec the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LichLamViec> partialUpdate(LichLamViec lichLamViec) {
        log.debug("Request to partially update LichLamViec : {}", lichLamViec);

        return lichLamViecRepository
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
    }

    /**
     * Get all the lichLamViecs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<LichLamViec> findAll(Pageable pageable) {
        log.debug("Request to get all LichLamViecs");
        return lichLamViecRepository.findAll(pageable);
    }

    /**
     * Get all the lichLamViecs with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<LichLamViec> findAllWithEagerRelationships(Pageable pageable) {
        return lichLamViecRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one lichLamViec by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<LichLamViec> findOne(String id) {
        log.debug("Request to get LichLamViec : {}", id);
        return lichLamViecRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the lichLamViec by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete LichLamViec : {}", id);
        lichLamViecRepository.deleteById(id);
    }
}
