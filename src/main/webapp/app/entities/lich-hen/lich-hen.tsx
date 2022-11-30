import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILichHen } from 'app/shared/model/lich-hen.model';
import { getEntities } from './lich-hen.reducer';

export const LichHen = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const lichHenList = useAppSelector(state => state.lichHen.entities);
  const loading = useAppSelector(state => state.lichHen.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="lich-hen-heading" data-cy="LichHenHeading">
        <Translate contentKey="hustpitalApp.lichHen.home.title">Lich Hens</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hustpitalApp.lichHen.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/lich-hen/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hustpitalApp.lichHen.home.createLabel">Create new Lich Hen</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {lichHenList && lichHenList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hustpitalApp.lichHen.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.lichHen.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.lichHen.phone">Phone</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.lichHen.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.lichHen.address">Address</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.lichHen.lyDoKham">Ly Do Kham</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.lichHen.dateOfBirth">Date Of Birth</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.lichHen.lichhenType">Lichhen Type</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.lichHen.doctor">Doctor</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.lichHen.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {lichHenList.map((lichHen, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/lich-hen/${lichHen.id}`} color="link" size="sm">
                      {lichHen.id}
                    </Button>
                  </td>
                  <td>{lichHen.name}</td>
                  <td>{lichHen.phone}</td>
                  <td>{lichHen.email}</td>
                  <td>{lichHen.address}</td>
                  <td>{lichHen.lyDoKham}</td>
                  <td>{lichHen.dateOfBirth ? <TextFormat type="date" value={lichHen.dateOfBirth} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    <Translate contentKey={`hustpitalApp.Admission.${lichHen.lichhenType}`} />
                  </td>
                  <td>{lichHen.doctor ? <Link to={`/doctors/${lichHen.doctor.id}`}>{lichHen.doctor.name}</Link> : ''}</td>
                  <td>{lichHen.user ? lichHen.user.id : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/lich-hen/${lichHen.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/lich-hen/${lichHen.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/lich-hen/${lichHen.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hustpitalApp.lichHen.home.notFound">No Lich Hens found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default LichHen;
