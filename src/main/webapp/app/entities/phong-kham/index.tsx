import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PhongKham from './phong-kham';
import PhongKhamDetail from './phong-kham-detail';
import PhongKhamUpdate from './phong-kham-update';
import PhongKhamDeleteDialog from './phong-kham-delete-dialog';

const PhongKhamRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PhongKham />} />
    <Route path="new" element={<PhongKhamUpdate />} />
    <Route path=":id">
      <Route index element={<PhongKhamDetail />} />
      <Route path="edit" element={<PhongKhamUpdate />} />
      <Route path="delete" element={<PhongKhamDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PhongKhamRoutes;
