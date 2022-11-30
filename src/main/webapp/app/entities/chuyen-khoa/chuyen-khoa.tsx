import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IChuyenKhoa } from 'app/shared/model/chuyen-khoa.model';
import { getEntities } from './chuyen-khoa.reducer';

export const ChuyenKhoa = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const chuyenKhoaList = useAppSelector(state => state.chuyenKhoa.entities);
  const loading = useAppSelector(state => state.chuyenKhoa.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="chuyen-khoa-heading" data-cy="ChuyenKhoaHeading">
        <Translate contentKey="hustpitalApp.chuyenKhoa.home.title">Chuyen Khoas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hustpitalApp.chuyenKhoa.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/chuyen-khoa/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hustpitalApp.chuyenKhoa.home.createLabel">Create new Chuyen Khoa</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {chuyenKhoaList && chuyenKhoaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hustpitalApp.chuyenKhoa.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.chuyenKhoa.code">Code</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.chuyenKhoa.name">Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {chuyenKhoaList.map((chuyenKhoa, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/chuyen-khoa/${chuyenKhoa.id}`} color="link" size="sm">
                      {chuyenKhoa.id}
                    </Button>
                  </td>
                  <td>{chuyenKhoa.code}</td>
                  <td>{chuyenKhoa.name}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/chuyen-khoa/${chuyenKhoa.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/chuyen-khoa/${chuyenKhoa.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/chuyen-khoa/${chuyenKhoa.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hustpitalApp.chuyenKhoa.home.notFound">No Chuyen Khoas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ChuyenKhoa;
