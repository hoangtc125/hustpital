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
import { ILichLamViec } from 'app/shared/model/lich-lam-viec.model';
import { getEntity, updateEntity, createEntity, reset } from './lich-lam-viec.reducer';

export const LichLamViecUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const doctors = useAppSelector(state => state.doctors.entities);
  const lichLamViecEntity = useAppSelector(state => state.lichLamViec.entity);
  const loading = useAppSelector(state => state.lichLamViec.loading);
  const updating = useAppSelector(state => state.lichLamViec.updating);
  const updateSuccess = useAppSelector(state => state.lichLamViec.updateSuccess);

  const handleClose = () => {
    navigate('/lich-lam-viec');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getDoctors({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...lichLamViecEntity,
      ...values,
      doctors: mapIdList(values.doctors),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...lichLamViecEntity,
          doctors: lichLamViecEntity?.doctors?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hustpitalApp.lichLamViec.home.createOrEditLabel" data-cy="LichLamViecCreateUpdateHeading">
            <Translate contentKey="hustpitalApp.lichLamViec.home.createOrEditLabel">Create or edit a LichLamViec</Translate>
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
                  id="lich-lam-viec-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hustpitalApp.lichLamViec.thu')}
                id="lich-lam-viec-thu"
                name="thu"
                data-cy="thu"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.lichLamViec.thoiGian')}
                id="lich-lam-viec-thoiGian"
                name="thoiGian"
                data-cy="thoiGian"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.lichLamViec.doctor')}
                id="lich-lam-viec-doctor"
                data-cy="doctor"
                type="select"
                multiple
                name="doctors"
              >
                <option value="" key="0" />
                {doctors
                  ? doctors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/lich-lam-viec" replace color="info">
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

export default LichLamViecUpdate;
