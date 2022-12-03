package com.hust.hustpital.service;

import com.hust.hustpital.domain.ThongTinVaoVien;
import com.hust.hustpital.repository.ThongTinVaoVienRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link ThongTinVaoVien}.
 */
@Service
public class ThongTinVaoVienService {

    private final Logger log = LoggerFactory.getLogger(ThongTinVaoVienService.class);

    private final ThongTinVaoVienRepository thongTinVaoVienRepository;

    public ThongTinVaoVienService(ThongTinVaoVienRepository thongTinVaoVienRepository) {
        this.thongTinVaoVienRepository = thongTinVaoVienRepository;
    }

    /**
     * Save a thongTinVaoVien.
     *
     * @param thongTinVaoVien the entity to save.
     * @return the persisted entity.
     */
    public ThongTinVaoVien save(ThongTinVaoVien thongTinVaoVien) {
        log.debug("Request to save ThongTinVaoVien : {}", thongTinVaoVien);
        return thongTinVaoVienRepository.save(thongTinVaoVien);
    }

    /**
     * Update a thongTinVaoVien.
     *
     * @param thongTinVaoVien the entity to save.
     * @return the persisted entity.
     */
    public ThongTinVaoVien update(ThongTinVaoVien thongTinVaoVien) {
        log.debug("Request to update ThongTinVaoVien : {}", thongTinVaoVien);
        return thongTinVaoVienRepository.save(thongTinVaoVien);
    }

    /**
     * Partially update a thongTinVaoVien.
     *
     * @param thongTinVaoVien the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ThongTinVaoVien> partialUpdate(ThongTinVaoVien thongTinVaoVien) {
        log.debug("Request to partially update ThongTinVaoVien : {}", thongTinVaoVien);

        return thongTinVaoVienRepository
            .findById(thongTinVaoVien.getId())
            .map(existingThongTinVaoVien -> {
                if (thongTinVaoVien.getNgayKham() != null) {
                    existingThongTinVaoVien.setNgayKham(thongTinVaoVien.getNgayKham());
                }
                if (thongTinVaoVien.getTinhTrangVaoVien() != null) {
                    existingThongTinVaoVien.setTinhTrangVaoVien(thongTinVaoVien.getTinhTrangVaoVien());
                }
                if (thongTinVaoVien.getSoPhieu() != null) {
                    existingThongTinVaoVien.setSoPhieu(thongTinVaoVien.getSoPhieu());
                }
                if (thongTinVaoVien.getMaBVChuyenDen() != null) {
                    existingThongTinVaoVien.setMaBVChuyenDen(thongTinVaoVien.getMaBVChuyenDen());
                }
                if (thongTinVaoVien.getBenhChuyenDen() != null) {
                    existingThongTinVaoVien.setBenhChuyenDen(thongTinVaoVien.getBenhChuyenDen());
                }

                return existingThongTinVaoVien;
            })
            .map(thongTinVaoVienRepository::save);
    }

    /**
     * Get all the thongTinVaoViens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ThongTinVaoVien> findAll(Pageable pageable) {
        log.debug("Request to get all ThongTinVaoViens");
        return thongTinVaoVienRepository.findAll(pageable);
    }

    /**
     * Get all the thongTinVaoViens with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ThongTinVaoVien> findAllWithEagerRelationships(Pageable pageable) {
        return thongTinVaoVienRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one thongTinVaoVien by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ThongTinVaoVien> findOne(String id) {
        log.debug("Request to get ThongTinVaoVien : {}", id);
        return thongTinVaoVienRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the thongTinVaoVien by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete ThongTinVaoVien : {}", id);
        thongTinVaoVienRepository.deleteById(id);
    }
}
