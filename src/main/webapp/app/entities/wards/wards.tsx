import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IWards } from 'app/shared/model/wards.model';
import { getEntities } from './wards.reducer';

export const Wards = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const wardsList = useAppSelector(state => state.wards.entities);
  const loading = useAppSelector(state => state.wards.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="wards-heading" data-cy="WardsHeading">
        <Translate contentKey="hustpitalApp.wards.home.title">Wards</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hustpitalApp.wards.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/wards/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hustpitalApp.wards.home.createLabel">Create new Wards</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {wardsList && wardsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hustpitalApp.wards.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.wards.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.wards.district">District</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {wardsList.map((wards, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/wards/${wards.id}`} color="link" size="sm">
                      {wards.id}
                    </Button>
                  </td>
                  <td>{wards.name}</td>
                  <td>{wards.district ? <Link to={`/districts/${wards.district.id}`}>{wards.district.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/wards/${wards.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/wards/${wards.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/wards/${wards.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hustpitalApp.wards.home.notFound">No Wards found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Wards;
