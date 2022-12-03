package com.hust.hustpital.service;

import com.hust.hustpital.domain.Banks;
import com.hust.hustpital.repository.BanksRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Banks}.
 */
@Service
public class BanksService {

    private final Logger log = LoggerFactory.getLogger(BanksService.class);

    private final BanksRepository banksRepository;

    public BanksService(BanksRepository banksRepository) {
        this.banksRepository = banksRepository;
    }

    /**
     * Save a banks.
     *
     * @param banks the entity to save.
     * @return the persisted entity.
     */
    public Banks save(Banks banks) {
        log.debug("Request to save Banks : {}", banks);
        return banksRepository.save(banks);
    }

    /**
     * Update a banks.
     *
     * @param banks the entity to save.
     * @return the persisted entity.
     */
    public Banks update(Banks banks) {
        log.debug("Request to update Banks : {}", banks);
        return banksRepository.save(banks);
    }

    /**
     * Partially update a banks.
     *
     * @param banks the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Banks> partialUpdate(Banks banks) {
        log.debug("Request to partially update Banks : {}", banks);

        return banksRepository
            .findById(banks.getId())
            .map(existingBanks -> {
                if (banks.getCode() != null) {
                    existingBanks.setCode(banks.getCode());
                }
                if (banks.getName() != null) {
                    existingBanks.setName(banks.getName());
                }

                return existingBanks;
            })
            .map(banksRepository::save);
    }

    /**
     * Get all the banks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Banks> findAll(Pageable pageable) {
        log.debug("Request to get all Banks");
        return banksRepository.findAll(pageable);
    }

    /**
     * Get one banks by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Banks> findOne(String id) {
        log.debug("Request to get Banks : {}", id);
        return banksRepository.findById(id);
    }

    /**
     * Delete the banks by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Banks : {}", id);
        banksRepository.deleteById(id);
    }
}
