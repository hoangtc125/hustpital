package com.hust.hustpital.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hust.hustpital.domain.enumeration.BHYT;
import com.hust.hustpital.domain.enumeration.Gender;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Patients.
 */
@Document(collection = "patients")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Patients implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("gender")
    private Gender gender;

    @Field("address")
    private String address;

    @Field("date_of_birth")
    private Instant dateOfBirth;

    @Field("phone")
    private String phone;

    @Field("citizen_identification")
    private String citizenIdentification;

    @Field("ma_bhxh")
    private String maBHXH;

    @Field("work_place")
    private String workPlace;

    @Field("work_address")
    private String workAddress;

    @Field("patient_type")
    private BHYT patientType;

    @DBRef
    @Field("country")
    private Countries country;

    @DBRef
    @Field("city")
    private Cities city;

    @DBRef
    @Field("district")
    private Districts district;

    @DBRef
    @Field("ward")
    private Wards ward;

    @DBRef
    @Field("ethnic")
    private Ethnics ethnic;

    @DBRef
    @Field("job")
    private Jobs job;

    @DBRef
    @Field("bHYT")
    private Bhyt bHYT;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Patients id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Patients name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Patients gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return this.address;
    }

    public Patients address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Patients dateOfBirth(Instant dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return this.phone;
    }

    public Patients phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCitizenIdentification() {
        return this.citizenIdentification;
    }

    public Patients citizenIdentification(String citizenIdentification) {
        this.setCitizenIdentification(citizenIdentification);
        return this;
    }

    public void setCitizenIdentification(String citizenIdentification) {
        this.citizenIdentification = citizenIdentification;
    }

    public String getMaBHXH() {
        return this.maBHXH;
    }

    public Patients maBHXH(String maBHXH) {
        this.setMaBHXH(maBHXH);
        return this;
    }

    public void setMaBHXH(String maBHXH) {
        this.maBHXH = maBHXH;
    }

    public String getWorkPlace() {
        return this.workPlace;
    }

    public Patients workPlace(String workPlace) {
        this.setWorkPlace(workPlace);
        return this;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getWorkAddress() {
        return this.workAddress;
    }

    public Patients workAddress(String workAddress) {
        this.setWorkAddress(workAddress);
        return this;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public BHYT getPatientType() {
        return this.patientType;
    }

    public Patients patientType(BHYT patientType) {
        this.setPatientType(patientType);
        return this;
    }

    public void setPatientType(BHYT patientType) {
        this.patientType = patientType;
    }

    public Countries getCountry() {
        return this.country;
    }

    public void setCountry(Countries countries) {
        this.country = countries;
    }

    public Patients country(Countries countries) {
        this.setCountry(countries);
        return this;
    }

    public Cities getCity() {
        return this.city;
    }

    public void setCity(Cities cities) {
        this.city = cities;
    }

    public Patients city(Cities cities) {
        this.setCity(cities);
        return this;
    }

    public Districts getDistrict() {
        return this.district;
    }

    public void setDistrict(Districts districts) {
        this.district = districts;
    }

    public Patients district(Districts districts) {
        this.setDistrict(districts);
        return this;
    }

    public Wards getWard() {
        return this.ward;
    }

    public void setWard(Wards wards) {
        this.ward = wards;
    }

    public Patients ward(Wards wards) {
        this.setWard(wards);
        return this;
    }

    public Ethnics getEthnic() {
        return this.ethnic;
    }

    public void setEthnic(Ethnics ethnics) {
        this.ethnic = ethnics;
    }

    public Patients ethnic(Ethnics ethnics) {
        this.setEthnic(ethnics);
        return this;
    }

    public Jobs getJob() {
        return this.job;
    }

    public void setJob(Jobs jobs) {
        this.job = jobs;
    }

    public Patients job(Jobs jobs) {
        this.setJob(jobs);
        return this;
    }

    public Bhyt getBHYT() {
        return this.bHYT;
    }

    public void setBHYT(Bhyt bhyt) {
        this.bHYT = bhyt;
    }

    public Patients bHYT(Bhyt bhyt) {
        this.setBHYT(bhyt);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patients)) {
            return false;
        }
        return id != null && id.equals(((Patients) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patients{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", gender='" + getGender() + "'" +
            ", address='" + getAddress() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", phone='" + getPhone() + "'" +
            ", citizenIdentification='" + getCitizenIdentification() + "'" +
            ", maBHXH='" + getMaBHXH() + "'" +
            ", workPlace='" + getWorkPlace() + "'" +
            ", workAddress='" + getWorkAddress() + "'" +
            ", patientType='" + getPatientType() + "'" +
            "}";
    }
}
