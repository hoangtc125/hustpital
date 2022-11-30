import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPatients } from 'app/shared/model/patients.model';
import { getEntities } from './patients.reducer';

export const Patients = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const patientsList = useAppSelector(state => state.patients.entities);
  const loading = useAppSelector(state => state.patients.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="patients-heading" data-cy="PatientsHeading">
        <Translate contentKey="hustpitalApp.patients.home.title">Patients</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hustpitalApp.patients.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/patients/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hustpitalApp.patients.home.createLabel">Create new Patients</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {patientsList && patientsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hustpitalApp.patients.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.gender">Gender</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.address">Address</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.dateOfBirth">Date Of Birth</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.phone">Phone</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.citizenIdentification">Citizen Identification</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.maBHXH">Ma BHXH</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.workPlace">Work Place</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.workAddress">Work Address</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.patientType">Patient Type</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.country">Country</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.city">City</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.district">District</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.ward">Ward</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.ethnic">Ethnic</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.job">Job</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.bHYT">B HYT</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {patientsList.map((patients, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/patients/${patients.id}`} color="link" size="sm">
                      {patients.id}
                    </Button>
                  </td>
                  <td>{patients.name}</td>
                  <td>
                    <Translate contentKey={`hustpitalApp.Gender.${patients.gender}`} />
                  </td>
                  <td>{patients.address}</td>
                  <td>{patients.dateOfBirth ? <TextFormat type="date" value={patients.dateOfBirth} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{patients.phone}</td>
                  <td>{patients.citizenIdentification}</td>
                  <td>{patients.maBHXH}</td>
                  <td>{patients.workPlace}</td>
                  <td>{patients.workAddress}</td>
                  <td>
                    <Translate contentKey={`hustpitalApp.BHYT.${patients.patientType}`} />
                  </td>
                  <td>{patients.country ? <Link to={`/countries/${patients.country.id}`}>{patients.country.name}</Link> : ''}</td>
                  <td>{patients.city ? <Link to={`/cities/${patients.city.id}`}>{patients.city.name}</Link> : ''}</td>
                  <td>{patients.district ? <Link to={`/districts/${patients.district.id}`}>{patients.district.name}</Link> : ''}</td>
                  <td>{patients.ward ? <Link to={`/wards/${patients.ward.id}`}>{patients.ward.name}</Link> : ''}</td>
                  <td>{patients.ethnic ? <Link to={`/ethnics/${patients.ethnic.id}`}>{patients.ethnic.name}</Link> : ''}</td>
                  <td>{patients.job ? <Link to={`/jobs/${patients.job.id}`}>{patients.job.name}</Link> : ''}</td>
                  <td>{patients.bHYT ? <Link to={`/bhyt/${patients.bHYT.id}`}>{patients.bHYT.sothe}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/patients/${patients.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/patients/${patients.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/patients/${patients.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hustpitalApp.patients.home.notFound">No Patients found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Patients;
