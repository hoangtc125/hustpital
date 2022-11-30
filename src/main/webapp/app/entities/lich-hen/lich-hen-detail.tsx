import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lich-hen.reducer';

export const LichHenDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const lichHenEntity = useAppSelector(state => state.lichHen.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lichHenDetailsHeading">
          <Translate contentKey="hustpitalApp.lichHen.detail.title">LichHen</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{lichHenEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hustpitalApp.lichHen.name">Name</Translate>
            </span>
          </dt>
          <dd>{lichHenEntity.name}</dd>
          <dt>
            <span id="phone">
              <Translate contentKey="hustpitalApp.lichHen.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{lichHenEntity.phone}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="hustpitalApp.lichHen.email">Email</Translate>
            </span>
          </dt>
          <dd>{lichHenEntity.email}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="hustpitalApp.lichHen.address">Address</Translate>
            </span>
          </dt>
          <dd>{lichHenEntity.address}</dd>
          <dt>
            <span id="lyDoKham">
              <Translate contentKey="hustpitalApp.lichHen.lyDoKham">Ly Do Kham</Translate>
            </span>
          </dt>
          <dd>{lichHenEntity.lyDoKham}</dd>
          <dt>
            <span id="dateOfBirth">
              <Translate contentKey="hustpitalApp.lichHen.dateOfBirth">Date Of Birth</Translate>
            </span>
          </dt>
          <dd>
            {lichHenEntity.dateOfBirth ? <TextFormat value={lichHenEntity.dateOfBirth} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lichhenType">
              <Translate contentKey="hustpitalApp.lichHen.lichhenType">Lichhen Type</Translate>
            </span>
          </dt>
          <dd>{lichHenEntity.lichhenType}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.lichHen.doctor">Doctor</Translate>
          </dt>
          <dd>{lichHenEntity.doctor ? lichHenEntity.doctor.name : ''}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.lichHen.user">User</Translate>
          </dt>
          <dd>{lichHenEntity.user ? lichHenEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/lich-hen" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lich-hen/${lichHenEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LichHenDetail;
