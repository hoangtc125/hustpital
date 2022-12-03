package com.hust.hustpital.service;

import com.hust.hustpital.domain.ChuyenKhoa;
import com.hust.hustpital.repository.ChuyenKhoaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link ChuyenKhoa}.
 */
@Service
public class ChuyenKhoaService {

    private final Logger log = LoggerFactory.getLogger(ChuyenKhoaService.class);

    private final ChuyenKhoaRepository chuyenKhoaRepository;

    public ChuyenKhoaService(ChuyenKhoaRepository chuyenKhoaRepository) {
        this.chuyenKhoaRepository = chuyenKhoaRepository;
    }

    /**
     * Save a chuyenKhoa.
     *
     * @param chuyenKhoa the entity to save.
     * @return the persisted entity.
     */
    public ChuyenKhoa save(ChuyenKhoa chuyenKhoa) {
        log.debug("Request to save ChuyenKhoa : {}", chuyenKhoa);
        return chuyenKhoaRepository.save(chuyenKhoa);
    }

    /**
     * Update a chuyenKhoa.
     *
     * @param chuyenKhoa the entity to save.
     * @return the persisted entity.
     */
    public ChuyenKhoa update(ChuyenKhoa chuyenKhoa) {
        log.debug("Request to update ChuyenKhoa : {}", chuyenKhoa);
        return chuyenKhoaRepository.save(chuyenKhoa);
    }

    /**
     * Partially update a chuyenKhoa.
     *
     * @param chuyenKhoa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChuyenKhoa> partialUpdate(ChuyenKhoa chuyenKhoa) {
        log.debug("Request to partially update ChuyenKhoa : {}", chuyenKhoa);

        return chuyenKhoaRepository
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
    }

    /**
     * Get all the chuyenKhoas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ChuyenKhoa> findAll(Pageable pageable) {
        log.debug("Request to get all ChuyenKhoas");
        return chuyenKhoaRepository.findAll(pageable);
    }

    /**
     * Get one chuyenKhoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ChuyenKhoa> findOne(String id) {
        log.debug("Request to get ChuyenKhoa : {}", id);
        return chuyenKhoaRepository.findById(id);
    }

    /**
     * Delete the chuyenKhoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete ChuyenKhoa : {}", id);
        chuyenKhoaRepository.deleteById(id);
    }
}
