package com.hust.hustpital.domain;

import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Bhyt.
 */
@Document(collection = "bhyt")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bhyt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("qrcode")
    private String qrcode;

    @Field("sothe")
    private String sothe;

    @Field("ma_kcbbd")
    private String maKCBBD;

    @Field("diachi")
    private String diachi;

    @Field("ngay_bat_dau")
    private Instant ngayBatDau;

    @Field("ngay_ket_thuc")
    private Instant ngayKetThuc;

    @Field("ngay_bat_dau_5_nam_lt")
    private Instant ngayBatDau5namLT;

    @Field("ngay_bat_dau_mien_cct")
    private Instant ngayBatDauMienCCT;

    @Field("ngay_ket_thuc_mien_cct")
    private Instant ngayKetThucMienCCT;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Bhyt id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQrcode() {
        return this.qrcode;
    }

    public Bhyt qrcode(String qrcode) {
        this.setQrcode(qrcode);
        return this;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getSothe() {
        return this.sothe;
    }

    public Bhyt sothe(String sothe) {
        this.setSothe(sothe);
        return this;
    }

    public void setSothe(String sothe) {
        this.sothe = sothe;
    }

    public String getMaKCBBD() {
        return this.maKCBBD;
    }

    public Bhyt maKCBBD(String maKCBBD) {
        this.setMaKCBBD(maKCBBD);
        return this;
    }

    public void setMaKCBBD(String maKCBBD) {
        this.maKCBBD = maKCBBD;
    }

    public String getDiachi() {
        return this.diachi;
    }

    public Bhyt diachi(String diachi) {
        this.setDiachi(diachi);
        return this;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Instant getNgayBatDau() {
        return this.ngayBatDau;
    }

    public Bhyt ngayBatDau(Instant ngayBatDau) {
        this.setNgayBatDau(ngayBatDau);
        return this;
    }

    public void setNgayBatDau(Instant ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Instant getNgayKetThuc() {
        return this.ngayKetThuc;
    }

    public Bhyt ngayKetThuc(Instant ngayKetThuc) {
        this.setNgayKetThuc(ngayKetThuc);
        return this;
    }

    public void setNgayKetThuc(Instant ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public Instant getNgayBatDau5namLT() {
        return this.ngayBatDau5namLT;
    }

    public Bhyt ngayBatDau5namLT(Instant ngayBatDau5namLT) {
        this.setNgayBatDau5namLT(ngayBatDau5namLT);
        return this;
    }

    public void setNgayBatDau5namLT(Instant ngayBatDau5namLT) {
        this.ngayBatDau5namLT = ngayBatDau5namLT;
    }

    public Instant getNgayBatDauMienCCT() {
        return this.ngayBatDauMienCCT;
    }

    public Bhyt ngayBatDauMienCCT(Instant ngayBatDauMienCCT) {
        this.setNgayBatDauMienCCT(ngayBatDauMienCCT);
        return this;
    }

    public void setNgayBatDauMienCCT(Instant ngayBatDauMienCCT) {
        this.ngayBatDauMienCCT = ngayBatDauMienCCT;
    }

    public Instant getNgayKetThucMienCCT() {
        return this.ngayKetThucMienCCT;
    }

    public Bhyt ngayKetThucMienCCT(Instant ngayKetThucMienCCT) {
        this.setNgayKetThucMienCCT(ngayKetThucMienCCT);
        return this;
    }

    public void setNgayKetThucMienCCT(Instant ngayKetThucMienCCT) {
        this.ngayKetThucMienCCT = ngayKetThucMienCCT;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bhyt)) {
            return false;
        }
        return id != null && id.equals(((Bhyt) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bhyt{" +
            "id=" + getId() +
            ", qrcode='" + getQrcode() + "'" +
            ", sothe='" + getSothe() + "'" +
            ", maKCBBD='" + getMaKCBBD() + "'" +
            ", diachi='" + getDiachi() + "'" +
            ", ngayBatDau='" + getNgayBatDau() + "'" +
            ", ngayKetThuc='" + getNgayKetThuc() + "'" +
            ", ngayBatDau5namLT='" + getNgayBatDau5namLT() + "'" +
            ", ngayBatDauMienCCT='" + getNgayBatDauMienCCT() + "'" +
            ", ngayKetThucMienCCT='" + getNgayKetThucMienCCT() + "'" +
            "}";
    }
}
