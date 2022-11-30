import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILichLamViec } from 'app/shared/model/lich-lam-viec.model';
import { getEntities } from './lich-lam-viec.reducer';

export const LichLamViec = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const lichLamViecList = useAppSelector(state => state.lichLamViec.entities);
  const loading = useAppSelector(state => state.lichLamViec.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="lich-lam-viec-heading" data-cy="LichLamViecHeading">
        <Translate contentKey="hustpitalApp.lichLamViec.home.title">Lich Lam Viecs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hustpitalApp.lichLamViec.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/lich-lam-viec/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hustpitalApp.lichLamViec.home.createLabel">Create new Lich Lam Viec</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {lichLamViecList && lichLamViecList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hustpitalApp.lichLamViec.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.lichLamViec.thu">Thu</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.lichLamViec.thoiGian">Thoi Gian</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.lichLamViec.doctor">Doctor</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {lichLamViecList.map((lichLamViec, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/lich-lam-viec/${lichLamViec.id}`} color="link" size="sm">
                      {lichLamViec.id}
                    </Button>
                  </td>
                  <td>{lichLamViec.thu}</td>
                  <td>{lichLamViec.thoiGian}</td>
                  <td>
                    {lichLamViec.doctors
                      ? lichLamViec.doctors.map((val, j) => (
                          <span key={j}>
                            <Link to={`/doctors/${val.id}`}>{val.id}</Link>
                            {j === lichLamViec.doctors.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/lich-lam-viec/${lichLamViec.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/lich-lam-viec/${lichLamViec.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/lich-lam-viec/${lichLamViec.id}/delete`}
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
              <Translate contentKey="hustpitalApp.lichLamViec.home.notFound">No Lich Lam Viecs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default LichLamViec;
