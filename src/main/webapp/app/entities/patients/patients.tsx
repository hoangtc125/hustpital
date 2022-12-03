import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPatients } from 'app/shared/model/patients.model';
import { getEntities } from './patients.reducer';

export const Patients = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const patientsList = useAppSelector(state => state.patients.entities);
  const loading = useAppSelector(state => state.patients.loading);
  const totalItems = useAppSelector(state => state.patients.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
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
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="hustpitalApp.patients.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="hustpitalApp.patients.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gender')}>
                  <Translate contentKey="hustpitalApp.patients.gender">Gender</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('address')}>
                  <Translate contentKey="hustpitalApp.patients.address">Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dateOfBirth')}>
                  <Translate contentKey="hustpitalApp.patients.dateOfBirth">Date Of Birth</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('phone')}>
                  <Translate contentKey="hustpitalApp.patients.phone">Phone</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('citizenIdentification')}>
                  <Translate contentKey="hustpitalApp.patients.citizenIdentification">Citizen Identification</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('maBHXH')}>
                  <Translate contentKey="hustpitalApp.patients.maBHXH">Ma BHXH</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('workPlace')}>
                  <Translate contentKey="hustpitalApp.patients.workPlace">Work Place</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('workAddress')}>
                  <Translate contentKey="hustpitalApp.patients.workAddress">Work Address</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('patientType')}>
                  <Translate contentKey="hustpitalApp.patients.patientType">Patient Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.country">Country</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.city">City</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.district">District</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.ward">Ward</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.ethnic">Ethnic</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.job">Job</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.patients.bHYT">B HYT</Translate> <FontAwesomeIcon icon="sort" />
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
                      <Button
                        tag={Link}
                        to={`/patients/${patients.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/patients/${patients.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="hustpitalApp.patients.home.notFound">No Patients found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={patientsList && patientsList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Patients;
