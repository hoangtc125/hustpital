import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './patients.reducer';

export const PatientsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const patientsEntity = useAppSelector(state => state.patients.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="patientsDetailsHeading">
          <Translate contentKey="hustpitalApp.patients.detail.title">Patients</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{patientsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hustpitalApp.patients.name">Name</Translate>
            </span>
          </dt>
          <dd>{patientsEntity.name}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="hustpitalApp.patients.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{patientsEntity.gender}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="hustpitalApp.patients.address">Address</Translate>
            </span>
          </dt>
          <dd>{patientsEntity.address}</dd>
          <dt>
            <span id="dateOfBirth">
              <Translate contentKey="hustpitalApp.patients.dateOfBirth">Date Of Birth</Translate>
            </span>
          </dt>
          <dd>
            {patientsEntity.dateOfBirth ? <TextFormat value={patientsEntity.dateOfBirth} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="phone">
              <Translate contentKey="hustpitalApp.patients.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{patientsEntity.phone}</dd>
          <dt>
            <span id="citizenIdentification">
              <Translate contentKey="hustpitalApp.patients.citizenIdentification">Citizen Identification</Translate>
            </span>
          </dt>
          <dd>{patientsEntity.citizenIdentification}</dd>
          <dt>
            <span id="maBHXH">
              <Translate contentKey="hustpitalApp.patients.maBHXH">Ma BHXH</Translate>
            </span>
          </dt>
          <dd>{patientsEntity.maBHXH}</dd>
          <dt>
            <span id="workPlace">
              <Translate contentKey="hustpitalApp.patients.workPlace">Work Place</Translate>
            </span>
          </dt>
          <dd>{patientsEntity.workPlace}</dd>
          <dt>
            <span id="workAddress">
              <Translate contentKey="hustpitalApp.patients.workAddress">Work Address</Translate>
            </span>
          </dt>
          <dd>{patientsEntity.workAddress}</dd>
          <dt>
            <span id="patientType">
              <Translate contentKey="hustpitalApp.patients.patientType">Patient Type</Translate>
            </span>
          </dt>
          <dd>{patientsEntity.patientType}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.patients.country">Country</Translate>
          </dt>
          <dd>{patientsEntity.country ? patientsEntity.country.name : ''}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.patients.city">City</Translate>
          </dt>
          <dd>{patientsEntity.city ? patientsEntity.city.name : ''}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.patients.district">District</Translate>
          </dt>
          <dd>{patientsEntity.district ? patientsEntity.district.name : ''}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.patients.ward">Ward</Translate>
          </dt>
          <dd>{patientsEntity.ward ? patientsEntity.ward.name : ''}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.patients.ethnic">Ethnic</Translate>
          </dt>
          <dd>{patientsEntity.ethnic ? patientsEntity.ethnic.name : ''}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.patients.job">Job</Translate>
          </dt>
          <dd>{patientsEntity.job ? patientsEntity.job.name : ''}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.patients.bHYT">B HYT</Translate>
          </dt>
          <dd>{patientsEntity.bHYT ? patientsEntity.bHYT.sothe : ''}</dd>
        </dl>
        <Button tag={Link} to="/patients" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/patients/${patientsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PatientsDetail;
