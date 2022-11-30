package com.hust.hustpital.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hust.hustpital.IntegrationTest;
import com.hust.hustpital.domain.Cities;
import com.hust.hustpital.domain.Districts;
import com.hust.hustpital.repository.DistrictsRepository;
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
 * Integration tests for the {@link DistrictsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DistrictsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/districts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DistrictsRepository districtsRepository;

    @Mock
    private DistrictsRepository districtsRepositoryMock;

    @Autowired
    private MockMvc restDistrictsMockMvc;

    private Districts districts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Districts createEntity() {
        Districts districts = new Districts().name(DEFAULT_NAME);
        // Add required entity
        Cities cities;
        cities = CitiesResourceIT.createEntity();
        cities.setId("fixed-id-for-tests");
        districts.setCity(cities);
        return districts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Districts createUpdatedEntity() {
        Districts districts = new Districts().name(UPDATED_NAME);
        // Add required entity
        Cities cities;
        cities = CitiesResourceIT.createUpdatedEntity();
        cities.setId("fixed-id-for-tests");
        districts.setCity(cities);
        return districts;
    }

    @BeforeEach
    public void initTest() {
        districtsRepository.deleteAll();
        districts = createEntity();
    }

    @Test
    void createDistricts() throws Exception {
        int databaseSizeBeforeCreate = districtsRepository.findAll().size();
        // Create the Districts
        restDistrictsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districts)))
            .andExpect(status().isCreated());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeCreate + 1);
        Districts testDistricts = districtsList.get(districtsList.size() - 1);
        assertThat(testDistricts.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void createDistrictsWithExistingId() throws Exception {
        // Create the Districts with an existing ID
        districts.setId("existing_id");

        int databaseSizeBeforeCreate = districtsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districts)))
            .andExpect(status().isBadRequest());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllDistricts() throws Exception {
        // Initialize the database
        districtsRepository.save(districts);

        // Get all the districtsList
        restDistrictsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(districts.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDistrictsWithEagerRelationshipsIsEnabled() throws Exception {
        when(districtsRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDistrictsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(districtsRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDistrictsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(districtsRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDistrictsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(districtsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getDistricts() throws Exception {
        // Initialize the database
        districtsRepository.save(districts);

        // Get the districts
        restDistrictsMockMvc
            .perform(get(ENTITY_API_URL_ID, districts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(districts.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingDistricts() throws Exception {
        // Get the districts
        restDistrictsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingDistricts() throws Exception {
        // Initialize the database
        districtsRepository.save(districts);

        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();

        // Update the districts
        Districts updatedDistricts = districtsRepository.findById(districts.getId()).get();
        updatedDistricts.name(UPDATED_NAME);

        restDistrictsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDistricts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDistricts))
            )
            .andExpect(status().isOk());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
        Districts testDistricts = districtsList.get(districtsList.size() - 1);
        assertThat(testDistricts.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void putNonExistingDistricts() throws Exception {
        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();
        districts.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, districts.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDistricts() throws Exception {
        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();
        districts.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDistricts() throws Exception {
        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();
        districts.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districts)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDistrictsWithPatch() throws Exception {
        // Initialize the database
        districtsRepository.save(districts);

        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();

        // Update the districts using partial update
        Districts partialUpdatedDistricts = new Districts();
        partialUpdatedDistricts.setId(districts.getId());

        restDistrictsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistricts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistricts))
            )
            .andExpect(status().isOk());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
        Districts testDistricts = districtsList.get(districtsList.size() - 1);
        assertThat(testDistricts.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void fullUpdateDistrictsWithPatch() throws Exception {
        // Initialize the database
        districtsRepository.save(districts);

        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();

        // Update the districts using partial update
        Districts partialUpdatedDistricts = new Districts();
        partialUpdatedDistricts.setId(districts.getId());

        partialUpdatedDistricts.name(UPDATED_NAME);

        restDistrictsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistricts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistricts))
            )
            .andExpect(status().isOk());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
        Districts testDistricts = districtsList.get(districtsList.size() - 1);
        assertThat(testDistricts.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void patchNonExistingDistricts() throws Exception {
        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();
        districts.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, districts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDistricts() throws Exception {
        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();
        districts.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districts))
            )
            .andExpect(status().isBadRequest());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDistricts() throws Exception {
        int databaseSizeBeforeUpdate = districtsRepository.findAll().size();
        districts.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(districts))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Districts in the database
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDistricts() throws Exception {
        // Initialize the database
        districtsRepository.save(districts);

        int databaseSizeBeforeDelete = districtsRepository.findAll().size();

        // Delete the districts
        restDistrictsMockMvc
            .perform(delete(ENTITY_API_URL_ID, districts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Districts> districtsList = districtsRepository.findAll();
        assertThat(districtsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
