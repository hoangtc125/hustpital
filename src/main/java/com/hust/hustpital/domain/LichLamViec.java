package com.hust.hustpital.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A LichLamViec.
 */
@Document(collection = "lich_lam_viec")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LichLamViec implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("thu")
    private Integer thu;

    @Field("thoi_gian")
    private String thoiGian;

    @DBRef
    @Field("doctors")
    @JsonIgnoreProperties(value = { "user", "ethnic", "country", "bank", "chuyenkhoa", "lichlamviecs" }, allowSetters = true)
    private Set<Doctors> doctors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public LichLamViec id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getThu() {
        return this.thu;
    }

    public LichLamViec thu(Integer thu) {
        this.setThu(thu);
        return this;
    }

    public void setThu(Integer thu) {
        this.thu = thu;
    }

    public String getThoiGian() {
        return this.thoiGian;
    }

    public LichLamViec thoiGian(String thoiGian) {
        this.setThoiGian(thoiGian);
        return this;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Set<Doctors> getDoctors() {
        return this.doctors;
    }

    public void setDoctors(Set<Doctors> doctors) {
        this.doctors = doctors;
    }

    public LichLamViec doctors(Set<Doctors> doctors) {
        this.setDoctors(doctors);
        return this;
    }

    public LichLamViec addDoctor(Doctors doctors) {
        this.doctors.add(doctors);
        doctors.getLichlamviecs().add(this);
        return this;
    }

    public LichLamViec removeDoctor(Doctors doctors) {
        this.doctors.remove(doctors);
        doctors.getLichlamviecs().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LichLamViec)) {
            return false;
        }
        return id != null && id.equals(((LichLamViec) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LichLamViec{" +
            "id=" + getId() +
            ", thu=" + getThu() +
            ", thoiGian='" + getThoiGian() + "'" +
            "}";
    }
}
