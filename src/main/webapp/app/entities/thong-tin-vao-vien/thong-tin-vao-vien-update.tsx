import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPatients } from 'app/shared/model/patients.model';
import { getEntities as getPatients } from 'app/entities/patients/patients.reducer';
import { IPhongKham } from 'app/shared/model/phong-kham.model';
import { getEntities as getPhongKhams } from 'app/entities/phong-kham/phong-kham.reducer';
import { IThongTinVaoVien } from 'app/shared/model/thong-tin-vao-vien.model';
import { getEntity, updateEntity, createEntity, reset } from './thong-tin-vao-vien.reducer';

export const ThongTinVaoVienUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const patients = useAppSelector(state => state.patients.entities);
  const phongKhams = useAppSelector(state => state.phongKham.entities);
  const thongTinVaoVienEntity = useAppSelector(state => state.thongTinVaoVien.entity);
  const loading = useAppSelector(state => state.thongTinVaoVien.loading);
  const updating = useAppSelector(state => state.thongTinVaoVien.updating);
  const updateSuccess = useAppSelector(state => state.thongTinVaoVien.updateSuccess);

  const handleClose = () => {
    navigate('/thong-tin-vao-vien' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPatients({}));
    dispatch(getPhongKhams({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.ngayKham = convertDateTimeToServer(values.ngayKham);

    const entity = {
      ...thongTinVaoVienEntity,
      ...values,
      patient: patients.find(it => it.id.toString() === values.patient.toString()),
      phongkham: phongKhams.find(it => it.id.toString() === values.phongkham.toString()),
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
          ngayKham: displayDefaultDateTime(),
        }
      : {
          ...thongTinVaoVienEntity,
          ngayKham: convertDateTimeFromServer(thongTinVaoVienEntity.ngayKham),
          patient: thongTinVaoVienEntity?.patient?.id,
          phongkham: thongTinVaoVienEntity?.phongkham?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hustpitalApp.thongTinVaoVien.home.createOrEditLabel" data-cy="ThongTinVaoVienCreateUpdateHeading">
            <Translate contentKey="hustpitalApp.thongTinVaoVien.home.createOrEditLabel">Create or edit a ThongTinVaoVien</Translate>
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
                  id="thong-tin-vao-vien-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hustpitalApp.thongTinVaoVien.ngayKham')}
                id="thong-tin-vao-vien-ngayKham"
                name="ngayKham"
                data-cy="ngayKham"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('hustpitalApp.thongTinVaoVien.tinhTrangVaoVien')}
                id="thong-tin-vao-vien-tinhTrangVaoVien"
                name="tinhTrangVaoVien"
                data-cy="tinhTrangVaoVien"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.thongTinVaoVien.soPhieu')}
                id="thong-tin-vao-vien-soPhieu"
                name="soPhieu"
                data-cy="soPhieu"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.thongTinVaoVien.maBVChuyenDen')}
                id="thong-tin-vao-vien-maBVChuyenDen"
                name="maBVChuyenDen"
                data-cy="maBVChuyenDen"
                type="text"
              />
              <ValidatedField
                label={translate('hustpitalApp.thongTinVaoVien.benhChuyenDen')}
                id="thong-tin-vao-vien-benhChuyenDen"
                name="benhChuyenDen"
                data-cy="benhChuyenDen"
                type="text"
              />
              <ValidatedField
                id="thong-tin-vao-vien-patient"
                name="patient"
                data-cy="patient"
                label={translate('hustpitalApp.thongTinVaoVien.patient')}
                type="select"
                required
              >
                <option value="" key="0" />
                {patients
                  ? patients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="thong-tin-vao-vien-phongkham"
                name="phongkham"
                data-cy="phongkham"
                label={translate('hustpitalApp.thongTinVaoVien.phongkham')}
                type="select"
              >
                <option value="" key="0" />
                {phongKhams
                  ? phongKhams.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/thong-tin-vao-vien" replace color="info">
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

export default ThongTinVaoVienUpdate;
