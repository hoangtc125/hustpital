package com.hust.hustpital.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hust.hustpital.IntegrationTest;
import com.hust.hustpital.domain.Ethnics;
import com.hust.hustpital.repository.EthnicsRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link EthnicsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EthnicsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ethnics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EthnicsRepository ethnicsRepository;

    @Autowired
    private MockMvc restEthnicsMockMvc;

    private Ethnics ethnics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ethnics createEntity() {
        Ethnics ethnics = new Ethnics().name(DEFAULT_NAME);
        return ethnics;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ethnics createUpdatedEntity() {
        Ethnics ethnics = new Ethnics().name(UPDATED_NAME);
        return ethnics;
    }

    @BeforeEach
    public void initTest() {
        ethnicsRepository.deleteAll();
        ethnics = createEntity();
    }

    @Test
    void createEthnics() throws Exception {
        int databaseSizeBeforeCreate = ethnicsRepository.findAll().size();
        // Create the Ethnics
        restEthnicsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ethnics)))
            .andExpect(status().isCreated());

        // Validate the Ethnics in the database
        List<Ethnics> ethnicsList = ethnicsRepository.findAll();
        assertThat(ethnicsList).hasSize(databaseSizeBeforeCreate + 1);
        Ethnics testEthnics = ethnicsList.get(ethnicsList.size() - 1);
        assertThat(testEthnics.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void createEthnicsWithExistingId() throws Exception {
        // Create the Ethnics with an existing ID
        ethnics.setId("existing_id");

        int databaseSizeBeforeCreate = ethnicsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEthnicsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ethnics)))
            .andExpect(status().isBadRequest());

        // Validate the Ethnics in the database
        List<Ethnics> ethnicsList = ethnicsRepository.findAll();
        assertThat(ethnicsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllEthnics() throws Exception {
        // Initialize the database
        ethnicsRepository.save(ethnics);

        // Get all the ethnicsList
        restEthnicsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ethnics.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    void getEthnics() throws Exception {
        // Initialize the database
        ethnicsRepository.save(ethnics);

        // Get the ethnics
        restEthnicsMockMvc
            .perform(get(ENTITY_API_URL_ID, ethnics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ethnics.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingEthnics() throws Exception {
        // Get the ethnics
        restEthnicsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingEthnics() throws Exception {
        // Initialize the database
        ethnicsRepository.save(ethnics);

        int databaseSizeBeforeUpdate = ethnicsRepository.findAll().size();

        // Update the ethnics
        Ethnics updatedEthnics = ethnicsRepository.findById(ethnics.getId()).get();
        updatedEthnics.name(UPDATED_NAME);

        restEthnicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEthnics.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEthnics))
            )
            .andExpect(status().isOk());

        // Validate the Ethnics in the database
        List<Ethnics> ethnicsList = ethnicsRepository.findAll();
        assertThat(ethnicsList).hasSize(databaseSizeBeforeUpdate);
        Ethnics testEthnics = ethnicsList.get(ethnicsList.size() - 1);
        assertThat(testEthnics.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void putNonExistingEthnics() throws Exception {
        int databaseSizeBeforeUpdate = ethnicsRepository.findAll().size();
        ethnics.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEthnicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ethnics.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ethnics))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ethnics in the database
        List<Ethnics> ethnicsList = ethnicsRepository.findAll();
        assertThat(ethnicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEthnics() throws Exception {
        int databaseSizeBeforeUpdate = ethnicsRepository.findAll().size();
        ethnics.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEthnicsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ethnics))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ethnics in the database
        List<Ethnics> ethnicsList = ethnicsRepository.findAll();
        assertThat(ethnicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEthnics() throws Exception {
        int databaseSizeBeforeUpdate = ethnicsRepository.findAll().size();
        ethnics.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEthnicsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ethnics)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ethnics in the database
        List<Ethnics> ethnicsList = ethnicsRepository.findAll();
        assertThat(ethnicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEthnicsWithPatch() throws Exception {
        // Initialize the database
        ethnicsRepository.save(ethnics);

        int databaseSizeBeforeUpdate = ethnicsRepository.findAll().size();

        // Update the ethnics using partial update
        Ethnics partialUpdatedEthnics = new Ethnics();
        partialUpdatedEthnics.setId(ethnics.getId());

        restEthnicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEthnics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEthnics))
            )
            .andExpect(status().isOk());

        // Validate the Ethnics in the database
        List<Ethnics> ethnicsList = ethnicsRepository.findAll();
        assertThat(ethnicsList).hasSize(databaseSizeBeforeUpdate);
        Ethnics testEthnics = ethnicsList.get(ethnicsList.size() - 1);
        assertThat(testEthnics.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void fullUpdateEthnicsWithPatch() throws Exception {
        // Initialize the database
        ethnicsRepository.save(ethnics);

        int databaseSizeBeforeUpdate = ethnicsRepository.findAll().size();

        // Update the ethnics using partial update
        Ethnics partialUpdatedEthnics = new Ethnics();
        partialUpdatedEthnics.setId(ethnics.getId());

        partialUpdatedEthnics.name(UPDATED_NAME);

        restEthnicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEthnics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEthnics))
            )
            .andExpect(status().isOk());

        // Validate the Ethnics in the database
        List<Ethnics> ethnicsList = ethnicsRepository.findAll();
        assertThat(ethnicsList).hasSize(databaseSizeBeforeUpdate);
        Ethnics testEthnics = ethnicsList.get(ethnicsList.size() - 1);
        assertThat(testEthnics.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void patchNonExistingEthnics() throws Exception {
        int databaseSizeBeforeUpdate = ethnicsRepository.findAll().size();
        ethnics.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEthnicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ethnics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ethnics))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ethnics in the database
        List<Ethnics> ethnicsList = ethnicsRepository.findAll();
        assertThat(ethnicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEthnics() throws Exception {
        int databaseSizeBeforeUpdate = ethnicsRepository.findAll().size();
        ethnics.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEthnicsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ethnics))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ethnics in the database
        List<Ethnics> ethnicsList = ethnicsRepository.findAll();
        assertThat(ethnicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEthnics() throws Exception {
        int databaseSizeBeforeUpdate = ethnicsRepository.findAll().size();
        ethnics.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEthnicsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ethnics)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ethnics in the database
        List<Ethnics> ethnicsList = ethnicsRepository.findAll();
        assertThat(ethnicsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEthnics() throws Exception {
        // Initialize the database
        ethnicsRepository.save(ethnics);

        int databaseSizeBeforeDelete = ethnicsRepository.findAll().size();

        // Delete the ethnics
        restEthnicsMockMvc
            .perform(delete(ENTITY_API_URL_ID, ethnics.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ethnics> ethnicsList = ethnicsRepository.findAll();
        assertThat(ethnicsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
