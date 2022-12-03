package com.hust.hustpital.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hust.hustpital.IntegrationTest;
import com.hust.hustpital.domain.LichHen;
import com.hust.hustpital.domain.enumeration.Admission;
import com.hust.hustpital.repository.LichHenRepository;
import com.hust.hustpital.service.LichHenService;
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
 * Integration tests for the {@link LichHenResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class LichHenResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_LY_DO_KHAM = "AAAAAAAAAA";
    private static final String UPDATED_LY_DO_KHAM = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OF_BIRTH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_BIRTH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Admission DEFAULT_LICHHEN_TYPE = Admission.DatChoBanThan;
    private static final Admission UPDATED_LICHHEN_TYPE = Admission.DatChoNguoiThan;

    private static final String ENTITY_API_URL = "/api/lich-hens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private LichHenRepository lichHenRepository;

    @Mock
    private LichHenRepository lichHenRepositoryMock;

    @Mock
    private LichHenService lichHenServiceMock;

    @Autowired
    private MockMvc restLichHenMockMvc;

    private LichHen lichHen;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LichHen createEntity() {
        LichHen lichHen = new LichHen()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .lyDoKham(DEFAULT_LY_DO_KHAM)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .lichhenType(DEFAULT_LICHHEN_TYPE);
        return lichHen;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LichHen createUpdatedEntity() {
        LichHen lichHen = new LichHen()
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .lyDoKham(UPDATED_LY_DO_KHAM)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .lichhenType(UPDATED_LICHHEN_TYPE);
        return lichHen;
    }

    @BeforeEach
    public void initTest() {
        lichHenRepository.deleteAll();
        lichHen = createEntity();
    }

    @Test
    void createLichHen() throws Exception {
        int databaseSizeBeforeCreate = lichHenRepository.findAll().size();
        // Create the LichHen
        restLichHenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lichHen)))
            .andExpect(status().isCreated());

        // Validate the LichHen in the database
        List<LichHen> lichHenList = lichHenRepository.findAll();
        assertThat(lichHenList).hasSize(databaseSizeBeforeCreate + 1);
        LichHen testLichHen = lichHenList.get(lichHenList.size() - 1);
        assertThat(testLichHen.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLichHen.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testLichHen.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLichHen.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testLichHen.getLyDoKham()).isEqualTo(DEFAULT_LY_DO_KHAM);
        assertThat(testLichHen.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testLichHen.getLichhenType()).isEqualTo(DEFAULT_LICHHEN_TYPE);
    }

    @Test
    void createLichHenWithExistingId() throws Exception {
        // Create the LichHen with an existing ID
        lichHen.setId("existing_id");

        int databaseSizeBeforeCreate = lichHenRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLichHenMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lichHen)))
            .andExpect(status().isBadRequest());

        // Validate the LichHen in the database
        List<LichHen> lichHenList = lichHenRepository.findAll();
        assertThat(lichHenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllLichHens() throws Exception {
        // Initialize the database
        lichHenRepository.save(lichHen);

        // Get all the lichHenList
        restLichHenMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lichHen.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].lyDoKham").value(hasItem(DEFAULT_LY_DO_KHAM)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].lichhenType").value(hasItem(DEFAULT_LICHHEN_TYPE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLichHensWithEagerRelationshipsIsEnabled() throws Exception {
        when(lichHenServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLichHenMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(lichHenServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllLichHensWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(lichHenServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restLichHenMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(lichHenRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getLichHen() throws Exception {
        // Initialize the database
        lichHenRepository.save(lichHen);

        // Get the lichHen
        restLichHenMockMvc
            .perform(get(ENTITY_API_URL_ID, lichHen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lichHen.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.lyDoKham").value(DEFAULT_LY_DO_KHAM))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.lichhenType").value(DEFAULT_LICHHEN_TYPE.toString()));
    }

    @Test
    void getNonExistingLichHen() throws Exception {
        // Get the lichHen
        restLichHenMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingLichHen() throws Exception {
        // Initialize the database
        lichHenRepository.save(lichHen);

        int databaseSizeBeforeUpdate = lichHenRepository.findAll().size();

        // Update the lichHen
        LichHen updatedLichHen = lichHenRepository.findById(lichHen.getId()).get();
        updatedLichHen
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .lyDoKham(UPDATED_LY_DO_KHAM)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .lichhenType(UPDATED_LICHHEN_TYPE);

        restLichHenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedLichHen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedLichHen))
            )
            .andExpect(status().isOk());

        // Validate the LichHen in the database
        List<LichHen> lichHenList = lichHenRepository.findAll();
        assertThat(lichHenList).hasSize(databaseSizeBeforeUpdate);
        LichHen testLichHen = lichHenList.get(lichHenList.size() - 1);
        assertThat(testLichHen.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLichHen.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testLichHen.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLichHen.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testLichHen.getLyDoKham()).isEqualTo(UPDATED_LY_DO_KHAM);
        assertThat(testLichHen.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testLichHen.getLichhenType()).isEqualTo(UPDATED_LICHHEN_TYPE);
    }

    @Test
    void putNonExistingLichHen() throws Exception {
        int databaseSizeBeforeUpdate = lichHenRepository.findAll().size();
        lichHen.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLichHenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, lichHen.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lichHen))
            )
            .andExpect(status().isBadRequest());

        // Validate the LichHen in the database
        List<LichHen> lichHenList = lichHenRepository.findAll();
        assertThat(lichHenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchLichHen() throws Exception {
        int databaseSizeBeforeUpdate = lichHenRepository.findAll().size();
        lichHen.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLichHenMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(lichHen))
            )
            .andExpect(status().isBadRequest());

        // Validate the LichHen in the database
        List<LichHen> lichHenList = lichHenRepository.findAll();
        assertThat(lichHenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamLichHen() throws Exception {
        int databaseSizeBeforeUpdate = lichHenRepository.findAll().size();
        lichHen.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLichHenMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(lichHen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LichHen in the database
        List<LichHen> lichHenList = lichHenRepository.findAll();
        assertThat(lichHenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateLichHenWithPatch() throws Exception {
        // Initialize the database
        lichHenRepository.save(lichHen);

        int databaseSizeBeforeUpdate = lichHenRepository.findAll().size();

        // Update the lichHen using partial update
        LichHen partialUpdatedLichHen = new LichHen();
        partialUpdatedLichHen.setId(lichHen.getId());

        partialUpdatedLichHen
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .lyDoKham(UPDATED_LY_DO_KHAM)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH);

        restLichHenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLichHen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLichHen))
            )
            .andExpect(status().isOk());

        // Validate the LichHen in the database
        List<LichHen> lichHenList = lichHenRepository.findAll();
        assertThat(lichHenList).hasSize(databaseSizeBeforeUpdate);
        LichHen testLichHen = lichHenList.get(lichHenList.size() - 1);
        assertThat(testLichHen.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLichHen.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testLichHen.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLichHen.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testLichHen.getLyDoKham()).isEqualTo(UPDATED_LY_DO_KHAM);
        assertThat(testLichHen.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testLichHen.getLichhenType()).isEqualTo(DEFAULT_LICHHEN_TYPE);
    }

    @Test
    void fullUpdateLichHenWithPatch() throws Exception {
        // Initialize the database
        lichHenRepository.save(lichHen);

        int databaseSizeBeforeUpdate = lichHenRepository.findAll().size();

        // Update the lichHen using partial update
        LichHen partialUpdatedLichHen = new LichHen();
        partialUpdatedLichHen.setId(lichHen.getId());

        partialUpdatedLichHen
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .lyDoKham(UPDATED_LY_DO_KHAM)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .lichhenType(UPDATED_LICHHEN_TYPE);

        restLichHenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLichHen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLichHen))
            )
            .andExpect(status().isOk());

        // Validate the LichHen in the database
        List<LichHen> lichHenList = lichHenRepository.findAll();
        assertThat(lichHenList).hasSize(databaseSizeBeforeUpdate);
        LichHen testLichHen = lichHenList.get(lichHenList.size() - 1);
        assertThat(testLichHen.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLichHen.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testLichHen.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLichHen.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testLichHen.getLyDoKham()).isEqualTo(UPDATED_LY_DO_KHAM);
        assertThat(testLichHen.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testLichHen.getLichhenType()).isEqualTo(UPDATED_LICHHEN_TYPE);
    }

    @Test
    void patchNonExistingLichHen() throws Exception {
        int databaseSizeBeforeUpdate = lichHenRepository.findAll().size();
        lichHen.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLichHenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, lichHen.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lichHen))
            )
            .andExpect(status().isBadRequest());

        // Validate the LichHen in the database
        List<LichHen> lichHenList = lichHenRepository.findAll();
        assertThat(lichHenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchLichHen() throws Exception {
        int databaseSizeBeforeUpdate = lichHenRepository.findAll().size();
        lichHen.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLichHenMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(lichHen))
            )
            .andExpect(status().isBadRequest());

        // Validate the LichHen in the database
        List<LichHen> lichHenList = lichHenRepository.findAll();
        assertThat(lichHenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamLichHen() throws Exception {
        int databaseSizeBeforeUpdate = lichHenRepository.findAll().size();
        lichHen.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLichHenMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(lichHen)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LichHen in the database
        List<LichHen> lichHenList = lichHenRepository.findAll();
        assertThat(lichHenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteLichHen() throws Exception {
        // Initialize the database
        lichHenRepository.save(lichHen);

        int databaseSizeBeforeDelete = lichHenRepository.findAll().size();

        // Delete the lichHen
        restLichHenMockMvc
            .perform(delete(ENTITY_API_URL_ID, lichHen.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LichHen> lichHenList = lichHenRepository.findAll();
        assertThat(lichHenList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
