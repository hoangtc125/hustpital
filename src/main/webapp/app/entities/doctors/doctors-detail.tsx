import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './doctors.reducer';

export const DoctorsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const doctorsEntity = useAppSelector(state => state.doctors.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="doctorsDetailsHeading">
          <Translate contentKey="hustpitalApp.doctors.detail.title">Doctors</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{doctorsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hustpitalApp.doctors.name">Name</Translate>
            </span>
          </dt>
          <dd>{doctorsEntity.name}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="hustpitalApp.doctors.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{doctorsEntity.phone}</dd>
          <dt>
            <span id="citizenIdentification">
              <Translate contentKey="hustpitalApp.doctors.citizenIdentification">Citizen Identification</Translate>
            </span>
          </dt>
          <dd>{doctorsEntity.citizenIdentification}</dd>
          <dt>
            <span id="maBHXH">
              <Translate contentKey="hustpitalApp.doctors.maBHXH">Ma BHXH</Translate>
            </span>
          </dt>
          <dd>{doctorsEntity.maBHXH}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="hustpitalApp.doctors.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{doctorsEntity.gender}</dd>
          <dt>
            <span id="dateOfBirth">
              <Translate contentKey="hustpitalApp.doctors.dateOfBirth">Date Of Birth</Translate>
            </span>
          </dt>
          <dd>
            {doctorsEntity.dateOfBirth ? <TextFormat value={doctorsEntity.dateOfBirth} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="address">
              <Translate contentKey="hustpitalApp.doctors.address">Address</Translate>
            </span>
          </dt>
          <dd>{doctorsEntity.address}</dd>
          <dt>
            <span id="maSoThue">
              <Translate contentKey="hustpitalApp.doctors.maSoThue">Ma So Thue</Translate>
            </span>
          </dt>
          <dd>{doctorsEntity.maSoThue}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.doctors.user">User</Translate>
          </dt>
          <dd>{doctorsEntity.user ? doctorsEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.doctors.ethnic">Ethnic</Translate>
          </dt>
          <dd>{doctorsEntity.ethnic ? doctorsEntity.ethnic.name : ''}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.doctors.country">Country</Translate>
          </dt>
          <dd>{doctorsEntity.country ? doctorsEntity.country.name : ''}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.doctors.bank">Bank</Translate>
          </dt>
          <dd>{doctorsEntity.bank ? doctorsEntity.bank.name : ''}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.doctors.chuyenkhoa">Chuyenkhoa</Translate>
          </dt>
          <dd>{doctorsEntity.chuyenkhoa ? doctorsEntity.chuyenkhoa.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/doctors" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/doctors/${doctorsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DoctorsDetail;
