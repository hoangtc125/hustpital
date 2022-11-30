import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDistricts } from 'app/shared/model/districts.model';
import { getEntities } from './districts.reducer';

export const Districts = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const districtsList = useAppSelector(state => state.districts.entities);
  const loading = useAppSelector(state => state.districts.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="districts-heading" data-cy="DistrictsHeading">
        <Translate contentKey="hustpitalApp.districts.home.title">Districts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hustpitalApp.districts.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/districts/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hustpitalApp.districts.home.createLabel">Create new Districts</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {districtsList && districtsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hustpitalApp.districts.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.districts.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.districts.city">City</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {districtsList.map((districts, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/districts/${districts.id}`} color="link" size="sm">
                      {districts.id}
                    </Button>
                  </td>
                  <td>{districts.name}</td>
                  <td>{districts.city ? <Link to={`/cities/${districts.city.id}`}>{districts.city.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/districts/${districts.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/districts/${districts.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/districts/${districts.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hustpitalApp.districts.home.notFound">No Districts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Districts;
