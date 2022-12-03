import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBhyt } from 'app/shared/model/bhyt.model';
import { getEntity, updateEntity, createEntity, reset } from './bhyt.reducer';

export const BhytUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const bhytEntity = useAppSelector(state => state.bhyt.entity);
  const loading = useAppSelector(state => state.bhyt.loading);
  const updating = useAppSelector(state => state.bhyt.updating);
  const updateSuccess = useAppSelector(state => state.bhyt.updateSuccess);

  const handleClose = () => {
    navigate('/bhyt' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.ngayBatDau = convertDateTimeToServer(values.ngayBatDau);
    values.ngayKetThuc = convertDateTimeToServer(values.ngayKetThuc);
    values.ngayBatDau5namLT = convertDateTimeToServer(values.ngayBatDau5namLT);
    values.ngayBatDauMienCCT = convertDateTimeToServer(values.ngayBatDauMienCCT);
    values.ngayKetThucMienCCT = convertDateTimeToServer(values.ngayKetThucMienCCT);

    const entity = {
      ...bhytEntity,
      ...values,
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
          ngayBatDau: displayDefaultDateTime(),
          ngayKetThuc: displayDefaultDateTime(),
          ngayBatDau5namLT: displayDefaultDateTime(),
          ngayBatDauMienCCT: displayDefaultDateTime(),
          ngayKetThucMienCCT: displayDefaultDateTime(),
        }
      : {
          ...bhytEntity,
          ngayBatDau: convertDateTimeFromServer(bhytEntity.ngayBatDau),
          ngayKetThuc: convertDateTimeFromServer(bhytEntity.ngayKetThuc),
          ngayBatDau5namLT: convertDateTimeFromServer(bhytEntity.ngayBatDau5namLT),
          ngayBatDauMienCCT: convertDateTimeFromServer(bhytEntity.ngayBatDauMienCCT),
          ngayKetThucMienCCT: convertDateTimeFromServer(bhytEntity.ngayKetThucMienCCT),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hustpitalApp.bhyt.home.createOrEditLabel" data-cy="BhytCreateUpdateHeading">
            <Translate contentKey="hustpitalApp.bhyt.home.createOrEditLabel">Create or edit a Bhyt</Translate>
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
                  id="bhyt-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('hustpitalApp.bhyt.qrcode')} id="bhyt-qrcode" name="qrcode" data-cy="qrcode" type="text" />
              <ValidatedField label={translate('hustpitalApp.bhyt.sothe')} id="bhyt-sothe" name="sothe" data-cy="sothe" type="text" />
              <ValidatedField
                label={translate('hustpitalApp.bhyt.maKCBBD')}
                id="bhyt-maKCBBD"
                name="maKCBBD"
                data-cy="maKCBBD"
                type="text"
              />
              <ValidatedField label={translate('hustpitalApp.bhyt.diachi')} id="bhyt-diachi" name="diachi" data-cy="diachi" type="text" />
              <ValidatedField
                label={translate('hustpitalApp.bhyt.ngayBatDau')}
                id="bhyt-ngayBatDau"
                name="ngayBatDau"
                data-cy="ngayBatDau"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hustpitalApp.bhyt.ngayKetThuc')}
                id="bhyt-ngayKetThuc"
                name="ngayKetThuc"
                data-cy="ngayKetThuc"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hustpitalApp.bhyt.ngayBatDau5namLT')}
                id="bhyt-ngayBatDau5namLT"
                name="ngayBatDau5namLT"
                data-cy="ngayBatDau5namLT"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hustpitalApp.bhyt.ngayBatDauMienCCT')}
                id="bhyt-ngayBatDauMienCCT"
                name="ngayBatDauMienCCT"
                data-cy="ngayBatDauMienCCT"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hustpitalApp.bhyt.ngayKetThucMienCCT')}
                id="bhyt-ngayKetThucMienCCT"
                name="ngayKetThucMienCCT"
                data-cy="ngayKetThucMienCCT"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bhyt" replace color="info">
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

export default BhytUpdate;
