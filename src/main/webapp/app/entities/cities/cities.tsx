import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICities } from 'app/shared/model/cities.model';
import { getEntities } from './cities.reducer';

export const Cities = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const citiesList = useAppSelector(state => state.cities.entities);
  const loading = useAppSelector(state => state.cities.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="cities-heading" data-cy="CitiesHeading">
        <Translate contentKey="hustpitalApp.cities.home.title">Cities</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hustpitalApp.cities.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/cities/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hustpitalApp.cities.home.createLabel">Create new Cities</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {citiesList && citiesList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hustpitalApp.cities.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.cities.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.cities.country">Country</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {citiesList.map((cities, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/cities/${cities.id}`} color="link" size="sm">
                      {cities.id}
                    </Button>
                  </td>
                  <td>{cities.name}</td>
                  <td>{cities.country ? <Link to={`/countries/${cities.country.id}`}>{cities.country.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/cities/${cities.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/cities/${cities.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/cities/${cities.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hustpitalApp.cities.home.notFound">No Cities found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Cities;
