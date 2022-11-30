import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ThongTinVaoVien from './thong-tin-vao-vien';
import ThongTinVaoVienDetail from './thong-tin-vao-vien-detail';
import ThongTinVaoVienUpdate from './thong-tin-vao-vien-update';
import ThongTinVaoVienDeleteDialog from './thong-tin-vao-vien-delete-dialog';

const ThongTinVaoVienRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ThongTinVaoVien />} />
    <Route path="new" element={<ThongTinVaoVienUpdate />} />
    <Route path=":id">
      <Route index element={<ThongTinVaoVienDetail />} />
      <Route path="edit" element={<ThongTinVaoVienUpdate />} />
      <Route path="delete" element={<ThongTinVaoVienDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ThongTinVaoVienRoutes;
