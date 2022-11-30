import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBhyt } from 'app/shared/model/bhyt.model';
import { getEntities } from './bhyt.reducer';

export const Bhyt = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const bhytList = useAppSelector(state => state.bhyt.entities);
  const loading = useAppSelector(state => state.bhyt.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="bhyt-heading" data-cy="BhytHeading">
        <Translate contentKey="hustpitalApp.bhyt.home.title">Bhyts</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hustpitalApp.bhyt.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/bhyt/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hustpitalApp.bhyt.home.createLabel">Create new Bhyt</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {bhytList && bhytList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="hustpitalApp.bhyt.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.bhyt.qrcode">Qrcode</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.bhyt.sothe">Sothe</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.bhyt.maKCBBD">Ma KCBBD</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.bhyt.diachi">Diachi</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.bhyt.ngayBatDau">Ngay Bat Dau</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.bhyt.ngayKetThuc">Ngay Ket Thuc</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.bhyt.ngayBatDau5namLT">Ngay Bat Dau 5 Nam LT</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.bhyt.ngayBatDauMienCCT">Ngay Bat Dau Mien CCT</Translate>
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.bhyt.ngayKetThucMienCCT">Ngay Ket Thuc Mien CCT</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {bhytList.map((bhyt, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/bhyt/${bhyt.id}`} color="link" size="sm">
                      {bhyt.id}
                    </Button>
                  </td>
                  <td>{bhyt.qrcode}</td>
                  <td>{bhyt.sothe}</td>
                  <td>{bhyt.maKCBBD}</td>
                  <td>{bhyt.diachi}</td>
                  <td>{bhyt.ngayBatDau ? <TextFormat type="date" value={bhyt.ngayBatDau} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{bhyt.ngayKetThuc ? <TextFormat type="date" value={bhyt.ngayKetThuc} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {bhyt.ngayBatDau5namLT ? <TextFormat type="date" value={bhyt.ngayBatDau5namLT} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {bhyt.ngayBatDauMienCCT ? <TextFormat type="date" value={bhyt.ngayBatDauMienCCT} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {bhyt.ngayKetThucMienCCT ? <TextFormat type="date" value={bhyt.ngayKetThucMienCCT} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/bhyt/${bhyt.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/bhyt/${bhyt.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/bhyt/${bhyt.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="hustpitalApp.bhyt.home.notFound">No Bhyts found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Bhyt;
