import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IThongTinVaoVien } from 'app/shared/model/thong-tin-vao-vien.model';
import { getEntities } from './thong-tin-vao-vien.reducer';

export const ThongTinVaoVien = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const thongTinVaoVienList = useAppSelector(state => state.thongTinVaoVien.entities);
  const loading = useAppSelector(state => state.thongTinVaoVien.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="thong-tin-vao-vien-heading" data-cy="ThongTinVaoVienHeading">
        <Translate contentKey="hustpitalApp.thongTinVaoVien.home.title">Thong Tin Vao Viens</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hustpitalApp.thongTinVaoVien.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/thong-tin-vao-vien/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hustpitalApp.thongTinVaoVien.home.createLabel">Create new Thong Tin Vao Vien</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {thongTinVaoVienList && thongTinVaoVienList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.ngayKham">Ngay Kham</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.tinhTrangVaoVien">Tinh Trang Vao Vien</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.soPhieu">So Phieu</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.maBVChuyenDen">Ma BV Chuyen Den</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.benhChuyenDen">Benh Chuyen Den</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.patient">Patient</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.phongkham">Phongkham</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {thongTinVaoVienList.map((thongTinVaoVien, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/thong-tin-vao-vien/${thongTinVaoVien.id}`} color="link" size="sm">
                      {thongTinVaoVien.id}
                    </Button>
                  </td>
                  <td>
                    {thongTinVaoVien.ngayKham ? <TextFormat type="date" value={thongTinVaoVien.ngayKham} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{thongTinVaoVien.tinhTrangVaoVien}</td>
                  <td>{thongTinVaoVien.soPhieu}</td>
                  <td>{thongTinVaoVien.maBVChuyenDen}</td>
                  <td>{thongTinVaoVien.benhChuyenDen}</td>
                  <td>
                    {thongTinVaoVien.patient ? (
                      <Link to={`/patients/${thongTinVaoVien.patient.id}`}>{thongTinVaoVien.patient.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thongTinVaoVien.phongkham ? (
                      <Link to={`/phong-kham/${thongTinVaoVien.phongkham.id}`}>{thongTinVaoVien.phongkham.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/thong-tin-vao-vien/${thongTinVaoVien.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/thong-tin-vao-vien/${thongTinVaoVien.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/thong-tin-vao-vien/${thongTinVaoVien.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="hustpitalApp.thongTinVaoVien.home.notFound">No Thong Tin Vao Viens found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ThongTinVaoVien;
