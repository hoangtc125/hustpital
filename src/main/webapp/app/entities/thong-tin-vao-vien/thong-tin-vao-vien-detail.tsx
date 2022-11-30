import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './thong-tin-vao-vien.reducer';

export const ThongTinVaoVienDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const thongTinVaoVienEntity = useAppSelector(state => state.thongTinVaoVien.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="thongTinVaoVienDetailsHeading">
          <Translate contentKey="hustpitalApp.thongTinVaoVien.detail.title">ThongTinVaoVien</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{thongTinVaoVienEntity.id}</dd>
          <dt>
            <span id="ngayKham">
              <Translate contentKey="hustpitalApp.thongTinVaoVien.ngayKham">Ngay Kham</Translate>
            </span>
          </dt>
          <dd>
            {thongTinVaoVienEntity.ngayKham ? (
              <TextFormat value={thongTinVaoVienEntity.ngayKham} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tinhTrangVaoVien">
              <Translate contentKey="hustpitalApp.thongTinVaoVien.tinhTrangVaoVien">Tinh Trang Vao Vien</Translate>
            </span>
          </dt>
          <dd>{thongTinVaoVienEntity.tinhTrangVaoVien}</dd>
          <dt>
            <span id="soPhieu">
              <Translate contentKey="hustpitalApp.thongTinVaoVien.soPhieu">So Phieu</Translate>
            </span>
          </dt>
          <dd>{thongTinVaoVienEntity.soPhieu}</dd>
          <dt>
            <span id="maBVChuyenDen">
              <Translate contentKey="hustpitalApp.thongTinVaoVien.maBVChuyenDen">Ma BV Chuyen Den</Translate>
            </span>
          </dt>
          <dd>{thongTinVaoVienEntity.maBVChuyenDen}</dd>
          <dt>
            <span id="benhChuyenDen">
              <Translate contentKey="hustpitalApp.thongTinVaoVien.benhChuyenDen">Benh Chuyen Den</Translate>
            </span>
          </dt>
          <dd>{thongTinVaoVienEntity.benhChuyenDen}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.thongTinVaoVien.patient">Patient</Translate>
          </dt>
          <dd>{thongTinVaoVienEntity.patient ? thongTinVaoVienEntity.patient.id : ''}</dd>
          <dt>
            <Translate contentKey="hustpitalApp.thongTinVaoVien.phongkham">Phongkham</Translate>
          </dt>
          <dd>{thongTinVaoVienEntity.phongkham ? thongTinVaoVienEntity.phongkham.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/thong-tin-vao-vien" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/thong-tin-vao-vien/${thongTinVaoVienEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ThongTinVaoVienDetail;
