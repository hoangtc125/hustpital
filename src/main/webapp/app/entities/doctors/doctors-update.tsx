import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEthnics } from 'app/shared/model/ethnics.model';
import { getEntities as getEthnics } from 'app/entities/ethnics/ethnics.reducer';
import { ICountries } from 'app/shared/model/countries.model';
import { getEntities as getCountries } from 'app/entities/countries/countries.reducer';
import { IBanks } from 'app/shared/model/banks.model';
import { getEntities as getBanks } from 'app/entities/banks/banks.reducer';
import { IChuyenKhoa } from 'app/shared/model/chuyen-khoa.model';
import { getEntities as getChuyenKhoas } from 'app/entities/chuyen-khoa/chuyen-khoa.reducer';
import { ILichLamViec } from 'app/shared/model/lich-lam-viec.model';
import { getEntities as getLichLamViecs } from 'app/entities/lich-lam-viec/lich-lam-viec.reducer';
import { IDoctors } from 'app/shared/model/doctors.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { getEntity, updateEntity, createEntity, reset } from './doctors.reducer';

export const DoctorsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const ethnics = useAppSelector(state => state.ethnics.entities);
  const countries = useAppSelector(state => state.countries.entities);
  const banks = useAppSelector(state => state.banks.entities);
  const chuyenKhoas = useAppSelector(state => state.chuyenKhoa.entities);
  const lichLamViecs = useAppSelector(state => state.lichLamViec.entities);
  const doctorsEntity = useAppSelector(state => state.doctors.entity);
  const loading = useAppSelector(state => state.doctors.loading);
  const updating = useAppSelector(state => state.doctors.updating);
  const updateSuccess = useAppSelector(state => state.doctors.updateSuccess);
  const genderValues = Object.keys(Gender);

  const handleClose = () => {
    navigate('/doctors' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getEthnics({}));
    dispatch(getCountries({}));
    dispatch(getBanks({}));
    dispatch(getChuyenKhoas({}));
    dispatch(getLichLamViecs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateOfBirth = convertDateTimeToServer(values.dateOfBirth);

    const entity = {
      ...doctorsEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      ethnic: ethnics.find(it => it.id.toString() === values.ethnic.toString()),
      country: countries.find(it => it.id.toString() === values.country.toString()),
      bank: banks.find(it => it.id.toString() === values.bank.toString()),
      chuyenkhoa: chuyenKhoas.find(it => it.id.toString() === values.chuyenkhoa.toString()),
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
          ...doctorsEntity,
          dateOfBirth: convertDateTimeFromServer(doctorsEntity.dateOfBirth),
          user: doctorsEntity?.user?.id,
          ethnic: doctorsEntity?.ethnic?.id,
          country: doctorsEntity?.country?.id,
          bank: doctorsEntity?.bank?.id,
          chuyenkhoa: doctorsEntity?.chuyenkhoa?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hustpitalApp.doctors.home.createOrEditLabel" data-cy="DoctorsCreateUpdateHeading">
            <Translate contentKey="hustpitalApp.doctors.home.createOrEditLabel">Create or edit a Doctors</Translate>
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
                  id="doctors-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('hustpitalApp.doctors.name')} id="doctors-name" name="name" data-cy="name" type="text" />
              <ValidatedField label={translate('hustpitalApp.doctors.phone')} id="doctors-phone" name="phone" data-cy="phone" type="text" />
              <ValidatedField
                label={translate('hustpitalApp.doctors.citizenIdentification')}
                id="doctors-citizenIdentification"
                name="citizenIdentification"
                data-cy="citizenIdentification"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.doctors.maBHXH')}
                id="doctors-maBHXH"
                name="maBHXH"
                data-cy="maBHXH"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.doctors.gender')}
                id="doctors-gender"
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
                label={translate('hustpitalApp.doctors.dateOfBirth')}
                id="doctors-dateOfBirth"
                name="dateOfBirth"
                data-cy="dateOfBirth"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hustpitalApp.doctors.address')}
                id="doctors-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.doctors.maSoThue')}
                id="doctors-maSoThue"
                name="maSoThue"
                data-cy="maSoThue"
                type="text"
              />
              <ValidatedField
                id="doctors-user"
                name="user"
                data-cy="user"
                label={translate('hustpitalApp.doctors.user')}
                type="select"
                required
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="doctors-ethnic"
                name="ethnic"
                data-cy="ethnic"
                label={translate('hustpitalApp.doctors.ethnic')}
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
              <ValidatedField
                id="doctors-country"
                name="country"
                data-cy="country"
                label={translate('hustpitalApp.doctors.country')}
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
              <ValidatedField id="doctors-bank" name="bank" data-cy="bank" label={translate('hustpitalApp.doctors.bank')} type="select">
                <option value="" key="0" />
                {banks
                  ? banks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="doctors-chuyenkhoa"
                name="chuyenkhoa"
                data-cy="chuyenkhoa"
                label={translate('hustpitalApp.doctors.chuyenkhoa')}
                type="select"
              >
                <option value="" key="0" />
                {chuyenKhoas
                  ? chuyenKhoas.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/doctors" replace color="info">
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

export default DoctorsUpdate;
