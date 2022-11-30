import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDoctors } from 'app/shared/model/doctors.model';
import { getEntities } from './doctors.reducer';

export const Doctors = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const doctorsList = useAppSelector(state => state.doctors.entities);
  const loading = useAppSelector(state => state.doctors.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="doctors-heading" data-cy="DoctorsHeading">
        <Translate contentKey="hustpitalApp.doctors.home.title">Doctors</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hustpitalApp.doctors.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/doctors/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hustpitalApp.doctors.home.createLabel">Create new Doctors</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {doctorsList && doctorsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hustpitalApp.doctors.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.doctors.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.doctors.phone">Phone</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.doctors.citizenIdentification">Citizen Identification</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.doctors.maBHXH">Ma BHXH</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.doctors.gender">Gender</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.doctors.dateOfBirth">Date Of Birth</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.doctors.address">Address</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.doctors.maSoThue">Ma So Thue</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.doctors.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.doctors.ethnic">Ethnic</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.doctors.country">Country</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.doctors.bank">Bank</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.doctors.chuyenkhoa">Chuyenkhoa</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {doctorsList.map((doctors, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/doctors/${doctors.id}`} color="link" size="sm">
                      {doctors.id}
                    </Button>
                  </td>
                  <td>{doctors.name}</td>
                  <td>{doctors.phone}</td>
                  <td>{doctors.citizenIdentification}</td>
                  <td>{doctors.maBHXH}</td>
                  <td>
                    <Translate contentKey={`hustpitalApp.Gender.${doctors.gender}`} />
                  </td>
                  <td>{doctors.dateOfBirth ? <TextFormat type="date" value={doctors.dateOfBirth} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{doctors.address}</td>
                  <td>{doctors.maSoThue}</td>
                  <td>{doctors.user ? doctors.user.login : ''}</td>
                  <td>{doctors.ethnic ? <Link to={`/ethnics/${doctors.ethnic.id}`}>{doctors.ethnic.name}</Link> : ''}</td>
                  <td>{doctors.country ? <Link to={`/countries/${doctors.country.id}`}>{doctors.country.name}</Link> : ''}</td>
                  <td>{doctors.bank ? <Link to={`/banks/${doctors.bank.id}`}>{doctors.bank.name}</Link> : ''}</td>
                  <td>{doctors.chuyenkhoa ? <Link to={`/chuyen-khoa/${doctors.chuyenkhoa.id}`}>{doctors.chuyenkhoa.name}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/doctors/${doctors.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/doctors/${doctors.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/doctors/${doctors.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hustpitalApp.doctors.home.notFound">No Doctors found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Doctors;
