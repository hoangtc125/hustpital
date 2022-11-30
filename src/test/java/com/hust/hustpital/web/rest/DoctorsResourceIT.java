package com.hust.hustpital.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hust.hustpital.IntegrationTest;
import com.hust.hustpital.domain.Doctors;
import com.hust.hustpital.domain.User;
import com.hust.hustpital.domain.enumeration.Gender;
import com.hust.hustpital.repository.DoctorsRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link DoctorsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DoctorsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CITIZEN_IDENTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_CITIZEN_IDENTIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_MA_BHXH = "AAAAAAAAAA";
    private static final String UPDATED_MA_BHXH = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.Male;
    private static final Gender UPDATED_GENDER = Gender.Female;

    private static final Instant DEFAULT_DATE_OF_BIRTH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_BIRTH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_MA_SO_THUE = "AAAAAAAAAA";
    private static final String UPDATED_MA_SO_THUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/doctors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DoctorsRepository doctorsRepository;

    @Mock
    private DoctorsRepository doctorsRepositoryMock;

    @Autowired
    private MockMvc restDoctorsMockMvc;

    private Doctors doctors;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctors createEntity() {
        Doctors doctors = new Doctors()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .citizenIdentification(DEFAULT_CITIZEN_IDENTIFICATION)
            .maBHXH(DEFAULT_MA_BHXH)
            .gender(DEFAULT_GENDER)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .address(DEFAULT_ADDRESS)
            .maSoThue(DEFAULT_MA_SO_THUE);
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        doctors.setUser(user);
        return doctors;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctors createUpdatedEntity() {
        Doctors doctors = new Doctors()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .citizenIdentification(UPDATED_CITIZEN_IDENTIFICATION)
            .maBHXH(UPDATED_MA_BHXH)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .address(UPDATED_ADDRESS)
            .maSoThue(UPDATED_MA_SO_THUE);
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        doctors.setUser(user);
        return doctors;
    }

    @BeforeEach
    public void initTest() {
        doctorsRepository.deleteAll();
        doctors = createEntity();
    }

    @Test
    void createDoctors() throws Exception {
        int databaseSizeBeforeCreate = doctorsRepository.findAll().size();
        // Create the Doctors
        restDoctorsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctors)))
            .andExpect(status().isCreated());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeCreate + 1);
        Doctors testDoctors = doctorsList.get(doctorsList.size() - 1);
        assertThat(testDoctors.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDoctors.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testDoctors.getCitizenIdentification()).isEqualTo(DEFAULT_CITIZEN_IDENTIFICATION);
        assertThat(testDoctors.getMaBHXH()).isEqualTo(DEFAULT_MA_BHXH);
        assertThat(testDoctors.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testDoctors.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testDoctors.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testDoctors.getMaSoThue()).isEqualTo(DEFAULT_MA_SO_THUE);
    }

    @Test
    void createDoctorsWithExistingId() throws Exception {
        // Create the Doctors with an existing ID
        doctors.setId("existing_id");

        int databaseSizeBeforeCreate = doctorsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctors)))
            .andExpect(status().isBadRequest());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllDoctors() throws Exception {
        // Initialize the database
        doctorsRepository.save(doctors);

        // Get all the doctorsList
        restDoctorsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctors.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].citizenIdentification").value(hasItem(DEFAULT_CITIZEN_IDENTIFICATION)))
            .andExpect(jsonPath("$.[*].maBHXH").value(hasItem(DEFAULT_MA_BHXH)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].maSoThue").value(hasItem(DEFAULT_MA_SO_THUE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDoctorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(doctorsRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDoctorsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(doctorsRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDoctorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(doctorsRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDoctorsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(doctorsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getDoctors() throws Exception {
        // Initialize the database
        doctorsRepository.save(doctors);

        // Get the doctors
        restDoctorsMockMvc
            .perform(get(ENTITY_API_URL_ID, doctors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doctors.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.citizenIdentification").value(DEFAULT_CITIZEN_IDENTIFICATION))
            .andExpect(jsonPath("$.maBHXH").value(DEFAULT_MA_BHXH))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.maSoThue").value(DEFAULT_MA_SO_THUE));
    }

    @Test
    void getNonExistingDoctors() throws Exception {
        // Get the doctors
        restDoctorsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingDoctors() throws Exception {
        // Initialize the database
        doctorsRepository.save(doctors);

        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();

        // Update the doctors
        Doctors updatedDoctors = doctorsRepository.findById(doctors.getId()).get();
        updatedDoctors
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .citizenIdentification(UPDATED_CITIZEN_IDENTIFICATION)
            .maBHXH(UPDATED_MA_BHXH)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .address(UPDATED_ADDRESS)
            .maSoThue(UPDATED_MA_SO_THUE);

        restDoctorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDoctors.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDoctors))
            )
            .andExpect(status().isOk());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
        Doctors testDoctors = doctorsList.get(doctorsList.size() - 1);
        assertThat(testDoctors.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDoctors.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testDoctors.getCitizenIdentification()).isEqualTo(UPDATED_CITIZEN_IDENTIFICATION);
        assertThat(testDoctors.getMaBHXH()).isEqualTo(UPDATED_MA_BHXH);
        assertThat(testDoctors.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testDoctors.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testDoctors.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDoctors.getMaSoThue()).isEqualTo(UPDATED_MA_SO_THUE);
    }

    @Test
    void putNonExistingDoctors() throws Exception {
        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();
        doctors.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctors.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctors))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDoctors() throws Exception {
        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();
        doctors.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctors))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDoctors() throws Exception {
        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();
        doctors.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctors)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDoctorsWithPatch() throws Exception {
        // Initialize the database
        doctorsRepository.save(doctors);

        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();

        // Update the doctors using partial update
        Doctors partialUpdatedDoctors = new Doctors();
        partialUpdatedDoctors.setId(doctors.getId());

        partialUpdatedDoctors
            .phone(UPDATED_PHONE)
            .citizenIdentification(UPDATED_CITIZEN_IDENTIFICATION)
            .maBHXH(UPDATED_MA_BHXH)
            .gender(UPDATED_GENDER)
            .maSoThue(UPDATED_MA_SO_THUE);

        restDoctorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctors.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoctors))
            )
            .andExpect(status().isOk());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
        Doctors testDoctors = doctorsList.get(doctorsList.size() - 1);
        assertThat(testDoctors.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDoctors.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testDoctors.getCitizenIdentification()).isEqualTo(UPDATED_CITIZEN_IDENTIFICATION);
        assertThat(testDoctors.getMaBHXH()).isEqualTo(UPDATED_MA_BHXH);
        assertThat(testDoctors.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testDoctors.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testDoctors.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testDoctors.getMaSoThue()).isEqualTo(UPDATED_MA_SO_THUE);
    }

    @Test
    void fullUpdateDoctorsWithPatch() throws Exception {
        // Initialize the database
        doctorsRepository.save(doctors);

        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();

        // Update the doctors using partial update
        Doctors partialUpdatedDoctors = new Doctors();
        partialUpdatedDoctors.setId(doctors.getId());

        partialUpdatedDoctors
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .citizenIdentification(UPDATED_CITIZEN_IDENTIFICATION)
            .maBHXH(UPDATED_MA_BHXH)
            .gender(UPDATED_GENDER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .address(UPDATED_ADDRESS)
            .maSoThue(UPDATED_MA_SO_THUE);

        restDoctorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctors.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoctors))
            )
            .andExpect(status().isOk());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
        Doctors testDoctors = doctorsList.get(doctorsList.size() - 1);
        assertThat(testDoctors.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDoctors.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testDoctors.getCitizenIdentification()).isEqualTo(UPDATED_CITIZEN_IDENTIFICATION);
        assertThat(testDoctors.getMaBHXH()).isEqualTo(UPDATED_MA_BHXH);
        assertThat(testDoctors.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testDoctors.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testDoctors.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDoctors.getMaSoThue()).isEqualTo(UPDATED_MA_SO_THUE);
    }

    @Test
    void patchNonExistingDoctors() throws Exception {
        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();
        doctors.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doctors.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctors))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDoctors() throws Exception {
        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();
        doctors.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctors))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDoctors() throws Exception {
        int databaseSizeBeforeUpdate = doctorsRepository.findAll().size();
        doctors.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(doctors)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doctors in the database
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDoctors() throws Exception {
        // Initialize the database
        doctorsRepository.save(doctors);

        int databaseSizeBeforeDelete = doctorsRepository.findAll().size();

        // Delete the doctors
        restDoctorsMockMvc
            .perform(delete(ENTITY_API_URL_ID, doctors.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Doctors> doctorsList = doctorsRepository.findAll();
        assertThat(doctorsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
