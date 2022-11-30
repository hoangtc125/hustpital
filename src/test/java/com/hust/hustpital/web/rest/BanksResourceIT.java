package com.hust.hustpital.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hust.hustpital.IntegrationTest;
import com.hust.hustpital.domain.Banks;
import com.hust.hustpital.repository.BanksRepository;
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
 * Integration tests for the {@link BanksResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BanksResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/banks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BanksRepository banksRepository;

    @Autowired
    private MockMvc restBanksMockMvc;

    private Banks banks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banks createEntity() {
        Banks banks = new Banks().code(DEFAULT_CODE).name(DEFAULT_NAME);
        return banks;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banks createUpdatedEntity() {
        Banks banks = new Banks().code(UPDATED_CODE).name(UPDATED_NAME);
        return banks;
    }

    @BeforeEach
    public void initTest() {
        banksRepository.deleteAll();
        banks = createEntity();
    }

    @Test
    void createBanks() throws Exception {
        int databaseSizeBeforeCreate = banksRepository.findAll().size();
        // Create the Banks
        restBanksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banks)))
            .andExpect(status().isCreated());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeCreate + 1);
        Banks testBanks = banksList.get(banksList.size() - 1);
        assertThat(testBanks.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBanks.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void createBanksWithExistingId() throws Exception {
        // Create the Banks with an existing ID
        banks.setId("existing_id");

        int databaseSizeBeforeCreate = banksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBanksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banks)))
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllBanks() throws Exception {
        // Initialize the database
        banksRepository.save(banks);

        // Get all the banksList
        restBanksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banks.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    void getBanks() throws Exception {
        // Initialize the database
        banksRepository.save(banks);

        // Get the banks
        restBanksMockMvc
            .perform(get(ENTITY_API_URL_ID, banks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(banks.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingBanks() throws Exception {
        // Get the banks
        restBanksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingBanks() throws Exception {
        // Initialize the database
        banksRepository.save(banks);

        int databaseSizeBeforeUpdate = banksRepository.findAll().size();

        // Update the banks
        Banks updatedBanks = banksRepository.findById(banks.getId()).get();
        updatedBanks.code(UPDATED_CODE).name(UPDATED_NAME);

        restBanksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBanks.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBanks))
            )
            .andExpect(status().isOk());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
        Banks testBanks = banksList.get(banksList.size() - 1);
        assertThat(testBanks.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBanks.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void putNonExistingBanks() throws Exception {
        int databaseSizeBeforeUpdate = banksRepository.findAll().size();
        banks.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, banks.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchBanks() throws Exception {
        int databaseSizeBeforeUpdate = banksRepository.findAll().size();
        banks.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamBanks() throws Exception {
        int databaseSizeBeforeUpdate = banksRepository.findAll().size();
        banks.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(banks)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateBanksWithPatch() throws Exception {
        // Initialize the database
        banksRepository.save(banks);

        int databaseSizeBeforeUpdate = banksRepository.findAll().size();

        // Update the banks using partial update
        Banks partialUpdatedBanks = new Banks();
        partialUpdatedBanks.setId(banks.getId());

        partialUpdatedBanks.code(UPDATED_CODE).name(UPDATED_NAME);

        restBanksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanks))
            )
            .andExpect(status().isOk());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
        Banks testBanks = banksList.get(banksList.size() - 1);
        assertThat(testBanks.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBanks.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void fullUpdateBanksWithPatch() throws Exception {
        // Initialize the database
        banksRepository.save(banks);

        int databaseSizeBeforeUpdate = banksRepository.findAll().size();

        // Update the banks using partial update
        Banks partialUpdatedBanks = new Banks();
        partialUpdatedBanks.setId(banks.getId());

        partialUpdatedBanks.code(UPDATED_CODE).name(UPDATED_NAME);

        restBanksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanks))
            )
            .andExpect(status().isOk());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
        Banks testBanks = banksList.get(banksList.size() - 1);
        assertThat(testBanks.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBanks.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void patchNonExistingBanks() throws Exception {
        int databaseSizeBeforeUpdate = banksRepository.findAll().size();
        banks.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, banks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchBanks() throws Exception {
        int databaseSizeBeforeUpdate = banksRepository.findAll().size();
        banks.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banks))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamBanks() throws Exception {
        int databaseSizeBeforeUpdate = banksRepository.findAll().size();
        banks.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(banks)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteBanks() throws Exception {
        // Initialize the database
        banksRepository.save(banks);

        int databaseSizeBeforeDelete = banksRepository.findAll().size();

        // Delete the banks
        restBanksMockMvc
            .perform(delete(ENTITY_API_URL_ID, banks.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
