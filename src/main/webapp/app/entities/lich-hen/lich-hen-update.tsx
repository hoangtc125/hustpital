import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDoctors } from 'app/shared/model/doctors.model';
import { getEntities as getDoctors } from 'app/entities/doctors/doctors.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { ILichHen } from 'app/shared/model/lich-hen.model';
import { Admission } from 'app/shared/model/enumerations/admission.model';
import { getEntity, updateEntity, createEntity, reset } from './lich-hen.reducer';

export const LichHenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const doctors = useAppSelector(state => state.doctors.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const lichHenEntity = useAppSelector(state => state.lichHen.entity);
  const loading = useAppSelector(state => state.lichHen.loading);
  const updating = useAppSelector(state => state.lichHen.updating);
  const updateSuccess = useAppSelector(state => state.lichHen.updateSuccess);
  const admissionValues = Object.keys(Admission);

  const handleClose = () => {
    navigate('/lich-hen' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDoctors({}));
    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateOfBirth = convertDateTimeToServer(values.dateOfBirth);

    const entity = {
      ...lichHenEntity,
      ...values,
      doctor: doctors.find(it => it.id.toString() === values.doctor.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          lichhenType: 'DatChoBanThan',
          ...lichHenEntity,
          dateOfBirth: convertDateTimeFromServer(lichHenEntity.dateOfBirth),
          doctor: lichHenEntity?.doctor?.id,
          user: lichHenEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hustpitalApp.lichHen.home.createOrEditLabel" data-cy="LichHenCreateUpdateHeading">
            <Translate contentKey="hustpitalApp.lichHen.home.createOrEditLabel">Create or edit a LichHen</Translate>
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
                  id="lich-hen-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('hustpitalApp.lichHen.name')} id="lich-hen-name" name="name" data-cy="name" type="text" />
              <ValidatedField
                label={translate('hustpitalApp.lichHen.phone')}
                id="lich-hen-phone"
                name="phone"
                data-cy="phone"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.lichHen.email')}
                id="lich-hen-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.lichHen.address')}
                id="lich-hen-address"
                name="address"
                data-cy="address"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.lichHen.lyDoKham')}
                id="lich-hen-lyDoKham"
                name="lyDoKham"
                data-cy="lyDoKham"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.lichHen.dateOfBirth')}
                id="lich-hen-dateOfBirth"
                name="dateOfBirth"
                data-cy="dateOfBirth"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hustpitalApp.lichHen.lichhenType')}
                id="lich-hen-lichhenType"
                name="lichhenType"
                data-cy="lichhenType"
                type="select"
              >
                {admissionValues.map(admission => (
                  <option value={admission} key={admission}>
                    {translate('hustpitalApp.Admission.' + admission)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="lich-hen-doctor"
                name="doctor"
                data-cy="doctor"
                label={translate('hustpitalApp.lichHen.doctor')}
                type="select"
              >
                <option value="" key="0" />
                {doctors
                  ? doctors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="lich-hen-user" name="user" data-cy="user" label={translate('hustpitalApp.lichHen.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lich-hen" replace color="info">
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

export default LichHenUpdate;
