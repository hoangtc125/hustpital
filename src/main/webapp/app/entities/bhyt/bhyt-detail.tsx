import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './bhyt.reducer';

export const BhytDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bhytEntity = useAppSelector(state => state.bhyt.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bhytDetailsHeading">
          <Translate contentKey="hustpitalApp.bhyt.detail.title">Bhyt</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bhytEntity.id}</dd>
          <dt>
            <span id="qrcode">
              <Translate contentKey="hustpitalApp.bhyt.qrcode">Qrcode</Translate>
            </span>
          </dt>
          <dd>{bhytEntity.qrcode}</dd>
          <dt>
            <span id="sothe">
              <Translate contentKey="hustpitalApp.bhyt.sothe">Sothe</Translate>
            </span>
          </dt>
          <dd>{bhytEntity.sothe}</dd>
          <dt>
            <span id="maKCBBD">
              <Translate contentKey="hustpitalApp.bhyt.maKCBBD">Ma KCBBD</Translate>
            </span>
          </dt>
          <dd>{bhytEntity.maKCBBD}</dd>
          <dt>
            <span id="diachi">
              <Translate contentKey="hustpitalApp.bhyt.diachi">Diachi</Translate>
            </span>
          </dt>
          <dd>{bhytEntity.diachi}</dd>
          <dt>
            <span id="ngayBatDau">
              <Translate contentKey="hustpitalApp.bhyt.ngayBatDau">Ngay Bat Dau</Translate>
            </span>
          </dt>
          <dd>{bhytEntity.ngayBatDau ? <TextFormat value={bhytEntity.ngayBatDau} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="ngayKetThuc">
              <Translate contentKey="hustpitalApp.bhyt.ngayKetThuc">Ngay Ket Thuc</Translate>
            </span>
          </dt>
          <dd>{bhytEntity.ngayKetThuc ? <TextFormat value={bhytEntity.ngayKetThuc} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="ngayBatDau5namLT">
              <Translate contentKey="hustpitalApp.bhyt.ngayBatDau5namLT">Ngay Bat Dau 5 Nam LT</Translate>
            </span>
          </dt>
          <dd>
            {bhytEntity.ngayBatDau5namLT ? <TextFormat value={bhytEntity.ngayBatDau5namLT} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="ngayBatDauMienCCT">
              <Translate contentKey="hustpitalApp.bhyt.ngayBatDauMienCCT">Ngay Bat Dau Mien CCT</Translate>
            </span>
          </dt>
          <dd>
            {bhytEntity.ngayBatDauMienCCT ? <TextFormat value={bhytEntity.ngayBatDauMienCCT} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="ngayKetThucMienCCT">
              <Translate contentKey="hustpitalApp.bhyt.ngayKetThucMienCCT">Ngay Ket Thuc Mien CCT</Translate>
            </span>
          </dt>
          <dd>
            {bhytEntity.ngayKetThucMienCCT ? (
              <TextFormat value={bhytEntity.ngayKetThucMienCCT} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/bhyt" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bhyt/${bhytEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BhytDetail;
