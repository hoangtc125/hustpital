import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Wards from './wards';
import WardsDetail from './wards-detail';
import WardsUpdate from './wards-update';
import WardsDeleteDialog from './wards-delete-dialog';

const WardsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Wards />} />
    <Route path="new" element={<WardsUpdate />} />
    <Route path=":id">
      <Route index element={<WardsDetail />} />
      <Route path="edit" element={<WardsUpdate />} />
      <Route path="delete" element={<WardsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WardsRoutes;
