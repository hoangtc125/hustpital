package com.hust.hustpital.service;

import com.hust.hustpital.domain.Doctors;
import com.hust.hustpital.repository.DoctorsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Doctors}.
 */
@Service
public class DoctorsService {

    private final Logger log = LoggerFactory.getLogger(DoctorsService.class);

    private final DoctorsRepository doctorsRepository;

    public DoctorsService(DoctorsRepository doctorsRepository) {
        this.doctorsRepository = doctorsRepository;
    }

    /**
     * Save a doctors.
     *
     * @param doctors the entity to save.
     * @return the persisted entity.
     */
    public Doctors save(Doctors doctors) {
        log.debug("Request to save Doctors : {}", doctors);
        return doctorsRepository.save(doctors);
    }

    /**
     * Update a doctors.
     *
     * @param doctors the entity to save.
     * @return the persisted entity.
     */
    public Doctors update(Doctors doctors) {
        log.debug("Request to update Doctors : {}", doctors);
        return doctorsRepository.save(doctors);
    }

    /**
     * Partially update a doctors.
     *
     * @param doctors the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Doctors> partialUpdate(Doctors doctors) {
        log.debug("Request to partially update Doctors : {}", doctors);

        return doctorsRepository
            .findById(doctors.getId())
            .map(existingDoctors -> {
                if (doctors.getName() != null) {
                    existingDoctors.setName(doctors.getName());
                }
                if (doctors.getPhone() != null) {
                    existingDoctors.setPhone(doctors.getPhone());
                }
                if (doctors.getCitizenIdentification() != null) {
                    existingDoctors.setCitizenIdentification(doctors.getCitizenIdentification());
                }
                if (doctors.getMaBHXH() != null) {
                    existingDoctors.setMaBHXH(doctors.getMaBHXH());
                }
                if (doctors.getGender() != null) {
                    existingDoctors.setGender(doctors.getGender());
                }
                if (doctors.getDateOfBirth() != null) {
                    existingDoctors.setDateOfBirth(doctors.getDateOfBirth());
                }
                if (doctors.getAddress() != null) {
                    existingDoctors.setAddress(doctors.getAddress());
                }
                if (doctors.getMaSoThue() != null) {
                    existingDoctors.setMaSoThue(doctors.getMaSoThue());
                }

                return existingDoctors;
            })
            .map(doctorsRepository::save);
    }

    /**
     * Get all the doctors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Doctors> findAll(Pageable pageable) {
        log.debug("Request to get all Doctors");
        return doctorsRepository.findAll(pageable);
    }

    /**
     * Get all the doctors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Doctors> findAllWithEagerRelationships(Pageable pageable) {
        return doctorsRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one doctors by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Doctors> findOne(String id) {
        log.debug("Request to get Doctors : {}", id);
        return doctorsRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the doctors by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Doctors : {}", id);
        doctorsRepository.deleteById(id);
    }
}
