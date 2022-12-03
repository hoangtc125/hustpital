package com.hust.hustpital.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hust.hustpital.IntegrationTest;
import com.hust.hustpital.domain.Districts;
import com.hust.hustpital.domain.Wards;
import com.hust.hustpital.repository.WardsRepository;
import com.hust.hustpital.service.WardsService;
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
 * Integration tests for the {@link WardsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class WardsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/wards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private WardsRepository wardsRepository;

    @Mock
    private WardsRepository wardsRepositoryMock;

    @Mock
    private WardsService wardsServiceMock;

    @Autowired
    private MockMvc restWardsMockMvc;

    private Wards wards;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wards createEntity() {
        Wards wards = new Wards().name(DEFAULT_NAME);
        // Add required entity
        Districts districts;
        districts = DistrictsResourceIT.createEntity();
        districts.setId("fixed-id-for-tests");
        wards.setDistrict(districts);
        return wards;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Wards createUpdatedEntity() {
        Wards wards = new Wards().name(UPDATED_NAME);
        // Add required entity
        Districts districts;
        districts = DistrictsResourceIT.createUpdatedEntity();
        districts.setId("fixed-id-for-tests");
        wards.setDistrict(districts);
        return wards;
    }

    @BeforeEach
    public void initTest() {
        wardsRepository.deleteAll();
        wards = createEntity();
    }

    @Test
    void createWards() throws Exception {
        int databaseSizeBeforeCreate = wardsRepository.findAll().size();
        // Create the Wards
        restWardsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wards)))
            .andExpect(status().isCreated());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeCreate + 1);
        Wards testWards = wardsList.get(wardsList.size() - 1);
        assertThat(testWards.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void createWardsWithExistingId() throws Exception {
        // Create the Wards with an existing ID
        wards.setId("existing_id");

        int databaseSizeBeforeCreate = wardsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWardsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wards)))
            .andExpect(status().isBadRequest());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllWards() throws Exception {
        // Initialize the database
        wardsRepository.save(wards);

        // Get all the wardsList
        restWardsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wards.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWardsWithEagerRelationshipsIsEnabled() throws Exception {
        when(wardsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWardsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(wardsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWardsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(wardsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWardsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(wardsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getWards() throws Exception {
        // Initialize the database
        wardsRepository.save(wards);

        // Get the wards
        restWardsMockMvc
            .perform(get(ENTITY_API_URL_ID, wards.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wards.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingWards() throws Exception {
        // Get the wards
        restWardsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingWards() throws Exception {
        // Initialize the database
        wardsRepository.save(wards);

        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();

        // Update the wards
        Wards updatedWards = wardsRepository.findById(wards.getId()).get();
        updatedWards.name(UPDATED_NAME);

        restWardsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWards.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWards))
            )
            .andExpect(status().isOk());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
        Wards testWards = wardsList.get(wardsList.size() - 1);
        assertThat(testWards.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void putNonExistingWards() throws Exception {
        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();
        wards.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWardsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wards.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wards))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchWards() throws Exception {
        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();
        wards.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wards))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamWards() throws Exception {
        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();
        wards.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wards)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateWardsWithPatch() throws Exception {
        // Initialize the database
        wardsRepository.save(wards);

        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();

        // Update the wards using partial update
        Wards partialUpdatedWards = new Wards();
        partialUpdatedWards.setId(wards.getId());

        restWardsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWards.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWards))
            )
            .andExpect(status().isOk());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
        Wards testWards = wardsList.get(wardsList.size() - 1);
        assertThat(testWards.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void fullUpdateWardsWithPatch() throws Exception {
        // Initialize the database
        wardsRepository.save(wards);

        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();

        // Update the wards using partial update
        Wards partialUpdatedWards = new Wards();
        partialUpdatedWards.setId(wards.getId());

        partialUpdatedWards.name(UPDATED_NAME);

        restWardsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWards.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWards))
            )
            .andExpect(status().isOk());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
        Wards testWards = wardsList.get(wardsList.size() - 1);
        assertThat(testWards.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void patchNonExistingWards() throws Exception {
        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();
        wards.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWardsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wards.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wards))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchWards() throws Exception {
        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();
        wards.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wards))
            )
            .andExpect(status().isBadRequest());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamWards() throws Exception {
        int databaseSizeBeforeUpdate = wardsRepository.findAll().size();
        wards.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWardsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(wards)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Wards in the database
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteWards() throws Exception {
        // Initialize the database
        wardsRepository.save(wards);

        int databaseSizeBeforeDelete = wardsRepository.findAll().size();

        // Delete the wards
        restWardsMockMvc
            .perform(delete(ENTITY_API_URL_ID, wards.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wards> wardsList = wardsRepository.findAll();
        assertThat(wardsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
