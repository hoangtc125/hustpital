import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './districts.reducer';

export const DistrictsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const districtsEntity = useAppSelector(state => state.districts.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="districtsDetailsHeading">
          <Translate contentKey="hustpitalApp.districts.detail.title">Districts</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{districtsEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hustpitalApp.districts.name">Name</Translate>
            </span>
          </dt>
          <dd>{districtsEntity.name}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.districts.city">City</Translate>
          </dt>
          <dd>{districtsEntity.city ? districtsEntity.city.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/districts" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/districts/${districtsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DistrictsDetail;
