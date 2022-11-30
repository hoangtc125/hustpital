package com.hust.hustpital.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hust.hustpital.IntegrationTest;
import com.hust.hustpital.domain.ChuyenKhoa;
import com.hust.hustpital.repository.ChuyenKhoaRepository;
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
 * Integration tests for the {@link ChuyenKhoaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChuyenKhoaResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/chuyen-khoas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ChuyenKhoaRepository chuyenKhoaRepository;

    @Autowired
    private MockMvc restChuyenKhoaMockMvc;

    private ChuyenKhoa chuyenKhoa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChuyenKhoa createEntity() {
        ChuyenKhoa chuyenKhoa = new ChuyenKhoa().code(DEFAULT_CODE).name(DEFAULT_NAME);
        return chuyenKhoa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChuyenKhoa createUpdatedEntity() {
        ChuyenKhoa chuyenKhoa = new ChuyenKhoa().code(UPDATED_CODE).name(UPDATED_NAME);
        return chuyenKhoa;
    }

    @BeforeEach
    public void initTest() {
        chuyenKhoaRepository.deleteAll();
        chuyenKhoa = createEntity();
    }

    @Test
    void createChuyenKhoa() throws Exception {
        int databaseSizeBeforeCreate = chuyenKhoaRepository.findAll().size();
        // Create the ChuyenKhoa
        restChuyenKhoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chuyenKhoa)))
            .andExpect(status().isCreated());

        // Validate the ChuyenKhoa in the database
        List<ChuyenKhoa> chuyenKhoaList = chuyenKhoaRepository.findAll();
        assertThat(chuyenKhoaList).hasSize(databaseSizeBeforeCreate + 1);
        ChuyenKhoa testChuyenKhoa = chuyenKhoaList.get(chuyenKhoaList.size() - 1);
        assertThat(testChuyenKhoa.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testChuyenKhoa.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void createChuyenKhoaWithExistingId() throws Exception {
        // Create the ChuyenKhoa with an existing ID
        chuyenKhoa.setId("existing_id");

        int databaseSizeBeforeCreate = chuyenKhoaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChuyenKhoaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chuyenKhoa)))
            .andExpect(status().isBadRequest());

        // Validate the ChuyenKhoa in the database
        List<ChuyenKhoa> chuyenKhoaList = chuyenKhoaRepository.findAll();
        assertThat(chuyenKhoaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllChuyenKhoas() throws Exception {
        // Initialize the database
        chuyenKhoaRepository.save(chuyenKhoa);

        // Get all the chuyenKhoaList
        restChuyenKhoaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chuyenKhoa.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    void getChuyenKhoa() throws Exception {
        // Initialize the database
        chuyenKhoaRepository.save(chuyenKhoa);

        // Get the chuyenKhoa
        restChuyenKhoaMockMvc
            .perform(get(ENTITY_API_URL_ID, chuyenKhoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chuyenKhoa.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingChuyenKhoa() throws Exception {
        // Get the chuyenKhoa
        restChuyenKhoaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingChuyenKhoa() throws Exception {
        // Initialize the database
        chuyenKhoaRepository.save(chuyenKhoa);

        int databaseSizeBeforeUpdate = chuyenKhoaRepository.findAll().size();

        // Update the chuyenKhoa
        ChuyenKhoa updatedChuyenKhoa = chuyenKhoaRepository.findById(chuyenKhoa.getId()).get();
        updatedChuyenKhoa.code(UPDATED_CODE).name(UPDATED_NAME);

        restChuyenKhoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedChuyenKhoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedChuyenKhoa))
            )
            .andExpect(status().isOk());

        // Validate the ChuyenKhoa in the database
        List<ChuyenKhoa> chuyenKhoaList = chuyenKhoaRepository.findAll();
        assertThat(chuyenKhoaList).hasSize(databaseSizeBeforeUpdate);
        ChuyenKhoa testChuyenKhoa = chuyenKhoaList.get(chuyenKhoaList.size() - 1);
        assertThat(testChuyenKhoa.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testChuyenKhoa.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void putNonExistingChuyenKhoa() throws Exception {
        int databaseSizeBeforeUpdate = chuyenKhoaRepository.findAll().size();
        chuyenKhoa.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChuyenKhoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, chuyenKhoa.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chuyenKhoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChuyenKhoa in the database
        List<ChuyenKhoa> chuyenKhoaList = chuyenKhoaRepository.findAll();
        assertThat(chuyenKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchChuyenKhoa() throws Exception {
        int databaseSizeBeforeUpdate = chuyenKhoaRepository.findAll().size();
        chuyenKhoa.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChuyenKhoaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(chuyenKhoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChuyenKhoa in the database
        List<ChuyenKhoa> chuyenKhoaList = chuyenKhoaRepository.findAll();
        assertThat(chuyenKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamChuyenKhoa() throws Exception {
        int databaseSizeBeforeUpdate = chuyenKhoaRepository.findAll().size();
        chuyenKhoa.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChuyenKhoaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chuyenKhoa)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChuyenKhoa in the database
        List<ChuyenKhoa> chuyenKhoaList = chuyenKhoaRepository.findAll();
        assertThat(chuyenKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateChuyenKhoaWithPatch() throws Exception {
        // Initialize the database
        chuyenKhoaRepository.save(chuyenKhoa);

        int databaseSizeBeforeUpdate = chuyenKhoaRepository.findAll().size();

        // Update the chuyenKhoa using partial update
        ChuyenKhoa partialUpdatedChuyenKhoa = new ChuyenKhoa();
        partialUpdatedChuyenKhoa.setId(chuyenKhoa.getId());

        restChuyenKhoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChuyenKhoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChuyenKhoa))
            )
            .andExpect(status().isOk());

        // Validate the ChuyenKhoa in the database
        List<ChuyenKhoa> chuyenKhoaList = chuyenKhoaRepository.findAll();
        assertThat(chuyenKhoaList).hasSize(databaseSizeBeforeUpdate);
        ChuyenKhoa testChuyenKhoa = chuyenKhoaList.get(chuyenKhoaList.size() - 1);
        assertThat(testChuyenKhoa.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testChuyenKhoa.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void fullUpdateChuyenKhoaWithPatch() throws Exception {
        // Initialize the database
        chuyenKhoaRepository.save(chuyenKhoa);

        int databaseSizeBeforeUpdate = chuyenKhoaRepository.findAll().size();

        // Update the chuyenKhoa using partial update
        ChuyenKhoa partialUpdatedChuyenKhoa = new ChuyenKhoa();
        partialUpdatedChuyenKhoa.setId(chuyenKhoa.getId());

        partialUpdatedChuyenKhoa.code(UPDATED_CODE).name(UPDATED_NAME);

        restChuyenKhoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChuyenKhoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChuyenKhoa))
            )
            .andExpect(status().isOk());

        // Validate the ChuyenKhoa in the database
        List<ChuyenKhoa> chuyenKhoaList = chuyenKhoaRepository.findAll();
        assertThat(chuyenKhoaList).hasSize(databaseSizeBeforeUpdate);
        ChuyenKhoa testChuyenKhoa = chuyenKhoaList.get(chuyenKhoaList.size() - 1);
        assertThat(testChuyenKhoa.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testChuyenKhoa.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void patchNonExistingChuyenKhoa() throws Exception {
        int databaseSizeBeforeUpdate = chuyenKhoaRepository.findAll().size();
        chuyenKhoa.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChuyenKhoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, chuyenKhoa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chuyenKhoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChuyenKhoa in the database
        List<ChuyenKhoa> chuyenKhoaList = chuyenKhoaRepository.findAll();
        assertThat(chuyenKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchChuyenKhoa() throws Exception {
        int databaseSizeBeforeUpdate = chuyenKhoaRepository.findAll().size();
        chuyenKhoa.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChuyenKhoaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(chuyenKhoa))
            )
            .andExpect(status().isBadRequest());

        // Validate the ChuyenKhoa in the database
        List<ChuyenKhoa> chuyenKhoaList = chuyenKhoaRepository.findAll();
        assertThat(chuyenKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamChuyenKhoa() throws Exception {
        int databaseSizeBeforeUpdate = chuyenKhoaRepository.findAll().size();
        chuyenKhoa.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChuyenKhoaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(chuyenKhoa))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ChuyenKhoa in the database
        List<ChuyenKhoa> chuyenKhoaList = chuyenKhoaRepository.findAll();
        assertThat(chuyenKhoaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteChuyenKhoa() throws Exception {
        // Initialize the database
        chuyenKhoaRepository.save(chuyenKhoa);

        int databaseSizeBeforeDelete = chuyenKhoaRepository.findAll().size();

        // Delete the chuyenKhoa
        restChuyenKhoaMockMvc
            .perform(delete(ENTITY_API_URL_ID, chuyenKhoa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ChuyenKhoa> chuyenKhoaList = chuyenKhoaRepository.findAll();
        assertThat(chuyenKhoaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
