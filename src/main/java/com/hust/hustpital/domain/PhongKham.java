package com.hust.hustpital.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A PhongKham.
 */
@Document(collection = "phong_kham")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhongKham implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("code")
    private String code;

    @Field("name")
    private String name;

    @DBRef
    @Field("chuyenkhoa")
    private ChuyenKhoa chuyenkhoa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public PhongKham id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public PhongKham code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public PhongKham name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChuyenKhoa getChuyenkhoa() {
        return this.chuyenkhoa;
    }

    public void setChuyenkhoa(ChuyenKhoa chuyenKhoa) {
        this.chuyenkhoa = chuyenKhoa;
    }

    public PhongKham chuyenkhoa(ChuyenKhoa chuyenKhoa) {
        this.setChuyenkhoa(chuyenKhoa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhongKham)) {
            return false;
        }
        return id != null && id.equals(((PhongKham) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhongKham{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
