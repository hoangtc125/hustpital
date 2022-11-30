import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './phong-kham.reducer';

export const PhongKhamDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const phongKhamEntity = useAppSelector(state => state.phongKham.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="phongKhamDetailsHeading">
          <Translate contentKey="hustpitalApp.phongKham.detail.title">PhongKham</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{phongKhamEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="hustpitalApp.phongKham.code">Code</Translate>
            </span>
          </dt>
          <dd>{phongKhamEntity.code}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="hustpitalApp.phongKham.name">Name</Translate>
            </span>
          </dt>
          <dd>{phongKhamEntity.name}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.phongKham.chuyenkhoa">Chuyenkhoa</Translate>
          </dt>
          <dd>{phongKhamEntity.chuyenkhoa ? phongKhamEntity.chuyenkhoa.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/phong-kham" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/phong-kham/${phongKhamEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PhongKhamDetail;
