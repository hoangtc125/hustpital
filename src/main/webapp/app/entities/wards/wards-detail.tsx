import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wards.reducer';

export const WardsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const wardsEntity = useAppSelector(state => state.wards.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="wardsDetailsHeading">
          <Translate contentKey="hustpitalApp.wards.detail.title">Wards</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{wardsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hustpitalApp.wards.name">Name</Translate>
            </span>
          </dt>
          <dd>{wardsEntity.name}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.wards.district">District</Translate>
          </dt>
          <dd>{wardsEntity.district ? wardsEntity.district.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/wards" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wards/${wardsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WardsDetail;
