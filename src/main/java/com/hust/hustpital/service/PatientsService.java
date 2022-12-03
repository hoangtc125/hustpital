package com.hust.hustpital.service;

import com.hust.hustpital.domain.Patients;
import com.hust.hustpital.repository.PatientsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Patients}.
 */
@Service
public class PatientsService {

    private final Logger log = LoggerFactory.getLogger(PatientsService.class);

    private final PatientsRepository patientsRepository;

    public PatientsService(PatientsRepository patientsRepository) {
        this.patientsRepository = patientsRepository;
    }

    /**
     * Save a patients.
     *
     * @param patients the entity to save.
     * @return the persisted entity.
     */
    public Patients save(Patients patients) {
        log.debug("Request to save Patients : {}", patients);
        return patientsRepository.save(patients);
    }

    /**
     * Update a patients.
     *
     * @param patients the entity to save.
     * @return the persisted entity.
     */
    public Patients update(Patients patients) {
        log.debug("Request to update Patients : {}", patients);
        return patientsRepository.save(patients);
    }

    /**
     * Partially update a patients.
     *
     * @param patients the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Patients> partialUpdate(Patients patients) {
        log.debug("Request to partially update Patients : {}", patients);

        return patientsRepository
            .findById(patients.getId())
            .map(existingPatients -> {
                if (patients.getName() != null) {
                    existingPatients.setName(patients.getName());
                }
                if (patients.getGender() != null) {
                    existingPatients.setGender(patients.getGender());
                }
                if (patients.getAddress() != null) {
                    existingPatients.setAddress(patients.getAddress());
                }
                if (patients.getDateOfBirth() != null) {
                    existingPatients.setDateOfBirth(patients.getDateOfBirth());
                }
                if (patients.getPhone() != null) {
                    existingPatients.setPhone(patients.getPhone());
                }
                if (patients.getCitizenIdentification() != null) {
                    existingPatients.setCitizenIdentification(patients.getCitizenIdentification());
                }
                if (patients.getMaBHXH() != null) {
                    existingPatients.setMaBHXH(patients.getMaBHXH());
                }
                if (patients.getWorkPlace() != null) {
                    existingPatients.setWorkPlace(patients.getWorkPlace());
                }
                if (patients.getWorkAddress() != null) {
                    existingPatients.setWorkAddress(patients.getWorkAddress());
                }
                if (patients.getPatientType() != null) {
                    existingPatients.setPatientType(patients.getPatientType());
                }

                return existingPatients;
            })
            .map(patientsRepository::save);
    }

    /**
     * Get all the patients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Patients> findAll(Pageable pageable) {
        log.debug("Request to get all Patients");
        return patientsRepository.findAll(pageable);
    }

    /**
     * Get all the patients with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Patients> findAllWithEagerRelationships(Pageable pageable) {
        return patientsRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one patients by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Patients> findOne(String id) {
        log.debug("Request to get Patients : {}", id);
        return patientsRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the patients by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Patients : {}", id);
        patientsRepository.deleteById(id);
    }
}
