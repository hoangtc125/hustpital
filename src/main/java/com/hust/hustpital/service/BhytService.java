package com.hust.hustpital.service;

import com.hust.hustpital.domain.Bhyt;
import com.hust.hustpital.repository.BhytRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Bhyt}.
 */
@Service
public class BhytService {

    private final Logger log = LoggerFactory.getLogger(BhytService.class);

    private final BhytRepository bhytRepository;

    public BhytService(BhytRepository bhytRepository) {
        this.bhytRepository = bhytRepository;
    }

    /**
     * Save a bhyt.
     *
     * @param bhyt the entity to save.
     * @return the persisted entity.
     */
    public Bhyt save(Bhyt bhyt) {
        log.debug("Request to save Bhyt : {}", bhyt);
        return bhytRepository.save(bhyt);
    }

    /**
     * Update a bhyt.
     *
     * @param bhyt the entity to save.
     * @return the persisted entity.
     */
    public Bhyt update(Bhyt bhyt) {
        log.debug("Request to update Bhyt : {}", bhyt);
        return bhytRepository.save(bhyt);
    }

    /**
     * Partially update a bhyt.
     *
     * @param bhyt the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Bhyt> partialUpdate(Bhyt bhyt) {
        log.debug("Request to partially update Bhyt : {}", bhyt);

        return bhytRepository
            .findById(bhyt.getId())
            .map(existingBhyt -> {
                if (bhyt.getQrcode() != null) {
                    existingBhyt.setQrcode(bhyt.getQrcode());
                }
                if (bhyt.getSothe() != null) {
                    existingBhyt.setSothe(bhyt.getSothe());
                }
                if (bhyt.getMaKCBBD() != null) {
                    existingBhyt.setMaKCBBD(bhyt.getMaKCBBD());
                }
                if (bhyt.getDiachi() != null) {
                    existingBhyt.setDiachi(bhyt.getDiachi());
                }
                if (bhyt.getNgayBatDau() != null) {
                    existingBhyt.setNgayBatDau(bhyt.getNgayBatDau());
                }
                if (bhyt.getNgayKetThuc() != null) {
                    existingBhyt.setNgayKetThuc(bhyt.getNgayKetThuc());
                }
                if (bhyt.getNgayBatDau5namLT() != null) {
                    existingBhyt.setNgayBatDau5namLT(bhyt.getNgayBatDau5namLT());
                }
                if (bhyt.getNgayBatDauMienCCT() != null) {
                    existingBhyt.setNgayBatDauMienCCT(bhyt.getNgayBatDauMienCCT());
                }
                if (bhyt.getNgayKetThucMienCCT() != null) {
                    existingBhyt.setNgayKetThucMienCCT(bhyt.getNgayKetThucMienCCT());
                }

                return existingBhyt;
            })
            .map(bhytRepository::save);
    }

    /**
     * Get all the bhyts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Bhyt> findAll(Pageable pageable) {
        log.debug("Request to get all Bhyts");
        return bhytRepository.findAll(pageable);
    }

    /**
     * Get one bhyt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Bhyt> findOne(String id) {
        log.debug("Request to get Bhyt : {}", id);
        return bhytRepository.findById(id);
    }

    /**
     * Delete the bhyt by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Bhyt : {}", id);
        bhytRepository.deleteById(id);
    }
}
