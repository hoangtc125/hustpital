import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IThongTinVaoVien } from 'app/shared/model/thong-tin-vao-vien.model';
import { getEntities } from './thong-tin-vao-vien.reducer';

export const ThongTinVaoVien = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const thongTinVaoVienList = useAppSelector(state => state.thongTinVaoVien.entities);
  const loading = useAppSelector(state => state.thongTinVaoVien.loading);
  const totalItems = useAppSelector(state => state.thongTinVaoVien.totalItems);

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
      <h2 id="thong-tin-vao-vien-heading" data-cy="ThongTinVaoVienHeading">
        <Translate contentKey="hustpitalApp.thongTinVaoVien.home.title">Thong Tin Vao Viens</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="hustpitalApp.thongTinVaoVien.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/thong-tin-vao-vien/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="hustpitalApp.thongTinVaoVien.home.createLabel">Create new Thong Tin Vao Vien</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {thongTinVaoVienList && thongTinVaoVienList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ngayKham')}>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.ngayKham">Ngay Kham</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tinhTrangVaoVien')}>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.tinhTrangVaoVien">Tinh Trang Vao Vien</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('soPhieu')}>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.soPhieu">So Phieu</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('maBVChuyenDen')}>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.maBVChuyenDen">Ma BV Chuyen Den</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('benhChuyenDen')}>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.benhChuyenDen">Benh Chuyen Den</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.patient">Patient</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="hustpitalApp.thongTinVaoVien.phongkham">Phongkham</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {thongTinVaoVienList.map((thongTinVaoVien, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/thong-tin-vao-vien/${thongTinVaoVien.id}`} color="link" size="sm">
                      {thongTinVaoVien.id}
                    </Button>
                  </td>
                  <td>
                    {thongTinVaoVien.ngayKham ? <TextFormat type="date" value={thongTinVaoVien.ngayKham} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{thongTinVaoVien.tinhTrangVaoVien}</td>
                  <td>{thongTinVaoVien.soPhieu}</td>
                  <td>{thongTinVaoVien.maBVChuyenDen}</td>
                  <td>{thongTinVaoVien.benhChuyenDen}</td>
                  <td>
                    {thongTinVaoVien.patient ? (
                      <Link to={`/patients/${thongTinVaoVien.patient.id}`}>{thongTinVaoVien.patient.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {thongTinVaoVien.phongkham ? (
                      <Link to={`/phong-kham/${thongTinVaoVien.phongkham.id}`}>{thongTinVaoVien.phongkham.name}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/thong-tin-vao-vien/${thongTinVaoVien.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/thong-tin-vao-vien/${thongTinVaoVien.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/thong-tin-vao-vien/${thongTinVaoVien.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="hustpitalApp.thongTinVaoVien.home.notFound">No Thong Tin Vao Viens found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={thongTinVaoVienList && thongTinVaoVienList.length > 0 ? '' : 'd-none'}>
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

export default ThongTinVaoVien;
