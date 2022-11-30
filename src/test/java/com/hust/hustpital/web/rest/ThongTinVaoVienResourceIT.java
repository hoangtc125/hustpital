package com.hust.hustpital.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hust.hustpital.IntegrationTest;
import com.hust.hustpital.domain.Patients;
import com.hust.hustpital.domain.ThongTinVaoVien;
import com.hust.hustpital.repository.ThongTinVaoVienRepository;
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
 * Integration tests for the {@link ThongTinVaoVienResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ThongTinVaoVienResourceIT {

    private static final Instant DEFAULT_NGAY_KHAM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_KHAM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TINH_TRANG_VAO_VIEN = "AAAAAAAAAA";
    private static final String UPDATED_TINH_TRANG_VAO_VIEN = "BBBBBBBBBB";

    private static final Integer DEFAULT_SO_PHIEU = 1;
    private static final Integer UPDATED_SO_PHIEU = 2;

    private static final Integer DEFAULT_MA_BV_CHUYEN_DEN = 1;
    private static final Integer UPDATED_MA_BV_CHUYEN_DEN = 2;

    private static final String DEFAULT_BENH_CHUYEN_DEN = "AAAAAAAAAA";
    private static final String UPDATED_BENH_CHUYEN_DEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/thong-tin-vao-viens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ThongTinVaoVienRepository thongTinVaoVienRepository;

    @Mock
    private ThongTinVaoVienRepository thongTinVaoVienRepositoryMock;

    @Autowired
    private MockMvc restThongTinVaoVienMockMvc;

    private ThongTinVaoVien thongTinVaoVien;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThongTinVaoVien createEntity() {
        ThongTinVaoVien thongTinVaoVien = new ThongTinVaoVien()
            .ngayKham(DEFAULT_NGAY_KHAM)
            .tinhTrangVaoVien(DEFAULT_TINH_TRANG_VAO_VIEN)
            .soPhieu(DEFAULT_SO_PHIEU)
            .maBVChuyenDen(DEFAULT_MA_BV_CHUYEN_DEN)
            .benhChuyenDen(DEFAULT_BENH_CHUYEN_DEN);
        // Add required entity
        Patients patients;
        patients = PatientsResourceIT.createEntity();
        patients.setId("fixed-id-for-tests");
        thongTinVaoVien.setPatient(patients);
        return thongTinVaoVien;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ThongTinVaoVien createUpdatedEntity() {
        ThongTinVaoVien thongTinVaoVien = new ThongTinVaoVien()
            .ngayKham(UPDATED_NGAY_KHAM)
            .tinhTrangVaoVien(UPDATED_TINH_TRANG_VAO_VIEN)
            .soPhieu(UPDATED_SO_PHIEU)
            .maBVChuyenDen(UPDATED_MA_BV_CHUYEN_DEN)
            .benhChuyenDen(UPDATED_BENH_CHUYEN_DEN);
        // Add required entity
        Patients patients;
        patients = PatientsResourceIT.createUpdatedEntity();
        patients.setId("fixed-id-for-tests");
        thongTinVaoVien.setPatient(patients);
        return thongTinVaoVien;
    }

    @BeforeEach
    public void initTest() {
        thongTinVaoVienRepository.deleteAll();
        thongTinVaoVien = createEntity();
    }

    @Test
    void createThongTinVaoVien() throws Exception {
        int databaseSizeBeforeCreate = thongTinVaoVienRepository.findAll().size();
        // Create the ThongTinVaoVien
        restThongTinVaoVienMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thongTinVaoVien))
            )
            .andExpect(status().isCreated());

        // Validate the ThongTinVaoVien in the database
        List<ThongTinVaoVien> thongTinVaoVienList = thongTinVaoVienRepository.findAll();
        assertThat(thongTinVaoVienList).hasSize(databaseSizeBeforeCreate + 1);
        ThongTinVaoVien testThongTinVaoVien = thongTinVaoVienList.get(thongTinVaoVienList.size() - 1);
        assertThat(testThongTinVaoVien.getNgayKham()).isEqualTo(DEFAULT_NGAY_KHAM);
        assertThat(testThongTinVaoVien.getTinhTrangVaoVien()).isEqualTo(DEFAULT_TINH_TRANG_VAO_VIEN);
        assertThat(testThongTinVaoVien.getSoPhieu()).isEqualTo(DEFAULT_SO_PHIEU);
        assertThat(testThongTinVaoVien.getMaBVChuyenDen()).isEqualTo(DEFAULT_MA_BV_CHUYEN_DEN);
        assertThat(testThongTinVaoVien.getBenhChuyenDen()).isEqualTo(DEFAULT_BENH_CHUYEN_DEN);
    }

    @Test
    void createThongTinVaoVienWithExistingId() throws Exception {
        // Create the ThongTinVaoVien with an existing ID
        thongTinVaoVien.setId("existing_id");

        int databaseSizeBeforeCreate = thongTinVaoVienRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThongTinVaoVienMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thongTinVaoVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinVaoVien in the database
        List<ThongTinVaoVien> thongTinVaoVienList = thongTinVaoVienRepository.findAll();
        assertThat(thongTinVaoVienList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllThongTinVaoViens() throws Exception {
        // Initialize the database
        thongTinVaoVienRepository.save(thongTinVaoVien);

        // Get all the thongTinVaoVienList
        restThongTinVaoVienMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thongTinVaoVien.getId())))
            .andExpect(jsonPath("$.[*].ngayKham").value(hasItem(DEFAULT_NGAY_KHAM.toString())))
            .andExpect(jsonPath("$.[*].tinhTrangVaoVien").value(hasItem(DEFAULT_TINH_TRANG_VAO_VIEN)))
            .andExpect(jsonPath("$.[*].soPhieu").value(hasItem(DEFAULT_SO_PHIEU)))
            .andExpect(jsonPath("$.[*].maBVChuyenDen").value(hasItem(DEFAULT_MA_BV_CHUYEN_DEN)))
            .andExpect(jsonPath("$.[*].benhChuyenDen").value(hasItem(DEFAULT_BENH_CHUYEN_DEN)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllThongTinVaoViensWithEagerRelationshipsIsEnabled() throws Exception {
        when(thongTinVaoVienRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restThongTinVaoVienMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(thongTinVaoVienRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllThongTinVaoViensWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(thongTinVaoVienRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restThongTinVaoVienMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(thongTinVaoVienRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getThongTinVaoVien() throws Exception {
        // Initialize the database
        thongTinVaoVienRepository.save(thongTinVaoVien);

        // Get the thongTinVaoVien
        restThongTinVaoVienMockMvc
            .perform(get(ENTITY_API_URL_ID, thongTinVaoVien.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thongTinVaoVien.getId()))
            .andExpect(jsonPath("$.ngayKham").value(DEFAULT_NGAY_KHAM.toString()))
            .andExpect(jsonPath("$.tinhTrangVaoVien").value(DEFAULT_TINH_TRANG_VAO_VIEN))
            .andExpect(jsonPath("$.soPhieu").value(DEFAULT_SO_PHIEU))
            .andExpect(jsonPath("$.maBVChuyenDen").value(DEFAULT_MA_BV_CHUYEN_DEN))
            .andExpect(jsonPath("$.benhChuyenDen").value(DEFAULT_BENH_CHUYEN_DEN));
    }

    @Test
    void getNonExistingThongTinVaoVien() throws Exception {
        // Get the thongTinVaoVien
        restThongTinVaoVienMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingThongTinVaoVien() throws Exception {
        // Initialize the database
        thongTinVaoVienRepository.save(thongTinVaoVien);

        int databaseSizeBeforeUpdate = thongTinVaoVienRepository.findAll().size();

        // Update the thongTinVaoVien
        ThongTinVaoVien updatedThongTinVaoVien = thongTinVaoVienRepository.findById(thongTinVaoVien.getId()).get();
        updatedThongTinVaoVien
            .ngayKham(UPDATED_NGAY_KHAM)
            .tinhTrangVaoVien(UPDATED_TINH_TRANG_VAO_VIEN)
            .soPhieu(UPDATED_SO_PHIEU)
            .maBVChuyenDen(UPDATED_MA_BV_CHUYEN_DEN)
            .benhChuyenDen(UPDATED_BENH_CHUYEN_DEN);

        restThongTinVaoVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedThongTinVaoVien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedThongTinVaoVien))
            )
            .andExpect(status().isOk());

        // Validate the ThongTinVaoVien in the database
        List<ThongTinVaoVien> thongTinVaoVienList = thongTinVaoVienRepository.findAll();
        assertThat(thongTinVaoVienList).hasSize(databaseSizeBeforeUpdate);
        ThongTinVaoVien testThongTinVaoVien = thongTinVaoVienList.get(thongTinVaoVienList.size() - 1);
        assertThat(testThongTinVaoVien.getNgayKham()).isEqualTo(UPDATED_NGAY_KHAM);
        assertThat(testThongTinVaoVien.getTinhTrangVaoVien()).isEqualTo(UPDATED_TINH_TRANG_VAO_VIEN);
        assertThat(testThongTinVaoVien.getSoPhieu()).isEqualTo(UPDATED_SO_PHIEU);
        assertThat(testThongTinVaoVien.getMaBVChuyenDen()).isEqualTo(UPDATED_MA_BV_CHUYEN_DEN);
        assertThat(testThongTinVaoVien.getBenhChuyenDen()).isEqualTo(UPDATED_BENH_CHUYEN_DEN);
    }

    @Test
    void putNonExistingThongTinVaoVien() throws Exception {
        int databaseSizeBeforeUpdate = thongTinVaoVienRepository.findAll().size();
        thongTinVaoVien.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThongTinVaoVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thongTinVaoVien.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thongTinVaoVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinVaoVien in the database
        List<ThongTinVaoVien> thongTinVaoVienList = thongTinVaoVienRepository.findAll();
        assertThat(thongTinVaoVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchThongTinVaoVien() throws Exception {
        int databaseSizeBeforeUpdate = thongTinVaoVienRepository.findAll().size();
        thongTinVaoVien.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThongTinVaoVienMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thongTinVaoVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinVaoVien in the database
        List<ThongTinVaoVien> thongTinVaoVienList = thongTinVaoVienRepository.findAll();
        assertThat(thongTinVaoVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamThongTinVaoVien() throws Exception {
        int databaseSizeBeforeUpdate = thongTinVaoVienRepository.findAll().size();
        thongTinVaoVien.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThongTinVaoVienMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thongTinVaoVien))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThongTinVaoVien in the database
        List<ThongTinVaoVien> thongTinVaoVienList = thongTinVaoVienRepository.findAll();
        assertThat(thongTinVaoVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateThongTinVaoVienWithPatch() throws Exception {
        // Initialize the database
        thongTinVaoVienRepository.save(thongTinVaoVien);

        int databaseSizeBeforeUpdate = thongTinVaoVienRepository.findAll().size();

        // Update the thongTinVaoVien using partial update
        ThongTinVaoVien partialUpdatedThongTinVaoVien = new ThongTinVaoVien();
        partialUpdatedThongTinVaoVien.setId(thongTinVaoVien.getId());

        partialUpdatedThongTinVaoVien.soPhieu(UPDATED_SO_PHIEU);

        restThongTinVaoVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThongTinVaoVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThongTinVaoVien))
            )
            .andExpect(status().isOk());

        // Validate the ThongTinVaoVien in the database
        List<ThongTinVaoVien> thongTinVaoVienList = thongTinVaoVienRepository.findAll();
        assertThat(thongTinVaoVienList).hasSize(databaseSizeBeforeUpdate);
        ThongTinVaoVien testThongTinVaoVien = thongTinVaoVienList.get(thongTinVaoVienList.size() - 1);
        assertThat(testThongTinVaoVien.getNgayKham()).isEqualTo(DEFAULT_NGAY_KHAM);
        assertThat(testThongTinVaoVien.getTinhTrangVaoVien()).isEqualTo(DEFAULT_TINH_TRANG_VAO_VIEN);
        assertThat(testThongTinVaoVien.getSoPhieu()).isEqualTo(UPDATED_SO_PHIEU);
        assertThat(testThongTinVaoVien.getMaBVChuyenDen()).isEqualTo(DEFAULT_MA_BV_CHUYEN_DEN);
        assertThat(testThongTinVaoVien.getBenhChuyenDen()).isEqualTo(DEFAULT_BENH_CHUYEN_DEN);
    }

    @Test
    void fullUpdateThongTinVaoVienWithPatch() throws Exception {
        // Initialize the database
        thongTinVaoVienRepository.save(thongTinVaoVien);

        int databaseSizeBeforeUpdate = thongTinVaoVienRepository.findAll().size();

        // Update the thongTinVaoVien using partial update
        ThongTinVaoVien partialUpdatedThongTinVaoVien = new ThongTinVaoVien();
        partialUpdatedThongTinVaoVien.setId(thongTinVaoVien.getId());

        partialUpdatedThongTinVaoVien
            .ngayKham(UPDATED_NGAY_KHAM)
            .tinhTrangVaoVien(UPDATED_TINH_TRANG_VAO_VIEN)
            .soPhieu(UPDATED_SO_PHIEU)
            .maBVChuyenDen(UPDATED_MA_BV_CHUYEN_DEN)
            .benhChuyenDen(UPDATED_BENH_CHUYEN_DEN);

        restThongTinVaoVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThongTinVaoVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThongTinVaoVien))
            )
            .andExpect(status().isOk());

        // Validate the ThongTinVaoVien in the database
        List<ThongTinVaoVien> thongTinVaoVienList = thongTinVaoVienRepository.findAll();
        assertThat(thongTinVaoVienList).hasSize(databaseSizeBeforeUpdate);
        ThongTinVaoVien testThongTinVaoVien = thongTinVaoVienList.get(thongTinVaoVienList.size() - 1);
        assertThat(testThongTinVaoVien.getNgayKham()).isEqualTo(UPDATED_NGAY_KHAM);
        assertThat(testThongTinVaoVien.getTinhTrangVaoVien()).isEqualTo(UPDATED_TINH_TRANG_VAO_VIEN);
        assertThat(testThongTinVaoVien.getSoPhieu()).isEqualTo(UPDATED_SO_PHIEU);
        assertThat(testThongTinVaoVien.getMaBVChuyenDen()).isEqualTo(UPDATED_MA_BV_CHUYEN_DEN);
        assertThat(testThongTinVaoVien.getBenhChuyenDen()).isEqualTo(UPDATED_BENH_CHUYEN_DEN);
    }

    @Test
    void patchNonExistingThongTinVaoVien() throws Exception {
        int databaseSizeBeforeUpdate = thongTinVaoVienRepository.findAll().size();
        thongTinVaoVien.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThongTinVaoVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thongTinVaoVien.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thongTinVaoVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinVaoVien in the database
        List<ThongTinVaoVien> thongTinVaoVienList = thongTinVaoVienRepository.findAll();
        assertThat(thongTinVaoVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchThongTinVaoVien() throws Exception {
        int databaseSizeBeforeUpdate = thongTinVaoVienRepository.findAll().size();
        thongTinVaoVien.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThongTinVaoVienMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thongTinVaoVien))
            )
            .andExpect(status().isBadRequest());

        // Validate the ThongTinVaoVien in the database
        List<ThongTinVaoVien> thongTinVaoVienList = thongTinVaoVienRepository.findAll();
        assertThat(thongTinVaoVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamThongTinVaoVien() throws Exception {
        int databaseSizeBeforeUpdate = thongTinVaoVienRepository.findAll().size();
        thongTinVaoVien.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThongTinVaoVienMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thongTinVaoVien))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ThongTinVaoVien in the database
        List<ThongTinVaoVien> thongTinVaoVienList = thongTinVaoVienRepository.findAll();
        assertThat(thongTinVaoVienList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteThongTinVaoVien() throws Exception {
        // Initialize the database
        thongTinVaoVienRepository.save(thongTinVaoVien);

        int databaseSizeBeforeDelete = thongTinVaoVienRepository.findAll().size();

        // Delete the thongTinVaoVien
        restThongTinVaoVienMockMvc
            .perform(delete(ENTITY_API_URL_ID, thongTinVaoVien.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ThongTinVaoVien> thongTinVaoVienList = thongTinVaoVienRepository.findAll();
        assertThat(thongTinVaoVienList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
