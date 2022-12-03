package com.hust.hustpital.service;

import com.hust.hustpital.domain.LichHen;
import com.hust.hustpital.repository.LichHenRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link LichHen}.
 */
@Service
public class LichHenService {

    private final Logger log = LoggerFactory.getLogger(LichHenService.class);

    private final LichHenRepository lichHenRepository;

    public LichHenService(LichHenRepository lichHenRepository) {
        this.lichHenRepository = lichHenRepository;
    }

    /**
     * Save a lichHen.
     *
     * @param lichHen the entity to save.
     * @return the persisted entity.
     */
    public LichHen save(LichHen lichHen) {
        log.debug("Request to save LichHen : {}", lichHen);
        return lichHenRepository.save(lichHen);
    }

    /**
     * Update a lichHen.
     *
     * @param lichHen the entity to save.
     * @return the persisted entity.
     */
    public LichHen update(LichHen lichHen) {
        log.debug("Request to update LichHen : {}", lichHen);
        return lichHenRepository.save(lichHen);
    }

    /**
     * Partially update a lichHen.
     *
     * @param lichHen the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LichHen> partialUpdate(LichHen lichHen) {
        log.debug("Request to partially update LichHen : {}", lichHen);

        return lichHenRepository
            .findById(lichHen.getId())
            .map(existingLichHen -> {
                if (lichHen.getName() != null) {
                    existingLichHen.setName(lichHen.getName());
                }
                if (lichHen.getPhone() != null) {
                    existingLichHen.setPhone(lichHen.getPhone());
                }
                if (lichHen.getEmail() != null) {
                    existingLichHen.setEmail(lichHen.getEmail());
                }
                if (lichHen.getAddress() != null) {
                    existingLichHen.setAddress(lichHen.getAddress());
                }
                if (lichHen.getLyDoKham() != null) {
                    existingLichHen.setLyDoKham(lichHen.getLyDoKham());
                }
                if (lichHen.getDateOfBirth() != null) {
                    existingLichHen.setDateOfBirth(lichHen.getDateOfBirth());
                }
                if (lichHen.getLichhenType() != null) {
                    existingLichHen.setLichhenType(lichHen.getLichhenType());
                }

                return existingLichHen;
            })
            .map(lichHenRepository::save);
    }

    /**
     * Get all the lichHens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<LichHen> findAll(Pageable pageable) {
        log.debug("Request to get all LichHens");
        return lichHenRepository.findAll(pageable);
    }

    /**
     * Get all the lichHens with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<LichHen> findAllWithEagerRelationships(Pageable pageable) {
        return lichHenRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one lichHen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<LichHen> findOne(String id) {
        log.debug("Request to get LichHen : {}", id);
        return lichHenRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the lichHen by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete LichHen : {}", id);
        lichHenRepository.deleteById(id);
    }
}
