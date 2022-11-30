package com.hust.hustpital.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hust.hustpital.IntegrationTest;
import com.hust.hustpital.domain.Countries;
import com.hust.hustpital.repository.CountriesRepository;
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
 * Integration tests for the {@link CountriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CountriesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/countries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private MockMvc restCountriesMockMvc;

    private Countries countries;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Countries createEntity() {
        Countries countries = new Countries().name(DEFAULT_NAME);
        return countries;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Countries createUpdatedEntity() {
        Countries countries = new Countries().name(UPDATED_NAME);
        return countries;
    }

    @BeforeEach
    public void initTest() {
        countriesRepository.deleteAll();
        countries = createEntity();
    }

    @Test
    void createCountries() throws Exception {
        int databaseSizeBeforeCreate = countriesRepository.findAll().size();
        // Create the Countries
        restCountriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countries)))
            .andExpect(status().isCreated());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeCreate + 1);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void createCountriesWithExistingId() throws Exception {
        // Create the Countries with an existing ID
        countries.setId("existing_id");

        int databaseSizeBeforeCreate = countriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCountriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countries)))
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCountries() throws Exception {
        // Initialize the database
        countriesRepository.save(countries);

        // Get all the countriesList
        restCountriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(countries.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    void getCountries() throws Exception {
        // Initialize the database
        countriesRepository.save(countries);

        // Get the countries
        restCountriesMockMvc
            .perform(get(ENTITY_API_URL_ID, countries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(countries.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingCountries() throws Exception {
        // Get the countries
        restCountriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingCountries() throws Exception {
        // Initialize the database
        countriesRepository.save(countries);

        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();

        // Update the countries
        Countries updatedCountries = countriesRepository.findById(countries.getId()).get();
        updatedCountries.name(UPDATED_NAME);

        restCountriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCountries.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCountries))
            )
            .andExpect(status().isOk());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void putNonExistingCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, countries.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countries))
            )
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(countries))
            )
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(countries)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCountriesWithPatch() throws Exception {
        // Initialize the database
        countriesRepository.save(countries);

        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();

        // Update the countries using partial update
        Countries partialUpdatedCountries = new Countries();
        partialUpdatedCountries.setId(countries.getId());

        partialUpdatedCountries.name(UPDATED_NAME);

        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCountries))
            )
            .andExpect(status().isOk());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void fullUpdateCountriesWithPatch() throws Exception {
        // Initialize the database
        countriesRepository.save(countries);

        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();

        // Update the countries using partial update
        Countries partialUpdatedCountries = new Countries();
        partialUpdatedCountries.setId(countries.getId());

        partialUpdatedCountries.name(UPDATED_NAME);

        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCountries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCountries))
            )
            .andExpect(status().isOk());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
        Countries testCountries = countriesList.get(countriesList.size() - 1);
        assertThat(testCountries.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void patchNonExistingCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, countries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(countries))
            )
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(countries))
            )
            .andExpect(status().isBadRequest());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCountries() throws Exception {
        int databaseSizeBeforeUpdate = countriesRepository.findAll().size();
        countries.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCountriesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(countries))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Countries in the database
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCountries() throws Exception {
        // Initialize the database
        countriesRepository.save(countries);

        int databaseSizeBeforeDelete = countriesRepository.findAll().size();

        // Delete the countries
        restCountriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, countries.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Countries> countriesList = countriesRepository.findAll();
        assertThat(countriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
