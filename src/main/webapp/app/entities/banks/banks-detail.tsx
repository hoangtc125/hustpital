import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './banks.reducer';

export const BanksDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const banksEntity = useAppSelector(state => state.banks.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="banksDetailsHeading">
          <Translate contentKey="hustpitalApp.banks.detail.title">Banks</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{banksEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="hustpitalApp.banks.code">Code</Translate>
            </span>
          </dt>
          <dd>{banksEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hustpitalApp.banks.name">Name</Translate>
            </span>
          </dt>
          <dd>{banksEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/banks" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/banks/${banksEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BanksDetail;
