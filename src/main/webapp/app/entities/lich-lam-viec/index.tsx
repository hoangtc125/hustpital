import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LichLamViec from './lich-lam-viec';
import LichLamViecDetail from './lich-lam-viec-detail';
import LichLamViecUpdate from './lich-lam-viec-update';
import LichLamViecDeleteDialog from './lich-lam-viec-delete-dialog';

const LichLamViecRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LichLamViec />} />
    <Route path="new" element={<LichLamViecUpdate />} />
    <Route path=":id">
      <Route index element={<LichLamViecDetail />} />
      <Route path="edit" element={<LichLamViecUpdate />} />
      <Route path="delete" element={<LichLamViecDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LichLamViecRoutes;
