package com.hust.hustpital.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hust.hustpital.domain.enumeration.Gender;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Doctors.
 */
@Document(collection = "doctors")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Doctors implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("phone")
    private String phone;

    @Field("citizen_identification")
    private String citizenIdentification;

    @Field("ma_bhxh")
    private String maBHXH;

    @Field("gender")
    private Gender gender;

    @Field("date_of_birth")
    private Instant dateOfBirth;

    @Field("address")
    private String address;

    @Field("ma_so_thue")
    private String maSoThue;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("ethnic")
    private Ethnics ethnic;

    @DBRef
    @Field("country")
    private Countries country;

    @DBRef
    @Field("bank")
    private Banks bank;

    @DBRef
    @Field("chuyenkhoa")
    private ChuyenKhoa chuyenkhoa;

    @DBRef
    @Field("lichlamviecs")
    @JsonIgnoreProperties(value = { "doctors" }, allowSetters = true)
    private Set<LichLamViec> lichlamviecs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Doctors id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Doctors name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public Doctors phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCitizenIdentification() {
        return this.citizenIdentification;
    }

    public Doctors citizenIdentification(String citizenIdentification) {
        this.setCitizenIdentification(citizenIdentification);
        return this;
    }

    public void setCitizenIdentification(String citizenIdentification) {
        this.citizenIdentification = citizenIdentification;
    }

    public String getMaBHXH() {
        return this.maBHXH;
    }

    public Doctors maBHXH(String maBHXH) {
        this.setMaBHXH(maBHXH);
        return this;
    }

    public void setMaBHXH(String maBHXH) {
        this.maBHXH = maBHXH;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Doctors gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Instant getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Doctors dateOfBirth(Instant dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return this.address;
    }

    public Doctors address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMaSoThue() {
        return this.maSoThue;
    }

    public Doctors maSoThue(String maSoThue) {
        this.setMaSoThue(maSoThue);
        return this;
    }

    public void setMaSoThue(String maSoThue) {
        this.maSoThue = maSoThue;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Doctors user(User user) {
        this.setUser(user);
        return this;
    }

    public Ethnics getEthnic() {
        return this.ethnic;
    }

    public void setEthnic(Ethnics ethnics) {
        this.ethnic = ethnics;
    }

    public Doctors ethnic(Ethnics ethnics) {
        this.setEthnic(ethnics);
        return this;
    }

    public Countries getCountry() {
        return this.country;
    }

    public void setCountry(Countries countries) {
        this.country = countries;
    }

    public Doctors country(Countries countries) {
        this.setCountry(countries);
        return this;
    }

    public Banks getBank() {
        return this.bank;
    }

    public void setBank(Banks banks) {
        this.bank = banks;
    }

    public Doctors bank(Banks banks) {
        this.setBank(banks);
        return this;
    }

    public ChuyenKhoa getChuyenkhoa() {
        return this.chuyenkhoa;
    }

    public void setChuyenkhoa(ChuyenKhoa chuyenKhoa) {
        this.chuyenkhoa = chuyenKhoa;
    }

    public Doctors chuyenkhoa(ChuyenKhoa chuyenKhoa) {
        this.setChuyenkhoa(chuyenKhoa);
        return this;
    }

    public Set<LichLamViec> getLichlamviecs() {
        return this.lichlamviecs;
    }

    public void setLichlamviecs(Set<LichLamViec> lichLamViecs) {
        if (this.lichlamviecs != null) {
            this.lichlamviecs.forEach(i -> i.removeDoctor(this));
        }
        if (lichLamViecs != null) {
            lichLamViecs.forEach(i -> i.addDoctor(this));
        }
        this.lichlamviecs = lichLamViecs;
    }

    public Doctors lichlamviecs(Set<LichLamViec> lichLamViecs) {
        this.setLichlamviecs(lichLamViecs);
        return this;
    }

    public Doctors addLichlamviec(LichLamViec lichLamViec) {
        this.lichlamviecs.add(lichLamViec);
        lichLamViec.getDoctors().add(this);
        return this;
    }

    public Doctors removeLichlamviec(LichLamViec lichLamViec) {
        this.lichlamviecs.remove(lichLamViec);
        lichLamViec.getDoctors().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Doctors)) {
            return false;
        }
        return id != null && id.equals(((Doctors) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Doctors{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", citizenIdentification='" + getCitizenIdentification() + "'" +
            ", maBHXH='" + getMaBHXH() + "'" +
            ", gender='" + getGender() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", address='" + getAddress() + "'" +
            ", maSoThue='" + getMaSoThue() + "'" +
            "}";
    }
}
