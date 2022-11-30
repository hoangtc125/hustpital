import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ChuyenKhoa from './chuyen-khoa';
import ChuyenKhoaDetail from './chuyen-khoa-detail';
import ChuyenKhoaUpdate from './chuyen-khoa-update';
import ChuyenKhoaDeleteDialog from './chuyen-khoa-delete-dialog';

const ChuyenKhoaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ChuyenKhoa />} />
    <Route path="new" element={<ChuyenKhoaUpdate />} />
    <Route path=":id">
      <Route index element={<ChuyenKhoaDetail />} />
      <Route path="edit" element={<ChuyenKhoaUpdate />} />
      <Route path="delete" element={<ChuyenKhoaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ChuyenKhoaRoutes;
