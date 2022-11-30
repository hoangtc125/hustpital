package com.hust.hustpital.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hust.hustpital.domain.enumeration.Admission;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A LichHen.
 */
@Document(collection = "lich_hen")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LichHen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("phone")
    private String phone;

    @Field("email")
    private String email;

    @Field("address")
    private String address;

    @Field("ly_do_kham")
    private String lyDoKham;

    @Field("date_of_birth")
    private Instant dateOfBirth;

    @Field("lichhen_type")
    private Admission lichhenType;

    @DBRef
    @Field("doctor")
    @JsonIgnoreProperties(value = { "user", "ethnic", "country", "bank", "chuyenkhoa", "lichlamviecs" }, allowSetters = true)
    private Doctors doctor;

    @DBRef
    @Field("user")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public LichHen id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public LichHen name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public LichHen phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public LichHen email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public LichHen address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLyDoKham() {
        return this.lyDoKham;
    }

    public LichHen lyDoKham(String lyDoKham) {
        this.setLyDoKham(lyDoKham);
        return this;
    }

    public void setLyDoKham(String lyDoKham) {
        this.lyDoKham = lyDoKham;
    }

    public Instant getDateOfBirth() {
        return this.dateOfBirth;
    }

    public LichHen dateOfBirth(Instant dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Admission getLichhenType() {
        return this.lichhenType;
    }

    public LichHen lichhenType(Admission lichhenType) {
        this.setLichhenType(lichhenType);
        return this;
    }

    public void setLichhenType(Admission lichhenType) {
        this.lichhenType = lichhenType;
    }

    public Doctors getDoctor() {
        return this.doctor;
    }

    public void setDoctor(Doctors doctors) {
        this.doctor = doctors;
    }

    public LichHen doctor(Doctors doctors) {
        this.setDoctor(doctors);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LichHen user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LichHen)) {
            return false;
        }
        return id != null && id.equals(((LichHen) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LichHen{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", address='" + getAddress() + "'" +
            ", lyDoKham='" + getLyDoKham() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", lichhenType='" + getLichhenType() + "'" +
            "}";
    }
}
