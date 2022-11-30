package com.hust.hustpital.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hust.hustpital.IntegrationTest;
import com.hust.hustpital.domain.Patients;
import com.hust.hustpital.domain.enumeration.BHYT;
import com.hust.hustpital.domain.enumeration.Gender;
import com.hust.hustpital.repository.PatientsRepository;
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
 * Integration tests for the {@link PatientsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PatientsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.Male;
    private static final Gender UPDATED_GENDER = Gender.Female;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OF_BIRTH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_BIRTH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CITIZEN_IDENTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_CITIZEN_IDENTIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_MA_BHXH = "AAAAAAAAAA";
    private static final String UPDATED_MA_BHXH = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_WORK_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_WORK_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_WORK_ADDRESS = "BBBBBBBBBB";

    private static final BHYT DEFAULT_PATIENT_TYPE = BHYT.Yes;
    private static final BHYT UPDATED_PATIENT_TYPE = BHYT.No;

    private static final String ENTITY_API_URL = "/api/patients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PatientsRepository patientsRepository;

    @Mock
    private PatientsRepository patientsRepositoryMock;

    @Autowired
    private MockMvc restPatientsMockMvc;

    private Patients patients;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patients createEntity() {
        Patients patients = new Patients()
            .name(DEFAULT_NAME)
            .gender(DEFAULT_GENDER)
            .address(DEFAULT_ADDRESS)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .phone(DEFAULT_PHONE)
            .citizenIdentification(DEFAULT_CITIZEN_IDENTIFICATION)
            .maBHXH(DEFAULT_MA_BHXH)
            .workPlace(DEFAULT_WORK_PLACE)
            .workAddress(DEFAULT_WORK_ADDRESS)
            .patientType(DEFAULT_PATIENT_TYPE);
        return patients;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patients createUpdatedEntity() {
        Patients patients = new Patients()
            .name(UPDATED_NAME)
            .gender(UPDATED_GENDER)
            .address(UPDATED_ADDRESS)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .phone(UPDATED_PHONE)
            .citizenIdentification(UPDATED_CITIZEN_IDENTIFICATION)
            .maBHXH(UPDATED_MA_BHXH)
            .workPlace(UPDATED_WORK_PLACE)
            .workAddress(UPDATED_WORK_ADDRESS)
            .patientType(UPDATED_PATIENT_TYPE);
        return patients;
    }

    @BeforeEach
    public void initTest() {
        patientsRepository.deleteAll();
        patients = createEntity();
    }

    @Test
    void createPatients() throws Exception {
        int databaseSizeBeforeCreate = patientsRepository.findAll().size();
        // Create the Patients
        restPatientsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patients)))
            .andExpect(status().isCreated());

        // Validate the Patients in the database
        List<Patients> patientsList = patientsRepository.findAll();
        assertThat(patientsList).hasSize(databaseSizeBeforeCreate + 1);
        Patients testPatients = patientsList.get(patientsList.size() - 1);
        assertThat(testPatients.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPatients.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPatients.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPatients.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testPatients.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPatients.getCitizenIdentification()).isEqualTo(DEFAULT_CITIZEN_IDENTIFICATION);
        assertThat(testPatients.getMaBHXH()).isEqualTo(DEFAULT_MA_BHXH);
        assertThat(testPatients.getWorkPlace()).isEqualTo(DEFAULT_WORK_PLACE);
        assertThat(testPatients.getWorkAddress()).isEqualTo(DEFAULT_WORK_ADDRESS);
        assertThat(testPatients.getPatientType()).isEqualTo(DEFAULT_PATIENT_TYPE);
    }

    @Test
    void createPatientsWithExistingId() throws Exception {
        // Create the Patients with an existing ID
        patients.setId("existing_id");

        int databaseSizeBeforeCreate = patientsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patients)))
            .andExpect(status().isBadRequest());

        // Validate the Patients in the database
        List<Patients> patientsList = patientsRepository.findAll();
        assertThat(patientsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllPatients() throws Exception {
        // Initialize the database
        patientsRepository.save(patients);

        // Get all the patientsList
        restPatientsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patients.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].citizenIdentification").value(hasItem(DEFAULT_CITIZEN_IDENTIFICATION)))
            .andExpect(jsonPath("$.[*].maBHXH").value(hasItem(DEFAULT_MA_BHXH)))
            .andExpect(jsonPath("$.[*].workPlace").value(hasItem(DEFAULT_WORK_PLACE)))
            .andExpect(jsonPath("$.[*].workAddress").value(hasItem(DEFAULT_WORK_ADDRESS)))
            .andExpect(jsonPath("$.[*].patientType").value(hasItem(DEFAULT_PATIENT_TYPE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPatientsWithEagerRelationshipsIsEnabled() throws Exception {
        when(patientsRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPatientsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(patientsRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPatientsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(patientsRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPatientsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(patientsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getPatients() throws Exception {
        // Initialize the database
        patientsRepository.save(patients);

        // Get the patients
        restPatientsMockMvc
            .perform(get(ENTITY_API_URL_ID, patients.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patients.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.citizenIdentification").value(DEFAULT_CITIZEN_IDENTIFICATION))
            .andExpect(jsonPath("$.maBHXH").value(DEFAULT_MA_BHXH))
            .andExpect(jsonPath("$.workPlace").value(DEFAULT_WORK_PLACE))
            .andExpect(jsonPath("$.workAddress").value(DEFAULT_WORK_ADDRESS))
            .andExpect(jsonPath("$.patientType").value(DEFAULT_PATIENT_TYPE.toString()));
    }

    @Test
    void getNonExistingPatients() throws Exception {
        // Get the patients
        restPatientsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPatients() throws Exception {
        // Initialize the database
        patientsRepository.save(patients);

        int databaseSizeBeforeUpdate = patientsRepository.findAll().size();

        // Update the patients
        Patients updatedPatients = patientsRepository.findById(patients.getId()).get();
        updatedPatients
            .name(UPDATED_NAME)
            .gender(UPDATED_GENDER)
            .address(UPDATED_ADDRESS)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .phone(UPDATED_PHONE)
            .citizenIdentification(UPDATED_CITIZEN_IDENTIFICATION)
            .maBHXH(UPDATED_MA_BHXH)
            .workPlace(UPDATED_WORK_PLACE)
            .workAddress(UPDATED_WORK_ADDRESS)
            .patientType(UPDATED_PATIENT_TYPE);

        restPatientsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPatients.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPatients))
            )
            .andExpect(status().isOk());

        // Validate the Patients in the database
        List<Patients> patientsList = patientsRepository.findAll();
        assertThat(patientsList).hasSize(databaseSizeBeforeUpdate);
        Patients testPatients = patientsList.get(patientsList.size() - 1);
        assertThat(testPatients.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPatients.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatients.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPatients.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPatients.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPatients.getCitizenIdentification()).isEqualTo(UPDATED_CITIZEN_IDENTIFICATION);
        assertThat(testPatients.getMaBHXH()).isEqualTo(UPDATED_MA_BHXH);
        assertThat(testPatients.getWorkPlace()).isEqualTo(UPDATED_WORK_PLACE);
        assertThat(testPatients.getWorkAddress()).isEqualTo(UPDATED_WORK_ADDRESS);
        assertThat(testPatients.getPatientType()).isEqualTo(UPDATED_PATIENT_TYPE);
    }

    @Test
    void putNonExistingPatients() throws Exception {
        int databaseSizeBeforeUpdate = patientsRepository.findAll().size();
        patients.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patients.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patients))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patients in the database
        List<Patients> patientsList = patientsRepository.findAll();
        assertThat(patientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPatients() throws Exception {
        int databaseSizeBeforeUpdate = patientsRepository.findAll().size();
        patients.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patients))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patients in the database
        List<Patients> patientsList = patientsRepository.findAll();
        assertThat(patientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPatients() throws Exception {
        int databaseSizeBeforeUpdate = patientsRepository.findAll().size();
        patients.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patients)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Patients in the database
        List<Patients> patientsList = patientsRepository.findAll();
        assertThat(patientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePatientsWithPatch() throws Exception {
        // Initialize the database
        patientsRepository.save(patients);

        int databaseSizeBeforeUpdate = patientsRepository.findAll().size();

        // Update the patients using partial update
        Patients partialUpdatedPatients = new Patients();
        partialUpdatedPatients.setId(patients.getId());

        partialUpdatedPatients.gender(UPDATED_GENDER).maBHXH(UPDATED_MA_BHXH);

        restPatientsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatients.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatients))
            )
            .andExpect(status().isOk());

        // Validate the Patients in the database
        List<Patients> patientsList = patientsRepository.findAll();
        assertThat(patientsList).hasSize(databaseSizeBeforeUpdate);
        Patients testPatients = patientsList.get(patientsList.size() - 1);
        assertThat(testPatients.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPatients.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatients.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPatients.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testPatients.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPatients.getCitizenIdentification()).isEqualTo(DEFAULT_CITIZEN_IDENTIFICATION);
        assertThat(testPatients.getMaBHXH()).isEqualTo(UPDATED_MA_BHXH);
        assertThat(testPatients.getWorkPlace()).isEqualTo(DEFAULT_WORK_PLACE);
        assertThat(testPatients.getWorkAddress()).isEqualTo(DEFAULT_WORK_ADDRESS);
        assertThat(testPatients.getPatientType()).isEqualTo(DEFAULT_PATIENT_TYPE);
    }

    @Test
    void fullUpdatePatientsWithPatch() throws Exception {
        // Initialize the database
        patientsRepository.save(patients);

        int databaseSizeBeforeUpdate = patientsRepository.findAll().size();

        // Update the patients using partial update
        Patients partialUpdatedPatients = new Patients();
        partialUpdatedPatients.setId(patients.getId());

        partialUpdatedPatients
            .name(UPDATED_NAME)
            .gender(UPDATED_GENDER)
            .address(UPDATED_ADDRESS)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .phone(UPDATED_PHONE)
            .citizenIdentification(UPDATED_CITIZEN_IDENTIFICATION)
            .maBHXH(UPDATED_MA_BHXH)
            .workPlace(UPDATED_WORK_PLACE)
            .workAddress(UPDATED_WORK_ADDRESS)
            .patientType(UPDATED_PATIENT_TYPE);

        restPatientsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatients.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatients))
            )
            .andExpect(status().isOk());

        // Validate the Patients in the database
        List<Patients> patientsList = patientsRepository.findAll();
        assertThat(patientsList).hasSize(databaseSizeBeforeUpdate);
        Patients testPatients = patientsList.get(patientsList.size() - 1);
        assertThat(testPatients.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPatients.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatients.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPatients.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPatients.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPatients.getCitizenIdentification()).isEqualTo(UPDATED_CITIZEN_IDENTIFICATION);
        assertThat(testPatients.getMaBHXH()).isEqualTo(UPDATED_MA_BHXH);
        assertThat(testPatients.getWorkPlace()).isEqualTo(UPDATED_WORK_PLACE);
        assertThat(testPatients.getWorkAddress()).isEqualTo(UPDATED_WORK_ADDRESS);
        assertThat(testPatients.getPatientType()).isEqualTo(UPDATED_PATIENT_TYPE);
    }

    @Test
    void patchNonExistingPatients() throws Exception {
        int databaseSizeBeforeUpdate = patientsRepository.findAll().size();
        patients.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, patients.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patients))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patients in the database
        List<Patients> patientsList = patientsRepository.findAll();
        assertThat(patientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPatients() throws Exception {
        int databaseSizeBeforeUpdate = patientsRepository.findAll().size();
        patients.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patients))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patients in the database
        List<Patients> patientsList = patientsRepository.findAll();
        assertThat(patientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPatients() throws Exception {
        int databaseSizeBeforeUpdate = patientsRepository.findAll().size();
        patients.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(patients)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Patients in the database
        List<Patients> patientsList = patientsRepository.findAll();
        assertThat(patientsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePatients() throws Exception {
        // Initialize the database
        patientsRepository.save(patients);

        int databaseSizeBeforeDelete = patientsRepository.findAll().size();

        // Delete the patients
        restPatientsMockMvc
            .perform(delete(ENTITY_API_URL_ID, patients.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Patients> patientsList = patientsRepository.findAll();
        assertThat(patientsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
