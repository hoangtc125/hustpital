import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './lich-lam-viec.reducer';

export const LichLamViecDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const lichLamViecEntity = useAppSelector(state => state.lichLamViec.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="lichLamViecDetailsHeading">
          <Translate contentKey="hustpitalApp.lichLamViec.detail.title">LichLamViec</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{lichLamViecEntity.id}</dd>
          <dt>
            <span id="thu">
              <Translate contentKey="hustpitalApp.lichLamViec.thu">Thu</Translate>
            </span>
          </dt>
          <dd>{lichLamViecEntity.thu}</dd>
          <dt>
            <span id="thoiGian">
              <Translate contentKey="hustpitalApp.lichLamViec.thoiGian">Thoi Gian</Translate>
            </span>
          </dt>
          <dd>{lichLamViecEntity.thoiGian}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.lichLamViec.doctor">Doctor</Translate>
          </dt>
          <dd>
            {lichLamViecEntity.doctors
              ? lichLamViecEntity.doctors.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {lichLamViecEntity.doctors && i === lichLamViecEntity.doctors.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/lich-lam-viec" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/lich-lam-viec/${lichLamViecEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LichLamViecDetail;
