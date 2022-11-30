import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cities.reducer';

export const CitiesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const citiesEntity = useAppSelector(state => state.cities.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="citiesDetailsHeading">
          <Translate contentKey="hustpitalApp.cities.detail.title">Cities</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{citiesEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hustpitalApp.cities.name">Name</Translate>
            </span>
          </dt>
          <dd>{citiesEntity.name}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.cities.country">Country</Translate>
          </dt>
          <dd>{citiesEntity.country ? citiesEntity.country.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/cities" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cities/${citiesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CitiesDetail;
