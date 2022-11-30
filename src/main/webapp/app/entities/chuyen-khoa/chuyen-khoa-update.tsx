import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IChuyenKhoa } from 'app/shared/model/chuyen-khoa.model';
import { getEntity, updateEntity, createEntity, reset } from './chuyen-khoa.reducer';

export const ChuyenKhoaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const chuyenKhoaEntity = useAppSelector(state => state.chuyenKhoa.entity);
  const loading = useAppSelector(state => state.chuyenKhoa.loading);
  const updating = useAppSelector(state => state.chuyenKhoa.updating);
  const updateSuccess = useAppSelector(state => state.chuyenKhoa.updateSuccess);

  const handleClose = () => {
    navigate('/chuyen-khoa');
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
    const entity = {
      ...chuyenKhoaEntity,
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
      ? {}
      : {
          ...chuyenKhoaEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hustpitalApp.chuyenKhoa.home.createOrEditLabel" data-cy="ChuyenKhoaCreateUpdateHeading">
            <Translate contentKey="hustpitalApp.chuyenKhoa.home.createOrEditLabel">Create or edit a ChuyenKhoa</Translate>
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
                  id="chuyen-khoa-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hustpitalApp.chuyenKhoa.code')}
                id="chuyen-khoa-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.chuyenKhoa.name')}
                id="chuyen-khoa-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/chuyen-khoa" replace color="info">
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

export default ChuyenKhoaUpdate;
