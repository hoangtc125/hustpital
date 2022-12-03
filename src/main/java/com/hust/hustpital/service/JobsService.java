package com.hust.hustpital.service;

import com.hust.hustpital.domain.Jobs;
import com.hust.hustpital.repository.JobsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Jobs}.
 */
@Service
public class JobsService {

    private final Logger log = LoggerFactory.getLogger(JobsService.class);

    private final JobsRepository jobsRepository;

    public JobsService(JobsRepository jobsRepository) {
        this.jobsRepository = jobsRepository;
    }

    /**
     * Save a jobs.
     *
     * @param jobs the entity to save.
     * @return the persisted entity.
     */
    public Jobs save(Jobs jobs) {
        log.debug("Request to save Jobs : {}", jobs);
        return jobsRepository.save(jobs);
    }

    /**
     * Update a jobs.
     *
     * @param jobs the entity to save.
     * @return the persisted entity.
     */
    public Jobs update(Jobs jobs) {
        log.debug("Request to update Jobs : {}", jobs);
        return jobsRepository.save(jobs);
    }

    /**
     * Partially update a jobs.
     *
     * @param jobs the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Jobs> partialUpdate(Jobs jobs) {
        log.debug("Request to partially update Jobs : {}", jobs);

        return jobsRepository
            .findById(jobs.getId())
            .map(existingJobs -> {
                if (jobs.getName() != null) {
                    existingJobs.setName(jobs.getName());
                }

                return existingJobs;
            })
            .map(jobsRepository::save);
    }

    /**
     * Get all the jobs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Jobs> findAll(Pageable pageable) {
        log.debug("Request to get all Jobs");
        return jobsRepository.findAll(pageable);
    }

    /**
     * Get one jobs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Jobs> findOne(String id) {
        log.debug("Request to get Jobs : {}", id);
        return jobsRepository.findById(id);
    }

    /**
     * Delete the jobs by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Jobs : {}", id);
        jobsRepository.deleteById(id);
    }
}
