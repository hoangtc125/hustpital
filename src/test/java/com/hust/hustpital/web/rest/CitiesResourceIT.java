package com.hust.hustpital.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hust.hustpital.IntegrationTest;
import com.hust.hustpital.domain.Cities;
import com.hust.hustpital.domain.Countries;
import com.hust.hustpital.repository.CitiesRepository;
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
 * Integration tests for the {@link CitiesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CitiesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CitiesRepository citiesRepository;

    @Mock
    private CitiesRepository citiesRepositoryMock;

    @Autowired
    private MockMvc restCitiesMockMvc;

    private Cities cities;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cities createEntity() {
        Cities cities = new Cities().name(DEFAULT_NAME);
        // Add required entity
        Countries countries;
        countries = CountriesResourceIT.createEntity();
        countries.setId("fixed-id-for-tests");
        cities.setCountry(countries);
        return cities;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cities createUpdatedEntity() {
        Cities cities = new Cities().name(UPDATED_NAME);
        // Add required entity
        Countries countries;
        countries = CountriesResourceIT.createUpdatedEntity();
        countries.setId("fixed-id-for-tests");
        cities.setCountry(countries);
        return cities;
    }

    @BeforeEach
    public void initTest() {
        citiesRepository.deleteAll();
        cities = createEntity();
    }

    @Test
    void createCities() throws Exception {
        int databaseSizeBeforeCreate = citiesRepository.findAll().size();
        // Create the Cities
        restCitiesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cities)))
            .andExpect(status().isCreated());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeCreate + 1);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void createCitiesWithExistingId() throws Exception {
        // Create the Cities with an existing ID
        cities.setId("existing_id");

        int databaseSizeBeforeCreate = citiesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitiesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cities)))
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCities() throws Exception {
        // Initialize the database
        citiesRepository.save(cities);

        // Get all the citiesList
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cities.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCitiesWithEagerRelationshipsIsEnabled() throws Exception {
        when(citiesRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCitiesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(citiesRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCitiesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(citiesRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCitiesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(citiesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getCities() throws Exception {
        // Initialize the database
        citiesRepository.save(cities);

        // Get the cities
        restCitiesMockMvc
            .perform(get(ENTITY_API_URL_ID, cities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cities.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingCities() throws Exception {
        // Get the cities
        restCitiesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCities() throws Exception {
        // Initialize the database
        citiesRepository.save(cities);

        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities
        Cities updatedCities = citiesRepository.findById(cities.getId()).get();
        updatedCities.name(UPDATED_NAME);

        restCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCities.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCities))
            )
            .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void putNonExistingCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cities.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cities))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cities))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cities)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCitiesWithPatch() throws Exception {
        // Initialize the database
        citiesRepository.save(cities);

        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities using partial update
        Cities partialUpdatedCities = new Cities();
        partialUpdatedCities.setId(cities.getId());

        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCities.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCities))
            )
            .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void fullUpdateCitiesWithPatch() throws Exception {
        // Initialize the database
        citiesRepository.save(cities);

        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();

        // Update the cities using partial update
        Cities partialUpdatedCities = new Cities();
        partialUpdatedCities.setId(cities.getId());

        partialUpdatedCities.name(UPDATED_NAME);

        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCities.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCities))
            )
            .andExpect(status().isOk());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
        Cities testCities = citiesList.get(citiesList.size() - 1);
        assertThat(testCities.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void patchNonExistingCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cities.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cities))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cities))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCities() throws Exception {
        int databaseSizeBeforeUpdate = citiesRepository.findAll().size();
        cities.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitiesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cities)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cities in the database
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCities() throws Exception {
        // Initialize the database
        citiesRepository.save(cities);

        int databaseSizeBeforeDelete = citiesRepository.findAll().size();

        // Delete the cities
        restCitiesMockMvc
            .perform(delete(ENTITY_API_URL_ID, cities.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cities> citiesList = citiesRepository.findAll();
        assertThat(citiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
