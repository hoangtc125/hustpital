import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IChuyenKhoa } from 'app/shared/model/chuyen-khoa.model';
import { getEntities as getChuyenKhoas } from 'app/entities/chuyen-khoa/chuyen-khoa.reducer';
import { IPhongKham } from 'app/shared/model/phong-kham.model';
import { getEntity, updateEntity, createEntity, reset } from './phong-kham.reducer';

export const PhongKhamUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const chuyenKhoas = useAppSelector(state => state.chuyenKhoa.entities);
  const phongKhamEntity = useAppSelector(state => state.phongKham.entity);
  const loading = useAppSelector(state => state.phongKham.loading);
  const updating = useAppSelector(state => state.phongKham.updating);
  const updateSuccess = useAppSelector(state => state.phongKham.updateSuccess);

  const handleClose = () => {
    navigate('/phong-kham');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getChuyenKhoas({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...phongKhamEntity,
      ...values,
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
      ? {}
      : {
          ...phongKhamEntity,
          chuyenkhoa: phongKhamEntity?.chuyenkhoa?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hustpitalApp.phongKham.home.createOrEditLabel" data-cy="PhongKhamCreateUpdateHeading">
            <Translate contentKey="hustpitalApp.phongKham.home.createOrEditLabel">Create or edit a PhongKham</Translate>
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
                  id="phong-kham-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hustpitalApp.phongKham.code')}
                id="phong-kham-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.phongKham.name')}
                id="phong-kham-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                id="phong-kham-chuyenkhoa"
                name="chuyenkhoa"
                data-cy="chuyenkhoa"
                label={translate('hustpitalApp.phongKham.chuyenkhoa')}
                type="select"
                required
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
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/phong-kham" replace color="info">
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

export default PhongKhamUpdate;
