package com.hust.hustpital.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hust.hustpital.IntegrationTest;
import com.hust.hustpital.domain.ChuyenKhoa;
import com.hust.hustpital.domain.PhongKham;
import com.hust.hustpital.repository.PhongKhamRepository;
import com.hust.hustpital.service.PhongKhamService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link PhongKhamResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PhongKhamResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/phong-khams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PhongKhamRepository phongKhamRepository;

    @Mock
    private PhongKhamRepository phongKhamRepositoryMock;

    @Mock
    private PhongKhamService phongKhamServiceMock;

    @Autowired
    private MockMvc restPhongKhamMockMvc;

    private PhongKham phongKham;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhongKham createEntity() {
        PhongKham phongKham = new PhongKham().code(DEFAULT_CODE).name(DEFAULT_NAME);
        // Add required entity
        ChuyenKhoa chuyenKhoa;
        chuyenKhoa = ChuyenKhoaResourceIT.createEntity();
        chuyenKhoa.setId("fixed-id-for-tests");
        phongKham.setChuyenkhoa(chuyenKhoa);
        return phongKham;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhongKham createUpdatedEntity() {
        PhongKham phongKham = new PhongKham().code(UPDATED_CODE).name(UPDATED_NAME);
        // Add required entity
        ChuyenKhoa chuyenKhoa;
        chuyenKhoa = ChuyenKhoaResourceIT.createUpdatedEntity();
        chuyenKhoa.setId("fixed-id-for-tests");
        phongKham.setChuyenkhoa(chuyenKhoa);
        return phongKham;
    }

    @BeforeEach
    public void initTest() {
        phongKhamRepository.deleteAll();
        phongKham = createEntity();
    }

    @Test
    void createPhongKham() throws Exception {
        int databaseSizeBeforeCreate = phongKhamRepository.findAll().size();
        // Create the PhongKham
        restPhongKhamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongKham)))
            .andExpect(status().isCreated());

        // Validate the PhongKham in the database
        List<PhongKham> phongKhamList = phongKhamRepository.findAll();
        assertThat(phongKhamList).hasSize(databaseSizeBeforeCreate + 1);
        PhongKham testPhongKham = phongKhamList.get(phongKhamList.size() - 1);
        assertThat(testPhongKham.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPhongKham.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void createPhongKhamWithExistingId() throws Exception {
        // Create the PhongKham with an existing ID
        phongKham.setId("existing_id");

        int databaseSizeBeforeCreate = phongKhamRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhongKhamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongKham)))
            .andExpect(status().isBadRequest());

        // Validate the PhongKham in the database
        List<PhongKham> phongKhamList = phongKhamRepository.findAll();
        assertThat(phongKhamList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPhongKhams() throws Exception {
        // Initialize the database
        phongKhamRepository.save(phongKham);

        // Get all the phongKhamList
        restPhongKhamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phongKham.getId())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPhongKhamsWithEagerRelationshipsIsEnabled() throws Exception {
        when(phongKhamServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPhongKhamMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(phongKhamServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPhongKhamsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(phongKhamServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPhongKhamMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(phongKhamRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getPhongKham() throws Exception {
        // Initialize the database
        phongKhamRepository.save(phongKham);

        // Get the phongKham
        restPhongKhamMockMvc
            .perform(get(ENTITY_API_URL_ID, phongKham.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phongKham.getId()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingPhongKham() throws Exception {
        // Get the phongKham
        restPhongKhamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPhongKham() throws Exception {
        // Initialize the database
        phongKhamRepository.save(phongKham);

        int databaseSizeBeforeUpdate = phongKhamRepository.findAll().size();

        // Update the phongKham
        PhongKham updatedPhongKham = phongKhamRepository.findById(phongKham.getId()).get();
        updatedPhongKham.code(UPDATED_CODE).name(UPDATED_NAME);

        restPhongKhamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPhongKham.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPhongKham))
            )
            .andExpect(status().isOk());

        // Validate the PhongKham in the database
        List<PhongKham> phongKhamList = phongKhamRepository.findAll();
        assertThat(phongKhamList).hasSize(databaseSizeBeforeUpdate);
        PhongKham testPhongKham = phongKhamList.get(phongKhamList.size() - 1);
        assertThat(testPhongKham.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPhongKham.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void putNonExistingPhongKham() throws Exception {
        int databaseSizeBeforeUpdate = phongKhamRepository.findAll().size();
        phongKham.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhongKhamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phongKham.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phongKham))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongKham in the database
        List<PhongKham> phongKhamList = phongKhamRepository.findAll();
        assertThat(phongKhamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPhongKham() throws Exception {
        int databaseSizeBeforeUpdate = phongKhamRepository.findAll().size();
        phongKham.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongKhamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phongKham))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongKham in the database
        List<PhongKham> phongKhamList = phongKhamRepository.findAll();
        assertThat(phongKhamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPhongKham() throws Exception {
        int databaseSizeBeforeUpdate = phongKhamRepository.findAll().size();
        phongKham.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongKhamMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongKham)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhongKham in the database
        List<PhongKham> phongKhamList = phongKhamRepository.findAll();
        assertThat(phongKhamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePhongKhamWithPatch() throws Exception {
        // Initialize the database
        phongKhamRepository.save(phongKham);

        int databaseSizeBeforeUpdate = phongKhamRepository.findAll().size();

        // Update the phongKham using partial update
        PhongKham partialUpdatedPhongKham = new PhongKham();
        partialUpdatedPhongKham.setId(phongKham.getId());

        partialUpdatedPhongKham.code(UPDATED_CODE);

        restPhongKhamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhongKham.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhongKham))
            )
            .andExpect(status().isOk());

        // Validate the PhongKham in the database
        List<PhongKham> phongKhamList = phongKhamRepository.findAll();
        assertThat(phongKhamList).hasSize(databaseSizeBeforeUpdate);
        PhongKham testPhongKham = phongKhamList.get(phongKhamList.size() - 1);
        assertThat(testPhongKham.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPhongKham.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void fullUpdatePhongKhamWithPatch() throws Exception {
        // Initialize the database
        phongKhamRepository.save(phongKham);

        int databaseSizeBeforeUpdate = phongKhamRepository.findAll().size();

        // Update the phongKham using partial update
        PhongKham partialUpdatedPhongKham = new PhongKham();
        partialUpdatedPhongKham.setId(phongKham.getId());

        partialUpdatedPhongKham.code(UPDATED_CODE).name(UPDATED_NAME);

        restPhongKhamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhongKham.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhongKham))
            )
            .andExpect(status().isOk());

        // Validate the PhongKham in the database
        List<PhongKham> phongKhamList = phongKhamRepository.findAll();
        assertThat(phongKhamList).hasSize(databaseSizeBeforeUpdate);
        PhongKham testPhongKham = phongKhamList.get(phongKhamList.size() - 1);
        assertThat(testPhongKham.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPhongKham.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void patchNonExistingPhongKham() throws Exception {
        int databaseSizeBeforeUpdate = phongKhamRepository.findAll().size();
        phongKham.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhongKhamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phongKham.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phongKham))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongKham in the database
        List<PhongKham> phongKhamList = phongKhamRepository.findAll();
        assertThat(phongKhamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPhongKham() throws Exception {
        int databaseSizeBeforeUpdate = phongKhamRepository.findAll().size();
        phongKham.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongKhamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phongKham))
            )
            .andExpect(status().isBadRequest());

        // Validate the PhongKham in the database
        List<PhongKham> phongKhamList = phongKhamRepository.findAll();
        assertThat(phongKhamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPhongKham() throws Exception {
        int databaseSizeBeforeUpdate = phongKhamRepository.findAll().size();
        phongKham.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongKhamMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(phongKham))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PhongKham in the database
        List<PhongKham> phongKhamList = phongKhamRepository.findAll();
        assertThat(phongKhamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePhongKham() throws Exception {
        // Initialize the database
        phongKhamRepository.save(phongKham);

        int databaseSizeBeforeDelete = phongKhamRepository.findAll().size();

        // Delete the phongKham
        restPhongKhamMockMvc
            .perform(delete(ENTITY_API_URL_ID, phongKham.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhongKham> phongKhamList = phongKhamRepository.findAll();
        assertThat(phongKhamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
