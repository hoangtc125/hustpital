import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICountries } from 'app/shared/model/countries.model';
import { getEntities as getCountries } from 'app/entities/countries/countries.reducer';
import { ICities } from 'app/shared/model/cities.model';
import { getEntities as getCities } from 'app/entities/cities/cities.reducer';
import { IDistricts } from 'app/shared/model/districts.model';
import { getEntities as getDistricts } from 'app/entities/districts/districts.reducer';
import { IWards } from 'app/shared/model/wards.model';
import { getEntities as getWards } from 'app/entities/wards/wards.reducer';
import { IEthnics } from 'app/shared/model/ethnics.model';
import { getEntities as getEthnics } from 'app/entities/ethnics/ethnics.reducer';
import { IJobs } from 'app/shared/model/jobs.model';
import { getEntities as getJobs } from 'app/entities/jobs/jobs.reducer';
import { IBhyt } from 'app/shared/model/bhyt.model';
import { getEntities as getBhyts } from 'app/entities/bhyt/bhyt.reducer';
import { IPatients } from 'app/shared/model/patients.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { BHYT } from 'app/shared/model/enumerations/bhyt.model';
import { getEntity, updateEntity, createEntity, reset } from './patients.reducer';

export const PatientsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const countries = useAppSelector(state => state.countries.entities);
  const cities = useAppSelector(state => state.cities.entities);
  const districts = useAppSelector(state => state.districts.entities);
  const wards = useAppSelector(state => state.wards.entities);
  const ethnics = useAppSelector(state => state.ethnics.entities);
  const jobs = useAppSelector(state => state.jobs.entities);
  const bhyts = useAppSelector(state => state.bhyt.entities);
  const patientsEntity = useAppSelector(state => state.patients.entity);
  const loading = useAppSelector(state => state.patients.loading);
  const updating = useAppSelector(state => state.patients.updating);
  const updateSuccess = useAppSelector(state => state.patients.updateSuccess);
  const genderValues = Object.keys(Gender);
  const bHYTValues = Object.keys(BHYT);

  const handleClose = () => {
    navigate('/patients' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getCountries({}));
    dispatch(getCities({}));
    dispatch(getDistricts({}));
    dispatch(getWards({}));
    dispatch(getEthnics({}));
    dispatch(getJobs({}));
    dispatch(getBhyts({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateOfBirth = convertDateTimeToServer(values.dateOfBirth);

    const entity = {
      ...patientsEntity,
      ...values,
      country: countries.find(it => it.id.toString() === values.country.toString()),
      city: cities.find(it => it.id.toString() === values.city.toString()),
      district: districts.find(it => it.id.toString() === values.district.toString()),
      ward: wards.find(it => it.id.toString() === values.ward.toString()),
      ethnic: ethnics.find(it => it.id.toString() === values.ethnic.toString()),
      job: jobs.find(it => it.id.toString() === values.job.toString()),
      bHYT: bhyts.find(it => it.id.toString() === values.bHYT.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dateOfBirth: displayDefaultDateTime(),
        }
      : {
          gender: 'Male',
          patientType: 'Yes',
          ...patientsEntity,
          dateOfBirth: convertDateTimeFromServer(patientsEntity.dateOfBirth),
          country: patientsEntity?.country?.id,
          city: patientsEntity?.city?.id,
          district: patientsEntity?.district?.id,
          ward: patientsEntity?.ward?.id,
          ethnic: patientsEntity?.ethnic?.id,
          job: patientsEntity?.job?.id,
          bHYT: patientsEntity?.bHYT?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hustpitalApp.patients.home.createOrEditLabel" data-cy="PatientsCreateUpdateHeading">
            <Translate contentKey="hustpitalApp.patients.home.createOrEditLabel">Create or edit a Patients</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="patients-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('hustpitalApp.patients.name')} id="patients-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('hustpitalApp.patients.gender')}
                id="patients-gender"
                name="gender"
                data-cy="gender"
                type="select"
              >
                {genderValues.map(gender => (
                  <option value={gender} key={gender}>
                    {translate('hustpitalApp.Gender.' + gender)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('hustpitalApp.patients.address')}
                id="patients-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.patients.dateOfBirth')}
                id="patients-dateOfBirth"
                name="dateOfBirth"
                data-cy="dateOfBirth"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hustpitalApp.patients.phone')}
                id="patients-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.patients.citizenIdentification')}
                id="patients-citizenIdentification"
                name="citizenIdentification"
                data-cy="citizenIdentification"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.patients.maBHXH')}
                id="patients-maBHXH"
                name="maBHXH"
                data-cy="maBHXH"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.patients.workPlace')}
                id="patients-workPlace"
                name="workPlace"
                data-cy="workPlace"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.patients.workAddress')}
                id="patients-workAddress"
                name="workAddress"
                data-cy="workAddress"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.patients.patientType')}
                id="patients-patientType"
                name="patientType"
                data-cy="patientType"
                type="select"
              >
                {bHYTValues.map(bHYT => (
                  <option value={bHYT} key={bHYT}>
                    {translate('hustpitalApp.BHYT.' + bHYT)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="patients-country"
                name="country"
                data-cy="country"
                label={translate('hustpitalApp.patients.country')}
                type="select"
              >
                <option value="" key="0" />
                {countries
                  ? countries.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="patients-city" name="city" data-cy="city" label={translate('hustpitalApp.patients.city')} type="select">
                <option value="" key="0" />
                {cities
                  ? cities.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="patients-district"
                name="district"
                data-cy="district"
                label={translate('hustpitalApp.patients.district')}
                type="select"
              >
                <option value="" key="0" />
                {districts
                  ? districts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="patients-ward" name="ward" data-cy="ward" label={translate('hustpitalApp.patients.ward')} type="select">
                <option value="" key="0" />
                {wards
                  ? wards.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="patients-ethnic"
                name="ethnic"
                data-cy="ethnic"
                label={translate('hustpitalApp.patients.ethnic')}
                type="select"
              >
                <option value="" key="0" />
                {ethnics
                  ? ethnics.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="patients-job" name="job" data-cy="job" label={translate('hustpitalApp.patients.job')} type="select">
                <option value="" key="0" />
                {jobs
                  ? jobs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="patients-bHYT" name="bHYT" data-cy="bHYT" label={translate('hustpitalApp.patients.bHYT')} type="select">
                <option value="" key="0" />
                {bhyts
                  ? bhyts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.sothe}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/patients" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PatientsUpdate;
