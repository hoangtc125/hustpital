import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IJobs } from 'app/shared/model/jobs.model';
import { getEntities } from './jobs.reducer';

export const Jobs = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const jobsList = useAppSelector(state => state.jobs.entities);
  const loading = useAppSelector(state => state.jobs.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="jobs-heading" data-cy="JobsHeading">
        <Translate contentKey="hustpitalApp.jobs.home.title">Jobs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hustpitalApp.jobs.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/jobs/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hustpitalApp.jobs.home.createLabel">Create new Jobs</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {jobsList && jobsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hustpitalApp.jobs.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.jobs.name">Name</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {jobsList.map((jobs, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/jobs/${jobs.id}`} color="link" size="sm">
                      {jobs.id}
                    </Button>
                  </td>
                  <td>{jobs.name}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/jobs/${jobs.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/jobs/${jobs.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/jobs/${jobs.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hustpitalApp.jobs.home.notFound">No Jobs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Jobs;
