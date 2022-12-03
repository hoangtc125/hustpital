package com.hust.hustpital.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hust.hustpital.IntegrationTest;
import com.hust.hustpital.domain.Doctors;
import com.hust.hustpital.domain.LichLamViec;
import com.hust.hustpital.repository.LichLamViecRepository;
import com.hust.hustpital.service.LichLamViecService;
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
 * Integration tests for the {@link LichLamViecResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LichLamViecResourceIT {

    private static final Integer DEFAULT_THU = 1;
    private static final Integer UPDATED_THU = 2;

    private static final String DEFAULT_THOI_GIAN = "AAAAAAAAAA";
    private static final String UPDATED_THOI_GIAN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/lich-lam-viecs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private LichLamViecRepository lichLamViecRepository;

    @Mock
    private LichLamViecRepository lichLamViecRepositoryMock;

    @Mock
    private LichLamViecService lichLamViecServiceMock;

    @Autowired
    private MockMvc restLichLamViecMockMvc;

    private LichLamViec lichLamViec;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LichLamViec createEntity() {
        LichLamViec lichLamViec = new LichLamViec().thu(DEFAULT_THU).thoiGian(DEFAULT_THOI_GIAN);
        // Add required entity
        Doctors doctors;
        doctors = DoctorsResourceIT.createEntity();
        doctors.setId("fixed-id-for-tests");
        lichLamViec.getDoctors().add(doctors);
        return lichLamViec;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LichLamViec createUpdatedEntity() {
        LichLamViec lichLamViec = new LichLamViec().thu(UPDATED_THU).thoiGian(UPDATED_THOI_GIAN);
        // Add required entity
        Doctors doctors;
        doctors = DoctorsResourceIT.createUpdatedEntity();
        doctors.setId("fixed-id-for-tests");
        lichLamViec.getDoctors().add(doctors);
        return lichLamViec;
    }

    @BeforeEach
    public void initTest() {
        lichLamViecRepository.deleteAll();
        lichLamViec = createEntity();
    }

    @Test
    void createLichLamViec() throws Exception {
        int databaseSizeBeforeCreate = lichLamViecRepository.findAll().size();
        // Create the LichLamViec
        restLichLamViecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lichLamViec)))
            .andExpect(status().isCreated());

        // Validate the LichLamViec in the database
        List<LichLamViec> lichLamViecList = lichLamViecRepository.findAll();
        assertThat(lichLamViecList).hasSize(databaseSizeBeforeCreate + 1);
        LichLamViec testLichLamViec = lichLamViecList.get(lichLamViecList.size() - 1);
        assertThat(testLichLamViec.getThu()).isEqualTo(DEFAULT_THU);
        assertThat(testLichLamViec.getThoiGian()).isEqualTo(DEFAULT_THOI_GIAN);
    }

    @Test
    void createLichLamViecWithExistingId() throws Exception {
        // Create the LichLamViec with an existing ID
        lichLamViec.setId("existing_id");

        int databaseSizeBeforeCreate = lichLamViecRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLichLamViecMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lichLamViec)))
            .andExpect(status().isBadRequest());

        // Validate the LichLamViec in the database
        List<LichLamViec> lichLamViecList = lichLamViecRepository.findAll();
        assertThat(lichLamViecList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllLichLamViecs() throws Exception {
        // Initialize the database
        lichLamViecRepository.save(lichLamViec);

        // Get all the lichLamViecList
        restLichLamViecMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lichLamViec.getId())))
            .andExpect(jsonPath("$.[*].thu").value(hasItem(DEFAULT_THU)))
            .andExpect(jsonPath("$.[*].thoiGian").value(hasItem(DEFAULT_THOI_GIAN)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLichLamViecsWithEagerRelationshipsIsEnabled() throws Exception {
        when(lichLamViecServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLichLamViecMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(lichLamViecServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLichLamViecsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(lichLamViecServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLichLamViecMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(lichLamViecRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getLichLamViec() throws Exception {
        // Initialize the database
        lichLamViecRepository.save(lichLamViec);

        // Get the lichLamViec
        restLichLamViecMockMvc
            .perform(get(ENTITY_API_URL_ID, lichLamViec.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lichLamViec.getId()))
            .andExpect(jsonPath("$.thu").value(DEFAULT_THU))
            .andExpect(jsonPath("$.thoiGian").value(DEFAULT_THOI_GIAN));
    }

    @Test
    void getNonExistingLichLamViec() throws Exception {
        // Get the lichLamViec
        restLichLamViecMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingLichLamViec() throws Exception {
        // Initialize the database
        lichLamViecRepository.save(lichLamViec);

        int databaseSizeBeforeUpdate = lichLamViecRepository.findAll().size();

        // Update the lichLamViec
        LichLamViec updatedLichLamViec = lichLamViecRepository.findById(lichLamViec.getId()).get();
        updatedLichLamViec.thu(UPDATED_THU).thoiGian(UPDATED_THOI_GIAN);

        restLichLamViecMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLichLamViec.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLichLamViec))
            )
            .andExpect(status().isOk());

        // Validate the LichLamViec in the database
        List<LichLamViec> lichLamViecList = lichLamViecRepository.findAll();
        assertThat(lichLamViecList).hasSize(databaseSizeBeforeUpdate);
        LichLamViec testLichLamViec = lichLamViecList.get(lichLamViecList.size() - 1);
        assertThat(testLichLamViec.getThu()).isEqualTo(UPDATED_THU);
        assertThat(testLichLamViec.getThoiGian()).isEqualTo(UPDATED_THOI_GIAN);
    }

    @Test
    void putNonExistingLichLamViec() throws Exception {
        int databaseSizeBeforeUpdate = lichLamViecRepository.findAll().size();
        lichLamViec.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLichLamViecMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lichLamViec.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lichLamViec))
            )
            .andExpect(status().isBadRequest());

        // Validate the LichLamViec in the database
        List<LichLamViec> lichLamViecList = lichLamViecRepository.findAll();
        assertThat(lichLamViecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchLichLamViec() throws Exception {
        int databaseSizeBeforeUpdate = lichLamViecRepository.findAll().size();
        lichLamViec.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLichLamViecMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lichLamViec))
            )
            .andExpect(status().isBadRequest());

        // Validate the LichLamViec in the database
        List<LichLamViec> lichLamViecList = lichLamViecRepository.findAll();
        assertThat(lichLamViecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamLichLamViec() throws Exception {
        int databaseSizeBeforeUpdate = lichLamViecRepository.findAll().size();
        lichLamViec.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLichLamViecMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lichLamViec)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LichLamViec in the database
        List<LichLamViec> lichLamViecList = lichLamViecRepository.findAll();
        assertThat(lichLamViecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateLichLamViecWithPatch() throws Exception {
        // Initialize the database
        lichLamViecRepository.save(lichLamViec);

        int databaseSizeBeforeUpdate = lichLamViecRepository.findAll().size();

        // Update the lichLamViec using partial update
        LichLamViec partialUpdatedLichLamViec = new LichLamViec();
        partialUpdatedLichLamViec.setId(lichLamViec.getId());

        partialUpdatedLichLamViec.thu(UPDATED_THU);

        restLichLamViecMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLichLamViec.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLichLamViec))
            )
            .andExpect(status().isOk());

        // Validate the LichLamViec in the database
        List<LichLamViec> lichLamViecList = lichLamViecRepository.findAll();
        assertThat(lichLamViecList).hasSize(databaseSizeBeforeUpdate);
        LichLamViec testLichLamViec = lichLamViecList.get(lichLamViecList.size() - 1);
        assertThat(testLichLamViec.getThu()).isEqualTo(UPDATED_THU);
        assertThat(testLichLamViec.getThoiGian()).isEqualTo(DEFAULT_THOI_GIAN);
    }

    @Test
    void fullUpdateLichLamViecWithPatch() throws Exception {
        // Initialize the database
        lichLamViecRepository.save(lichLamViec);

        int databaseSizeBeforeUpdate = lichLamViecRepository.findAll().size();

        // Update the lichLamViec using partial update
        LichLamViec partialUpdatedLichLamViec = new LichLamViec();
        partialUpdatedLichLamViec.setId(lichLamViec.getId());

        partialUpdatedLichLamViec.thu(UPDATED_THU).thoiGian(UPDATED_THOI_GIAN);

        restLichLamViecMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLichLamViec.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLichLamViec))
            )
            .andExpect(status().isOk());

        // Validate the LichLamViec in the database
        List<LichLamViec> lichLamViecList = lichLamViecRepository.findAll();
        assertThat(lichLamViecList).hasSize(databaseSizeBeforeUpdate);
        LichLamViec testLichLamViec = lichLamViecList.get(lichLamViecList.size() - 1);
        assertThat(testLichLamViec.getThu()).isEqualTo(UPDATED_THU);
        assertThat(testLichLamViec.getThoiGian()).isEqualTo(UPDATED_THOI_GIAN);
    }

    @Test
    void patchNonExistingLichLamViec() throws Exception {
        int databaseSizeBeforeUpdate = lichLamViecRepository.findAll().size();
        lichLamViec.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLichLamViecMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lichLamViec.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lichLamViec))
            )
            .andExpect(status().isBadRequest());

        // Validate the LichLamViec in the database
        List<LichLamViec> lichLamViecList = lichLamViecRepository.findAll();
        assertThat(lichLamViecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchLichLamViec() throws Exception {
        int databaseSizeBeforeUpdate = lichLamViecRepository.findAll().size();
        lichLamViec.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLichLamViecMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lichLamViec))
            )
            .andExpect(status().isBadRequest());

        // Validate the LichLamViec in the database
        List<LichLamViec> lichLamViecList = lichLamViecRepository.findAll();
        assertThat(lichLamViecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamLichLamViec() throws Exception {
        int databaseSizeBeforeUpdate = lichLamViecRepository.findAll().size();
        lichLamViec.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLichLamViecMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lichLamViec))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LichLamViec in the database
        List<LichLamViec> lichLamViecList = lichLamViecRepository.findAll();
        assertThat(lichLamViecList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteLichLamViec() throws Exception {
        // Initialize the database
        lichLamViecRepository.save(lichLamViec);

        int databaseSizeBeforeDelete = lichLamViecRepository.findAll().size();

        // Delete the lichLamViec
        restLichLamViecMockMvc
            .perform(delete(ENTITY_API_URL_ID, lichLamViec.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LichLamViec> lichLamViecList = lichLamViecRepository.findAll();
        assertThat(lichLamViecList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
