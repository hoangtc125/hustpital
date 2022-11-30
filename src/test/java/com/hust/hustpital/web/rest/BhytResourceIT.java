package com.hust.hustpital.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hust.hustpital.IntegrationTest;
import com.hust.hustpital.domain.Bhyt;
import com.hust.hustpital.repository.BhytRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link BhytResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BhytResourceIT {

    private static final String DEFAULT_QRCODE = "AAAAAAAAAA";
    private static final String UPDATED_QRCODE = "BBBBBBBBBB";

    private static final String DEFAULT_SOTHE = "AAAAAAAAAA";
    private static final String UPDATED_SOTHE = "BBBBBBBBBB";

    private static final String DEFAULT_MA_KCBBD = "AAAAAAAAAA";
    private static final String UPDATED_MA_KCBBD = "BBBBBBBBBB";

    private static final String DEFAULT_DIACHI = "AAAAAAAAAA";
    private static final String UPDATED_DIACHI = "BBBBBBBBBB";

    private static final Instant DEFAULT_NGAY_BAT_DAU = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_BAT_DAU = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NGAY_KET_THUC = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_KET_THUC = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NGAY_BAT_DAU_5_NAM_LT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_BAT_DAU_5_NAM_LT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NGAY_BAT_DAU_MIEN_CCT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_BAT_DAU_MIEN_CCT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NGAY_KET_THUC_MIEN_CCT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_KET_THUC_MIEN_CCT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/bhyts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BhytRepository bhytRepository;

    @Autowired
    private MockMvc restBhytMockMvc;

    private Bhyt bhyt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bhyt createEntity() {
        Bhyt bhyt = new Bhyt()
            .qrcode(DEFAULT_QRCODE)
            .sothe(DEFAULT_SOTHE)
            .maKCBBD(DEFAULT_MA_KCBBD)
            .diachi(DEFAULT_DIACHI)
            .ngayBatDau(DEFAULT_NGAY_BAT_DAU)
            .ngayKetThuc(DEFAULT_NGAY_KET_THUC)
            .ngayBatDau5namLT(DEFAULT_NGAY_BAT_DAU_5_NAM_LT)
            .ngayBatDauMienCCT(DEFAULT_NGAY_BAT_DAU_MIEN_CCT)
            .ngayKetThucMienCCT(DEFAULT_NGAY_KET_THUC_MIEN_CCT);
        return bhyt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bhyt createUpdatedEntity() {
        Bhyt bhyt = new Bhyt()
            .qrcode(UPDATED_QRCODE)
            .sothe(UPDATED_SOTHE)
            .maKCBBD(UPDATED_MA_KCBBD)
            .diachi(UPDATED_DIACHI)
            .ngayBatDau(UPDATED_NGAY_BAT_DAU)
            .ngayKetThuc(UPDATED_NGAY_KET_THUC)
            .ngayBatDau5namLT(UPDATED_NGAY_BAT_DAU_5_NAM_LT)
            .ngayBatDauMienCCT(UPDATED_NGAY_BAT_DAU_MIEN_CCT)
            .ngayKetThucMienCCT(UPDATED_NGAY_KET_THUC_MIEN_CCT);
        return bhyt;
    }

    @BeforeEach
    public void initTest() {
        bhytRepository.deleteAll();
        bhyt = createEntity();
    }

    @Test
    void createBhyt() throws Exception {
        int databaseSizeBeforeCreate = bhytRepository.findAll().size();
        // Create the Bhyt
        restBhytMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bhyt)))
            .andExpect(status().isCreated());

        // Validate the Bhyt in the database
        List<Bhyt> bhytList = bhytRepository.findAll();
        assertThat(bhytList).hasSize(databaseSizeBeforeCreate + 1);
        Bhyt testBhyt = bhytList.get(bhytList.size() - 1);
        assertThat(testBhyt.getQrcode()).isEqualTo(DEFAULT_QRCODE);
        assertThat(testBhyt.getSothe()).isEqualTo(DEFAULT_SOTHE);
        assertThat(testBhyt.getMaKCBBD()).isEqualTo(DEFAULT_MA_KCBBD);
        assertThat(testBhyt.getDiachi()).isEqualTo(DEFAULT_DIACHI);
        assertThat(testBhyt.getNgayBatDau()).isEqualTo(DEFAULT_NGAY_BAT_DAU);
        assertThat(testBhyt.getNgayKetThuc()).isEqualTo(DEFAULT_NGAY_KET_THUC);
        assertThat(testBhyt.getNgayBatDau5namLT()).isEqualTo(DEFAULT_NGAY_BAT_DAU_5_NAM_LT);
        assertThat(testBhyt.getNgayBatDauMienCCT()).isEqualTo(DEFAULT_NGAY_BAT_DAU_MIEN_CCT);
        assertThat(testBhyt.getNgayKetThucMienCCT()).isEqualTo(DEFAULT_NGAY_KET_THUC_MIEN_CCT);
    }

    @Test
    void createBhytWithExistingId() throws Exception {
        // Create the Bhyt with an existing ID
        bhyt.setId("existing_id");

        int databaseSizeBeforeCreate = bhytRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBhytMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bhyt)))
            .andExpect(status().isBadRequest());

        // Validate the Bhyt in the database
        List<Bhyt> bhytList = bhytRepository.findAll();
        assertThat(bhytList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllBhyts() throws Exception {
        // Initialize the database
        bhytRepository.save(bhyt);

        // Get all the bhytList
        restBhytMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bhyt.getId())))
            .andExpect(jsonPath("$.[*].qrcode").value(hasItem(DEFAULT_QRCODE)))
            .andExpect(jsonPath("$.[*].sothe").value(hasItem(DEFAULT_SOTHE)))
            .andExpect(jsonPath("$.[*].maKCBBD").value(hasItem(DEFAULT_MA_KCBBD)))
            .andExpect(jsonPath("$.[*].diachi").value(hasItem(DEFAULT_DIACHI)))
            .andExpect(jsonPath("$.[*].ngayBatDau").value(hasItem(DEFAULT_NGAY_BAT_DAU.toString())))
            .andExpect(jsonPath("$.[*].ngayKetThuc").value(hasItem(DEFAULT_NGAY_KET_THUC.toString())))
            .andExpect(jsonPath("$.[*].ngayBatDau5namLT").value(hasItem(DEFAULT_NGAY_BAT_DAU_5_NAM_LT.toString())))
            .andExpect(jsonPath("$.[*].ngayBatDauMienCCT").value(hasItem(DEFAULT_NGAY_BAT_DAU_MIEN_CCT.toString())))
            .andExpect(jsonPath("$.[*].ngayKetThucMienCCT").value(hasItem(DEFAULT_NGAY_KET_THUC_MIEN_CCT.toString())));
    }

    @Test
    void getBhyt() throws Exception {
        // Initialize the database
        bhytRepository.save(bhyt);

        // Get the bhyt
        restBhytMockMvc
            .perform(get(ENTITY_API_URL_ID, bhyt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bhyt.getId()))
            .andExpect(jsonPath("$.qrcode").value(DEFAULT_QRCODE))
            .andExpect(jsonPath("$.sothe").value(DEFAULT_SOTHE))
            .andExpect(jsonPath("$.maKCBBD").value(DEFAULT_MA_KCBBD))
            .andExpect(jsonPath("$.diachi").value(DEFAULT_DIACHI))
            .andExpect(jsonPath("$.ngayBatDau").value(DEFAULT_NGAY_BAT_DAU.toString()))
            .andExpect(jsonPath("$.ngayKetThuc").value(DEFAULT_NGAY_KET_THUC.toString()))
            .andExpect(jsonPath("$.ngayBatDau5namLT").value(DEFAULT_NGAY_BAT_DAU_5_NAM_LT.toString()))
            .andExpect(jsonPath("$.ngayBatDauMienCCT").value(DEFAULT_NGAY_BAT_DAU_MIEN_CCT.toString()))
            .andExpect(jsonPath("$.ngayKetThucMienCCT").value(DEFAULT_NGAY_KET_THUC_MIEN_CCT.toString()));
    }

    @Test
    void getNonExistingBhyt() throws Exception {
        // Get the bhyt
        restBhytMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingBhyt() throws Exception {
        // Initialize the database
        bhytRepository.save(bhyt);

        int databaseSizeBeforeUpdate = bhytRepository.findAll().size();

        // Update the bhyt
        Bhyt updatedBhyt = bhytRepository.findById(bhyt.getId()).get();
        updatedBhyt
            .qrcode(UPDATED_QRCODE)
            .sothe(UPDATED_SOTHE)
            .maKCBBD(UPDATED_MA_KCBBD)
            .diachi(UPDATED_DIACHI)
            .ngayBatDau(UPDATED_NGAY_BAT_DAU)
            .ngayKetThuc(UPDATED_NGAY_KET_THUC)
            .ngayBatDau5namLT(UPDATED_NGAY_BAT_DAU_5_NAM_LT)
            .ngayBatDauMienCCT(UPDATED_NGAY_BAT_DAU_MIEN_CCT)
            .ngayKetThucMienCCT(UPDATED_NGAY_KET_THUC_MIEN_CCT);

        restBhytMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBhyt.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBhyt))
            )
            .andExpect(status().isOk());

        // Validate the Bhyt in the database
        List<Bhyt> bhytList = bhytRepository.findAll();
        assertThat(bhytList).hasSize(databaseSizeBeforeUpdate);
        Bhyt testBhyt = bhytList.get(bhytList.size() - 1);
        assertThat(testBhyt.getQrcode()).isEqualTo(UPDATED_QRCODE);
        assertThat(testBhyt.getSothe()).isEqualTo(UPDATED_SOTHE);
        assertThat(testBhyt.getMaKCBBD()).isEqualTo(UPDATED_MA_KCBBD);
        assertThat(testBhyt.getDiachi()).isEqualTo(UPDATED_DIACHI);
        assertThat(testBhyt.getNgayBatDau()).isEqualTo(UPDATED_NGAY_BAT_DAU);
        assertThat(testBhyt.getNgayKetThuc()).isEqualTo(UPDATED_NGAY_KET_THUC);
        assertThat(testBhyt.getNgayBatDau5namLT()).isEqualTo(UPDATED_NGAY_BAT_DAU_5_NAM_LT);
        assertThat(testBhyt.getNgayBatDauMienCCT()).isEqualTo(UPDATED_NGAY_BAT_DAU_MIEN_CCT);
        assertThat(testBhyt.getNgayKetThucMienCCT()).isEqualTo(UPDATED_NGAY_KET_THUC_MIEN_CCT);
    }

    @Test
    void putNonExistingBhyt() throws Exception {
        int databaseSizeBeforeUpdate = bhytRepository.findAll().size();
        bhyt.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBhytMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bhyt.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bhyt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bhyt in the database
        List<Bhyt> bhytList = bhytRepository.findAll();
        assertThat(bhytList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchBhyt() throws Exception {
        int databaseSizeBeforeUpdate = bhytRepository.findAll().size();
        bhyt.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBhytMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bhyt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bhyt in the database
        List<Bhyt> bhytList = bhytRepository.findAll();
        assertThat(bhytList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamBhyt() throws Exception {
        int databaseSizeBeforeUpdate = bhytRepository.findAll().size();
        bhyt.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBhytMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bhyt)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bhyt in the database
        List<Bhyt> bhytList = bhytRepository.findAll();
        assertThat(bhytList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateBhytWithPatch() throws Exception {
        // Initialize the database
        bhytRepository.save(bhyt);

        int databaseSizeBeforeUpdate = bhytRepository.findAll().size();

        // Update the bhyt using partial update
        Bhyt partialUpdatedBhyt = new Bhyt();
        partialUpdatedBhyt.setId(bhyt.getId());

        partialUpdatedBhyt.maKCBBD(UPDATED_MA_KCBBD).ngayKetThuc(UPDATED_NGAY_KET_THUC);

        restBhytMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBhyt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBhyt))
            )
            .andExpect(status().isOk());

        // Validate the Bhyt in the database
        List<Bhyt> bhytList = bhytRepository.findAll();
        assertThat(bhytList).hasSize(databaseSizeBeforeUpdate);
        Bhyt testBhyt = bhytList.get(bhytList.size() - 1);
        assertThat(testBhyt.getQrcode()).isEqualTo(DEFAULT_QRCODE);
        assertThat(testBhyt.getSothe()).isEqualTo(DEFAULT_SOTHE);
        assertThat(testBhyt.getMaKCBBD()).isEqualTo(UPDATED_MA_KCBBD);
        assertThat(testBhyt.getDiachi()).isEqualTo(DEFAULT_DIACHI);
        assertThat(testBhyt.getNgayBatDau()).isEqualTo(DEFAULT_NGAY_BAT_DAU);
        assertThat(testBhyt.getNgayKetThuc()).isEqualTo(UPDATED_NGAY_KET_THUC);
        assertThat(testBhyt.getNgayBatDau5namLT()).isEqualTo(DEFAULT_NGAY_BAT_DAU_5_NAM_LT);
        assertThat(testBhyt.getNgayBatDauMienCCT()).isEqualTo(DEFAULT_NGAY_BAT_DAU_MIEN_CCT);
        assertThat(testBhyt.getNgayKetThucMienCCT()).isEqualTo(DEFAULT_NGAY_KET_THUC_MIEN_CCT);
    }

    @Test
    void fullUpdateBhytWithPatch() throws Exception {
        // Initialize the database
        bhytRepository.save(bhyt);

        int databaseSizeBeforeUpdate = bhytRepository.findAll().size();

        // Update the bhyt using partial update
        Bhyt partialUpdatedBhyt = new Bhyt();
        partialUpdatedBhyt.setId(bhyt.getId());

        partialUpdatedBhyt
            .qrcode(UPDATED_QRCODE)
            .sothe(UPDATED_SOTHE)
            .maKCBBD(UPDATED_MA_KCBBD)
            .diachi(UPDATED_DIACHI)
            .ngayBatDau(UPDATED_NGAY_BAT_DAU)
            .ngayKetThuc(UPDATED_NGAY_KET_THUC)
            .ngayBatDau5namLT(UPDATED_NGAY_BAT_DAU_5_NAM_LT)
            .ngayBatDauMienCCT(UPDATED_NGAY_BAT_DAU_MIEN_CCT)
            .ngayKetThucMienCCT(UPDATED_NGAY_KET_THUC_MIEN_CCT);

        restBhytMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBhyt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBhyt))
            )
            .andExpect(status().isOk());

        // Validate the Bhyt in the database
        List<Bhyt> bhytList = bhytRepository.findAll();
        assertThat(bhytList).hasSize(databaseSizeBeforeUpdate);
        Bhyt testBhyt = bhytList.get(bhytList.size() - 1);
        assertThat(testBhyt.getQrcode()).isEqualTo(UPDATED_QRCODE);
        assertThat(testBhyt.getSothe()).isEqualTo(UPDATED_SOTHE);
        assertThat(testBhyt.getMaKCBBD()).isEqualTo(UPDATED_MA_KCBBD);
        assertThat(testBhyt.getDiachi()).isEqualTo(UPDATED_DIACHI);
        assertThat(testBhyt.getNgayBatDau()).isEqualTo(UPDATED_NGAY_BAT_DAU);
        assertThat(testBhyt.getNgayKetThuc()).isEqualTo(UPDATED_NGAY_KET_THUC);
        assertThat(testBhyt.getNgayBatDau5namLT()).isEqualTo(UPDATED_NGAY_BAT_DAU_5_NAM_LT);
        assertThat(testBhyt.getNgayBatDauMienCCT()).isEqualTo(UPDATED_NGAY_BAT_DAU_MIEN_CCT);
        assertThat(testBhyt.getNgayKetThucMienCCT()).isEqualTo(UPDATED_NGAY_KET_THUC_MIEN_CCT);
    }

    @Test
    void patchNonExistingBhyt() throws Exception {
        int databaseSizeBeforeUpdate = bhytRepository.findAll().size();
        bhyt.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBhytMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bhyt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bhyt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bhyt in the database
        List<Bhyt> bhytList = bhytRepository.findAll();
        assertThat(bhytList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchBhyt() throws Exception {
        int databaseSizeBeforeUpdate = bhytRepository.findAll().size();
        bhyt.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBhytMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bhyt))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bhyt in the database
        List<Bhyt> bhytList = bhytRepository.findAll();
        assertThat(bhytList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamBhyt() throws Exception {
        int databaseSizeBeforeUpdate = bhytRepository.findAll().size();
        bhyt.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBhytMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bhyt)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bhyt in the database
        List<Bhyt> bhytList = bhytRepository.findAll();
        assertThat(bhytList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteBhyt() throws Exception {
        // Initialize the database
        bhytRepository.save(bhyt);

        int databaseSizeBeforeDelete = bhytRepository.findAll().size();

        // Delete the bhyt
        restBhytMockMvc
            .perform(delete(ENTITY_API_URL_ID, bhyt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bhyt> bhytList = bhytRepository.findAll();
        assertThat(bhytList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
