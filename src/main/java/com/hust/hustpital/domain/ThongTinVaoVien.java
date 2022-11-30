package com.hust.hustpital.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ThongTinVaoVien.
 */
@Document(collection = "thong_tin_vao_vien")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ThongTinVaoVien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("ngay_kham")
    private Instant ngayKham;

    @Field("tinh_trang_vao_vien")
    private String tinhTrangVaoVien;

    @Field("so_phieu")
    private Integer soPhieu;

    @Field("ma_bv_chuyen_den")
    private Integer maBVChuyenDen;

    @Field("benh_chuyen_den")
    private String benhChuyenDen;

    @DBRef
    @Field("patient")
    private Patients patient;

    @DBRef
    @Field("phongkham")
    private PhongKham phongkham;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ThongTinVaoVien id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getNgayKham() {
        return this.ngayKham;
    }

    public ThongTinVaoVien ngayKham(Instant ngayKham) {
        this.setNgayKham(ngayKham);
        return this;
    }

    public void setNgayKham(Instant ngayKham) {
        this.ngayKham = ngayKham;
    }

    public String getTinhTrangVaoVien() {
        return this.tinhTrangVaoVien;
    }

    public ThongTinVaoVien tinhTrangVaoVien(String tinhTrangVaoVien) {
        this.setTinhTrangVaoVien(tinhTrangVaoVien);
        return this;
    }

    public void setTinhTrangVaoVien(String tinhTrangVaoVien) {
        this.tinhTrangVaoVien = tinhTrangVaoVien;
    }

    public Integer getSoPhieu() {
        return this.soPhieu;
    }

    public ThongTinVaoVien soPhieu(Integer soPhieu) {
        this.setSoPhieu(soPhieu);
        return this;
    }

    public void setSoPhieu(Integer soPhieu) {
        this.soPhieu = soPhieu;
    }

    public Integer getMaBVChuyenDen() {
        return this.maBVChuyenDen;
    }

    public ThongTinVaoVien maBVChuyenDen(Integer maBVChuyenDen) {
        this.setMaBVChuyenDen(maBVChuyenDen);
        return this;
    }

    public void setMaBVChuyenDen(Integer maBVChuyenDen) {
        this.maBVChuyenDen = maBVChuyenDen;
    }

    public String getBenhChuyenDen() {
        return this.benhChuyenDen;
    }

    public ThongTinVaoVien benhChuyenDen(String benhChuyenDen) {
        this.setBenhChuyenDen(benhChuyenDen);
        return this;
    }

    public void setBenhChuyenDen(String benhChuyenDen) {
        this.benhChuyenDen = benhChuyenDen;
    }

    public Patients getPatient() {
        return this.patient;
    }

    public void setPatient(Patients patients) {
        this.patient = patients;
    }

    public ThongTinVaoVien patient(Patients patients) {
        this.setPatient(patients);
        return this;
    }

    public PhongKham getPhongkham() {
        return this.phongkham;
    }

    public void setPhongkham(PhongKham phongKham) {
        this.phongkham = phongKham;
    }

    public ThongTinVaoVien phongkham(PhongKham phongKham) {
        this.setPhongkham(phongKham);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThongTinVaoVien)) {
            return false;
        }
        return id != null && id.equals(((ThongTinVaoVien) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThongTinVaoVien{" +
            "id=" + getId() +
            ", ngayKham='" + getNgayKham() + "'" +
            ", tinhTrangVaoVien='" + getTinhTrangVaoVien() + "'" +
            ", soPhieu=" + getSoPhieu() +
            ", maBVChuyenDen=" + getMaBVChuyenDen() +
            ", benhChuyenDen='" + getBenhChuyenDen() + "'" +
            "}";
    }
}
